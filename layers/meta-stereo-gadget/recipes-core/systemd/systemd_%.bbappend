do_install:append() {
    # Create a network conf file for WiFi so that it works out of the box.
    cp ${D}/usr/lib/systemd/network/80-wifi-station.network.example \
        ${D}/usr/lib/systemd/network/80-wifi-station.network
}
