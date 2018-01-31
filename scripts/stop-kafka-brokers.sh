#!/bin/bash
cd /opt/kafka
export MESOS_NATIVE_JAVA_LIBRARY=/usr/local/lib/libmesos.so
/opt/kafka/kafka-mesos.sh broker stop 0
/opt/kafka/kafka-mesos.sh broker stop 1
/opt/kafka/kafka-mesos.sh broker stop 2
/opt/kafka/kafka-mesos.sh broker list
