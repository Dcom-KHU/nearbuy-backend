FROM openjdk:11-jdk
ARG JAR_FILE=/build/libs/nearbuy-backend-0.0.1-SNAPSHOT
COPY ${JAR_FILE} nearbuy-backend-0.0.1-SNAPSHOT
ENTRYPOINT ["java","-jar","/nearbuy-backend-0.0.1-SNAPSHOT"]