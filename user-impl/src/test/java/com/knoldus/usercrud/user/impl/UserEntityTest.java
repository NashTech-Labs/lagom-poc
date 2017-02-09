package com.knoldus.usercrud.user.impl;

import akka.Done;
import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.knoldus.usercrud.user.impl.commands.UserCommand;
import com.knoldus.usercrud.user.impl.commands.UserCommand.CreateUser;
import com.knoldus.usercrud.user.impl.commands.UserCommand.DeleteUser;
import com.knoldus.usercrud.user.impl.commands.UserCommand.UpdateUser;
import com.knoldus.usercrud.user.impl.events.UserEvent;
import com.knoldus.usercrud.user.impl.events.UserEvent.UserCreated;
import com.knoldus.usercrud.user.impl.events.UserEvent.UserDeleted;
import com.knoldus.usercrud.user.impl.states.UserState;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by knoldus on 1/2/17.
 */
public class UserEntityTest {

    private static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testAddNewUser() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = User.builder().id("1001").name("James").age(27).build();
        Outcome<UserEvent, UserState> outcome = driver.run(CreateUser.builder().user(user).build());

        assertThat(outcome.events().get(0), is(equalTo(UserCreated.builder().user(user).entityId("1001").build())));
        assertThat(outcome.events().size(), is(equalTo(1)));
        assertThat(outcome.state().getUser().get(), is(equalTo(user)));

        assertThat(outcome.getReplies().get(0), is(equalTo(Done.getInstance())));
        outcome.issues().stream().forEach(System.out::println);
        assertThat(outcome.issues().isEmpty(), is(true));
    }

    @Test
    public void testUpdateUser() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = User.builder().id("1001").name("James").age(27).build();
        driver.run(CreateUser.builder().user(user).build());

        User updateUser = User.builder().id("1001").name("Harmeet Singh").age(27).build();
        Outcome<UserEvent, UserState> outcome = driver.run(UpdateUser.builder().user(updateUser).build());

        assertThat(outcome.events().get(0), is(equalTo(UserEvent.UserUpdated.builder().user(updateUser).entityId("1001").build())));
        assertThat(outcome.events().size(), is(equalTo(1)));
        assertThat(outcome.state().getUser().get(), is(equalTo(updateUser)));

        assertThat(outcome.getReplies().get(0), is(equalTo(Done.getInstance())));
        assertThat(outcome.issues().isEmpty(), is(true));
    }

    @Test
    public void testDeleteUser() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = User.builder().id("1001").name("James").age(27).build();
        driver.run(CreateUser.builder().user(user).build());

        Outcome<UserEvent, UserState> outcome = driver.run(DeleteUser.builder().user(user).build());

        assertThat(outcome.events().get(0), is(equalTo(UserDeleted.builder().user(user).entityId("1001").build())));
        assertThat(outcome.events().size(), is(equalTo(1)));

        assertThat(outcome.state().getUser(), is(equalTo(Optional.empty())));

        assertThat(outcome.getReplies().get(0), is(equalTo(Done.getInstance())));
        assertThat(outcome.issues().size(), is(equalTo(0)));
    }

    @Test
    public void testUserCurrentState() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = User.builder().id("1001").name("James").age(27).build();
        Outcome<UserEvent, UserState> outcome1 = driver.run(CreateUser.builder().user(user).build());

        assertThat(outcome1.events().get(0), is(equalTo(UserCreated.builder().user(user).entityId("1001").build())));
        assertThat(outcome1.state().getUser().get(), is(equalTo(user)));

        User updateUser = User.builder().id("1001").name("Harmeet Singh").age(27).build();
        Outcome<UserEvent, UserState> outcome2 = driver.run(UpdateUser.builder().user(updateUser).build());
        assertThat(outcome2.state().getUser().get(), is(equalTo(updateUser)));

        Outcome<UserEvent, UserState> outcome3 = driver.run(DeleteUser.builder().user(user).build());
        assertThat(outcome3.state().getUser(), is(equalTo(Optional.empty())));
    }
}
