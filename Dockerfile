FROM adoptopenjdk/openjdk11

RUN mkdir /opt/app
COPY build/libs/lak-*.jar /opt/app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "-Dserver.port=8080", "/opt/app/app.jar"]
