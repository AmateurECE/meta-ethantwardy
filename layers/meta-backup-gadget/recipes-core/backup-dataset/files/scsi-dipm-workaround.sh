#!/bin/sh

# Two of these disks behave really poorly when DIPM is enabled. The device will
# shut down in the middle of a large bulk transfer. Disable this here to
# prevent this situation.
scsi_dipm_workaround() {
  for host in 4 5; do
    echo max_performance >/sys/class/scsi_host/host$host/link_power_management_policy
  done
}

scsi_dipm_workaround
