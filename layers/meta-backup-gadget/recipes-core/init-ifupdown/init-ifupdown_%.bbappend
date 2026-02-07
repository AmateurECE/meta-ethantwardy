FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

# Adjust the startup order so that iwd starts first (required in order to
# automatically connect to wireless networks on boot)
INITSCRIPT_PARAMS = "start 02 2 3 4 5 . stop 80 0 6 1 ."
