
package net.sombrius.thedeepvoid.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.sombrius.thedeepvoid.port.neo.DeferredSoundType;

public class PustulentFleshBrickStairsBlock extends StairBlock {
	public PustulentFleshBrickStairsBlock() {
		super(Blocks.AIR.defaultBlockState(), BlockBehaviour.Properties.of()
				.sound(new DeferredSoundType(1.0f, 1.0f, () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.break")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_step")),
						() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("the_deep_void:flesh_block_place")), () -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.slime_block.hit")),
						() -> BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("block.wet_grass.fall"))))
				.strength(1f, 4f).friction(0.7f).dynamicShape());
	}

	@Override
	public float getExplosionResistance() {
		return 4f;
	}
}
