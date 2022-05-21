FROM adoptopenjdk/openjdk11
LABEL maintainer="shrestha.dikeshny@gmail.com"
ARG ARTIFACT_NAME
EXPOSE 8080
ADD target/${ARTIFACT_NAME}*.jar app.jar
COPY start.sh ./start.sh
RUN chmod +x ./start.sh
ENTRYPOINT ["/bin/bash", "./start.sh"]