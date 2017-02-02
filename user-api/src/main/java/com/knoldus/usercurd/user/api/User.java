package com.knoldus.usercurd.user.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

/**
 * Created by knoldus on 30/1/17.
 */
@Value
@Builder
@Immutable
@JsonDeserialize
@AllArgsConstructor
public final class User implements Jsonable {

    String id;
    String name;
    int age;
}
