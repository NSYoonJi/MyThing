FROM openjdk:11-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/mything-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} Mything.jar
ENTRYPOINT [ "java", "-jar", "/Mything.jar" ]
ARG DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata

# ./gradlew clean build 먼저 시행
# docker build --tag gangesriver/mything:0.1 .
# docker push gangesriver/mything:0.1
# docker pull gangesriver/mything:0.1
# docker run --rm -d -p 8000:8000 --network ecommerce-network --name mything