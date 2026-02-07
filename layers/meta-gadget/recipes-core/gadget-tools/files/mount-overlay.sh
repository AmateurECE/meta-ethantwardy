#!/bin/sh
# Super useful script to mount an overlay on top of a read-only directory.

# Initialize variables
NAME=""
DIRECTORY=""

while getopts "n:d:" opt; do
  case $opt in
  n) NAME="$OPTARG" ;;
  d) DIRECTORY="$OPTARG" ;;
  *)
    echo "Usage: $0 -n <name> -d <directory>" >&2
    exit 1
    ;;
  esac
done

mkdir -p /run/${NAME}/.upper /run/${NAME}/.work
mount -t overlay \
  -o lowerdir=${DIRECTORY},upperdir=/run/${NAME}/.upper,workdir=/run/${NAME}/.work \
  overlay \
  ${DIRECTORY}
