FROM openjdk:11

COPY target/Entrepreneur-0.0.1-SNAPSHOT.jar /Entrepreneur.jar

CMD ["java" ,"-jar","Entrepreneur.jar"]