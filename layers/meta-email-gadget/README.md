# Provisioning an Email System

1. Create a btrfs partition with label `gadget-data`:
  `mkfs.btrfs -L gadget-data /dev/mmcblkXp1`
1. Create subvolumes:

```
btrfs subvolume create /mnt/@{etc,var,home}
mkdir /mnt/@var/.upper /mnt/@var/.work /mnt/@home/root
mkdir /mnt/@etc/overlay-etc/{upper,work}
```

1. Setup `/etc/network/interfaces`.
1. Provision the rest of the data:
```
ln -sf /usr/share/zoneinfo/America/Chicago /etc/localtime

echo 'mail.domain.com' > /etc/hostname

# Set up local forwarders to improve DNS performance
echo 'forwarders { 10.0.1.3; };' >/etc/bind/named.options.local

cp /etc/postfix/aliases /etc/aliases
touch /etc/aliases.db
postalias /etc/aliases

touch /etc/postfix/virtual_alias
postmap /etc/postfix/virtual_alias

touch /etc/postfix/internal_recipient
postmap /etc/postfix/internal_recipient

# Setup postfix configuration
cat - >/etc/postfix/main.cf.local <<EOF
> myhostname = mail.domain.com
> mydomain = domain.com
> EOF

# Obtain certificates using certbot
certbot certonly --webroot -w /var/www/certbot -n --agree-tos \
  -d mail.domain.com

# Install TLS certificates. The private key needs to be unencrypted!
mkdir -p /etc/gadget/tls
ln -s /etc/letsencrypt/live/mail.domain.com/privkey.pem \
  /etc/gadget/tls/privkey.pem
ln -s /etc/letsencrypt/live/mail.domain.com/fullchain.pem \
  /etc/gadget/tls/fullchain.pem

# Renew certificates weekly
cat - >/etc/cron.weekly/renew-certificates.sh <<EOF
> #!/bin/sh
>
> exec certbot renew -w /var/www/certbot -n
EOF
chmod 0755 /etc/cron.weekly/renew-certificates.sh

mkdir -p /etc/dkimkeys/ethantwardy.com
chown -R rspamd:rspamd /etc/dkimkeys
cd /etc/dkimkeys/ethantwardy.com
rspamadm dkim_keygen -s ethantwardy -d ethantwardy.com -b 2048 \
  -k ethantwardy.privkey >ethantwardy.txt

mkdir /etc/mail
echo '<selector>._domainkey.<domain.com>' \
  '<domain.com>:<selector>:/etc/dkimkeys/<domain.com>/ethantwardy.privkey' \
  >/etc/mail/dkim.keytable
echo '*@<domain.com> <selector>._domainkey.<domain.com>' \
  >/etc/mail/dkim.signingtable
```

# Setup MTA-STS

```
cat - >/etc/nginx/conf.d/ssl.conf <<EOF
> port_in_redirect on;
>
> ssl_certificate /etc/letsencrypt/live/domain.com/fullchain.pem;
> ssl_certificate_key /etc/letsencrypt/live/domain.com/privkey.pem;
> EOF

cat - >/etc/nginx/sites-available/mta-sts.conf <<EOF
> server {
>     listen 443 ssl;
>     listen [::]:443 ssl;
>     server_name mta-sts.ethantwardy.com;
> 
>     location ^~ /.well-known/ {
>         root /var/www/mta-sts;
>         allow all;
>     }
> }
> EOF

mkdir -p /var/www/mta-sts/.well-known
cat - >/var/www/mta-sts/.well-known/mta-sts.txt <<EOF
> version: STSv1
> mode: enforce
> mx: mail.ethantwardy.com
> max_age: 86400
> EOF

ln -s ../sites-available/mta-sts.conf /etc/nginx/sites-enabled/mta-sts.conf
nginx -s reload
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

# Only allow user ethantwardy to log in over SSH (and only using a key)
# IMPORTANT: Make sure that a public key has already been installed using
# ssh-copy-id first!
cat - >/etc/ssh/sshd_config.d/local.conf <<EOF
> AllowUsers ethantwardy
> PasswordAuthentication no
> EOF

# To disable root login, make sure the second field in /etc/shadow is '*'
grep 'root' /etc/shadow
root:*:15069:0:99999:7:::

# Set the Rspamd controller passwords:
echo 'password = "'$(rspamadm pw)'"' \
    >>/etc/rspamd/local.d/worker-controller.inc
echo 'enable_password = "'$(rspamadm pw)'"' \
    >>/etc/rspamd/local.d/worker-controller.inc
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

# Migrating Users

The user database needs to be migrated after a system update. These are the
files `/etc/{group,gshadow,passwd,shadow}` and their backup files (suffixed
with `-`). To migrate a file, first check to make sure the backup file
`/etc/<file>-` is up to date:

```
root@mail:~# diff -Naur /etc/passwd /etc/passwd-
# If there are differences, copy the working file to the backup:
root@mail:~# cp /etc/passwd /etc/passwd-
```
There should now be no differences. Next, migrate users from the backup to the
new primary:
```
# If system0 contains the upgraded passwd file, mount it:
root@mail:~# mount /dev/disk/by-label/system0 /mnt/rauc/rootfs.0
root@mail:~# diff -Naur /mnt/rauc/rootfs.0/etc/passwd /etc/passwd-
--- /mnt/rauc/rootfs.0/etc/passwd
+++ /etc/passwd-
@@ -15,12 +15,11 @@
 list:x:38:38:Mailing List Manager:/var/list:/sbin/nologin
 irc:x:39:39:ircd:/run/ircd:/sbin/nologin
 _apt:x:42:65534::/nonexistent:/sbin/nologin
-dovenull:x:992:991::/usr/libexec:/sbin/nologin
-dovecot:x:993:992::/usr/libexec:/sbin/nologin
-rspamd:x:994:993::/etc/rspamd:/bin/false
-bind:x:995:994::/var/cache/bind:/bin/sh
-redis:x:996:995::/var/lib/redis:/bin/false
+dovenull:x:994:993::/usr/libexec:/sbin/nologin
+dovecot:x:995:994::/usr/libexec:/sbin/nologin
+bind:x:996:995::/var/cache/bind:/bin/sh
 sshd:x:997:996::/var/run/sshd:/bin/false
 vmail:x:998:997::/var/spool/vmail:/bin/false
 postfix:x:999:999::/var/spool/postfix:/bin/false
 nobody:x:65534:65534:nobody:/nonexistent:/sbin/nologin
+ethantwardy:x:1000:1000:Linux User,,,:/home/ethantwardy:/bin/sh
```

In this case, I only care about migrating the last line--my custom user
account:

```
root@mail:~# cp /mnt/rauc/rootfs.0/etc/passwd /data/@etc/.upper/passwd
root@mail:~# tail -n1 /etc/passwd- >>/data/@etc/.upper/passwd
root@mail:~# cp /data/\@etc/.upper/passwd /etc/passwd-
```

Repeat this procedure for each of the files.

It's often the case that BIND9's `rndc.key` has been created with a different
UID than the one it has now. This may be the case if all DNS queries to
localhost fail. When bind starts, it should give a permission denied error in
the logs to indicate this:

```
root@mail:~# rm /etc/bind/rndc.key
root@mail:~# mount -o remount /etc
root@mail:~# service bind restart
```

# Creating Mail Folders by Hand

```
cd /var/spool/vmail/<domain.com>/<user>
mkdir -m 0700 -p .Spam/{cur,new,tmp}
chmod 0700 .Spam
chown -R vmail:vmail .Spam
service dovecot restart
```
