FROM maven:3.8.2-jdk-8
WORKDIR /challengeMeli
COPY . /challengeMeli
RUN mvn package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java","-jar","./target/challengeMeli-0.0.1-SNAPSHOT.jar"]

