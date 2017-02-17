package com.knoldus.microgateway.api;

import java.util.Optional;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import static com.lightbend.lagom.javadsl.api.Service.restCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;

/**
 * Created by deepak on 15/2/17.
 */
public interface MicrogatewayService extends Service {

  ServiceCall<NotUsed, Optional<String>> sayHello(String id);

  @Override default Descriptor descriptor() {
    return named("gateway").withCalls(restCall(GET, "/say/hello/:id", this::sayHello))
        .withAutoAcl(true);
  }
}
