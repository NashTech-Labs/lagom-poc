package com.knoldus.usercurd.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by harmeet on 30/1/17.
 */

@Immutable
@JsonDeserialize
public final class User implements Serializable {

    public final String id;
    public final String name;
    public final int age;

    @JsonCreator
    public User(@JsonProperty("id") final String id, @JsonProperty("name") final String name, @JsonProperty("age") final int age) {
        this.id = Preconditions.checkNotNull(id);
        this.name = Preconditions.checkNotNull(name);
        this.age = age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.age);
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if(this == another) {
            return true;
        }
        if(another instanceof User){
            User dto = (User) another;
            return Objects.equals(this.id, dto.id) &&
                    Objects.equals(this.age, dto.age);
        }
        return false;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("age", this.age)
                .toString();
    }
}
