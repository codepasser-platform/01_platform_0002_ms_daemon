echo "start codepasser io registry server ..."
nohup mvn clean spring-boot:run -Dcloud.configuration.uri=http://10.192.8.161:8888 1> ./logs/cloud-server-registry.out 2> ./logs/cloud-server-registry.err &
tail -fn 100 ./logs/cloud-server-registry.out
