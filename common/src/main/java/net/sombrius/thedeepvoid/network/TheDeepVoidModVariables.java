package net.sombrius.thedeepvoid.network;


import net.sombrius.thedeepvoid.port.bus.PlayerEvent;
import net.sombrius.thedeepvoid.port.bus.EventBusSubscriber;
import net.sombrius.thedeepvoid.port.bus.SubscribeEvent;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.sombrius.thedeepvoid.port.PortSync;
import net.sombrius.thedeepvoid.port.PortBench;
import net.sombrius.thedeepvoid.TheDeepVoidMod;

import net.sombrius.thedeepvoid.port.platform.Platform;
import net.sombrius.thedeepvoid.port.neo.IPayloadContext;

public class TheDeepVoidModVariables {
	/** The id of the player variables attachment (NeoForge and Fabric), as in 1.99.3.1 */
	public static final Identifier PLAYER_VARIABLES_ID = Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "player_variables");

	/** The player variables of an entity (NeoForge's {@code getData(PLAYER_VARIABLES)}) */
	public static PlayerVariables of(Entity entity) {
		return Platform.INSTANCE.playerVariables(entity);
	}

	/** Both loaders call this once at init */
	public static void registerPackets() {
		Platform.INSTANCE.registerPayload(SavedDataSyncMessage.TYPE, SavedDataSyncMessage.STREAM_CODEC, SavedDataSyncMessage::handleData);
		Platform.INSTANCE.registerPayload(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				of(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				of(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				of(player).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = of(event.getOriginal());
			PlayerVariables clone = new PlayerVariables();
			clone.StalkerCount = original.StalkerCount;
			clone.ForgottenValleyAmbientSound = original.ForgottenValleyAmbientSound;
			clone.StalkerSpawn = original.StalkerSpawn;
			clone.StalkerCountFinish = original.StalkerCountFinish;
			clone.hallucinate = original.hallucinate;
			clone.StalkerStalkCount = original.StalkerStalkCount;
			clone.VoidCaveAmbientSound = original.VoidCaveAmbientSound;
			clone.InCreative = original.InCreative;
			clone.InAdventure = original.InAdventure;
			clone.cave_tremble = original.cave_tremble;
			clone.seek_light = original.seek_light;
			clone.playerCallOfTheVoid = original.playerCallOfTheVoid;
			clone.InSurvival = original.InSurvival;
			clone.SendToHell = original.SendToHell;
			clone.randomStalkerValue = original.randomStalkerValue;
			clone.stalkingCount = original.stalkingCount;
			clone.stalkWatcherCount = original.stalkWatcherCount;
			clone.noGravity = original.noGravity;
			clone.teleportToVoid = original.teleportToVoid;
			clone.hiveCallX = original.hiveCallX;
			clone.hiveCallY = original.hiveCallY;
			clone.hiveCallZ = original.hiveCallZ;
			clone.saveArmor3 = original.saveArmor3;
			clone.saveArmor2 = original.saveArmor2;
			clone.saveArmor1 = original.saveArmor1;
			clone.saveArmor0 = original.saveArmor0;
			clone.saveArmorPoint = original.saveArmorPoint;
			clone.MusicTick = original.MusicTick;
			clone.MusicWait = original.MusicWait;
			clone.MusicLock = original.MusicLock;
			clone.MusicReplace = original.MusicReplace;
			clone.MusicPlay = original.MusicPlay;
			clone.pitShowText = original.pitShowText;
			clone.sendToPurgatory = original.sendToPurgatory;
			clone.receivedWikiBook = original.receivedWikiBook;
			clone.saveArmorToughness = original.saveArmorToughness;
			clone.saveKnockbackResistance = original.saveKnockbackResistance;
			clone.saveStepHeight = original.saveStepHeight;
			clone.parasitizedType = original.parasitizedType;
			if (!event.isWasDeath()) {
				clone.playMusicPit = original.playMusicPit;
				clone.remnantsFog = original.remnantsFog;
				clone.remnantsColor = original.remnantsColor;
				clone.playerX = original.playerX;
				clone.playerY = original.playerY;
				clone.playerZ = original.playerZ;
				clone.RottenArmorAbility = original.RottenArmorAbility;
				clone.VoidCloakAbility = original.VoidCloakAbility;
				clone.darkness_absorption = original.darkness_absorption;
				clone.hallucinating = original.hallucinating;
				clone.deathgrounds_ambience = original.deathgrounds_ambience;
				clone.flesh_roar = original.flesh_roar;
				clone.armorToggle = original.armorToggle;
				clone.monolithAmbience = original.monolithAmbience;
				clone.hasteAllSeeing = original.hasteAllSeeing;
				clone.allseeingArmor = original.allseeingArmor;
				clone.hallucinateX = original.hallucinateX;
				clone.hallucinateY = original.hallucinateY;
				clone.hallucinateZ = original.hallucinateZ;
				clone.soundHallX = original.soundHallX;
				clone.soundHallY = original.soundHallY;
				clone.soundHallZ = original.soundHallZ;
				clone.hallucinateHurt = original.hallucinateHurt;
				clone.nestAmbience = original.nestAmbience;
				clone.darknessDamageBuildUp = original.darknessDamageBuildUp;
				clone.motherCallOutCooldown = original.motherCallOutCooldown;
				clone.motherlyCallCooldown = original.motherlyCallCooldown;
				clone.FallSound = original.FallSound;
				clone.healthBoostLevel = original.healthBoostLevel;
				clone.healthMask = original.healthMask;
				clone.grimRottenArmorAttack = original.grimRottenArmorAttack;
				clone.naturalTeleport = original.naturalTeleport;
				clone.weaverBoots = original.weaverBoots;
				clone.heartbeatIndicator = original.heartbeatIndicator;
				clone.hasSoulOrb = original.hasSoulOrb;
				clone.weaverRobeCooldown = original.weaverRobeCooldown;
				clone.gatheringAmbience = original.gatheringAmbience;
				clone.attackedByAnimosity = original.attackedByAnimosity;
				clone.eyekinCooldown = original.eyekinCooldown;
				clone.eyekinDash = original.eyekinDash;
				clone.wingFlapSound = original.wingFlapSound;
				clone.hookX = original.hookX;
				clone.hookY = original.hookY;
				clone.hookZ = original.hookZ;
				clone.hooked = original.hooked;
				clone.voidriumTeleport = original.voidriumTeleport;
				clone.noEscape = original.noEscape;
				clone.hunterArmorAbility = original.hunterArmorAbility;
				clone.mistedRemnantAmbience = original.mistedRemnantAmbience;
				clone.gaol_ambience = original.gaol_ambience;
				clone.noHit = original.noHit;
				clone.vultureArmorAbility = original.vultureArmorAbility;
				clone.gunslingerArmorAbility = original.gunslingerArmorAbility;
				clone.sentientArmorAbility = original.sentientArmorAbility;
			}
			Platform.INSTANCE.setPlayerVariables(event.getEntity(), clone);
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					Platform.INSTANCE.sendToPlayer(player, new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					Platform.INSTANCE.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					Platform.INSTANCE.sendToPlayer(player, new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "the_deep_void_worldvars";
		/** 1.21.5: saved data has a type with a codec (the data fixes of the random sequences touch none of its keys) */
		public static final SavedDataType<WorldVariables> TYPE = new SavedDataType<>(DATA_NAME, WorldVariables::new,
				CompoundTag.CODEC.xmap(tag -> load(tag, null), data -> data.save(new CompoundTag(), null)), DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES);

		public static WorldVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			WorldVariables data = new WorldVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof ServerLevel level) {
				PortBench.syncs++;
				Platform.INSTANCE.sendToPlayersInDimension(level, new SavedDataSyncMessage(1, this));
			}
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(TYPE);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "the_deep_void_mapvars";
		/** 1.21.5: saved data has a type with a codec (the data fixes of the random sequences touch none of its keys) */
		public static final SavedDataType<MapVariables> TYPE = new SavedDataType<>(DATA_NAME, MapVariables::new,
				CompoundTag.CODEC.xmap(tag -> load(tag, null), data -> data.save(new CompoundTag(), null)), DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES);
		public boolean CultTemplePlaced = false;
		public boolean pitPlaced = false;
		public double undertakerSpawn = 24000.0;
		public boolean undertakerKilled = false;
		public boolean StalkerDespawned = false;
		public double breathingHillsAmbience = 0;
		public double breathingHillsHeart = 0;
		public boolean WeaverTemplePlaced = false;
		public double stalkerSpawnX = 0;
		public double stalkerSpawnZ = 0;
		public boolean overwritten = false;
		public boolean surfaceOverwritten = false;
		public double hunterSpawnCooldown = 72000.0;
		public double spawnHydra = 0;
		public boolean hydraKilled = false;

		public static MapVariables load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
			MapVariables data = new MapVariables();
			data.read(tag, lookupProvider);
			return data;
		}

		public void read(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			CultTemplePlaced = nbt.getBooleanOr("CultTemplePlaced", false);
			pitPlaced = nbt.getBooleanOr("pitPlaced", false);
			undertakerSpawn = nbt.getDoubleOr("undertakerSpawn", 0);
			undertakerKilled = nbt.getBooleanOr("undertakerKilled", false);
			StalkerDespawned = nbt.getBooleanOr("StalkerDespawned", false);
			breathingHillsAmbience = nbt.getDoubleOr("breathingHillsAmbience", 0);
			breathingHillsHeart = nbt.getDoubleOr("breathingHillsHeart", 0);
			WeaverTemplePlaced = nbt.getBooleanOr("WeaverTemplePlaced", false);
			stalkerSpawnX = nbt.getDoubleOr("stalkerSpawnX", 0);
			stalkerSpawnZ = nbt.getDoubleOr("stalkerSpawnZ", 0);
			overwritten = nbt.getBooleanOr("overwritten", false);
			surfaceOverwritten = nbt.getBooleanOr("surfaceOverwritten", false);
			hunterSpawnCooldown = nbt.getDoubleOr("hunterSpawnCooldown", 0);
			spawnHydra = nbt.getDoubleOr("spawnHydra", 0);
			hydraKilled = nbt.getBooleanOr("hydraKilled", false);
		}

		public CompoundTag save(CompoundTag nbt, HolderLookup.Provider lookupProvider) {
			nbt.putBoolean("CultTemplePlaced", CultTemplePlaced);
			nbt.putBoolean("pitPlaced", pitPlaced);
			nbt.putDouble("undertakerSpawn", undertakerSpawn);
			nbt.putBoolean("undertakerKilled", undertakerKilled);
			nbt.putBoolean("StalkerDespawned", StalkerDespawned);
			nbt.putDouble("breathingHillsAmbience", breathingHillsAmbience);
			nbt.putDouble("breathingHillsHeart", breathingHillsHeart);
			nbt.putBoolean("WeaverTemplePlaced", WeaverTemplePlaced);
			nbt.putDouble("stalkerSpawnX", stalkerSpawnX);
			nbt.putDouble("stalkerSpawnZ", stalkerSpawnZ);
			nbt.putBoolean("overwritten", overwritten);
			nbt.putBoolean("surfaceOverwritten", surfaceOverwritten);
			nbt.putDouble("hunterSpawnCooldown", hunterSpawnCooldown);
			nbt.putDouble("spawnHydra", spawnHydra);
			nbt.putBoolean("hydraKilled", hydraKilled);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide()) {
				PortBench.syncs++;
				Platform.INSTANCE.sendToAllPlayers(new SavedDataSyncMessage(0, this));
			}
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(TYPE);
			} else {
				return clientSide;
			}
		}
	}

	public record SavedDataSyncMessage(int dataType, SavedData data) implements CustomPacketPayload {
		public static final Type<SavedDataSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "saved_data_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, SavedDataSyncMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SavedDataSyncMessage message) -> {
			buffer.writeInt(message.dataType);
			if (message.data != null)
				buffer.writeNbt(write(message.data, buffer.registryAccess()));
		}, (RegistryFriendlyByteBuf buffer) -> {
			int dataType = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			SavedData data = null;
			if (nbt != null) {
				data = dataType == 0 ? new MapVariables() : new WorldVariables();
				if (data instanceof MapVariables mapVariables)
					mapVariables.read(nbt, buffer.registryAccess());
				else if (data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt, buffer.registryAccess());
			}
			return new SavedDataSyncMessage(dataType, data);
		});

		/** 1.21.5: SavedData has no save(CompoundTag, Provider) any more (codecs); the mod's two kinds keep theirs */
		private static CompoundTag write(SavedData data, HolderLookup.Provider provider) {
			if (data instanceof MapVariables mapVariables)
				return mapVariables.save(new CompoundTag(), provider);
			return ((WorldVariables) data).save(new CompoundTag(), provider);
		}

		@Override
		public Type<SavedDataSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final SavedDataSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> {
					if (message.dataType == 0)
						MapVariables.clientSide.read(write(message.data, context.player().registryAccess()), context.player().registryAccess());
					else
						WorldVariables.clientSide.read(write(message.data, context.player().registryAccess()), context.player().registryAccess());
				}).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}

	public static class PlayerVariables {
		public double StalkerCount = 0.0;
		public double ForgottenValleyAmbientSound = 0;
		public boolean StalkerSpawn = false;
		public boolean StalkerCountFinish = false;
		public double playerX = 0;
		public double playerY = 0;
		public double playerZ = 0;
		public double RottenArmorAbility = 0;
		public double hallucinate = 0;
		public double StalkerStalkCount = 0;
		public double VoidCaveAmbientSound = 0;
		public double VoidCloakAbility = 0;
		public boolean InCreative = false;
		public boolean InAdventure = false;
		public double cave_tremble = 0;
		public double darkness_absorption = 0;
		public boolean seek_light = false;
		public boolean playerCallOfTheVoid = false;
		public boolean hallucinating = false;
		public double deathgrounds_ambience = 0;
		public double flesh_roar = 0;
		public boolean armorToggle = false;
		public double monolithAmbience = 0;
		public double hasteAllSeeing = 0;
		public double allseeingArmor = 0;
		public double hallucinateX = 0;
		public double hallucinateY = 0;
		public double hallucinateZ = 0;
		public double soundHallX = 0;
		public double soundHallY = 0;
		public double soundHallZ = 0;
		public double hallucinateHurt = 0;
		public double nestAmbience = 0;
		public boolean InSurvival = false;
		public double darknessDamageBuildUp = 0;
		public double motherCallOutCooldown = 0;
		public double motherlyCallCooldown = 0;
		public boolean FallSound = false;
		public double healthBoostLevel = 0;
		public double healthMask = 0;
		public boolean grimRottenArmorAttack = false;
		public boolean SendToHell = false;
		public boolean naturalTeleport = false;
		public double weaverBoots = 0;
		public double randomStalkerValue = 0;
		public double stalkingCount = 0;
		public double stalkWatcherCount = 0;
		public boolean noGravity = false;
		public double heartbeatIndicator = 0;
		public boolean hasSoulOrb = false;
		public boolean teleportToVoid = false;
		public double weaverRobeCooldown = 0;
		public double gatheringAmbience = 0;
		public boolean attackedByAnimosity = false;
		public double eyekinCooldown = 0;
		public double eyekinDash = 0;
		public double wingFlapSound = 0;
		public double hookX = 0;
		public double hookY = 0;
		public double hookZ = 0;
		public boolean hooked = false;
		public double voidriumTeleport = 0;
		public boolean noEscape = false;
		public double hiveCallX = 0;
		public double hiveCallY = 0;
		public double hiveCallZ = 0;
		public double hunterArmorAbility = 0.0;
		public double mistedRemnantAmbience = 0;
		public ItemStack saveArmor3 = ItemStack.EMPTY;
		public ItemStack saveArmor2 = ItemStack.EMPTY;
		public ItemStack saveArmor1 = ItemStack.EMPTY;
		public ItemStack saveArmor0 = ItemStack.EMPTY;
		public double saveArmorPoint = 0;
		public double gaol_ambience = 0.0;
		public boolean noHit = false;
		public double MusicTick = 0.0;
		public double MusicWait = 0;
		public boolean MusicLock = false;
		public boolean MusicReplace = false;
		public String MusicPlay = "\"\"";
		public boolean sendToPurgatory = false;
		public boolean playMusicPit = false;
		public boolean pitShowText = false;
		public double remnantsFog = 400.0;
		public double remnantsColor = 24.0;
		public boolean receivedWikiBook = false;
		public double saveArmorToughness = 0;
		public double saveKnockbackResistance = 0;
		public double saveStepHeight = 0;
		public double parasitizedType = 0;
		public double vultureArmorAbility = 0;
		public double gunslingerArmorAbility = 0;
		public double sentientArmorAbility = 0;

		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("StalkerCount", StalkerCount);
			nbt.putDouble("ForgottenValleyAmbientSound", ForgottenValleyAmbientSound);
			nbt.putBoolean("StalkerSpawn", StalkerSpawn);
			nbt.putBoolean("StalkerCountFinish", StalkerCountFinish);
			nbt.putDouble("playerX", playerX);
			nbt.putDouble("playerY", playerY);
			nbt.putDouble("playerZ", playerZ);
			nbt.putDouble("RottenArmorAbility", RottenArmorAbility);
			nbt.putDouble("hallucinate", hallucinate);
			nbt.putDouble("StalkerStalkCount", StalkerStalkCount);
			nbt.putDouble("VoidCaveAmbientSound", VoidCaveAmbientSound);
			nbt.putDouble("VoidCloakAbility", VoidCloakAbility);
			nbt.putBoolean("InCreative", InCreative);
			nbt.putBoolean("InAdventure", InAdventure);
			nbt.putDouble("cave_tremble", cave_tremble);
			nbt.putDouble("darkness_absorption", darkness_absorption);
			nbt.putBoolean("seek_light", seek_light);
			nbt.putBoolean("playerCallOfTheVoid", playerCallOfTheVoid);
			nbt.putBoolean("hallucinating", hallucinating);
			nbt.putDouble("deathgrounds_ambience", deathgrounds_ambience);
			nbt.putDouble("flesh_roar", flesh_roar);
			nbt.putBoolean("armorToggle", armorToggle);
			nbt.putDouble("monolithAmbience", monolithAmbience);
			nbt.putDouble("hasteAllSeeing", hasteAllSeeing);
			nbt.putDouble("allseeingArmor", allseeingArmor);
			nbt.putDouble("hallucinateX", hallucinateX);
			nbt.putDouble("hallucinateY", hallucinateY);
			nbt.putDouble("hallucinateZ", hallucinateZ);
			nbt.putDouble("soundHallX", soundHallX);
			nbt.putDouble("soundHallY", soundHallY);
			nbt.putDouble("soundHallZ", soundHallZ);
			nbt.putDouble("hallucinateHurt", hallucinateHurt);
			nbt.putDouble("nestAmbience", nestAmbience);
			nbt.putBoolean("InSurvival", InSurvival);
			nbt.putDouble("darknessDamageBuildUp", darknessDamageBuildUp);
			nbt.putDouble("motherCallOutCooldown", motherCallOutCooldown);
			nbt.putDouble("motherlyCallCooldown", motherlyCallCooldown);
			nbt.putBoolean("FallSound", FallSound);
			nbt.putDouble("healthBoostLevel", healthBoostLevel);
			nbt.putDouble("healthMask", healthMask);
			nbt.putBoolean("grimRottenArmorAttack", grimRottenArmorAttack);
			nbt.putBoolean("SendToHell", SendToHell);
			nbt.putBoolean("naturalTeleport", naturalTeleport);
			nbt.putDouble("weaverBoots", weaverBoots);
			nbt.putDouble("randomStalkerValue", randomStalkerValue);
			nbt.putDouble("stalkingCount", stalkingCount);
			nbt.putDouble("stalkWatcherCount", stalkWatcherCount);
			nbt.putBoolean("noGravity", noGravity);
			nbt.putDouble("heartbeatIndicator", heartbeatIndicator);
			nbt.putBoolean("hasSoulOrb", hasSoulOrb);
			nbt.putBoolean("teleportToVoid", teleportToVoid);
			nbt.putDouble("weaverRobeCooldown", weaverRobeCooldown);
			nbt.putDouble("gatheringAmbience", gatheringAmbience);
			nbt.putBoolean("attackedByAnimosity", attackedByAnimosity);
			nbt.putDouble("eyekinCooldown", eyekinCooldown);
			nbt.putDouble("eyekinDash", eyekinDash);
			nbt.putDouble("wingFlapSound", wingFlapSound);
			nbt.putDouble("hookX", hookX);
			nbt.putDouble("hookY", hookY);
			nbt.putDouble("hookZ", hookZ);
			nbt.putBoolean("hooked", hooked);
			nbt.putDouble("voidriumTeleport", voidriumTeleport);
			nbt.putBoolean("noEscape", noEscape);
			nbt.putDouble("hiveCallX", hiveCallX);
			nbt.putDouble("hiveCallY", hiveCallY);
			nbt.putDouble("hiveCallZ", hiveCallZ);
			nbt.putDouble("hunterArmorAbility", hunterArmorAbility);
			nbt.putDouble("mistedRemnantAmbience", mistedRemnantAmbience);
			nbt.put("saveArmor3", ItemStack.OPTIONAL_CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), saveArmor3).getOrThrow());
			nbt.put("saveArmor2", ItemStack.OPTIONAL_CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), saveArmor2).getOrThrow());
			nbt.put("saveArmor1", ItemStack.OPTIONAL_CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), saveArmor1).getOrThrow());
			nbt.put("saveArmor0", ItemStack.OPTIONAL_CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), saveArmor0).getOrThrow());
			nbt.putDouble("saveArmorPoint", saveArmorPoint);
			nbt.putDouble("gaol_ambience", gaol_ambience);
			nbt.putBoolean("noHit", noHit);
			nbt.putDouble("MusicTick", MusicTick);
			nbt.putDouble("MusicWait", MusicWait);
			nbt.putBoolean("MusicLock", MusicLock);
			nbt.putBoolean("MusicReplace", MusicReplace);
			nbt.putString("MusicPlay", MusicPlay);
			nbt.putBoolean("sendToPurgatory", sendToPurgatory);
			nbt.putBoolean("playMusicPit", playMusicPit);
			nbt.putBoolean("pitShowText", pitShowText);
			nbt.putDouble("remnantsFog", remnantsFog);
			nbt.putDouble("remnantsColor", remnantsColor);
			nbt.putBoolean("receivedWikiBook", receivedWikiBook);
			nbt.putDouble("saveArmorToughness", saveArmorToughness);
			nbt.putDouble("saveKnockbackResistance", saveKnockbackResistance);
			nbt.putDouble("saveStepHeight", saveStepHeight);
			nbt.putDouble("parasitizedType", parasitizedType);
			nbt.putDouble("vultureArmorAbility", vultureArmorAbility);
			nbt.putDouble("gunslingerArmorAbility", gunslingerArmorAbility);
			nbt.putDouble("sentientArmorAbility", sentientArmorAbility);
			return nbt;
		}

		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			StalkerCount = nbt.getDoubleOr("StalkerCount", 0);
			ForgottenValleyAmbientSound = nbt.getDoubleOr("ForgottenValleyAmbientSound", 0);
			StalkerSpawn = nbt.getBooleanOr("StalkerSpawn", false);
			StalkerCountFinish = nbt.getBooleanOr("StalkerCountFinish", false);
			playerX = nbt.getDoubleOr("playerX", 0);
			playerY = nbt.getDoubleOr("playerY", 0);
			playerZ = nbt.getDoubleOr("playerZ", 0);
			RottenArmorAbility = nbt.getDoubleOr("RottenArmorAbility", 0);
			hallucinate = nbt.getDoubleOr("hallucinate", 0);
			StalkerStalkCount = nbt.getDoubleOr("StalkerStalkCount", 0);
			VoidCaveAmbientSound = nbt.getDoubleOr("VoidCaveAmbientSound", 0);
			VoidCloakAbility = nbt.getDoubleOr("VoidCloakAbility", 0);
			InCreative = nbt.getBooleanOr("InCreative", false);
			InAdventure = nbt.getBooleanOr("InAdventure", false);
			cave_tremble = nbt.getDoubleOr("cave_tremble", 0);
			darkness_absorption = nbt.getDoubleOr("darkness_absorption", 0);
			seek_light = nbt.getBooleanOr("seek_light", false);
			playerCallOfTheVoid = nbt.getBooleanOr("playerCallOfTheVoid", false);
			hallucinating = nbt.getBooleanOr("hallucinating", false);
			deathgrounds_ambience = nbt.getDoubleOr("deathgrounds_ambience", 0);
			flesh_roar = nbt.getDoubleOr("flesh_roar", 0);
			armorToggle = nbt.getBooleanOr("armorToggle", false);
			monolithAmbience = nbt.getDoubleOr("monolithAmbience", 0);
			hasteAllSeeing = nbt.getDoubleOr("hasteAllSeeing", 0);
			allseeingArmor = nbt.getDoubleOr("allseeingArmor", 0);
			hallucinateX = nbt.getDoubleOr("hallucinateX", 0);
			hallucinateY = nbt.getDoubleOr("hallucinateY", 0);
			hallucinateZ = nbt.getDoubleOr("hallucinateZ", 0);
			soundHallX = nbt.getDoubleOr("soundHallX", 0);
			soundHallY = nbt.getDoubleOr("soundHallY", 0);
			soundHallZ = nbt.getDoubleOr("soundHallZ", 0);
			hallucinateHurt = nbt.getDoubleOr("hallucinateHurt", 0);
			nestAmbience = nbt.getDoubleOr("nestAmbience", 0);
			InSurvival = nbt.getBooleanOr("InSurvival", false);
			darknessDamageBuildUp = nbt.getDoubleOr("darknessDamageBuildUp", 0);
			motherCallOutCooldown = nbt.getDoubleOr("motherCallOutCooldown", 0);
			motherlyCallCooldown = nbt.getDoubleOr("motherlyCallCooldown", 0);
			FallSound = nbt.getBooleanOr("FallSound", false);
			healthBoostLevel = nbt.getDoubleOr("healthBoostLevel", 0);
			healthMask = nbt.getDoubleOr("healthMask", 0);
			grimRottenArmorAttack = nbt.getBooleanOr("grimRottenArmorAttack", false);
			SendToHell = nbt.getBooleanOr("SendToHell", false);
			naturalTeleport = nbt.getBooleanOr("naturalTeleport", false);
			weaverBoots = nbt.getDoubleOr("weaverBoots", 0);
			randomStalkerValue = nbt.getDoubleOr("randomStalkerValue", 0);
			stalkingCount = nbt.getDoubleOr("stalkingCount", 0);
			stalkWatcherCount = nbt.getDoubleOr("stalkWatcherCount", 0);
			noGravity = nbt.getBooleanOr("noGravity", false);
			heartbeatIndicator = nbt.getDoubleOr("heartbeatIndicator", 0);
			hasSoulOrb = nbt.getBooleanOr("hasSoulOrb", false);
			teleportToVoid = nbt.getBooleanOr("teleportToVoid", false);
			weaverRobeCooldown = nbt.getDoubleOr("weaverRobeCooldown", 0);
			gatheringAmbience = nbt.getDoubleOr("gatheringAmbience", 0);
			attackedByAnimosity = nbt.getBooleanOr("attackedByAnimosity", false);
			eyekinCooldown = nbt.getDoubleOr("eyekinCooldown", 0);
			eyekinDash = nbt.getDoubleOr("eyekinDash", 0);
			wingFlapSound = nbt.getDoubleOr("wingFlapSound", 0);
			hookX = nbt.getDoubleOr("hookX", 0);
			hookY = nbt.getDoubleOr("hookY", 0);
			hookZ = nbt.getDoubleOr("hookZ", 0);
			hooked = nbt.getBooleanOr("hooked", false);
			voidriumTeleport = nbt.getDoubleOr("voidriumTeleport", 0);
			noEscape = nbt.getBooleanOr("noEscape", false);
			hiveCallX = nbt.getDoubleOr("hiveCallX", 0);
			hiveCallY = nbt.getDoubleOr("hiveCallY", 0);
			hiveCallZ = nbt.getDoubleOr("hiveCallZ", 0);
			hunterArmorAbility = nbt.getDoubleOr("hunterArmorAbility", 0);
			mistedRemnantAmbience = nbt.getDoubleOr("mistedRemnantAmbience", 0);
			saveArmor3 = nbt.get("saveArmor3") == null ? ItemStack.EMPTY : ItemStack.OPTIONAL_CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), nbt.get("saveArmor3")).result().orElse(ItemStack.EMPTY);
			saveArmor2 = nbt.get("saveArmor2") == null ? ItemStack.EMPTY : ItemStack.OPTIONAL_CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), nbt.get("saveArmor2")).result().orElse(ItemStack.EMPTY);
			saveArmor1 = nbt.get("saveArmor1") == null ? ItemStack.EMPTY : ItemStack.OPTIONAL_CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), nbt.get("saveArmor1")).result().orElse(ItemStack.EMPTY);
			saveArmor0 = nbt.get("saveArmor0") == null ? ItemStack.EMPTY : ItemStack.OPTIONAL_CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), nbt.get("saveArmor0")).result().orElse(ItemStack.EMPTY);
			saveArmorPoint = nbt.getDoubleOr("saveArmorPoint", 0);
			gaol_ambience = nbt.getDoubleOr("gaol_ambience", 0);
			noHit = nbt.getBooleanOr("noHit", false);
			MusicTick = nbt.getDoubleOr("MusicTick", 0);
			MusicWait = nbt.getDoubleOr("MusicWait", 0);
			MusicLock = nbt.getBooleanOr("MusicLock", false);
			MusicReplace = nbt.getBooleanOr("MusicReplace", false);
			MusicPlay = nbt.getStringOr("MusicPlay", "");
			sendToPurgatory = nbt.getBooleanOr("sendToPurgatory", false);
			playMusicPit = nbt.getBooleanOr("playMusicPit", false);
			pitShowText = nbt.getBooleanOr("pitShowText", false);
			remnantsFog = nbt.getDoubleOr("remnantsFog", 0);
			remnantsColor = nbt.getDoubleOr("remnantsColor", 0);
			receivedWikiBook = nbt.getBooleanOr("receivedWikiBook", false);
			saveArmorToughness = nbt.getDoubleOr("saveArmorToughness", 0);
			saveKnockbackResistance = nbt.getDoubleOr("saveKnockbackResistance", 0);
			saveStepHeight = nbt.getDoubleOr("saveStepHeight", 0);
			parasitizedType = nbt.getDoubleOr("parasitizedType", 0);
			vultureArmorAbility = nbt.getDoubleOr("vultureArmorAbility", 0);
			gunslingerArmorAbility = nbt.getDoubleOr("gunslingerArmorAbility", 0);
			sentientArmorAbility = nbt.getDoubleOr("sentientArmorAbility", 0);
		}

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer && !PortSync.defer(serverPlayer)) {
				PortBench.syncs++;
				Platform.INSTANCE.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
			}
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(Identifier.fromNamespaceAndPath(TheDeepVoidMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> of(context.player()).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}
