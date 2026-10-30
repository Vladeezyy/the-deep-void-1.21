
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import javax.annotation.Nullable;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.ApostleBossPlayerCollidesWithThisEntityProcedure;
import net.sombrius.thedeepvoid.procedures.ApostleOfCatastropheDiesProcedure;
import net.sombrius.thedeepvoid.procedures.ApostleSpawnsProcedure;
import net.sombrius.thedeepvoid.procedures.ApostleTickUpdateProcedure;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

public class ApostleBossEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_laughChance = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_random = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_doingAttack = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_runGrab = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_grabbedPlayer = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_jumpGrab = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_upMotion = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_downMotion = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_canDoGrab = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_taunt = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_spinning = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_fog = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_throwPlayer = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_piercedPlayer = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_tpTries = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_xPos = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_yPos = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_zPos = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_teleportCount = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_teleportCooldown = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_shieldIsUsed = SynchedEntityData.defineId(ApostleBossEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(java.util.UUID.randomUUID(), this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public ApostleBossEntity(EntityType<ApostleBossEntity> type, Level world) {
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
		builder.define(TEXTURE, "apostleofcatastrophe");
		builder.define(DATA_dying, false);
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_laughChance, 0);
		builder.define(DATA_random, 0);
		builder.define(DATA_doingAttack, false);
		builder.define(DATA_runGrab, false);
		builder.define(DATA_grabbedPlayer, false);
		builder.define(DATA_jumpGrab, false);
		builder.define(DATA_upMotion, false);
		builder.define(DATA_downMotion, false);
		builder.define(DATA_canDoGrab, false);
		builder.define(DATA_taunt, 300);
		builder.define(DATA_spinning, false);
		builder.define(DATA_fog, false);
		builder.define(DATA_throwPlayer, false);
		builder.define(DATA_piercedPlayer, false);
		builder.define(DATA_tpTries, 0);
		builder.define(DATA_xPos, 0);
		builder.define(DATA_yPos, 0);
		builder.define(DATA_zPos, 0);
		builder.define(DATA_teleportCount, 0);
		builder.define(DATA_teleportCooldown, 0);
		builder.define(DATA_shieldIsUsed, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < (this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth()) && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new FloatGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, false, false));
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
		ApostleOfCatastropheDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		ApostleSpawnsProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putInt("DatalaughChance", this.entityData.get(DATA_laughChance));
		compound.putInt("Datarandom", this.entityData.get(DATA_random));
		compound.putBoolean("DatadoingAttack", this.entityData.get(DATA_doingAttack));
		compound.putBoolean("DatarunGrab", this.entityData.get(DATA_runGrab));
		compound.putBoolean("DatagrabbedPlayer", this.entityData.get(DATA_grabbedPlayer));
		compound.putBoolean("DatajumpGrab", this.entityData.get(DATA_jumpGrab));
		compound.putBoolean("DataupMotion", this.entityData.get(DATA_upMotion));
		compound.putBoolean("DatadownMotion", this.entityData.get(DATA_downMotion));
		compound.putBoolean("DatacanDoGrab", this.entityData.get(DATA_canDoGrab));
		compound.putInt("Datataunt", this.entityData.get(DATA_taunt));
		compound.putBoolean("Dataspinning", this.entityData.get(DATA_spinning));
		compound.putBoolean("Datafog", this.entityData.get(DATA_fog));
		compound.putBoolean("DatathrowPlayer", this.entityData.get(DATA_throwPlayer));
		compound.putBoolean("DatapiercedPlayer", this.entityData.get(DATA_piercedPlayer));
		compound.putInt("DatatpTries", this.entityData.get(DATA_tpTries));
		compound.putInt("DataxPos", this.entityData.get(DATA_xPos));
		compound.putInt("DatayPos", this.entityData.get(DATA_yPos));
		compound.putInt("DatazPos", this.entityData.get(DATA_zPos));
		compound.putInt("DatateleportCount", this.entityData.get(DATA_teleportCount));
		compound.putInt("DatateleportCooldown", this.entityData.get(DATA_teleportCooldown));
		compound.putInt("DatashieldIsUsed", this.entityData.get(DATA_shieldIsUsed));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadying"))
			this.entityData.set(DATA_dying, compound.getBooleanOr("Datadying", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatalaughChance"))
			this.entityData.set(DATA_laughChance, compound.getIntOr("DatalaughChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datarandom"))
			this.entityData.set(DATA_random, compound.getIntOr("Datarandom", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadoingAttack"))
			this.entityData.set(DATA_doingAttack, compound.getBooleanOr("DatadoingAttack", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatarunGrab"))
			this.entityData.set(DATA_runGrab, compound.getBooleanOr("DatarunGrab", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatagrabbedPlayer"))
			this.entityData.set(DATA_grabbedPlayer, compound.getBooleanOr("DatagrabbedPlayer", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatajumpGrab"))
			this.entityData.set(DATA_jumpGrab, compound.getBooleanOr("DatajumpGrab", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataupMotion"))
			this.entityData.set(DATA_upMotion, compound.getBooleanOr("DataupMotion", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadownMotion"))
			this.entityData.set(DATA_downMotion, compound.getBooleanOr("DatadownMotion", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatacanDoGrab"))
			this.entityData.set(DATA_canDoGrab, compound.getBooleanOr("DatacanDoGrab", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datataunt"))
			this.entityData.set(DATA_taunt, compound.getIntOr("Datataunt", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataspinning"))
			this.entityData.set(DATA_spinning, compound.getBooleanOr("Dataspinning", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datafog"))
			this.entityData.set(DATA_fog, compound.getBooleanOr("Datafog", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatathrowPlayer"))
			this.entityData.set(DATA_throwPlayer, compound.getBooleanOr("DatathrowPlayer", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatapiercedPlayer"))
			this.entityData.set(DATA_piercedPlayer, compound.getBooleanOr("DatapiercedPlayer", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatatpTries"))
			this.entityData.set(DATA_tpTries, compound.getIntOr("DatatpTries", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataxPos"))
			this.entityData.set(DATA_xPos, compound.getIntOr("DataxPos", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatayPos"))
			this.entityData.set(DATA_yPos, compound.getIntOr("DatayPos", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatazPos"))
			this.entityData.set(DATA_zPos, compound.getIntOr("DatazPos", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatateleportCount"))
			this.entityData.set(DATA_teleportCount, compound.getIntOr("DatateleportCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatateleportCooldown"))
			this.entityData.set(DATA_teleportCooldown, compound.getIntOr("DatateleportCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatashieldIsUsed"))
			this.entityData.set(DATA_shieldIsUsed, compound.getIntOr("DatashieldIsUsed", 0));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ApostleTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	@Override
	public void playerTouch(Player sourceentity) {
		super.playerTouch(sourceentity);
		ApostleBossPlayerCollidesWithThisEntityProcedure.execute(this.level(), this, sourceentity);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 720);
		builder = builder.add(Attributes.ARMOR, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 74);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<ApostleBossEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle_walk"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.apostle_fall"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.apostle_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<ApostleBossEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 60) {
			this.remove(ApostleBossEntity.RemovalReason.KILLED);
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
