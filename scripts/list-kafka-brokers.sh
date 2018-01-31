#!/bin/bash
cd /opt/kafka
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
/opt/kafka/kafka-mesos.sh broker list
