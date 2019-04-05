FROM armv7/armhf-java8
VOLUME /tmp
COPY ./target/server-app-jar-with-dependencies.jar /launch.jar
ENTRYPOINT ["java", "-Dpi4j.linking=dynamic", "-jar","/launch.jar"]