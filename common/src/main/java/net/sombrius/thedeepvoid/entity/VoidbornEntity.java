
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.VoidbornBoundingBoxScaleProcedure;
import net.sombrius.thedeepvoid.procedures.VoidbornEntityIsHurtProcedure;
import net.sombrius.thedeepvoid.procedures.VoidbornOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.VoidbornThisEntityKillsAnotherOneProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidbornEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_spawnTentacles = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_hitCooldown = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_hit = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_cancelClose = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_heartbeat = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_crouch = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_cancelCrouch = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_crouching = SynchedEntityData.defineId(VoidbornEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public VoidbornEntity(EntityType<VoidbornEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "cavenightmare");
		builder.define(DATA_spawnTentacles, 0);
		builder.define(DATA_hitCooldown, 0);
		builder.define(DATA_hit, 0);
		builder.define(DATA_cancelClose, false);
		builder.define(DATA_heartbeat, 0);
		builder.define(DATA_crouch, false);
		builder.define(DATA_cancelCrouch, false);
		builder.define(DATA_crouching, 0);
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
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LightEntity.class, (float) 16, 1.8, 1.8));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.8, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 4.84 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, LightEntity.class, false, true));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		VoidbornEntityIsHurtProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this, source.getEntity());
		Entity immediatesourceentity = source.getDirectEntity();
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
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataspawnTentacles", this.entityData.get(DATA_spawnTentacles));
		compound.putInt("DatahitCooldown", this.entityData.get(DATA_hitCooldown));
		compound.putInt("Datahit", this.entityData.get(DATA_hit));
		compound.putBoolean("DatacancelClose", this.entityData.get(DATA_cancelClose));
		compound.putInt("Dataheartbeat", this.entityData.get(DATA_heartbeat));
		compound.putBoolean("Datacrouch", this.entityData.get(DATA_crouch));
		compound.putBoolean("DatacancelCrouch", this.entityData.get(DATA_cancelCrouch));
		compound.putInt("Datacrouching", this.entityData.get(DATA_crouching));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataspawnTentacles"))
			this.entityData.set(DATA_spawnTentacles, compound.getIntOr("DataspawnTentacles", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatahitCooldown"))
			this.entityData.set(DATA_hitCooldown, compound.getIntOr("DatahitCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datahit"))
			this.entityData.set(DATA_hit, compound.getIntOr("Datahit", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatacancelClose"))
			this.entityData.set(DATA_cancelClose, compound.getBooleanOr("DatacancelClose", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataheartbeat"))
			this.entityData.set(DATA_heartbeat, compound.getIntOr("Dataheartbeat", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datacrouch"))
			this.entityData.set(DATA_crouch, compound.getBooleanOr("Datacrouch", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatacancelCrouch"))
			this.entityData.set(DATA_cancelCrouch, compound.getBooleanOr("DatacancelCrouch", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datacrouching"))
			this.entityData.set(DATA_crouching, compound.getIntOr("Datacrouching", 0));
	}

	@Override
	public void awardKillScore(Entity entity, DamageSource damageSource) {
		super.awardKillScore(entity, damageSource);
		VoidbornThisEntityKillsAnotherOneProcedure.execute(this.level(), entity, this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		VoidbornOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		Entity entity = this;
		Level world = this.level();
		double x = this.getX();
		double y = entity.getY();
		double z = entity.getZ();
		return super.getDefaultDimensions(pose).scale((float) VoidbornBoundingBoxScaleProcedure.execute(entity));
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entityIn) {
	}

	@Override
	protected void pushEntities() {
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 999);
		builder = builder.add(Attributes.ARMOR, 99);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 15);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.STEP_HEIGHT, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<VoidbornEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.caveNightmare_walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.caveNightmare_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<VoidbornEntity> event) {
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.caveNightmare_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<VoidbornEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(VoidbornEntity.RemovalReason.KILLED);
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
