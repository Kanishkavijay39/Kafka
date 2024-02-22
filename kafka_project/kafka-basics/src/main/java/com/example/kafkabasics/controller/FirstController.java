package com.example.kafkabasics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.kafkabasics.producer.ProducerDemo;
import com.example.kafkabasics.producer.ProducerDemoCallBack;
import com.example.kafkabasics.producer.ProducerDemoKey;

@RestController
@RequestMapping(value="/")
public class FirstController {
  
  @Autowired
  ProducerDemo producerDemo;
  
  @Autowired
  ProducerDemoCallBack producerDemoCallBack;
  
  @Autowired
  ProducerDemoKey producerDemoKey;
  
  @GetMapping(value="ping")
  public String start(){
    producerDemo.kafkaTry();
    return "blah blah";
  }  
  
//  @GetMapping(value="pong")
//  public String kafkaCallBack(){
//    producerDemoCallBack.kafkaTry();
//    return "blah blah";
//  }
  @GetMapping(value="mm")
  public String kafkaCall(){
//    producerDemoKey.kafkaTry();
    return "blah blah";
  }

}
