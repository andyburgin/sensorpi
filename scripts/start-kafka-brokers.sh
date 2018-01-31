#!/bin/bash
cd /opt/kafka
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
/opt/kafka/kafka-mesos.sh broker start 0
/opt/kafka/kafka-mesos.sh broker start 1
/opt/kafka/kafka-mesos.sh broker start 2
/opt/kafka/kafka-mesos.sh broker list
