package net.sombrius.thedeepvoid.port.bus;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.world.level.material.FogType;

public abstract class ViewportEvent extends Event {
	private final Camera camera;
	private final double partialTick;

	protected ViewportEvent(Camera camera, double partialTick) {
		this.camera = camera;
		this.partialTick = partialTick;
	}

	public Camera getCamera() {
		return this.camera;
	}

	public double getPartialTick() {
		return this.partialTick;
	}

	/** 1.21.6+ has one fog setup per frame (FogData); MCreator's procedures adjust the terrain fog */
	public enum FogMode {
		FOG_SKY, FOG_TERRAIN
	}

	/** 1.21.6+ fog is always spherical; MCreator's shape requests are kept for the procedures and ignored */
	public enum FogShape {
		SPHERE, CYLINDER
	}

	/**
	 * The fog distances of the frame (the loaders' fog setup); the procedures set the terrain (environmental) fog.
	 * Canceling, as MCreator did to apply the values, changes nothing: the values are applied anyway.
	 */
	public static class RenderFog extends ViewportEvent implements ICancellableEvent {
		private final FogType type;
		private final FogData data;
		private FogShape fogShape = FogShape.SPHERE;

		public RenderFog(Camera camera, double partialTick, FogType type, FogData data) {
			super(camera, partialTick);
			this.type = type;
			this.data = data;
		}

		public FogMode getMode() {
			return FogMode.FOG_TERRAIN;
		}

		public FogType getType() {
			return this.type;
		}

		public float getNearPlaneDistance() {
			return this.data.environmentalStart;
		}

		public void setNearPlaneDistance(float distance) {
			this.data.environmentalStart = distance;
		}

		public float getFarPlaneDistance() {
			return this.data.environmentalEnd;
		}

		/** The sky and the clouds fade within the terrain fog, as the foggy dimensions of 1.21.1 did */
		public void setFarPlaneDistance(float distance) {
			this.data.environmentalEnd = distance;
			this.data.skyEnd = Math.min(this.data.skyEnd, distance);
			this.data.cloudEnd = Math.min(this.data.cloudEnd, distance);
		}

		public FogShape getFogShape() {
			return this.fogShape;
		}

		public void setFogShape(FogShape shape) {
			this.fogShape = shape;
		}
	}

	public static class ComputeFogColor extends ViewportEvent {
		private float red;
		private float green;
		private float blue;

		public ComputeFogColor(Camera camera, double partialTick, float red, float green, float blue) {
			super(camera, partialTick);
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		public float getRed() {
			return this.red;
		}

		public void setRed(float red) {
			this.red = red;
		}

		public float getGreen() {
			return this.green;
		}

		public void setGreen(float green) {
			this.green = green;
		}

		public float getBlue() {
			return this.blue;
		}

		public void setBlue(float blue) {
			this.blue = blue;
		}
	}
}
