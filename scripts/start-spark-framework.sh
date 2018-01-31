#!/bin/bash
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
/opt/spark/sbin/start-mesos-dispatcher.sh --master mesos://zk://worker01:2181,worker02:2181,worker03:2181/mesos
