
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
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModItems;
import net.sombrius.thedeepvoid.procedures.StalkerBoundingBoxScaleProcedure;
import net.sombrius.thedeepvoid.procedures.StalkerEntityDiesProcedure;
import net.sombrius.thedeepvoid.procedures.StalkerOnInitialEntitySpawnProcedure;
import net.sombrius.thedeepvoid.procedures.StalkerSpawnsProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class StalkerEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_voidCallCooldown = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_spawned = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_heartbeat = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_crouching = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_soundAngry = SynchedEntityData.defineId(StalkerEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public StalkerEntity(EntityType<StalkerEntity> type, Level world) {
		super(type, world);
		xpReward = 200;
		setNoAi(false);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(TheDeepVoidModItems.STALKER_SCYTHE_CLAW.get()));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "stalkernew");
		builder.define(DATA_voidCallCooldown, 0);
		builder.define(DATA_spawned, 0);
		builder.define(DATA_heartbeat, 0);
		builder.define(DATA_crouching, false);
		builder.define(DATA_soundAngry, false);
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
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LightEntity.class, (float) 6, 1.3, 1.2));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 9 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new FloatGoal(this));
		this.targetSelector.addGoal(7, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, LivingEntity.class, false, false));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource source, boolean recentlyHitIn) {
		super.dropCustomDeathLoot(serverLevel, source, recentlyHitIn);
		net.sombrius.thedeepvoid.port.PortUtil.spawnAtLocation(this, new ItemStack(TheDeepVoidModItems.STALKER_SCYTHE_CLAW.get()));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stalker_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stalker_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:stalker_death"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (source.is(DamageTypes.IN_FIRE))
			return false;
		if (source.getDirectEntity() instanceof AbstractThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud || source.typeHolder().is(net.sombrius.thedeepvoid.port.PortUtil.POISON_DAMAGE))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.PLAYER_EXPLOSION))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		if (source.is(DamageTypes.DRAGON_BREATH))
			return false;
		if (source.is(DamageTypes.WITHER) || source.is(DamageTypes.WITHER_SKULL))
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
	public void die(DamageSource source) {
		super.die(source);
		StalkerEntityDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), source.getEntity());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		StalkerSpawnsProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DatavoidCallCooldown", this.entityData.get(DATA_voidCallCooldown));
		compound.putInt("Dataspawned", this.entityData.get(DATA_spawned));
		compound.putInt("Dataheartbeat", this.entityData.get(DATA_heartbeat));
		compound.putBoolean("Datacrouching", this.entityData.get(DATA_crouching));
		compound.putBoolean("DatasoundAngry", this.entityData.get(DATA_soundAngry));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatavoidCallCooldown"))
			this.entityData.set(DATA_voidCallCooldown, compound.getIntOr("DatavoidCallCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataspawned"))
			this.entityData.set(DATA_spawned, compound.getIntOr("Dataspawned", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataheartbeat"))
			this.entityData.set(DATA_heartbeat, compound.getIntOr("Dataheartbeat", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datacrouching"))
			this.entityData.set(DATA_crouching, compound.getBooleanOr("Datacrouching", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatasoundAngry"))
			this.entityData.set(DATA_soundAngry, compound.getBooleanOr("DatasoundAngry", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		StalkerOnInitialEntitySpawnProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		Entity entity = this;
		Level world = this.level();
		double x = this.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return super.getDefaultDimensions(pose).scale((float) StalkerBoundingBoxScaleProcedure.execute(entity));
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 100);
		builder = builder.add(Attributes.STEP_HEIGHT, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 5);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.8);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<StalkerEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.stalker_aggressive"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.stalker_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.stalker_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<StalkerEntity> event) {
		double d1 = this.getX() - this.xOld;
		double d0 = this.getZ() - this.zOld;
		float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
		if (getAttackAnim(net.sombrius.thedeepvoid.port.GeoAnims.partialTick(event)) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && net.sombrius.thedeepvoid.port.GeoAnims.stopped(event.controller())) {
			event.controller().reset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.stalker_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<StalkerEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(StalkerEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>("movement", 4, this::movementPredicate));
		data.add(new AnimationController<>("attacking", 4, this::attackingPredicate));
		data.add(new AnimationController<>("procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
