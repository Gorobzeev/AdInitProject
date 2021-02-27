FROM openjdk:11-jdk-slim

WORKDIR /opt/app

EXPOSE 1090 8090

CMD ["java", "-agentlib:jdwp=transport=dt_socket,address=*:1090,server=y,suspend=n", "-Xmx512m", "-jar", "./mobile-service.jar"]

ADD ./target/mobile-service.jar ./
