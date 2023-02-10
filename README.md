# kafka-connector
---
The kafka-connector allows you to listen to topic(s) in a Kafka cluster. When messages are available it transforms them into the format requested from the dispatcher-service of Behooked application. Subsequently the kafka-connector sends an event-notification via a HTTP call to the dispatcher-service.

## How to start the kafka-connector:
---

1. Run `java -jar target/kafka-connector-1.0-SNAPSHOT.jar KAFKA-CLUSTER-TO-CONNECT-TO  TOPIC-NAME`
