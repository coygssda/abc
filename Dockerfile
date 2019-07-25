FROM openjdk:8
EXPOSE 8085 
ADD target/wallet-system-0.0.1-SNAPSHOT.jar wallet-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/wallet-system-0.0.1-SNAPSHOT.jar"]