
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.CrawlerTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.PrimordialBoneCrawlerPlayerCollidesProcedure;
import net.sombrius.thedeepvoid.procedures.PrimordialCrawlerDiesProcedure;
import net.sombrius.thedeepvoid.procedures.PrimordialCrawlerInitialSpawnProcedure;
import net.sombrius.thedeepvoid.procedures.PrimordialCrawlerSolidBoundingBoxConditionProcedure;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

public class PrimordialBoneCrawlerEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_digging = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_primordialCrawlerFall = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_playerCount = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_baseHealth = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_diggingCount = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_dashing = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_spit = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_shooting = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_airSlam = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_fly = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_stun = SynchedEntityData.defineId(PrimordialBoneCrawlerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(java.util.UUID.randomUUID(), this.getDisplayName(), ServerBossEvent.BossBarColor.WHITE, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public PrimordialBoneCrawlerEntity(EntityType<PrimordialBoneCrawlerEntity> type, Level world) {
		super(type, world);
		xpReward = 250;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "primordial_bone_crawler");
		builder.define(DATA_dying, false);
		builder.define(DATA_digging, false);
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_primordialCrawlerFall, false);
		builder.define(DATA_playerCount, 0);
		builder.define(DATA_baseHealth, 0);
		builder.define(DATA_diggingCount, 0);
		builder.define(DATA_dashing, false);
		builder.define(DATA_spit, false);
		builder.define(DATA_shooting, 0);
		builder.define(DATA_airSlam, false);
		builder.define(DATA_fly, false);
		builder.define(DATA_stun, 0);
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
		return PrimordialCrawlerSolidBoundingBoxConditionProcedure.execute();
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 6.25 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, (float) 40));
		this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.spider.step")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:primordial_crawler_death"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof AbstractThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud || source.typeHolder().is(net.sombrius.thedeepvoid.port.PortUtil.POISON_DAMAGE))
			return false;
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		PrimordialCrawlerDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		PrimordialCrawlerInitialSpawnProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putBoolean("Datadigging", this.entityData.get(DATA_digging));
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putBoolean("DataprimordialCrawlerFall", this.entityData.get(DATA_primordialCrawlerFall));
		compound.putInt("DataplayerCount", this.entityData.get(DATA_playerCount));
		compound.putInt("DatabaseHealth", this.entityData.get(DATA_baseHealth));
		compound.putInt("DatadiggingCount", this.entityData.get(DATA_diggingCount));
		compound.putBoolean("Datadashing", this.entityData.get(DATA_dashing));
		compound.putBoolean("Dataspit", this.entityData.get(DATA_spit));
		compound.putInt("Datashooting", this.entityData.get(DATA_shooting));
		compound.putBoolean("DataairSlam", this.entityData.get(DATA_airSlam));
		compound.putBoolean("Datafly", this.entityData.get(DATA_fly));
		compound.putInt("Datastun", this.entityData.get(DATA_stun));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadying"))
			this.entityData.set(DATA_dying, compound.getBooleanOr("Datadying", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadigging"))
			this.entityData.set(DATA_digging, compound.getBooleanOr("Datadigging", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataprimordialCrawlerFall"))
			this.entityData.set(DATA_primordialCrawlerFall, compound.getBooleanOr("DataprimordialCrawlerFall", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplayerCount"))
			this.entityData.set(DATA_playerCount, compound.getIntOr("DataplayerCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatabaseHealth"))
			this.entityData.set(DATA_baseHealth, compound.getIntOr("DatabaseHealth", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadiggingCount"))
			this.entityData.set(DATA_diggingCount, compound.getIntOr("DatadiggingCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadashing"))
			this.entityData.set(DATA_dashing, compound.getBooleanOr("Datadashing", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataspit"))
			this.entityData.set(DATA_spit, compound.getBooleanOr("Dataspit", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datashooting"))
			this.entityData.set(DATA_shooting, compound.getIntOr("Datashooting", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataairSlam"))
			this.entityData.set(DATA_airSlam, compound.getBooleanOr("DataairSlam", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datafly"))
			this.entityData.set(DATA_fly, compound.getBooleanOr("Datafly", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datastun"))
			this.entityData.set(DATA_stun, compound.getIntOr("Datastun", 0));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		CrawlerTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	@Override
	public void playerTouch(Player sourceentity) {
		super.playerTouch(sourceentity);
		PrimordialBoneCrawlerPlayerCollidesProcedure.execute(this.level(), this, sourceentity);
	}

	@Override
	public boolean canTeleport(Level from, Level to) {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void customServerAiStep(ServerLevel level) {
		super.customServerAiStep(level);
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 600);
		builder = builder.add(Attributes.ARMOR, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 58);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<PrimordialBoneCrawlerEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.primordialCrawler_walk"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.primordialCrawler_death"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.primordialCrawler_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.primordialCrawler_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<PrimordialBoneCrawlerEntity> event) {
		double d1 = this.getX() - this.xOld;
		double d0 = this.getZ() - this.zOld;
		float velocity = (float) Math.sqrt(d1 * d1 + d0 * d0);
		if (getSwingAnimation(net.sombrius.thedeepvoid.port.GeoAnims.partialTick(event)) > 0f && !this.swinging) {
			this.swinging = true;
			this.lastSwing = level().getGameTime();
		}
		if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
			this.swinging = false;
		}
		if (this.swinging && net.sombrius.thedeepvoid.port.GeoAnims.stopped(event.controller())) {
			event.controller().reset();
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.primordialCrawler_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<PrimordialBoneCrawlerEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 133) {
			this.remove(PrimordialBoneCrawlerEntity.RemovalReason.KILLED);
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
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("attacking", 4, this::attackingPredicate));
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
