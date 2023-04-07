FROM openjdk:11-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/mything-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Mything.jar
ENTRYPOINT [ "java", "-jar", "/Mything.jar" ]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata
