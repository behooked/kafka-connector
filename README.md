# kafka-connector

The kafka-connector allows you to listen to topic(s) in a Kafka cluster. When messages are available it transforms the data into the format requested from Behooked Dispatcher API and sends it to the Dispatcher Service of Behooked Application.
---
## How to start the kafka-connector:
---

1. Run `java -jar target/kafka-connector-1.0-SNAPSHOT.jar KAFKA-CLUSTER-TO-CONNECT-TO  TOPIC-NAME`
