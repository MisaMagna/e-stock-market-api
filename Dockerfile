FROM amazoncorretto:17
COPY ./target/e-stock-market-api-1.0.0.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
