FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar

ENV BOT_NAME="test_bot"
ENV BOT_TOKEN="dfshjfdks"
ENV BOT_DB_USERNAME="jrtb_db_user"
ENV BOT_DB_PASSWORD="jrtb_db_password"

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.datasource.username=${BOT_DB_USERNAME}", "-Dspring.datasource.password=${BOT_DB_PASSWORD}", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-Dspring.profiles.active=${PROFILE}", "-jar","/app.jar"]
