package com.knoldus.usercrud.user.impl.states;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Optional;

/**
 * Created by knoldus on 2/2/17.
 */
@Value
@Builder
@JsonDeserialize
@AllArgsConstructor
public class UserState implements CompressedJsonable {
    Optional<User> user;
    String timestamp;
}
