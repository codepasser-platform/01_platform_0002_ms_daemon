echo "start codepasser io baser service server ..."
nohup mvn clean spring-boot:run -Dcloud.configuration.uri=http://10.192.8.161:8888 -Dcloud.registry.uri=http://eureka:eureka_pw@10.192.8.161:8761/eureka -Dspring.profiles.active=uat,initialize 1> ./logs/base-service.out 2> ./logs/base-service.err &
tail -fn 100 ./logs/base-service.out
