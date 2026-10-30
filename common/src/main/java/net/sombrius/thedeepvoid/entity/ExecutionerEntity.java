
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.ChainedWeaverSolidBoundingBoxConditionProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerConditionsProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerEntityIsHurtProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerOnInitialEntitySpawnProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ExecutionerEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_attacking = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_grabbed = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_stunCooldown = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_stunned = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_asleep = SynchedEntityData.defineId(ExecutionerEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public ExecutionerEntity(EntityType<ExecutionerEntity> type, Level world) {
		super(type, world);
		xpReward = 100;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "executioner");
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_attacking, false);
		builder.define(DATA_grabbed, false);
		builder.define(DATA_stunCooldown, 300);
		builder.define(DATA_stunned, false);
		builder.define(DATA_asleep, true);
	}

	public void setTexture(String texture) {
		this.entityData.set(TEXTURE, texture);
	}

	public String getTexture() {
		return this.entityData.get(TEXTURE);
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return true;
	}

	@Override
	public boolean canBeCollidedWith(@javax.annotation.Nullable net.minecraft.world.entity.Entity other) {
		Entity entity = this;
		Level world = entity.level();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return ChainedWeaverSolidBoundingBoxConditionProcedure.execute();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1) {
			@Override
			public boolean canUse() {
				double x = ExecutionerEntity.this.getX();
				double y = ExecutionerEntity.this.getY();
				double z = ExecutionerEntity.this.getZ();
				Entity entity = ExecutionerEntity.this;
				Level world = ExecutionerEntity.this.level();
				return super.canUse() && ExecutionerConditionsProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ExecutionerEntity.this.getX();
				double y = ExecutionerEntity.this.getY();
				double z = ExecutionerEntity.this.getZ();
				Entity entity = ExecutionerEntity.this;
				Level world = ExecutionerEntity.this.level();
				return super.canContinueToUse() && ExecutionerConditionsProcedure.execute(entity);
			}
		});
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				double x = ExecutionerEntity.this.getX();
				double y = ExecutionerEntity.this.getY();
				double z = ExecutionerEntity.this.getZ();
				Entity entity = ExecutionerEntity.this;
				Level world = ExecutionerEntity.this.level();
				return super.canUse() && ExecutionerConditionsProcedure.execute(entity);
			}

			@Override
			public boolean canContinueToUse() {
				double x = ExecutionerEntity.this.getX();
				double y = ExecutionerEntity.this.getY();
				double z = ExecutionerEntity.this.getZ();
				Entity entity = ExecutionerEntity.this;
				Level world = ExecutionerEntity.this.level();
				return super.canContinueToUse() && ExecutionerConditionsProcedure.execute(entity);
			}
		});
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
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
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		ExecutionerEntityIsHurtProcedure.execute(this.level(), this, source.getEntity());
		Entity immediatesourceentity = source.getDirectEntity();
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.getDirectEntity() instanceof AbstractArrow)
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.PLAYER_EXPLOSION))
			return false;
		if (source.is(DamageTypes.TRIDENT))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		if (source.is(DamageTypes.DRAGON_BREATH))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		ExecutionerOnInitialEntitySpawnProcedure.execute(this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putBoolean("Dataattacking", this.entityData.get(DATA_attacking));
		compound.putBoolean("Datagrabbed", this.entityData.get(DATA_grabbed));
		compound.putInt("DatastunCooldown", this.entityData.get(DATA_stunCooldown));
		compound.putBoolean("Datastunned", this.entityData.get(DATA_stunned));
		compound.putBoolean("Dataasleep", this.entityData.get(DATA_asleep));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataattacking"))
			this.entityData.set(DATA_attacking, compound.getBooleanOr("Dataattacking", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datagrabbed"))
			this.entityData.set(DATA_grabbed, compound.getBooleanOr("Datagrabbed", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatastunCooldown"))
			this.entityData.set(DATA_stunCooldown, compound.getIntOr("DatastunCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datastunned"))
			this.entityData.set(DATA_stunned, compound.getBooleanOr("Datastunned", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataasleep"))
			this.entityData.set(DATA_asleep, compound.getBooleanOr("Dataasleep", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ExecutionerOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 350);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 99);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<ExecutionerEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.executioner_walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.executioner_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<ExecutionerEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(ExecutionerEntity.RemovalReason.KILLED);
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
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("movement", 4, this::movementPredicate));
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
