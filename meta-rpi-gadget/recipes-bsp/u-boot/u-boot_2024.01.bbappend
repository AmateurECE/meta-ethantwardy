FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append:netboot = " \
${@ "file://serverip.cfg" if d.getVar('NETBOOT_SERVER_IP') else ""} \
"
