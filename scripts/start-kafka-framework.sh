#!/bin/bash
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
cd /opt/kafka
/opt/kafka/kafka-mesos.sh scheduler
