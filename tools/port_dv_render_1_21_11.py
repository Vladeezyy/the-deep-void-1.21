#!/usr/bin/env python3
"""1.21.1 -> 1.21.11, the mod's vanilla entity rendering (idempotent; repo root; after tools/port_1_21_11.py):
- the Blockbench models (client/model): 1.21.2 EntityModel over a render state, the root renders itself, setupAnim
  reads its old values from the state (port.PortRender)
- the renderers of the three MCreator templates (client/renderer, the ones without GeckoLib): mobs, humanoid mobs and
  projectiles get a render state and submit their models (1.21.9)"""
import glob
import re

ROOT = 'common/src/main/java/net/sombrius/thedeepvoid/'
PKG = 'net.sombrius.thedeepvoid'
STATE = 'net.minecraft.client.renderer.entity.state'


def add_import(s, imp):
    if ('import ' + imp + ';') in s:
        return s
    return re.sub(r'^(package [\w.]+;\n)', r'\1\nimport ' + imp + ';', s, count=1, flags=re.M)


def method_end(s, open_brace):
    depth, i = 0, open_brace
    while True:
        depth += {'{': 1, '}': -1}.get(s[i], 0)
        if depth == 0:
            return i
        i += 1


def drop_method(s, header_regex):
    m = re.search(r'\n(\t@Override\n)?\t' + header_regex + r'[^{]*\{', s)
    if not m:
        return s
    end = method_end(s, m.end() - 1)
    return s[:m.start()] + s[end + 1:]


def model(s):
    if f'{STATE}.EntityRenderState' in s or not re.search(r'extends EntityModel<', s):
        return s
    s = re.sub(r'public class (\w+)<T extends Entity> extends EntityModel<T>', rf'public class \1<T extends {STATE}.EntityRenderState> extends EntityModel<T>', s)
    s = re.sub(r'public class (\w+) extends EntityModel<Entity>', rf'public class \1<T extends {STATE}.EntityRenderState> extends EntityModel<T>', s)
    s = re.sub(r'(public (\w+)\(ModelPart root\) \{)', r'\1\n\t\tsuper(root);', s)
    s = drop_method(s, r'public void renderToBuffer\(')
    s = re.sub(r'public void setupAnim\((?:T|Entity) entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch\) \{',
               'public void setupAnim(T state) {\n\t\tsuper.setupAnim(state);\n'
               f'\t\tfloat limbSwing = {PKG}.port.PortRender.limbSwing(state);\n\t\tfloat limbSwingAmount = {PKG}.port.PortRender.limbSwingAmount(state);\n'
               f'\t\tfloat ageInTicks = state.ageInTicks;\n\t\tfloat netHeadYaw = {PKG}.port.PortRender.netHeadYaw(state);\n\t\tfloat headPitch = {PKG}.port.PortRender.headPitch(state);', s)
    return s


def layer(m, state, model_type):
    texture, rtype, model_cls = m.group('tex'), m.group('rtype'), m.group('model')
    return (f'new RenderLayer<{state}, {model_type}>(this) {{\n'
            f'\t\t\tfinal Identifier LAYER_TEXTURE = {texture};\n'
            f'\t\t\tfinal {model_cls}<{state}> layerModel = new {model_cls}<>(context.bakeLayer({model_cls}.LAYER_LOCATION));\n\n'
            f'\t\t\t@Override\n'
            f'\t\t\tpublic void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, {state} state, float yRot, float xRot) {{\n'
            f'\t\t\t\tthis.layerModel.setupAnim(state);\n'
            f'\t\t\t\tcollector.submitModel(this.layerModel, state, poseStack, RenderTypes.{rtype}(LAYER_TEXTURE), light, LivingEntityRenderer.getOverlayCoords(state, 0), state.outlineColor, null);\n'
            f'\t\t\t}}\n\t\t}}')


LAYER = re.compile(r'new RenderLayer<\w+, (?:\w+<\w+>|\w+)>\(this\) \{\n\t\t\tfinal Identifier LAYER_TEXTURE = (?P<tex>[^;]+);\n.*?getBuffer\(RenderType\.(?P<rtype>\w+)\(LAYER_TEXTURE\)\);\n'
                   r'\t\t\t\tEntityModel model = new (?P<model>\w+)\(.*?\n\t\t\t\}\n\t\t\}', re.S)


