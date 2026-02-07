# Adjust startup order so that iwd starts before ifup runs.
INITSCRIPT_PARAMS = "start 01 5 2 3 . stop 99 0 1 6 ."
