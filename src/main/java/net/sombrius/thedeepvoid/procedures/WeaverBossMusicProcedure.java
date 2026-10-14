package net.sombrius.thedeepvoid.procedures;

import java.util.Comparator;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.sombrius.thedeepvoid.entity.ApostleBossEntity;
import net.sombrius.thedeepvoid.entity.FalseHydraEntity;
import net.sombrius.thedeepvoid.entity.FleshWormEntity;
import net.sombrius.thedeepvoid.entity.HarbingerOfCataclysmEntity;
import net.sombrius.thedeepvoid.entity.HiveWatcherEntity;
import net.sombrius.thedeepvoid.entity.MisanthropicHivemindEntity;
import net.sombrius.thedeepvoid.entity.PrimordialBoneCrawlerEntity;
import net.sombrius.thedeepvoid.entity.WeaverOfSoulsBossEntity;
import net.sombrius.thedeepvoid.network.TheDeepVoidModVariables;

public class WeaverBossMusicProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > 0) {
			if (entity instanceof WeaverOfSoulsBossEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "darkmare";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 3300;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof ApostleBossEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "chainsaw";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 2350;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof MisanthropicHivemindEntity || entity instanceof FleshWormEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "pitch_black";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 1700;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof PrimordialBoneCrawlerEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "the_haunt";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 5020;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof HiveWatcherEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "monsters_lair";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 3600;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof FalseHydraEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = true;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 0;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = "slaughter";
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = 3200;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			} else if (entity instanceof HarbingerOfCataclysmEntity) {
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicLock) {
							{
								boolean _setval = true;
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicLock = _setval;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								double _setval = 0;
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicTick = _setval;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								double _setval = 0;
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = _setval;
								_vars.syncPlayerVariables(entityiterator);
							}
							{
								String _setval = "dealing_with_the_devil";
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicPlay = _setval;
								_vars.syncPlayerVariables(entityiterator);
							}
						} else if (entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES).MusicReplace) {
							{
								double _setval = 1966;
								TheDeepVoidModVariables.PlayerVariables _vars = entityiterator.getData(TheDeepVoidModVariables.PLAYER_VARIABLES);
								_vars.MusicWait = _setval;
								_vars.syncPlayerVariables(entityiterator);
							}
						}
					}
				}
			}
		}
	}
}
