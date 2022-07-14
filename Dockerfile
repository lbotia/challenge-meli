FROM maven:3.8.2-jdk-8
WORKDIR /challenge-meli
COPY . /challenge-meli
RUN mvn package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/challenge-meli-0.0.1-SNAPSHOT.jar"]

