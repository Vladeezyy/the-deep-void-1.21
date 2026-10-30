
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.HarbingerOfCataclysmDiesProcedure;
import net.sombrius.thedeepvoid.procedures.HarbingerOfCataclysmEntityIsHurtProcedure;
import net.sombrius.thedeepvoid.procedures.HarbingerOfCataclysmOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.HarbingerOfCataclysmPlayerCollidesWithThisEntityProcedure;
import net.sombrius.thedeepvoid.procedures.HarbingerOfCataclysmSpawnsProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HarbingerOfCataclysmEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Boolean> DATA_dying = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_attacking = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_parry = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_attackCooldown = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_piercing = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_repeat = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_repeatCooldown = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_teleport = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> DATA_teleportAttack = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_dyingCooldown = SynchedEntityData.defineId(HarbingerOfCataclysmEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED, ServerBossEvent.BossBarOverlay.NOTCHED_6);


	public HarbingerOfCataclysmEntity(EntityType<HarbingerOfCataclysmEntity> type, Level world) {
		super(type, world);
		xpReward = 0;
		setNoAi(false);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(0.6f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "harbinger");
		builder.define(DATA_dying, false);
		builder.define(DATA_attackChance, 0);
		builder.define(DATA_attacking, false);
		builder.define(DATA_parry, 0);
		builder.define(DATA_attackCooldown, 0);
		builder.define(DATA_piercing, false);
		builder.define(DATA_repeat, 0);
		builder.define(DATA_repeatCooldown, 0);
		builder.define(DATA_teleport, false);
		builder.define(DATA_teleportAttack, false);
		builder.define(DATA_dyingCooldown, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth() && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
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
		HarbingerOfCataclysmEntityIsHurtProcedure.execute(this.level(), this, source.getEntity());
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.EXPLOSION))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		HarbingerOfCataclysmDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		HarbingerOfCataclysmSpawnsProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putBoolean("Datadying", this.entityData.get(DATA_dying));
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
		compound.putBoolean("Dataattacking", this.entityData.get(DATA_attacking));
		compound.putInt("Dataparry", this.entityData.get(DATA_parry));
		compound.putInt("DataattackCooldown", this.entityData.get(DATA_attackCooldown));
		compound.putBoolean("Datapiercing", this.entityData.get(DATA_piercing));
		compound.putInt("Datarepeat", this.entityData.get(DATA_repeat));
		compound.putInt("DatarepeatCooldown", this.entityData.get(DATA_repeatCooldown));
		compound.putBoolean("Datateleport", this.entityData.get(DATA_teleport));
		compound.putBoolean("DatateleportAttack", this.entityData.get(DATA_teleportAttack));
		compound.putInt("DatadyingCooldown", this.entityData.get(DATA_dyingCooldown));
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
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataattacking"))
			this.entityData.set(DATA_attacking, compound.getBooleanOr("Dataattacking", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataparry"))
			this.entityData.set(DATA_parry, compound.getIntOr("Dataparry", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackCooldown"))
			this.entityData.set(DATA_attackCooldown, compound.getIntOr("DataattackCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datapiercing"))
			this.entityData.set(DATA_piercing, compound.getBooleanOr("Datapiercing", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datarepeat"))
			this.entityData.set(DATA_repeat, compound.getIntOr("Datarepeat", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatarepeatCooldown"))
			this.entityData.set(DATA_repeatCooldown, compound.getIntOr("DatarepeatCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datateleport"))
			this.entityData.set(DATA_teleport, compound.getBooleanOr("Datateleport", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatateleportAttack"))
			this.entityData.set(DATA_teleportAttack, compound.getBooleanOr("DatateleportAttack", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadyingCooldown"))
			this.entityData.set(DATA_dyingCooldown, compound.getIntOr("DatadyingCooldown", 0));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		HarbingerOfCataclysmOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_33597_) {
		return super.getDefaultDimensions(p_33597_).scale((float) 1.2);
	}

	@Override
	public void playerTouch(Player sourceentity) {
		super.playerTouch(sourceentity);
		HarbingerOfCataclysmPlayerCollidesWithThisEntityProcedure.execute(this.level(), this, sourceentity);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 620);
		builder = builder.add(Attributes.ARMOR, 14);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 32);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<HarbingerOfCataclysmEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.harbinger_walk"));
			}
			if (this.isDeadOrDying()) {
				return event.setAndContinue(RawAnimation.begin().thenPlay("animation.harbinger_death"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.harbinger_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.harbinger_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<HarbingerOfCataclysmEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 120) {
			this.remove(HarbingerOfCataclysmEntity.RemovalReason.KILLED);
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
