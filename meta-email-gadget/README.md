# Provisioning and Email System

Initialize the aliases files:

```
# TODO: create /data/network/interfaces
echo 'mail.domain.com' > /data/network/hostname

# Set up local forwarders to improve DNS performance
cat - >/data/network/named.options.local <<EOF
> options {
>     forwarders {
>         10.0.2.3
>     }
> }
> EOF

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
