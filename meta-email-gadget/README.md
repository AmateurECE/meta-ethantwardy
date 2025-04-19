# Provisioning an Email System

1. Create a btrfs partition with label `gadget-data`:
  `mkfs.btrfs -L gadget-data /dev/mmcblkXp1`
1. Create subvolumes:

```
btrfs subvolume create /mnt/@{etc,var,home}
mkdir /mnt/@{etc,var}/.upper /mnt/@{etc,var}/.work /mnt/@home/root
```

1. Setup `/etc/network/interfaces`.
1. Provision the rest of the data:
```
ln -sf /usr/share/zoneinfo/America/Chicago /etc/localtime

echo 'mail.domain.com' > /etc/hostname

# Set up local forwarders to improve DNS performance
echo 'forwarders { 10.0.1.3; };' >/etc/bind/named.options.local

touch /etc/aliases
touch /etc/aliases.db
newaliases

touch /etc/postfix/virtual_alias
postmap /etc/postfix/virtual_alias

touch /etc/postfix/internal_recipient
postmap /etc/postfix/internal_recipient

# Setup postfix configuration
mkdir -p /etc/gadget
cat - >/etc/gadget/main.cf.vars.m4 <<EOF
> changequote([, ])
> define([MYHOSTNAME], [mail.domain.com])
> define([MYDOMAIN], [domain.com])
> EOF

# Install TLS certificates. The private key needs to be unencrypted!
mkdir -p /etc/gadget/tls
install -Dm600 <source> /etc/gadget/tls/privkey.pem
install -m600 <source> /etc/gadget/tls/fullchain.pem
```

1. Local user provisioning

```
adduser ethantwardy
# Add the ethantwardy user to the sudo group (this command may not work, so
# manual finagling of /etc/group may be required)
adduser sudo ethantwardy

mkdir -p /home/edtwardy/Maildir

# Forward mail from root to ethantwardy
echo 'root: ethantwardy' >/etc/aliases
postalias /etc/aliases

# Setup virtual users
echo 'ethantwardy@domain.com ethantwardy@domain.com' \
  > /etc/postfix/virtual
cat - >/etc/postfix/virtual_alias <<EOF
> postmaster@domain.com ethantwardy@domain.com
> hostmaster@domain.com ethantwardy@domain.com
> webmaster@domain.com ethantwardy@domain.com
> abuse@domain.com ethantwardy@domain.com
> EOF

postmap /etc/postfix/{virtual,virtual_alias}

echo 'etwardy@domain.com:@password@::::' >/etc/dovecot/passwd
echo 's/@password@/'$(doveadm pw -s blf-crypt)'/' \
  | xargs -I{} sed -i -e '{}' /etc/dovecot/passwd

# Only allow user ethantwardy to log in over SSH
echo 'AllowUsers ethantwardy' >/etc/ssh/sshd_config.d/local.conf

# To disable root login, make sure the second field in /etc/shadow is '*'
grep 'root' /etc/shadow
root:*:15069:0:99999:7:::
```

# Setting up the Test Environment

1. Create a test image: `truncate -s 256M email-data/ethantwardy.com.img`
1. Create a GPT label and one primary partition to contain a btrfs filesystem.
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

# Need to configure the reverse DNS lookup zone for the network:
cat - >/etc/bind/named.conf.local <<EOF
> zone "1.0.10.in-addr.arpa" {
>     type forward;
>     forward only;
>     forwarders { 10.0.1.3; };
> };
> EOF
```

## Setup the Test DNS Nameserver

1. Configure `/etc/network/interfaces` to set a static IP address of
   `10.0.1.3`.

```
# Set up DNS zones for all the VM's.
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

# The reverse DNS record
cat - >/etc/bind/db.10.0.1 <<EOF
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
> ; Need a PTR record for each host in this network.
> 1   IN  PTR ethantwardy.com.
> EOF

# The other VM's IP address is 10.0.1.2.
cat - >/etc/bind/named.conf.local <<EOF
> zone "ethantwardy.com" {
>     type master;
>     file "/etc/bind/db.ethantwardy.com";
> };
>
> zone "1.0.10.in-addr.arpa" {
>     type master;
>     file "/etc/bind/db.10.0.1";
> };
> EOF
```
