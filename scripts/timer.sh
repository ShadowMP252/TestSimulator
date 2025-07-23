#!/bin/bash

TIMER_LOCK="/tmp/wgu_oa_timer.lock"
TARGET_PID=$1
echo $$ > "$TIMER_LOCK"

# Start of test sound
paplay /usr/share/sounds/freedesktop/stereo/complete.oga
sleep 3600

# Halfway point sound
paplay /usr/share/sounds/freedesktop/stereo/complete.oga
notify-send "Halfway Point" "You have reached the halfway point of your exam session."
sleep 2700

# 15 minutes remaining sound
for i in {1..2}; do
    paplay /usr/share/sounds/freedesktop/stereo/complete.oga
    sleep .05
done
notify-send "15 Minutes Remaining" "You have 15 minutes left in your exam session."
sleep 90

# End of test sound
for i in {1..3}; do
    paplay /usr/share/sounds/freedesktop/stereo/complete.oga
    sleep .05
done

notify-send "Time is up!" "Your exam session has ended."

kill -9 "$TARGET_PID" 2>/dev/null && echo "[Timer] Java process killed." 


rm "$TIMER_LOCK"
