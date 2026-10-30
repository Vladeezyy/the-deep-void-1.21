
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;

import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;

import net.sombrius.thedeepvoid.procedures.HiveWatcherTickUpdateNewProcedure;
import net.sombrius.thedeepvoid.procedures.HiveWatcherPlayerCountProcedure;
import net.sombrius.thedeepvoid.procedures.HiveWatcherPlayerCollidesWithThisEntityProcedure;
import net.sombrius.thedeepvoid.procedures.HiveWatcherDiesProcedure;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class HiveWatcherEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_damageX = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_damageZ = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_spinning = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_spawnBlockade = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_playerCount = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_baseHealth = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_positive = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_positive2 = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_changeTexture = SynchedEntityData.defineId(HiveWatcherEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_6);


	public HiveWatcherEntity(EntityType<HiveWatcherEntity> type, Level world) {
		super(type, world);
		xpReward = 350;
		setNoAi(false);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(1f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "hivewatchernoeyes");
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_damageX, false);
		builder.define(DATA_damageZ, false);
		builder.define(DATA_spinning, false);
		builder.define(DATA_spawnBlockade, false);
		builder.define(DATA_playerCount, 0);
		builder.define(DATA_baseHealth, 0);
		builder.define(DATA_dying, false);
		builder.define(DATA_positive, false);
		builder.define(DATA_positive2, false);
		builder.define(DATA_changeTexture, false);
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
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth() && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
	}


	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:everhunger_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:everhunger_hurt"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		HiveWatcherDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		HiveWatcherPlayerCountProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putBoolean("DatadamageX", this.entityData.get(DATA_damageX));
		compound.putBoolean("DatadamageZ", this.entityData.get(DATA_damageZ));
		compound.putBoolean("Dataspinning", this.entityData.get(DATA_spinning));
		compound.putBoolean("DataspawnBlockade", this.entityData.get(DATA_spawnBlockade));
		compound.putInt("DataplayerCount", this.entityData.get(DATA_playerCount));
		compound.putInt("DatabaseHealth", this.entityData.get(DATA_baseHealth));
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putBoolean("Datapositive", this.entityData.get(DATA_positive));
		compound.putBoolean("Datapositive2", this.entityData.get(DATA_positive2));
		compound.putBoolean("DatachangeTexture", this.entityData.get(DATA_changeTexture));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadamageX"))
			this.entityData.set(DATA_damageX, compound.getBooleanOr("DatadamageX", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadamageZ"))
			this.entityData.set(DATA_damageZ, compound.getBooleanOr("DatadamageZ", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataspinning"))
			this.entityData.set(DATA_spinning, compound.getBooleanOr("Dataspinning", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataspawnBlockade"))
			this.entityData.set(DATA_spawnBlockade, compound.getBooleanOr("DataspawnBlockade", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplayerCount"))
			this.entityData.set(DATA_playerCount, compound.getIntOr("DataplayerCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatabaseHealth"))
			this.entityData.set(DATA_baseHealth, compound.getIntOr("DatabaseHealth", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadying"))
			this.entityData.set(DATA_dying, compound.getBooleanOr("Datadying", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datapositive"))
			this.entityData.set(DATA_positive, compound.getBooleanOr("Datapositive", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datapositive2"))
			this.entityData.set(DATA_positive2, compound.getBooleanOr("Datapositive2", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatachangeTexture"))
			this.entityData.set(DATA_changeTexture, compound.getBooleanOr("DatachangeTexture", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		HiveWatcherTickUpdateNewProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_33597_) {
		return super.getDefaultDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void playerTouch(Player sourceentity) {
		super.playerTouch(sourceentity);
		HiveWatcherPlayerCollidesWithThisEntityProcedure.execute(this.level(), this, sourceentity);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 440);
		builder = builder.add(Attributes.ARMOR, 13);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 58);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<HiveWatcherEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hiveWatcher_glide"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.hiveWatcher_death"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hiveWatcher_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<HiveWatcherEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(HiveWatcherEntity.RemovalReason.KILLED);
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
		data.add(new AnimationController<>("procedure", 4, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
