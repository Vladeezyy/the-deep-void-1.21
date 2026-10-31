
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;

import com.geckolib.util.GeckoLibUtil;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.AnimationController;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;

import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;

import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.nbt.CompoundTag;

public class ShankPrisonerEntity extends Monster implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(ShankPrisonerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(ShankPrisonerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(ShankPrisonerEntity.class, EntityDataSerializers.STRING);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";


	public ShankPrisonerEntity(EntityType<ShankPrisonerEntity> type, Level world) {
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
		builder.define(TEXTURE, "penitent_and_shank");
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
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1, false) {
			@Override
			protected boolean canPerformAttack(LivingEntity entity) {
				return this.isTimeToAttack() && this.mob.distanceToSqr(entity) < 3.326976 && this.mob.getSensing().hasLineOfSight(entity);
			}
		});
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.7));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal(this, Player.class, false, true));
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
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_33597_) {
		return super.getDefaultDimensions(p_33597_).scale((float) 1);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 45);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 10);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<ShankPrisonerEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

					&& !this.isAggressive()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.shankPrisoner_walk"));
			}
			if (this.isAggressive() && event.isMoving()) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.shankPrisoner_agressive"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.shankPrisoner_idle"));
		}
		return PlayState.STOP;
	}

	private PlayState attackingPredicate(AnimationTest<ShankPrisonerEntity> event) {
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
			return event.setAndContinue(RawAnimation.begin().thenPlay("animation.shankPrisoner_attack"));
		}
		return PlayState.CONTINUE;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<ShankPrisonerEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(ShankPrisonerEntity.RemovalReason.KILLED);
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
