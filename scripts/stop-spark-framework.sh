#!/bin/bash
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
/opt/spark/sbin/stop-mesos-dispatcher.sh
