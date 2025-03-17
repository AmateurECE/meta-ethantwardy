# Provisioning and Email System

Initialize the aliases files:

1. Create a btrfs partition with label `gadget-data`:
  `mkfs.btrfs -L gadget-data /dev/mmcblkXp1`
1. Create subvolumes:

```
btrfs subvolume create /mnt/@etc
mkdir /mnt/@etc/.upper /mnt/@etc/.work
btrfs subvolume create /mnt/@var
mkdir /mnt/@var/.upper /mnt/@var/.work
btrfs subvolume create /mnt/@home
mkdir /mnt/@home/root
```

1. Setup `/etc/network/interfaces`.
1. Provision the rest of the data:
```
echo 'mail.domain.com' > /etc/hostname

# Set up local forwarders to improve DNS performance
echo 'forwarders { 10.0.2.3; };' >/etc/bind/named.options.local

touch /etc/aliases
touch /etc/aliases.db
newaliases
touch /etc/postfix/virtual_alias
postmap /etc/postfix/virtual_alias

# Setup postfix configuration
mkdir -p /etc/gadget
cat - >/etc/gadget/main.cf.vars.m4 <<EOF
> changequote([, ])
> define([MYHOSTNAME], [mail.domain.com])
> define([MYDOMAIN], [domain.com])
> EOF

# Install TLS certificates
mkdir -p /etc/gadget/tls
install -Dm600 <source> /etc/gadget/tls/privkey.pem
install -m600 <source> /etc/gadget/tls/fullchain.pem
```

1. Local user provisioning

```
# Uncomment %sudo ALL=(ALL:ALL) ALL /etc/sudoers
adduser ethantwardy
adduser ethantwardy sudo
cat - >>/etc/aliases <<EOF
> root: ethantwardy
> EOF
postalias /etc/aliases
```

# Setting up the Test Environment

1. Create a test image: `truncate -s 256M email-data/ethantwardy.com.img`
1. Create a GPT label and one primary partition to contain a btrfs partition.
1. Follow the data partition setup instructions in the previous section.
1. Setup the rest of the data:

```
# Setup /etc/network/interfaces
cat - >/etc/network/interfaces <<EOF
> auto lo
> iface lo inet loopback
> 
> # WAN interface
> auto eth0
> iface eth0 inet static
> address 10.0.2.10
> netmask 255.255.255.0
> gateway 10.0.2.2
> 
> # LAN interface
> auto eth1
> iface eth1 inet static
> address 10.0.1.1
> netmask 255.255.255.0
> EOF

# The QEMU image removes allow-query from the bind configuration in the rootfs.
# Add it to the local configuration.
echo 'allow-query { 10.0.1.0/24; 127.0.0.1; };' >>/etc/bind/named.options.local

# Set up DNS zones for all the VM's. In this case, this VM is authoritative
# over the domain ethantwardy.com, and another VM on the network is
# authoritative over metalworkcpp.org.
cat - >/etc/bind/db.ethantwardy.com <<EOF
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
> ns1 IN  A   10.0.1.1
> @   IN  A   10.0.1.1
> mail IN A   10.0.1.1
> EOF

# The other VM's IP address is 10.0.1.2.
cat - >/etc/bind/named.conf.local <<EOF
> zone "ethantwardy.com" {
>     type master;
>     file "/etc/bind/db.ethantwardy.com";
> };
>
> zone "metalworkcpp.org" {
>     type forward;
>     forwarders { 10.0.1.2; };
> };
> EOF
```
