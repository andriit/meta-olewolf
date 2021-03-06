MAINTAINER = "Ole Wolf <ole@naturloven.dk>"
HOMEPAGE = "http://www.harding.motd.ca/autossh/"
SUMMARY = "Automatically restart SSH sessions and tunnels"
DESCRIPTION = "autossh is a program to start a copy of ssh and monitor it, restarting it as necessary should it die or stop passing traffic. The idea is from rstunnel (Reliable SSH Tunnel), but implemented in C."
PROVIDES = "autossh"
PR = "r1"

SRC_URI = " \
	http://www.harding.motd.ca/autossh/autossh-1.4c.tgz \
	file://01fix-rpl-malloc.patch \
"
SRC_URI[md5sum] = "26520eea934f296be0783dabe7fcfd28"
SRC_URI[sha256sum] = "6fcaba6a409a46bdf832086736bb8f09d245ebce11027f41d39588a95dc7fd1d"
LICENSE = "Distributable"
LIC_FILES_CHKSUM = "file://autossh.spec;md5=80a6701134723fd3420e733b46a0eb97"
S = "${WORKDIR}/autossh-1.4c/"

RDEPENDS_${PN} += "ssh"

FILES_${PN} = " \
	/bin/autossh \
"

inherit autotools

do_configure () {
	./configure --host=${MACHINE} --prefix=${D}
}

do_install () {
	install -m 0755 -D ${S}autossh ${D}bin
}
