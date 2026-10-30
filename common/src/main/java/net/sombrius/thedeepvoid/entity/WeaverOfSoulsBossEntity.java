
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
import net.minecraft.world.entity.AreaEffectCloud;
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
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.WeaverOfSoulsEntityDiesProcedure;
import net.sombrius.thedeepvoid.procedures.WeaverOfSoulsOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.WeaverOfSoulsPlayerCountProcedure;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

public class WeaverOfSoulsBossEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_stunned = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_locate = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_scream = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_giantHand = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_murmurs = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_stunCount = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_playerCount = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_baseHealth = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_blockade = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_handsCooldown = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_handWall = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_randomNumber = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_screamPlayed = SynchedEntityData.defineId(WeaverOfSoulsBossEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(java.util.UUID.randomUUID(), this.getDisplayName(), ServerBossEvent.BossBarColor.BLUE, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public WeaverOfSoulsBossEntity(EntityType<WeaverOfSoulsBossEntity> type, Level world) {
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
		builder.define(TEXTURE, "fallenweaver");
		builder.define(DATA_stunned, false);
		builder.define(DATA_locate, 0);
		builder.define(DATA_scream, 0);
		builder.define(DATA_giantHand, 0);
		builder.define(DATA_murmurs, 0);
		builder.define(DATA_dying, false);
		builder.define(DATA_stunCount, 0);
		builder.define(DATA_playerCount, 0);
		builder.define(DATA_baseHealth, 0);
		builder.define(DATA_blockade, false);
		builder.define(DATA_handsCooldown, 0);
		builder.define(DATA_handWall, 0);
		builder.define(DATA_randomNumber, 0);
		builder.define(DATA_screamPlayed, false);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.655, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 8.1225 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal(this, Player.class, true, false));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:weaver_scream"));
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
		if (source.is(DamageTypes.FALLING_ANVIL))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		WeaverOfSoulsEntityDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		WeaverOfSoulsPlayerCountProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datastunned", this.entityData.get(DATA_stunned));
		compound.putInt("Datalocate", this.entityData.get(DATA_locate));
		compound.putInt("Datascream", this.entityData.get(DATA_scream));
		compound.putInt("DatagiantHand", this.entityData.get(DATA_giantHand));
		compound.putInt("Datamurmurs", this.entityData.get(DATA_murmurs));
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putInt("DatastunCount", this.entityData.get(DATA_stunCount));
		compound.putInt("DataplayerCount", this.entityData.get(DATA_playerCount));
		compound.putInt("DatabaseHealth", this.entityData.get(DATA_baseHealth));
		compound.putBoolean("Datablockade", this.entityData.get(DATA_blockade));
		compound.putInt("DatahandsCooldown", this.entityData.get(DATA_handsCooldown));
		compound.putInt("DatahandWall", this.entityData.get(DATA_handWall));
		compound.putInt("DatarandomNumber", this.entityData.get(DATA_randomNumber));
		compound.putBoolean("DatascreamPlayed", this.entityData.get(DATA_screamPlayed));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datastunned"))
			this.entityData.set(DATA_stunned, compound.getBooleanOr("Datastunned", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datalocate"))
			this.entityData.set(DATA_locate, compound.getIntOr("Datalocate", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datascream"))
			this.entityData.set(DATA_scream, compound.getIntOr("Datascream", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatagiantHand"))
			this.entityData.set(DATA_giantHand, compound.getIntOr("DatagiantHand", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datamurmurs"))
			this.entityData.set(DATA_murmurs, compound.getIntOr("Datamurmurs", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadying"))
			this.entityData.set(DATA_dying, compound.getBooleanOr("Datadying", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatastunCount"))
			this.entityData.set(DATA_stunCount, compound.getIntOr("DatastunCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplayerCount"))
			this.entityData.set(DATA_playerCount, compound.getIntOr("DataplayerCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatabaseHealth"))
			this.entityData.set(DATA_baseHealth, compound.getIntOr("DatabaseHealth", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datablockade"))
			this.entityData.set(DATA_blockade, compound.getBooleanOr("Datablockade", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatahandsCooldown"))
			this.entityData.set(DATA_handsCooldown, compound.getIntOr("DatahandsCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatahandWall"))
			this.entityData.set(DATA_handWall, compound.getIntOr("DatahandWall", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatarandomNumber"))
			this.entityData.set(DATA_randomNumber, compound.getIntOr("DatarandomNumber", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatascreamPlayed"))
			this.entityData.set(DATA_screamPlayed, compound.getBooleanOr("DatascreamPlayed", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		WeaverOfSoulsOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
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
		builder = builder.add(Attributes.MAX_HEALTH, 500);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 47);
		builder = builder.add(Attributes.STEP_HEIGHT, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 4);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<WeaverOfSoulsBossEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.fallenWeaver_walk"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.fallenWeaver_death"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.fallenWeaver_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.fallenWeaver_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<WeaverOfSoulsBossEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 73) {
			this.remove(WeaverOfSoulsBossEntity.RemovalReason.KILLED);
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
