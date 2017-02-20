package com.knoldus.microgateway.api;

import java.util.Optional;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;

public interface MicrogatewayService extends Service {

  ServiceCall<NotUsed, Optional<String>> sayHello(String id);

  ServiceCall<NotUsed, String> health();

  @Override default Descriptor descriptor() {
    return named("gateway").withCalls(restCall(GET, "/say/hello/:id", this::sayHello),
        restCall(GET, "/api/health", this::health)).withAutoAcl(true);
  }
}
