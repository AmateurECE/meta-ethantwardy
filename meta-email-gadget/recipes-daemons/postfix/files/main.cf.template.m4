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
virtual_mailbox_domains = $mydomain
virtual_mailbox_base = /var/spool/vmail
virtual_mailbox_maps = hash:/etc/postfix/virtual
virtual_alias_maps = hash:/etc/postfix/virtual_alias

# Socket path is relative to queue_directory
virtual_transport = lmtp:unix:private/dovecot-lmtp

# Path configuration
sample_directory = /etc/postfix
readme_directory = no

command_directory = /usr/sbin
daemon_directory = /usr/libexec/postfix
sendmail_path = /usr/sbin/sendmail
newaliases_path = /usr/bin/newaliases
mailq_path = /usr/bin/mailq

# (Non-virtual) Mail storage
queue_directory = /var/spool/postfix
mail_spool_directory = /var/spool/mail
home_mailbox = Maildir/

# Policy Configuration
mail_owner = postfix
setgid_group = postdrop

unknown_local_recipient_reject_code = 450
debug_peer_level = 2

# SMTPD Configuration
smtpd_sasl_type = dovecot
smtpd_sasl_path = private/dovecot-sasl
smtpd_sasl_security_options = noanonymous
smtpd_sasl_local_domain = $myhostname
smtpd_sender_login_maps = $virtual_mailbox_maps

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

mua_client_restrictions =
        permit_mynetworks,
        permit_sasl_authenticated,
        reject

mua_sender_restrictions =
        reject_sender_login_mismatch

mua_recipient_restrictions =
        reject_non_fqdn_recipient,
        reject_unknown_recipient_domain,
        permit_sasl_authenticated,
        reject

disable_vrfy_command = yes
