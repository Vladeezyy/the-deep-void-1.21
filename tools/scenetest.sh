#!/bin/sh
# Runs port/DevScene (LOADER=neoforge|fabric) through the hang watchdog: Deep Void biomes and mobs, screenshots in
# <loader>/run/screenshots/scene_*.png. Extra arguments go to Gradle (-Pscene=lang; -Pscene=thaumaturge -Pwith_thaumaturge).
# SCENE_LANG=ru_ru sets the game language. Log: build-scene-<loader>.log
{
cd "$(dirname "$0")/.."
. tools/watchdog.sh
LOADER=${LOADER:-neoforge}
case $LOADER in
	neoforge) PROC='fml.modFolders=the_deep_void' ;;
	fabric) PROC="fabric\.dli\.config=.*$(basename "$PWD" | sed 's/ /@@0020/g; s/[.+]/\\&/g')/" ;;
	*) echo "LOADER must be neoforge or fabric"; exit 2 ;;
esac
kill_dev() { pkill -f "$PROC" 2>/dev/null; }
LOG=build-scene-$LOADER.log
rm -rf $LOADER/run/saves/dv_scene $LOADER/run/saves/dv_items $LOADER/run/saves/dv_regress $LOADER/run/saves/dv_thaumaturge $LOG
# -Pscene=thaumaturge (NeoForge, with -Pwith_thaumaturge): Thaumaturge shows aspects on tooltips without Shift
mkdir -p $LOADER/run/config
case " $* " in
	*" -Pscene=thaumaturge "*) printf '[tooltip]
show_aspects_by_default = true
' > $LOADER/run/config/thaumaturge-client.toml ;;
	*) rm -f $LOADER/run/config/thaumaturge-client.toml ;;
esac
# SCENE_LANG=ru_ru: the game language of the run (options.txt)
if [ -n "$SCENE_LANG" ]; then
	if [ -f $LOADER/run/options.txt ]; then sed -i "s/^lang:.*/lang:$SCENE_LANG/" $LOADER/run/options.txt; else echo "lang:$SCENE_LANG" > $LOADER/run/options.txt; fi
fi
# no movement tutorial toast over the screenshots
if [ -f $LOADER/run/options.txt ] && grep -q '^tutorialStep:' $LOADER/run/options.txt; then
	sed -i 's/^tutorialStep:.*/tutorialStep:none/' $LOADER/run/options.txt
else
	echo "tutorialStep:none" >> $LOADER/run/options.txt
fi
find $LOADER/run/screenshots -name 'scene_*.png' -delete 2>/dev/null
run_watched "$LOG" 900 ./gradlew :$LOADER:runScene --console=plain "$@"
code=$?
kill_dev
grep -E '\[scene\] ' $LOG | sed -E 's/^.*\[scene\] //' | grep -v '^tick' | head -40
echo "--- errors"
grep -E '/(ERROR|FATAL)\]|\[.*(ERROR|FATAL).*\]|Caused by|Exception' $LOG | sed -E 's/^\[[^]]*\] //' | cut -c1-250 | sort | uniq -c | sort -rn | head -25
exit $code
}
