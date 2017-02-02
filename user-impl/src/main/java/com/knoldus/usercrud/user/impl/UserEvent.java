package com.knoldus.usercrud.user.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.concurrent.Immutable;

/**
 * Created by harmeet on 30/1/17.
 */
public interface UserEvent extends Jsonable, AggregateEvent<UserEvent> {

    @Override
    default AggregateEventTagger<UserEvent> aggregateTag() {
        return UserEventTag.INSTANCE;
    }

    @Immutable
    @JsonDeserialize
    final class UserCreated implements UserEvent {
        public final User user;
        public final String eventId;

        @JsonCreator
        public UserCreated(@JsonProperty("user") final User user, @JsonProperty("eventId") final String eventId) {
            this.user = Preconditions.checkNotNull(user);
            this.eventId = Preconditions.checkNotNull(eventId);
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
            if(another instanceof UserCreated) {
                UserCreated userCreated = (UserCreated) another;
                return this.user.equals(userCreated.user);
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

    @Immutable
    @JsonDeserialize
    final class UserUpdated implements UserEvent {
        public final User user;
        public final String eventId;

        public UserUpdated(@JsonProperty("user") final User user, @JsonProperty("eventId") final String eventId) {
            this.user = Preconditions.checkNotNull(user);
            this.eventId = Preconditions.checkNotNull(eventId);
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
            if(another instanceof UserUpdated) {
                UserUpdated userUpdated = (UserUpdated) another;
                return this.user.equals(userUpdated.user);
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

    @Immutable
    @JsonDeserialize
    final class UserDeleted implements UserEvent {
        public final User user;
        public final String eventId;

        public UserDeleted(@JsonProperty("user") final User user, @JsonProperty("eventId") final String eventId) {
            this.user = Preconditions.checkNotNull(user);
            this.eventId = Preconditions.checkNotNull(eventId);
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
            if(another instanceof UserDeleted) {
                UserDeleted userDeleted = (UserDeleted) another;
                return this.user.equals(userDeleted.user);
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
