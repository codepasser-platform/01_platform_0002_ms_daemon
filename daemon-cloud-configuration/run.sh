echo "start codepasser io configuration server ..."
nohup mvn clean spring-boot:run -Dspring.profiles.active=native 1> ./logs/cloud-server-configuration.out 2> ./logs/cloud-server-configuration.err &
tail -fn 100 ./logs/cloud-server-configuration.out
