#!/bin/bash
/etc/init.d/nfs-common restart
/etc/init.d/nfs-kernel-server restart
/etc/init.d/nfs-common status
/etc/init.d/nfs-kernel-server status
