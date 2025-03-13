# Provisioning and Email System

Initialize the aliases files:

```
# TODO: create /data/network/interfaces
echo 'mail.domain.com' > /data/network/hostname

# Set up local forwarders to improve DNS performance
echo 'forwarders { 10.0.2.3; };' >/data/network/named.options.local

# This file just needs to exist.
touch /data/network/named.conf.local

mkdir /data/mail
touch /data/mail/aliases
touch /data/mail/aliases.db
touch /data/mail/virtual_alias
newaliases
postmap /etc/postfix/virtual_alias
mkdir -p /data/mail/spool/postfix

# Setup postfix configuration
cat - >/data/mail/main.cf.vars.m4 <<EOF
> changequote([, ])
> define([MYHOSTNAME], [mail.domain.com])
> define([MYDOMAIN], [domain.com])
> EOF

# Home directory setup
mkdir -p /data/home/root

# Install TLS certificates
install -Dm600 <source> /data/mail/tls/privkey.pem
install -m600 <source> /data/mail/tls/fullchain.pem
```

# Setting up the Test Environment

```
# Make a configuration
# Set up a test image
truncate -s 256M email-data/ethantwardy.com.img
losetup -P -f email-data/ethantwardy.com.img
fdisk /dev/loop0
mkfs.btrfs /dev/loop0p1

# The QEMU image removes allow-query from the bind configuration in the rootfs.
# Add it to the local configuration.
echo 'allow-query { 10.0.1.0/24; 127.0.0.1; };' >>/data/network/named.conf.local

# Set up DNS zones for all the VM's. In this case, this VM is authoritative
# over the domain ethantwardy.com, and another VM on the network is
# authoritative over metalworkcpp.org.
cat - >/data/network/db.ethantwardy.com <<EOF
> ; BIND zone file for ethantwardy.com
> $TTL 86400
> @   IN  SOA ns1.ethantwardy.com. admin.ethantwardy.com. (
>         2025031201 ; Serial
>         3600       ; Refresh
>         1800       ; Retry
>         604800     ; Expire
>         86400      ; Minimum TTL
> )
>
> ; Nameservers
> @   IN  NS  ns1.ethantwardy.com.
>
> ; A records
> ns1 IN  A   10.0.1.2
> @   IN  A   10.0.1.2
> EOF

# The other VM's IP address is 10.0.1.2.
cat - >/data/network/named.conf.local <<EOF
> zone "ethantwardy.com" {
>     type master;
>     file "/data/network/db.ethantwardy.com";
> };
>
> zone "metalworkcpp.org" {
>     type forward;
>     forwarders { 10.0.1.2; };
> };
> EOF
```
