package com.example.kafkabasics.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerGracefulShutDown {
  private static final Logger Log = LoggerFactory.getLogger(
      ConsumerDemo.class.getSimpleName());


  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
    // set consumer properties
    properties.setProperty("group.id", "abc");
    properties.setProperty("key.deserializer", StringDeserializer.class.getName());
    properties.setProperty("value.deserializer", StringDeserializer.class.getName());
    properties.setProperty("auto.offset.reset", "earliest");
// create the Producer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

    final Thread mainThread = Thread.currentThread();

    // adding the shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        Log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
        consumer.wakeup();

        // join the main thread to allow the execution of the code in the main thread
        try {
          mainThread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

//    properties.setProperty ("batch.size", "400");
//    properties.setProperty ("partitioner.class", RoundRobinPartitioner.class.getName());

    try {
      // subscribe to a topic
      consumer.subscribe(Arrays.asList("first-topic"));
      // poll for data
      while (true) {

        ConsumerRecords<String, String> records =
            consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
          Log.info("Key: " + record.key() + ", Value: " + record.value());
          Log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
        }

      }

    } catch (WakeupException e) {
      Log.info("Consumer is starting to shut down");
    } catch (Exception e) {
      Log.error("Unexpected exception in the consumer", e);
    } finally {
      consumer.close(); // close the consumer, this will also commit offsets
      Log.info("The consumer is now gracefully shut down");
    }

  }

}
