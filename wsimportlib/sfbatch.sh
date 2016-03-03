#!/bin/sh
echo "STARTING SF BATCH"
cd /home/ubuntu/sfclient
java -cp lib/jaxws-rt-2.1.7.jar:lib/getprocid.jar:lib/stax-ex-1.2.jar:lib/jaxb-impl-2.2.6.jar:lib/mysql-connector-java-5.1.10.jar:lib/streambuffer-0.9.jar:lib/enterprise.jar:lib/sfclient.jar com.topera.sfclient.SFClient
echo "SF BATCH FINISHED"
