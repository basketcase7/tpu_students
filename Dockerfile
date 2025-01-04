FROM openjdk:21-jdk-slim
COPY ./target/tpu-0.0.1.jar /opt/service.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/students
ENV POSTGRES_USER=students
ENV POSTGRES_PASSWORD=students
EXPOSE 8080
CMD java -jar /opt/service.jar