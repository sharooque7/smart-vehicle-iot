# First stage: Build dependencies
FROM ubuntu:20.04 AS builder


RUN apt-get update && apt-get install -y netcat

# Install unzip
RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*

# Install necessary packages for building
RUN apt-get update && apt-get install -y \
    openjdk-11-jre-headless \
    wget \
    tar \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables
ENV KAFKA_VERSION=3.8.0
ENV KAFKA_HOME=/opt/kafka
ENV TDENGINE_DIR=TDengineClient


# Download and install Kafka
RUN wget https://downloads.apache.org/kafka/$KAFKA_VERSION/kafka_2.13-$KAFKA_VERSION.tgz && \
    tar -xzf kafka_2.13-$KAFKA_VERSION.tgz -C /opt/ && \
    mv /opt/kafka_2.13-$KAFKA_VERSION $KAFKA_HOME && \
    rm kafka_2.13-$KAFKA_VERSION.tgz

# Create directories for connectors
RUN mkdir -p /opt/kafka/components/mongodb-kafka-connect-mongodb-1.13.0 \
    /opt/kafka/components/taosdata-kafka-connect-tdengine-1.1.6 \
    $TDENGINE_DIR

COPY connectors/mongodb-kafka-connect-mongodb-1.13.0.zip /opt/kafka/components/
RUN unzip /opt/kafka/components/mongodb-kafka-connect-mongodb-1.13.0.zip -d /opt/kafka/components/
RUN rm -rf /opt/kafka/components/mongodb-kafka-connect-mongodb-1.13.0.zip
COPY connectors/jars/taosdata-kafka-connect-tdengine-1.1.6/ /opt/kafka/components/taosdata-kafka-connect-tdengine-1.1.6/

# Copy and extract the TDengine client tar.gz file
COPY connectors/TDengine-client-3.2.3.0-Linux-x64.tar.gz $TDENGINE_DIR/
RUN tar -zxvf $TDENGINE_DIR/TDengine-client-3.2.3.0-Linux-x64.tar.gz -C $TDENGINE_DIR && \
    rm $TDENGINE_DIR/TDengine-client-3.2.3.0-Linux-x64.tar.gz


FROM ubuntu:20.04

# Install necessary runtime packages
RUN apt-get update && apt-get install -y \
    openjdk-11-jre-headless \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables
ENV KAFKA_HOME=/opt/kafka
ENV KAFKA_CONNECT_DIR=/opt/kafka/config
ENV TDENGINE_DIR=TDengineClient

# WORKDIR /app
WORKDIR /app

COPY wait_for_kafka.sh .

# Copy Kafka installation from the builder stage
COPY --from=builder $KAFKA_HOME $KAFKA_HOME
COPY --from=builder $TDENGINE_DIR $TDENGINE_DIR

COPY server.properties .
COPY connect-distributed.properties .

WORKDIR /app/TDengineClient/TDengine-client-3.2.3.0
RUN chmod +x install_client.sh && ./install_client.sh

# Expose the Kafka Connect port
EXPOSE 8083
EXPOSE 9092
EXPOSE 9094

CMD sh -c "$KAFKA_HOME/bin/kafka-storage.sh format -t tdTf08JbSpGbG6b4pLo_Lw -c /app/server.properties && \
$KAFKA_HOME/bin/kafka-server-start.sh /app/server.properties & \
/app/wait_for_kafka.sh  && \
$KAFKA_HOME/bin/connect-distributed.sh /app/connect-distributed.properties"
