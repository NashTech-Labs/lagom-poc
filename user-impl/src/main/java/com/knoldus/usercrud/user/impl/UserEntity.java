package com.knoldus.usercrud.user.impl;

import akka.Done;
import com.knoldus.usercrud.user.impl.UserCommand.AddNewUser;
import com.knoldus.usercrud.user.impl.UserCommand.UserCurrentState;
import com.knoldus.usercrud.user.impl.UserCommand.DeleteUser;
import com.knoldus.usercrud.user.impl.UserCommand.UpdateUser;
import com.knoldus.usercrud.user.impl.UserEvent.UserCreated;
import com.knoldus.usercrud.user.impl.UserEvent.UserDeleted;
import com.knoldus.usercrud.user.impl.UserEvent.UserUpdated;
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

        User initialUser = new User("-1", "", -1);

        // initial behaviour of user
        BehaviorBuilder behaviorBuilder = newBehaviorBuilder(
                new UserState(initialUser, LocalDateTime.now().toString())
        );

        behaviorBuilder.setCommandHandler(AddNewUser.class, (cmd, ctx) ->
            ctx.thenPersist(new UserCreated(cmd.user, entityId()), evt -> ctx.reply(Done.getInstance()))
        );

        behaviorBuilder.setEventHandler(UserCreated.class, evt ->
            new UserState(evt.user, LocalDateTime.now().toString())
        );

        behaviorBuilder.setCommandHandler(UpdateUser.class, (cmd, ctx) ->
            ctx.thenPersist(new UserUpdated(cmd.user, entityId()), evt -> ctx.reply(Done.getInstance()))
        );

        behaviorBuilder.setEventHandler(UserUpdated.class, evt ->
            new UserState(evt.user, LocalDateTime.now().toString())
        );

        behaviorBuilder.setCommandHandler(DeleteUser.class, (cmd, ctx) ->
            ctx.thenPersist(new UserDeleted(cmd.user, entityId()), evt -> ctx.reply(cmd.user))
        );

        behaviorBuilder.setEventHandler(UserDeleted.class, evt ->
            new UserState(initialUser, LocalDateTime.now().toString())
        );

        behaviorBuilder.setReadOnlyCommandHandler(UserCurrentState.class, (cmd, ctx) ->
            ctx.reply(state().user)
        );

        return behaviorBuilder.build();
    }
}
