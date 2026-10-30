
package net.sombrius.thedeepvoid.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.sombrius.thedeepvoid.port.bus.Dist;
import net.sombrius.thedeepvoid.port.bus.OnlyIn;
import net.sombrius.thedeepvoid.procedures.ChainParticleVisualScaleProcedure;

@net.neoforged.api.distmarker.OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT) @net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class ConnectingDarkSteelChainParticle extends SingleQuadParticle {
	public static ConnectingDarkSteelChainParticleProvider provider(SpriteSet spriteSet) {
		return new ConnectingDarkSteelChainParticleProvider(spriteSet);
	}

	public static class ConnectingDarkSteelChainParticleProvider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet spriteSet;

		public ConnectingDarkSteelChainParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, net.minecraft.util.RandomSource random) {
			return new ConnectingDarkSteelChainParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected ConnectingDarkSteelChainParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z, spriteSet.first());
		this.spriteSet = spriteSet;
		this.setSize(0.2f, 0.2f);
		this.lifetime = 1;
		this.gravity = 0f;
		this.hasPhysics = true;
		this.xd = vx * 1;
		this.yd = vy * 1;
		this.zd = vz * 1;
		this.setSprite(spriteSet.get(this.random));
	}

	@Override
	public SingleQuadParticle.Layer getLayer() {
		return SingleQuadParticle.Layer.OPAQUE;
	}

	@Override
	public float getQuadSize(float scale) {
		Level world = this.level;
		return super.getQuadSize(scale) * (float) ChainParticleVisualScaleProcedure.execute();
	}

	@Override
	public void tick() {
		super.tick();
	}
}
