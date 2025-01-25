do_install:append() {
    # Remove /etc/network/interfaces, this file is instead provided by a
    # populate-volatiles configuration that links it into the data drive.
    rm -f ${D}/etc/network/interfaces
}
