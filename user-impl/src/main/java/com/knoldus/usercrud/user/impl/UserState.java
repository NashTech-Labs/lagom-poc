package com.knoldus.usercrud.user.impl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.serialization.CompressedJsonable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 * Created by harmeet on 30/1/17.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public class UserState implements CompressedJsonable {
    public final User user;
    public final String timestamp;

    public UserState(final User user, final String timestamp){
        this.user = Preconditions.checkNotNull(user);
        this.timestamp = Preconditions.checkNotNull(timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, timestamp);
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if(this == another){
            return true;
        }
        if(another instanceof UserState) {
            UserState state = (UserState) another;
            return Objects.equals(this.user, state.user) &&
                    Objects.equals(this.timestamp, state.user);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.user)
                .append("name", this.timestamp)
                .toString();
    }
}
