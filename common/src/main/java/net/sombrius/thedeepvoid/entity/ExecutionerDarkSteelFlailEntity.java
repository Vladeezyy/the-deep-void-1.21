
package net.sombrius.thedeepvoid.entity;

import net.minecraft.server.level.ServerLevel;
import net.sombrius.thedeepvoid.port.bus.RegisterSpawnPlacementsEvent;

import net.minecraft.world.level.Explosion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sombrius.thedeepvoid.init.TheDeepVoidModEntities;
import net.sombrius.thedeepvoid.procedures.ExecutionerDarkSteelFlailEntityDiesProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerDarkSteelFlailEntityIsHurtProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerDarkSteelFlailOnEntityTickUpdateProcedure;
import net.sombrius.thedeepvoid.procedures.ExecutionerDarkSteelFlailSolidBoundingBoxConditionProcedure;

public class ExecutionerDarkSteelFlailEntity extends Monster {
	public static final EntityDataAccessor<Boolean> DATA_retract = SynchedEntityData.defineId(ExecutionerDarkSteelFlailEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Integer> DATA_despawn = SynchedEntityData.defineId(ExecutionerDarkSteelFlailEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Boolean> DATA_stun = SynchedEntityData.defineId(ExecutionerDarkSteelFlailEntity.class, EntityDataSerializers.BOOLEAN);


	public ExecutionerDarkSteelFlailEntity(EntityType<ExecutionerDarkSteelFlailEntity> type, Level world) {
		super(type, world);
		this.getAttribute(Attributes.STEP_HEIGHT).setBaseValue(0f);
		xpReward = 0;
		setNoAi(false);
	}


	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_retract, false);
		builder.define(DATA_despawn, 0);
		builder.define(DATA_stun, false);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

	}


	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return BuiltInRegistries.SOUND_EVENT.getValue(Identifier.parse("intentionally_empty"));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damagesource, float amount) {
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Level world = this.level();
		Entity entity = this;
		Entity sourceentity = damagesource.getEntity();
		Entity immediatesourceentity = damagesource.getDirectEntity();

		ExecutionerDarkSteelFlailEntityIsHurtProcedure.execute(world, entity, sourceentity);
		if (damagesource.is(DamageTypes.IN_FIRE))
			return false;
		if (damagesource.getDirectEntity() instanceof AbstractArrow)
			return false;
		if (damagesource.getDirectEntity() instanceof Player)
			return false;
		if (damagesource.getDirectEntity() instanceof AbstractThrownPotion || damagesource.getDirectEntity() instanceof AreaEffectCloud)
			return false;
		if (damagesource.is(DamageTypes.FALL))
			return false;
		if (damagesource.is(DamageTypes.CACTUS))
			return false;
		if (damagesource.is(DamageTypes.DROWN))
			return false;
		if (damagesource.is(DamageTypes.LIGHTNING_BOLT))
			return false;
		if (damagesource.is(DamageTypes.EXPLOSION) || damagesource.is(DamageTypes.PLAYER_EXPLOSION))
			return false;
		if (damagesource.is(DamageTypes.TRIDENT))
			return false;
		if (damagesource.is(DamageTypes.FALLING_ANVIL))
			return false;
		if (damagesource.is(DamageTypes.DRAGON_BREATH))
			return false;
		if (damagesource.is(DamageTypes.WITHER) || damagesource.is(DamageTypes.WITHER_SKULL))
			return false;
		return super.hurtServer(level, damagesource, amount);
	}

	@Override
	public boolean ignoreExplosion(Explosion explosion) {
		return true;
	}

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		ExecutionerDarkSteelFlailEntityDiesProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ());
	}

	@Override
	public void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("Dataretract", this.entityData.get(DATA_retract));
		compound.putInt("Datadespawn", this.entityData.get(DATA_despawn));
		compound.putBoolean("Datastun", this.entityData.get(DATA_stun));
	}

	@Override
	public void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput compound) {
		super.readAdditionalSaveData(compound);
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Dataretract"))
			this.entityData.set(DATA_retract, compound.getBooleanOr("Dataretract", false));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datadespawn"))
			this.entityData.set(DATA_despawn, compound.getIntOr("Datadespawn", 0));
		if (net.sombrius.thedeepvoid.port.PortUtil.has(compound, "Datastun"))
			this.entityData.set(DATA_stun, compound.getBooleanOr("Datastun", false));
	}

	@Override
	public void baseTick() {
		super.baseTick();
		ExecutionerDarkSteelFlailOnEntityTickUpdateProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), this);
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
		return ExecutionerDarkSteelFlailSolidBoundingBoxConditionProcedure.execute(world, x, y, z, entity);
	}

	public static void init(RegisterSpawnPlacementsEvent event) {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 12);
		builder = builder.add(Attributes.FOLLOW_RANGE, 1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 999);
		return builder;
	}
}
