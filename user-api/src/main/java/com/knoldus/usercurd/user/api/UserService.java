package com.knoldus.usercurd.user.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.util.Optional;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

/**
 * Created by harmeet on 30/1/17.
 */
public interface UserService extends Service {

    ServiceCall<NotUsed, Optional<User>> user(String id);

    ServiceCall<User, Done> newUser();

    @Override
    default Descriptor descriptor() {

        return named("user").withCalls(
                pathCall("/api/user/:id", this::user),
                pathCall("/api/user", this::newUser)
        ).withAutoAcl(true);
    }
}
