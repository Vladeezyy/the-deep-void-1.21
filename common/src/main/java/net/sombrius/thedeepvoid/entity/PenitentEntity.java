
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.PenitentAttackConditionProcedure;
import net.sombrius.thedeepvoid.procedures.PenitentDisableLookAroundProcedure;
import net.sombrius.thedeepvoid.procedures.PenitentNaturalEntitySpawningConditionProcedure;
import net.sombrius.thedeepvoid.procedures.PenitentOnEntityTickUpdateProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PenitentEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_pray = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_praying = SynchedEntityData.defineId(PenitentEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public PenitentEntity(EntityType<PenitentEntity> type, Level world) {
		super(type, world);
		xpReward = 10;
		setNoAi(false);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "penitent_and_shank");
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_pray, 0);
		builder.define(DATA_praying, false);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 6.25 && this.mob.getSensing().hasLineOfSight(entity);
			}

			@Override
			public boolean canUse() {
				double x = PenitentEntity.this.getX();
				double y = PenitentEntity.this.getY();
				double z = PenitentEntity.this.getZ();
				Entity entity = PenitentEntity.this;
				Level world = PenitentEntity.this.level();
				return super.canUse() && PenitentAttackConditionProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = PenitentEntity.this.getX();
				double y = PenitentEntity.this.getY();
				double z = PenitentEntity.this.getZ();
				Entity entity = PenitentEntity.this;
				Level world = PenitentEntity.this.level();
				return super.canContinueToUse() && PenitentAttackConditionProcedure.execute(entity);
			}

		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(3, new FollowMobGoal(this, 0.8, (float) 15, (float) 5));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = PenitentEntity.this.getX();
				double y = PenitentEntity.this.getY();
				double z = PenitentEntity.this.getZ();
				Entity entity = PenitentEntity.this;
				Level world = PenitentEntity.this.level();
				return super.canUse() && PenitentDisableLookAroundProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = PenitentEntity.this.getX();
				double y = PenitentEntity.this.getY();
				double z = PenitentEntity.this.getZ();
				Entity entity = PenitentEntity.this;
				Level world = PenitentEntity.this.level();
				return super.canContinueToUse() && PenitentDisableLookAroundProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Player.class, false, true));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.death"));
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putInt("Datapray", this.entityData.get(DATA_pray));
		compound.putBoolean("Datapraying", this.entityData.get(DATA_praying));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datapray"))
			this.entityData.set(DATA_pray, compound.getIntOr("Datapray", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datapraying"))
			this.entityData.set(DATA_praying, compound.getBooleanOr("Datapraying", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		PenitentOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(TheDeepVoidModEntities.PENITENT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return PenitentNaturalEntitySpawningConditionProcedure.execute(world, x, y, z);
		}, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 60);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 8);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 4);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<PenitentEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.penitent_walk"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.penitent_agressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.penitent_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<PenitentEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(PenitentEntity.RemovalReason.KILLED);
			if (this.level() instanceof ServerLevel _serverLevel)
				this.dropExperience(_serverLevel, null);
		}
	}

	public String getSyncedAnimation() {
		return this.entityData.get(ANIMATION);
	}

	public void setAnimation(String animation) {
		this.entityData.set(ANIMATION, animation);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar data) {
		data.add(new AnimationController<>("movement", 6, this::movementPredicate));
		data.add(new AnimationController<>("procedure", 6, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
