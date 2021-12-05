# Have to fool rpi-config recipe into thinking that vc4graphics are not enabled
# for this machine to avoid some issues around 3.5mm TRRS audio performance on
# some kernels. See: https://github.com/raspberrypi/linux/issues/3181
MACHINE_FEATURES:remove = "vc4graphics"
