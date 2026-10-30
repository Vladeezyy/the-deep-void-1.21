
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.UndertakerEntityDiesProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerOnInitialEntitySpawnProcedure;
import net.sombrius.thedeepvoid.procedures.UndertakerRightClickedOnEntityProcedure;
import software.bernie.geckolib.animatable.GeoEntity;

import net.sombrius.thedeepvoid.port.GeoAnims;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.state.AnimationTest;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UndertakerEntity extends PathfinderMob implements GeoEntity {
	public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.STRING);
	public static final EntityDataAccessor<Integer> DATA_despawn = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_empty = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_dialogueCooldown = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_talkedTo = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_talkCooldown = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_angry = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_attackChance = SynchedEntityData.defineId(UndertakerEntity.class, EntityDataSerializers.INT);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private boolean swinging;
	private long lastSwing;
	public String animationprocedure = "empty";


	public UndertakerEntity(EntityType<UndertakerEntity> type, Level world) {
		super(type, world);
		xpReward = 25;
		setNoAi(false);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(0.6f);
		setPersistenceRequired();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHOOT, false);
		builder.define(ANIMATION, "undefined");
		builder.define(TEXTURE, "undertakernew");
		builder.define(DATA_despawn, -1);
		builder.define(DATA_empty, false);
		builder.define(DATA_dialogueCooldown, 0);
		builder.define(DATA_talkedTo, false);
		builder.define(DATA_talkCooldown, 0);
		builder.define(DATA_angry, false);
		builder.define(DATA_attackChance, 0);
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
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(5, new FloatGoal(this));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, (float) 8));
		this.goalSelector.addGoal(7, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(8, new OpenDoorGoal(this, false));
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
	public void die(DamageSource source) {
		super.die(source);
		UndertakerEntityDiesProcedure.execute(this.level(), source.getEntity());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
		SpawnGroupData retval = super.finalizeSpawn(world, difficulty, reason, livingdata);
		UndertakerOnInitialEntitySpawnProcedure.execute(world, this.getX(), this.getY(), this.getZ(), this);
		return retval;
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putString("Texture", this.getTexture());
		compound.putInt("Datadespawn", this.entityData.get(DATA_despawn));
		compound.putBoolean("Dataempty", this.entityData.get(DATA_empty));
		compound.putInt("DatadialogueCooldown", this.entityData.get(DATA_dialogueCooldown));
		compound.putBoolean("DatatalkedTo", this.entityData.get(DATA_talkedTo));
		compound.putInt("DatatalkCooldown", this.entityData.get(DATA_talkCooldown));
		compound.putBoolean("Dataangry", this.entityData.get(DATA_angry));
		compound.putInt("DataattackChance", this.entityData.get(DATA_attackChance));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Texture"))
			this.setTexture(compound.getStringOr("Texture", ""));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadespawn"))
			this.entityData.set(DATA_despawn, compound.getIntOr("Datadespawn", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataempty"))
			this.entityData.set(DATA_empty, compound.getBooleanOr("Dataempty", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatadialogueCooldown"))
			this.entityData.set(DATA_dialogueCooldown, compound.getIntOr("DatadialogueCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatatalkedTo"))
			this.entityData.set(DATA_talkedTo, compound.getBooleanOr("DatatalkedTo", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DatatalkCooldown"))
			this.entityData.set(DATA_talkCooldown, compound.getIntOr("DatatalkCooldown", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataangry"))
			this.entityData.set(DATA_angry, compound.getBooleanOr("Dataangry", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "DataattackChance"))
			this.entityData.set(DATA_attackChance, compound.getIntOr("DataattackChance", 0));
	}

	@Override
	public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.SUCCESS;
		super.mobInteract(sourceentity, hand);
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level();
		return UndertakerRightClickedOnEntityProcedure.execute(world, x, y, z, entity, sourceentity);
	}

	@Override
	public void baseTick() {
		super.baseTick();
		UndertakerOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
		this.refreshDimensions();
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose p_33597_) {
		return super.getDefaultDimensions(p_33597_).scale((float) 1);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.MAX_HEALTH, 240);
		builder = builder.add(Attributes.ARMOR, 15);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}

	private PlayState movementPredicate(AnimationTest<UndertakerEntity> event) {
		if (this.animationprocedure.equals("empty")) {
			if ((event.isMoving() || !(this.walkAnimation.speed() > -0.15F && this.walkAnimation.speed() < 0.15F))

			) {
				return event.setAndContinue(RawAnimation.begin().thenLoop("animation.undertaker_walk"));
			}
			return event.setAndContinue(RawAnimation.begin().thenLoop("animation.undertaker_idle"));
		}
		return PlayState.STOP;
	}

	String prevAnim = "empty";

	private PlayState procedurePredicate(AnimationTest<UndertakerEntity> event) {
		return GeoAnims.procedure(event, this.animationprocedure, animation -> this.animationprocedure = animation);
	}

	@Override
	protected void tickDeath() {
		++this.deathTime;
		if (this.deathTime == 20) {
			this.remove(UndertakerEntity.RemovalReason.KILLED);
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
