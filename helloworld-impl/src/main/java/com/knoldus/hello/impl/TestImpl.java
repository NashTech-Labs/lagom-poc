package com.knoldus.hello.impl;

import akka.Done;
import akka.stream.javadsl.Flow;

import javax.inject.Inject;

import com.google.inject.Singleton;
import com.knoldus.hello.api.GreetingMessage;
import com.knoldus.hello.api.HelloService;

@Singleton public class TestImpl {

  private final HelloService helloService;

  @Inject public TestImpl(HelloService helloService) {
    this.helloService = helloService;
    helloService.greetingsTopic().subscribe()
        .atLeastOnce(Flow.fromFunction(this::doSomethingWithTheMessage));
  }

  private Done doSomethingWithTheMessage(GreetingMessage message) {
    System.out.println("Message :  " + message);
    return Done.getInstance();
  }

}