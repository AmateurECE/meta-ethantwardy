#!/bin/sh

date=$(date +%Y-%m-%d)
BACKUP_DIR=/dataset/postgresql/${date}
mkdir -p ${BACKUP_DIR}
for db in miniflux tandoor gnucash; do
  ssh -i /etc/btrbk/id_ed25519 root@ethantwardy.lan \
    "podman exec -it internal_postgresql pg_dump -U postgres $db" \
    >${BACKUP_DIR}/${db}.sql
done
