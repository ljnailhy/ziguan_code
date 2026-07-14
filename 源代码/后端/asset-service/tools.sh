#!/bin/sh

case "$1" in
 start)
  case "$2" in
   dev | test | demo | sit | uat)
    java -Xms128m -Xmx512m -Dlog.level=info -Dlog.level.platform=info -Dfile.encoding=utf-8 -Dspring.profiles.active="$2" -jar ./asset-service.jar >/dev/null 2>&1 &
   ;;
   prod)
    java -Xms256m -Xmx1g -Dlog.level=error -Dlog.level.platform=warn -Dfile.encoding=utf-8 -Dspring.profiles.active="$2" -jar ./asset-service.jar >/dev/null 2>&1 &
   ;;
   *)
#    printf 'append active profile: %s {dev|test|demo|sit|uat|prod}\n' "$prog"
#    exit 1
    java -Xms256m -Xmx1g -Dlog.level=error -Dlog.level.platform=warn -Dfile.encoding=utf-8 -Dspring.profiles.active="$2" -jar ./asset-service.jar >/dev/null 2>&1 &
   ;;
  esac
 ;;
 stop)
  pgrep -f asset-service.jar|while read pid
  do
    kill -15 $pid
  done
 ;;
 *)
  printf 'Usage: %s {start|stop}\n' "$prog"
  exit 1
 ;;
esac
