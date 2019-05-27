echo "start codepasser io web api server ..."
nohup mvn clean spring-boot:run -Dcloud.configuration.uri=http://10.192.8.161:8888 -Dcloud.registry.uri=http://eureka:eureka_pw@10.192.8.161:8761/eureka -Dspring.profiles.active=uat 1> ./logs/base-web.out 2> ./logs/base-web.err &
tail -fn 100 ./logs/base-web.out
