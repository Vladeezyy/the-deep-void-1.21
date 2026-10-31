
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.EyekinNaturalEntitySpawningConditionProcedure;
import net.sombrius.thedeepvoid.procedures.EyekinOnEntityTickUpdateProcedure;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;

public class EyekinEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(EyekinEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(EyekinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(EyekinEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_flyChance = SynchedEntityData.defineId(EyekinEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";

	public EyekinEntity(EntityType<EyekinEntity> type, Level world) {
		super(type, world);
		xpReward = 12;
		setNoAi(false);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "eyekin");
		builder.define(DATA_flyChance, 0);
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 8.1225 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Player.class, false, false));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:eyekin_ambient"));
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:eyekin_hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("the_deep_void:eyekin_death"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		if (source.is(DamageTypes.FALL))
			return false;
		return super.hurtServer(level, source, amount);
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("DataflyChance", this.entityData.get(DATA_flyChance));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataflyChance"))
			this.entityData.set(DATA_flyChance, compound.getIntOr("DataflyChance", 0));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		EyekinOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return super.getDefaultDimensions(pose).scale(1f);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
		event.register(TheDeepVoidModEntities.EYEKIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, reason, pos, random) -> {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			return EyekinNaturalEntitySpawningConditionProcedure.execute(world, x, y, z);
		}, RegisterSpawnPlacementsEvent.Operation.REPLACE);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 65);
		builder = builder.add(Attributes.ARMOR, 8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.STEP_HEIGHT, 0.6);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 9);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<EyekinEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.eye_walk"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.eye_aggressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.eye_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<EyekinEntity> event) {
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.eye_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<EyekinEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(EyekinEntity.RemovalReason.KILLED);
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
