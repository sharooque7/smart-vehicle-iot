#!/bin/sh

# Wait for Kafka to be ready
while true; do
    if $KAFKA_HOME/bin/kafka-topics.sh --list --bootstrap-server localhost:9092; then
        echo "Kafka is ready!"
        break
    else
        echo "Waiting for Kafka broker to be ready..."
        sleep 5
    fi
done
