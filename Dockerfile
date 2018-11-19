FROM openjdk:10-jre-slim
ENV PORT 4567
EXPOSE 4567
COPY build/libs/gateway-*.jar /opt/app.jar
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]
