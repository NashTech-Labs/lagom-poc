package com.knoldus.hello.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.Offset;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.knoldus.hello.api.HelloService;
import com.knoldus.hello.impl.HelloCommand.Hello;
import com.knoldus.hello.impl.HelloCommand.UseGreetingMessage;
import com.knoldus.hello.api.GreetingMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Inject;
import java.util.Optional;
import com.knoldus.common.*;

/**
 * Implementation of the HelloService.
 */
public class HelloServiceImpl implements HelloService {

  private static RcclLoggerService log = RcclLoggerFactory.getLogService(HelloServiceImpl.class.getName());

  private final PersistentEntityRegistry persistentEntityRegistry;

  @Inject public HelloServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(HelloWorld.class);
  }

  @Override public ServiceCall<NotUsed, String> hello(String id) {
    return request -> {
      log.warn("hello service is called ");
      log.info("hello service is called ");
      log.debug("hello service is called ");
      log.error("hello service is called ");
      PersistentEntityRef<HelloCommand> ref = persistentEntityRegistry.refFor(HelloWorld.class, id);
      return ref.ask(new Hello(id, Optional.empty()));
    };
  }

  @Override public ServiceCall<GreetingMessage, Done> useGreeting(String id) {
    return request -> {
      PersistentEntityRef<HelloCommand> ref = persistentEntityRegistry.refFor(HelloWorld.class, id);
      return ref.ask(new UseGreetingMessage(request.message));
    };

  }

  @Override
  public Topic<GreetingMessage> greetingsTopic() {
    return TopicProducer.singleStreamWithOffset(offset -> persistentEntityRegistry.eventStream(HelloEventTag.INSTANCE, offset)
        .map(this::convertEvent));
  }

  private Pair<GreetingMessage, Offset> convertEvent(Pair<HelloEvent, Offset> pair) {
    return new Pair<>(new GreetingMessage(pair.first().getMessage()), pair.second());
  }


}
