FROM eclipse-temurin:17-jre-focal
COPY build/libs/transaction-service-0.1-all.jar transaction-service-0.1-all.jar
ENTRYPOINT [ "java", "-jar","transaction-service-0.1-all.jar" ]