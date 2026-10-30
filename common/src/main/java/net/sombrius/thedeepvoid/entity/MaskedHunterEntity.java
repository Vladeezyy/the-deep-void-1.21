
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
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.MaskedHunterEntityIsHurtProcedure;
import net.sombrius.thedeepvoid.procedures.MaskedHunterNaturalEntitySpawningConditionProcedure;
import net.sombrius.thedeepvoid.procedures.MaskedHunterOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.MaskedHunterOnInitialEntitySpawnProcedure;
import net.sombrius.thedeepvoid.procedures.MaskedHunterThisEntityKillsAnotherOneProcedure;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

public class MaskedHunterEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_screamAmbient = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_randomScreamAmbient = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_jumping = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_placeTrap = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_playSound = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_randomPlaySound = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_shoot = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> DATA_spawnSpike = SynchedEntityData.defineId(MaskedHunterEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public MaskedHunterEntity(EntityType<MaskedHunterEntity> type, Level world) {
		super(type, world);
		xpReward = 15;
		setNoAi(false);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "huntertrue");
		builder.define(DATA_screamAmbient, 0);
		builder.define(DATA_randomScreamAmbient, 0);
		builder.define(DATA_jumping, false);
		builder.define(DATA_placeTrap, 0);
		builder.define(DATA_playSound, 0);
		builder.define(DATA_randomPlaySound, 0);
		builder.define(DATA_shoot, 0);
		builder.define(DATA_spawnSpike, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5, true) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 10.24 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, (float) 35));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.1));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(7, new FloatGoal(this));
		this.targetSelector.addGoal(8, new NearestAttackableTargetGoal(this, Animal.class, false, true));
		this.targetSelector.addGoal(9, new NearestAttackableTargetGoal(this, Villager.class, false, true));
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
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("entity.generic.death"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		MaskedHunterEntityIsHurtProcedure.execute(this.level(), this, source.getEntity());
		Entity immediatesourceentity = source.getDirectEntity();
		if (source.is(DamageTypes.FALL))
			return false;
		if (source.is(DamageTypes.CACTUS))
			return false;
		if (source.is(DamageTypes.DROWN))
			return false;
		if (source.is(DamageTypes.WITHER) || source.is(DamageTypes.WITHER_SKULL))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		MaskedHunterOnInitialEntitySpawnProcedure.execute(world, this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DatascreamAmbient", this.entityData.get(DATA_screamAmbient));
		compound.putInt("DatarandomScreamAmbient", this.entityData.get(DATA_randomScreamAmbient));
		compound.putBoolean("Datajumping", this.entityData.get(DATA_jumping));
		compound.putInt("DataplaceTrap", this.entityData.get(DATA_placeTrap));
		compound.putInt("DataplaySound", this.entityData.get(DATA_playSound));
		compound.putInt("DatarandomPlaySound", this.entityData.get(DATA_randomPlaySound));
		compound.putInt("Datashoot", this.entityData.get(DATA_shoot));
		compound.putInt("DataspawnSpike", this.entityData.get(DATA_spawnSpike));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatascreamAmbient"))
			this.entityData.set(DATA_screamAmbient, compound.getIntOr("DatascreamAmbient", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatarandomScreamAmbient"))
			this.entityData.set(DATA_randomScreamAmbient, compound.getIntOr("DatarandomScreamAmbient", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datajumping"))
			this.entityData.set(DATA_jumping, compound.getBooleanOr("Datajumping", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplaceTrap"))
			this.entityData.set(DATA_placeTrap, compound.getIntOr("DataplaceTrap", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataplaySound"))
			this.entityData.set(DATA_playSound, compound.getIntOr("DataplaySound", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatarandomPlaySound"))
			this.entityData.set(DATA_randomPlaySound, compound.getIntOr("DatarandomPlaySound", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datashoot"))
			this.entityData.set(DATA_shoot, compound.getIntOr("Datashoot", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataspawnSpike"))
			this.entityData.set(DATA_spawnSpike, compound.getIntOr("DataspawnSpike", 0));
	}

	@Override
	public void awardKillScore(Entity entity, DamageSource damageSource) {
		super.awardKillScore(entity, damageSource);
		MaskedHunterThisEntityKillsAnotherOneProcedure.execute(this.level(), entity, this);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		MaskedHunterOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(TheDeepVoidModEntities.MASKED_HUNTER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return MaskedHunterNaturalEntitySpawningConditionProcedure.execute(world, x, y, z);
		}, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 140);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 30);
		builder = builder.add(Attributes.STEP_HEIGHT, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 99);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.3);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<MaskedHunterEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hunterTrue_walk"));
			}
			if (this.isInWater()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hunterTrue_swim"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hunterTrue_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.hunterTrue_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<MaskedHunterEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(MaskedHunterEntity.RemovalReason.KILLED);
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
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("movement", 5, this::movementPredicate));
		data.add(new net.sombrius.thedeepvoid.port.GeoAnims.Controller<>("procedure", 5, this::procedurePredicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
