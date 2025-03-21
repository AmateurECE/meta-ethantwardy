compatibility_level = 3.10
smtputf8_enable = no

# Domain
myhostname = MYHOSTNAME
mydomain = MYDOMAIN
mydestination = $myhostname localhost.$mydomain localhost
mynetworks = 127.0.0.0/8
mynetworks_style = host

# TLS
smtpd_tls_chain_files =
    /etc/gadget/tls/privkey.pem,
    /etc/gadget/tls/fullchain.pem

# Offer TLS and use it when available
smtp_tls_security_level = may
smtpd_tls_security_level = may

# Aliases
alias_maps = hash:/etc/aliases
alias_database = hash:/etc/aliases

# Virtual Domain Configuration
# virtual_mailbox_domains = sample.com, other.net
# virtual_mailbox_maps = hash:/etc/postfix/virtual
# virtual_alias_maps = hash:/etc/postfix/virtual_alias

# You'll start with the following lines for maildir storage
virtual_mailbox_base = /var/spool/vmail
virtual_uid_maps = static:`grep vmail /etc/passwd | cut -d ":" -f 3`
virtual_gid_maps = static:`grep vmail /etc/passwd | cut -d ":" -f 4`


# You'll start with the following lines for IMAP storage
#virtual_transport = lmtp:unix:/var/lib/cyrus/socket/lmtp


# Path configuration
sample_directory = /etc/postfix
queue_directory = /var/spool/postfix
mail_spool_directory = /var/spool/mail
readme_directory = no
command_directory = /usr/sbin
daemon_directory = /usr/libexec/postfix
mail_owner = postfix
setgid_group = postdrop
unknown_local_recipient_reject_code = 450
debug_peer_level = 2
sendmail_path = /usr/sbin/sendmail
newaliases_path = /usr/bin/newaliases
mailq_path = /usr/bin/mailq
home_mailbox = Maildir/

# SMTPD Configuration
smtpd_data_restrictions =
        permit_mynetworks,
        reject_unauth_pipelining,
        permit

smtpd_client_restrictions =
        permit_mynetworks,
        # reject_unknown_client, # This can cause a lot of false rejects.
        reject_invalid_hostname,
        reject_rbl_client list.dsbl.org,
        reject_rbl_client sbl.spamhaus.org,
        reject_rbl_client cbl.abuseat.org,
        reject_rbl_client dul.dnsbl.sorbs.net,
        permit

smtpd_helo_required = yes
smtpd_helo_restrictions =
        permit_mynetworks,
        reject_unauth_pipelining,
        # reject_non_fqdn_hostname, # This can cause a lot of false rejects.
        # reject_unknown_hostname, # This can cause a lot of false rejects.
        reject_invalid_hostname,
        permit

smtpd_sender_restrictions =
        permit_mynetworks,
        reject_non_fqdn_sender,
        # check_sender_access hash:/etc/postfix/access_domains,
        reject_unknown_sender_domain,
        permit

smtpd_recipient_restrictions =
        permit_mynetworks,
        permit_sasl_authenticated,
        reject_unauth_destination,

        # check_recipient_access pcre:/etc/postfix/recipient_checks.pcre,
        # check_helo_access pcre:/etc/postfix/helo_checks.pcre,

        # check_client_access hash:/etc/postfix/maps/access_client,
        # check_client_access hash:/etc/postfix/maps/exceptions_client,
        # check_helo_access hash:/etc/postfix/maps/access_helo,
        # check_helo_access hash:/etc/postfix/maps/verify_helo,
        # check_sender_access hash:/etc/postfix/maps/access_sender,
        # check_sender_access hash:/etc/postfix/maps/verify_sender,
        # check_recipient_access hash:/etc/postfix/maps/access_recipient,

        # reject_multi_recipient_bounce,
        reject_non_fqdn_recipient,
        reject_unknown_recipient_domain,
        # reject_unlisted_recipient,
        #check_policy_service unix:private/policy,

        # check_sender_access hash:/etc/postfix/maps/no_verify_sender,
        # check_sender_access hash:/etc/postfix/access_domains,
        # reject_unverified_sender,
        # reject_unverified_recipient
        check_recipient_access hash:/etc/postfix/internal_recipient

disable_vrfy_command = yes
