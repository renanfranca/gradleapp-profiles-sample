FROM openjdk:21-slim
COPY . /code/jhipster-app/
RUN \
    cd /code/jhipster-app/ && \
    rm -Rf build node_modules && \
    chmod +x gradlew && \
    sleep 1 && \
    ./gradlew assemble -x test && \
    mv /code/jhipster-app/build/libs/*.jar /code/ && \
    rm -Rf /code/jhipster-app/ /root/.gradle /root/.cache /tmp/* /var/tmp/*

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""
CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /code/*.jar
EXPOSE 8081
