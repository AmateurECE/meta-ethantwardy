# Provisioning and Email System

Initialize the aliases files:

```
mkdir /data/mail
touch /data/mail/aliases
touch /data/mail/aliases.db
touch /data/mail/virtual_alias
newaliases
postmap /etc/postfix/virtual_alias
mkdir -p /data/mail/spool/postfix

# Home directory setup
mkdir -p /data/home/root
```