def living(s, kind):
    state = {'MobRenderer': 'LivingEntityRenderState', 'HumanoidMobRenderer': 'HumanoidRenderState'}[kind]
    m = re.search(r'extends ' + kind + r'<(\w+), (\w+)<\1>>', s)
    if not m:
        return s
    entity, model_cls = m.group(1), m.group(2)
    model_type = f'{model_cls}<{state}>'
    s = s.replace(m.group(0), f'extends {kind}<{entity}, {state}, {model_type}>')
    s = LAYER.sub(lambda x: layer(x, state, x.group('model') + f'<{state}>' if x.group('model') == model_cls else model_type), s)
    s = re.sub(r'public Identifier getTextureLocation\(' + entity + r' (\w+)\)', f'public Identifier getTextureLocation({state} state)', s)
    s = re.sub(r'(\n\t@Override\n\tpublic Identifier getTextureLocation)', f'\n\t@Override\n\tpublic {state} createRenderState() {{\n\t\treturn new {state}();\n\t}}\n\\1', s, count=1)
    if kind == 'HumanoidMobRenderer':
        s = re.sub(r'new HumanoidArmorLayer\(this, new HumanoidModel\(context\.bakeLayer\(ModelLayers\.PLAYER_INNER_ARMOR\)\), new HumanoidModel\(context\.bakeLayer\(ModelLayers\.PLAYER_OUTER_ARMOR\)\), context\.getModelManager\(\)\)',
                   'new HumanoidArmorLayer<>(this, ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer())', s)
        s = add_import(s, 'net.minecraft.client.renderer.entity.ArmorModelSet')
    for imp in (f'{STATE}.{state}', 'net.minecraft.client.renderer.SubmitNodeCollector', 'net.minecraft.client.renderer.rendertype.RenderTypes',
                'net.minecraft.client.renderer.entity.LivingEntityRenderer'):
        s = add_import(s, imp)
    return s


def projectile(s):
    m = re.search(r'extends EntityRenderer<(\w+)>', s)
    if not m:
        return s
    entity = m.group(1)
    state = f'{PKG}.port.PortRender.RotatedState'
    s = s.replace(m.group(0), f'extends EntityRenderer<{entity}, {state}>')
    r = re.search(r'\n\t@Override\n\tpublic void render\(' + entity + r' entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn\) \{\n', s)
    end = method_end(s, r.end() - 2)
    body = s[r.end():end]
    rtype = re.search(r'getBuffer\(RenderType\.(\w+)\(', body).group(1)
    body = re.sub(r'\t\tVertexConsumer vb = bufferIn\.getBuffer\([^\n]*\n', '', body)
    body = body.replace('Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot())', 'state.yRot').replace('Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())', 'state.xRot')
    body = re.sub(r'model\.renderToBuffer\(poseStack, vb, packedLightIn, OverlayTexture\.NO_OVERLAY, -1\);',
                  f'collector.submitModel(model, state, poseStack, RenderTypes.{rtype}(texture), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);', body)
    body = body.replace('super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);', 'super.submit(state, poseStack, collector, camera);')
    new = (f'\n\t@Override\n\tpublic {state} createRenderState() {{\n\t\treturn new {state}();\n\t}}\n\n'
           f'\t@Override\n\tpublic void extractRenderState({entity} entity, {state} state, float partialTicks) {{\n\t\tsuper.extractRenderState(entity, state, partialTicks);\n'
           f'\t\tstate.yRot = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());\n\t\tstate.xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());\n\t}}\n\n'
           f'\t@Override\n\tpublic void submit({state} state, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.CameraRenderState camera) {{\n'
           + body + '}')
    s = s[:r.start()] + new + s[end + 1:]
    s = re.sub(r'\n\t@Override\n(\tpublic Identifier getTextureLocation\()', r'\n\1', s)
    for imp in ('net.minecraft.client.renderer.SubmitNodeCollector', 'net.minecraft.client.renderer.rendertype.RenderTypes', 'net.minecraft.util.Mth'):
        s = add_import(s, imp)
    return s


def main():
    changed = 0
    for path in glob.glob(ROOT + 'client/model/*.java'):
        s = open(path).read()
        o = model(s)
        if o != s:
            open(path, 'w').write(o)
            changed += 1
    for path in glob.glob(ROOT + 'client/renderer/*.java'):
        s = open(path).read()
        if 'geckolib' in s or 'createRenderState' in s:
            continue
        o = living(s, 'MobRenderer') if 'extends MobRenderer<' in s else living(s, 'HumanoidMobRenderer') if 'extends HumanoidMobRenderer<' in s else projectile(s)
        if o != s:
            open(path, 'w').write(o)
            changed += 1
    print('changed', changed)


if __name__ == '__main__':
    main()
