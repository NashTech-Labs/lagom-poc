package com.knoldus.hello.impl;

import com.google.inject.AbstractModule;
import com.knoldus.hello.api.HelloService;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class HelloModule extends AbstractModule implements ServiceGuiceSupport {

  @Override
  protected void configure() {
    bindServices(serviceBinding(HelloService.class, HelloServiceImpl.class));
    bind(TestImpl.class).asEagerSingleton();
  }
}
