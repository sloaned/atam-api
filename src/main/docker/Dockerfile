FROM catalystdevlabs/centos7-java8:latest
MAINTAINER catalystdevlabs <philario@catalystdevworks.com>

# Add Compiled Spring Boot Package
VOLUME /tmp
ADD aperture-api.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

