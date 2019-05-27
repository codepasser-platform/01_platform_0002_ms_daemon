> DEV
-Dcloud.configuration.uri=http://127.0.0.1:8888
-Dcloud.registry.uri=http://eureka:eureka_pw@127.0.0.1:8761/eureka
-Dservice.base.port=9001
-Dservice.base.version=1.0.0.RELEASE
-DMACHINE_ID=00
-Dspring.profiles.active=

> UAT
-Dcloud.configuration.uri=http://172.16.20.121:8888
-Dcloud.registry.uri=http://eureka:eureka_pw@172.16.20.121:8761/eureka
-Dservice.base.port=9001
-Dservice.base.version=1.0.0.RELEASE
-DMACHINE_ID=01
-Dspring.profiles.active=uat

> Initializer
-Dspring.profiles.active=initialize
