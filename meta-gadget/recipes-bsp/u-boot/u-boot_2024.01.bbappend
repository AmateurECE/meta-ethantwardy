FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:netboot = " \
${@ "file://serverip.cfg" if d.getVar('NETBOOT_TFTP_HOST') else ""} \
${@ "file://tftpport.cfg" if ':' in d.getVar('NETBOOT_TFTP_HOST') else "" } \
"
