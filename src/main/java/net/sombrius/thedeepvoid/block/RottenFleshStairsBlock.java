
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class RottenFleshStairsBlock extends StairBlock {
	public RottenFleshStairsBlock() {
		super(Blocks.AIR.defaultBlockState(),
				BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_ORANGE)
						.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.break")),
								() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")),
								() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.hit")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.fall"))))
						.strength(0.4f, 1f).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 1f;
	}
}
