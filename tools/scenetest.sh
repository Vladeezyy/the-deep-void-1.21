#!/bin/sh
# Runs port/DevScene (LOADER=neoforge|fabric) through the hang watchdog: Deep Void biomes and mobs, screenshots in
# <loader>/run/screenshots/scene_*.png. Extra arguments go to Gradle (-Pscene=lang). Log: build-scene-<loader>.log
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
rm -rf $LOADER/run/saves/dv_scene $LOG
find $LOADER/run/screenshots -name 'scene_*.png' -delete 2>/dev/null
run_watched "$LOG" 900 ./gradlew :$LOADER:runScene --console=plain "$@"
code=$?
kill_dev
grep -E '\[scene\] ' $LOG | sed -E 's/^.*\[scene\] //' | grep -v '^tick' | head -40
echo "--- errors"
grep -E '/(ERROR|FATAL)\]|\[.*(ERROR|FATAL).*\]|Caused by|Exception' $LOG | sed -E 's/^\[[^]]*\] //' | cut -c1-250 | sort | uniq -c | sort -rn | head -25
exit $code
}
