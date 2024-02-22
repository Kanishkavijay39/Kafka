package com.example.kafkabasics.consumer;


import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDemo {

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

//    properties.setProperty ("batch.size", "400");
//    properties.setProperty ("partitioner.class", RoundRobinPartitioner.class.getName());
    
    consumer.subscribe(Arrays.asList("first-topic"));
    
  while(true){
    Log.info("Polling");
    
    ConsumerRecords<String,String> records =
        consumer.poll (Duration.ofMillis(1000));
    
    for (ConsumerRecord <String, String> record: records) {
      Log.info("Key: " + record.key() + ", Value: " +record.value());
      Log.info("Partition: " +record.partition () + " Offset: " + record. offset());
    }
    consumer.close();
  }

  }
}