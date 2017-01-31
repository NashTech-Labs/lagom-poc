package com.knoldus.usercrud.user.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.concurrent.Immutable;

/**
 * Created by harmeet on 30/1/17.
 */
public interface UserCommand extends Jsonable {

    @Immutable
    @JsonDeserialize
    final class AddNewUser implements UserCommand, PersistentEntity.ReplyType<Done> {
        public final User user;

        @JsonCreator
        public AddNewUser(final User user) {
            this.user = Preconditions.checkNotNull(user);
        }

        @Override
        public int hashCode() {
            return user.hashCode();
        }

        @Override
        public boolean equals(Object another) {
            if(this == another) {
                return true;
            }
            if(another instanceof AddNewUser) {
                AddNewUser newUser = (AddNewUser) another;
                return this.user.equals(newUser.user);
            }
            return false;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("user", this.user)
                    .toString();
        }
    }
}
