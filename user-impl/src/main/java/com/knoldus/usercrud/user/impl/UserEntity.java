package com.knoldus.usercrud.user.impl;

import akka.Done;
import com.knoldus.usercrud.user.impl.UserCommand.AddNewUser;
import com.knoldus.usercrud.user.impl.UserEvent.UserCreated;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by harmeet on 30/1/17.
 */
public class UserEntity extends PersistentEntity<UserCommand, UserEvent, UserState> {

    @Override
    public Behavior initialBehavior(Optional<UserState> snapshotState) {

        // initial behaviour of user
        BehaviorBuilder behaviorBuilder = newBehaviorBuilder(
                new UserState(new User("0", "na", 0),
                LocalDateTime.now().toString())
        );

        behaviorBuilder.setCommandHandler(AddNewUser.class, (cmd, ctx) ->
            ctx.thenPersist(new UserCreated(cmd.user), evt -> ctx.reply(Done.getInstance()))
        );

        behaviorBuilder.setEventHandler(UserCreated.class, evt ->
            new UserState(evt.user, LocalDateTime.now().toString())
        );


        return behaviorBuilder.build();
    }
}
