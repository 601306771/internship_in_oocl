FROM jeanblanchard/java:8

RUN apk upgrade --update && \
    apk add bash
RUN mkdir /data
COPY build/libs/snakebox-SNAPSHOT.jar /data/snakebox-SNAPSHOT.jar
COPY application.yml /data/application.yml

WORKDIR /data

ENTRYPOINT ["java","-jar","snakebox-SNAPSHOT.jar"]
CMD ["java","-jar","snakebox-SNAPSHOT.jar"]
EXPOSE 9000
