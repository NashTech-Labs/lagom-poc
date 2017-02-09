package com.knoldus.usercrud.user.impl.commands;

import akka.Done;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

/**
 * Created by knoldus on 30/1/17.
 */
public interface UserCommand extends Jsonable {

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    final class CreateUser implements UserCommand, PersistentEntity.ReplyType<Done> {
        User user;
    }

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    final class UpdateUser implements UserCommand, PersistentEntity.ReplyType<Done> {
        User user;
    }

    @Value
    @Builder
    @JsonDeserialize
    @AllArgsConstructor
    final class DeleteUser implements UserCommand, PersistentEntity.ReplyType<Done> {
        User user;
    }

    @JsonDeserialize
    final class UserCurrentState implements UserCommand, PersistentEntity.ReplyType<Optional<User>> {}
}
