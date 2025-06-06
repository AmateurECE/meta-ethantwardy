#!/bin/sh

export DBUS_SESSION_BUS_ADDRESS="unix:path=/run/dbus/system_bus_socket"
export PIPEWIRE_RUNTIME_DIR=/run/pipewire

exec pipewire
