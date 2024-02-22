package com.example.kafkabasics.producer;


import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.kafkabasics.consumer.ConsumerDemo;

@Component
public class ProducerDemoKey {

  private static final Logger Log = LoggerFactory.getLogger(
      ProducerDemoKey.class.getSimpleName());

  
  @Autowired
  ConsumerDemo consumerDemo;

  public static void main(String[] args) {
    Properties properties = new Properties();
    properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
    // set producer properties
    properties.setProperty("key.serializer", StringSerializer.class.getName());
    properties.setProperty("value.serializer", StringSerializer.class.getName());
// create the Producer
    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

    properties.setProperty ("batch.size", "400");
//    properties.setProperty ("partitioner.class", RoundRobinPartitioner.class.getName());
    
    
    for(int j=0;j<2;j++) {
      for (int i = 0; i < 50; i++) {
        String key="_id"+i;
        // create a Producer Record
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first-topic","_id"+i,
            "hello world" + i);
// send data
        // send data
        producer.send(producerRecord, new Callback() {
          @Override
          public void onCompletion(RecordMetadata metadata, Exception e) {
// executes every time a record successfully sent or an exception is thrown
            if (e == null) {
              
// the record was successfully sent
              Log.info(
                    "key: " + key + "\n" + "Partition: "
                      + metadata.partition() + "\n" + "Offset: " + metadata.offset() + "\n"
                      + "Timestamp: "
                      + metadata.timestamp());
            } else {
              Log.error("Error while producing data" + e);
            }
          }
        });

      }
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
// flush and close the producer
// tell the producer to send all dat
    producer.flush();
    producer.close();

//    consumerDemo.tryy();
  }
}