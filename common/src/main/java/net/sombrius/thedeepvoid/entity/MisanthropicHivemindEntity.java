
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.procedures.HivemindDiesProcedure;
import net.sombrius.thedeepvoid.procedures.HivemindPlayerCountProcedure;
import net.sombrius.thedeepvoid.procedures.HivemindTickUpdateProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MisanthropicHivemindEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_playerCount = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_baseHealth = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_sawThrowerCooldown = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_skullSmasherCooldown = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_fleshTentaclesCooldown = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_bloodSpit = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_attacking = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_sucking = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_digesting = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_summonedMinions = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_skipSummons = SynchedEntityData.defineId(MisanthropicHivemindEntity.class, EntityDataSerializers.BOOLEAN);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_6);

	public MisanthropicHivemindEntity(EntityType<MisanthropicHivemindEntity> type, Level world) {
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
		builder.define(TEXTURE, "hivemindrework_pinkbrain");
		builder.define(DATA_playerCount, 0);
		builder.define(DATA_baseHealth, 0);
		builder.define(DATA_dying, false);
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_sawThrowerCooldown, 0);
		builder.define(DATA_skullSmasherCooldown, 0);
		builder.define(DATA_fleshTentaclesCooldown, 0);
		builder.define(DATA_bloodSpit, 0);
		builder.define(DATA_attacking, false);
		builder.define(DATA_sucking, 0);
		builder.define(DATA_digesting, false);
		builder.define(DATA_summonedMinions, false);
		builder.define(DATA_skipSummons, false);
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
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 9.61 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, false, false));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:hivemind_scream"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (source.getDirectEntity() instanceof AbstractThrownPotion || source.getDirectEntity() instanceof AreaEffectCloud || source.typeHolder().is(net.sombrius.thedeepvoid.port.PortUtil.POISON_DAMAGE))
			return false;
		if (source.is(DamageTypes.FALL))
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
		HivemindDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		HivemindPlayerCountProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataplayerCount", this.entityData.get(DATA_playerCount));
		compound.putInt("DatabaseHealth", this.entityData.get(DATA_baseHealth));
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putInt("DatasawThrowerCooldown", this.entityData.get(DATA_sawThrowerCooldown));
		compound.putInt("DataskullSmasherCooldown", this.entityData.get(DATA_skullSmasherCooldown));
		compound.putInt("DatafleshTentaclesCooldown", this.entityData.get(DATA_fleshTentaclesCooldown));
		compound.putInt("DatabloodSpit", this.entityData.get(DATA_bloodSpit));
		compound.putBoolean("Dataattacking", this.entityData.get(DATA_attacking));
		compound.putInt("Datasucking", this.entityData.get(DATA_sucking));
		compound.putBoolean("Datadigesting", this.entityData.get(DATA_digesting));
		compound.putBoolean("DatasummonedMinions", this.entityData.get(DATA_summonedMinions));
		compound.putBoolean("DataskipSummons", this.entityData.get(DATA_skipSummons));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplayerCount"))
			this.entityData.set(DATA_playerCount, compound.getIntOr("DataplayerCount", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatabaseHealth"))
			this.entityData.set(DATA_baseHealth, compound.getIntOr("DatabaseHealth", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadying"))
			this.entityData.set(DATA_dying, compound.getBooleanOr("Datadying", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatasawThrowerCooldown"))
			this.entityData.set(DATA_sawThrowerCooldown, compound.getIntOr("DatasawThrowerCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataskullSmasherCooldown"))
			this.entityData.set(DATA_skullSmasherCooldown, compound.getIntOr("DataskullSmasherCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatafleshTentaclesCooldown"))
			this.entityData.set(DATA_fleshTentaclesCooldown, compound.getIntOr("DatafleshTentaclesCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatabloodSpit"))
			this.entityData.set(DATA_bloodSpit, compound.getIntOr("DatabloodSpit", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataattacking"))
			this.entityData.set(DATA_attacking, compound.getBooleanOr("Dataattacking", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datasucking"))
			this.entityData.set(DATA_sucking, compound.getIntOr("Datasucking", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadigesting"))
			this.entityData.set(DATA_digesting, compound.getBooleanOr("Datadigesting", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatasummonedMinions"))
			this.entityData.set(DATA_summonedMinions, compound.getBooleanOr("DatasummonedMinions", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataskipSummons"))
			this.entityData.set(DATA_skipSummons, compound.getBooleanOr("DataskipSummons", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		HivemindTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 420);
		builder = builder.add(Attributes.ARMOR, 17);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 58);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 6.5);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<MisanthropicHivemindEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.hivemind_death"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hivemind_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<MisanthropicHivemindEntity> event) {
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.hivemind_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<MisanthropicHivemindEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 30) {
			this.remove(MisanthropicHivemindEntity.RemovalReason.KILLED);
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
