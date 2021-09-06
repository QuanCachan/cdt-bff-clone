#OpenJ9 recommended for Spring app
ARG OPENJDK_IMAGE="adoptopenjdk/openjdk14-openj9:jdk-14.0.2_12_openj9-0.21.0-alpine@sha256:69570df9e04637e50c26bf218dda6ac331aa9f94601fae55d655e95c38966f0b"
#layer-kraft.registry.saas.cagip.group.gca/adoptopenjdk-openj9/openjdk14:jdk-14.0.2_12_openj9-0.21.0-alpine # <- CACF
FROM $OPENJDK_IMAGE as layers
ARG JAR_FILE="./target/*.jar"
WORKDIR /workspace
ADD ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM $OPENJDK_IMAGE as runtime
ENV TZ "Europe/paris"
ENV RUN_UNSECURE=false

ARG VERSION
ARG uid=65534
ARG gid=65534
ARG user=nobody
ARG group=nogroup

# CACF
LABEL "sofinco-api.cdt/cdt-bff.version"=$VERSION \
      "sofinco-api.cdt/cdt-bff.contact"=developer@cacd2.fr

#RUN keytool -importkeystore -srckeystore $TRUSTSTORE_PATH -srcstorepass $TRUSTSTORE_PASSWORD -destkeystore $JAVA_HOME/lib/security/cacerts -deststorepass changeit -noprompt # CACF

# Expose API port
ENV HTTP_PORT=8080
EXPOSE $HTTP_PORT
# Add entrypoint to rewrite configuration at launch time
#ENV APP_OPTIONS="--spring.profiles.active=secrets --spring.config.additional-location=file:/run/secrets/"
COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh && \
    chown -R ${user}:${group} /docker-entrypoint.sh

# Run as user
USER ${user}

ENTRYPOINT ["/docker-entrypoint.sh"]

# Copy the extracted layers
COPY --from=layers /workspace/dependencies/ ./
COPY --from=layers /workspace/spring-boot-loader ./
COPY --from=layers /workspace/snapshot-dependencies/ ./
COPY --from=layers /workspace/application/ ./
#CMD ["sh", "-c", "java", "${JAVA_OPTS} -Djavax.net.ssl.trustStore=$TRUSTSTORE_PATH -Djavax.net.ssl.trustStorePassword=$TRUSTSTORE_PASSWORD", "org.springframework.boot.loader.JarLauncher", "$APP_OPTIONS ${0} ${@}"]
CMD ["sh", "-c", "java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher ${APP_OPTIONS} ${0} ${@}"]