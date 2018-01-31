#!/bin/bash
mesos-master --log_dir=/var/log/mesos/ --work_dir=/tmp --ip=10.0.0.12 --hostname=master02 --zk=zk://worker01:2181,worker02:2181,worker03:2181/mesos --quorum=1 --registry=in_memory
