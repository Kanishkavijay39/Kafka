package com.example.kafkabasics.producer;


import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProducerDemo {
  private static final Logger Log = LoggerFactory.getLogger(ProducerDemo.class.getSimpleName());
  

public void kafkaTry(){
  Properties properties=new Properties();
  properties.setProperty ("bootstrap.servers", "127.0.0.1:9092");
  // set producer properties
  properties.setProperty ("key.serializer", StringSerializer.class.getName());
  properties.setProperty ("value.serializer", StringSerializer.class.getName());
// create the Producer
  KafkaProducer<String, String> producer = new KafkaProducer<> (properties);
// create a Producer Record
  ProducerRecord<String, String> producerRecord =
      new ProducerRecord<>( "first-topic","hello world");
// send data
  producer.send(producerRecord);
// flush and close the producer
// tell the producer to send all dat
  producer.flush ();
  producer.close();
  
}
  
}
