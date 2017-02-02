package com.knoldus.usercrud.user.impl;

import akka.Done;
import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;
import com.knoldus.usercrud.user.impl.UserCommand.AddNewUser;
import com.knoldus.usercrud.user.impl.UserCommand.DeleteUser;
import com.knoldus.usercrud.user.impl.UserCommand.UpdateUser;
import com.knoldus.usercrud.user.impl.UserEvent.UserCreated;
import com.knoldus.usercrud.user.impl.UserEvent.UserDeleted;
import com.knoldus.usercrud.user.impl.UserEvent.UserUpdated;
import com.knoldus.usercurd.user.api.User;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by harmeet on 1/2/17.
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

        User user = new User("1001", "James", 27);
        Outcome<UserEvent, UserState> outcome = driver.run(new AddNewUser(user));

        assertThat(outcome.events().get(0), is(equalTo(new UserCreated(user, "1001"))));
        assertThat(outcome.events().size(), is(equalTo(1)));
        assertThat(outcome.state().user, is(equalTo(user)));

        assertThat(outcome.getReplies().get(0), is(equalTo(Done.getInstance())));
        assertThat(outcome.issues().isEmpty(), is(true));
    }

    @Test
    public void testUpdateUser() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = new User("1001", "James", 27);
        driver.run(new AddNewUser(user));

        User updateUser = new User("1001", "Harmeet Singh", 27);
        Outcome<UserEvent, UserState> outcome = driver.run(new UpdateUser(updateUser));

        assertThat(outcome.events().get(0), is(equalTo(new UserUpdated(updateUser, "1001"))));
        assertThat(outcome.events().size(), is(equalTo(1)));
        assertThat(outcome.state().user, is(equalTo(updateUser)));

        assertThat(outcome.getReplies().get(0), is(equalTo(Done.getInstance())));
        assertThat(outcome.issues().isEmpty(), is(true));
    }

    @Test
    public void testDeleteUser() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = new User("1001", "James", 27);
        driver.run(new AddNewUser(user));

        Outcome<UserEvent, UserState> outcome = driver.run(new DeleteUser(user));

        assertThat(outcome.events().get(0), is(equalTo(new UserDeleted(user, "1001"))));
        assertThat(outcome.events().size(), is(equalTo(1)));

        User noUser = new User("-1", "", -1);
        assertThat(outcome.state().user, is(equalTo(noUser)));

        assertThat(outcome.getReplies().get(0), is(equalTo(user)));
        assertThat(outcome.issues().size(), is(equalTo(1)));
    }

    @Test
    public void testUserCurrentState() {
        PersistentEntityTestDriver<UserCommand, UserEvent, UserState> driver =
                new PersistentEntityTestDriver<>(system, new UserEntity(), "1001");

        User user = new User("1001", "James", 27);
        Outcome<UserEvent, UserState> outcome1 = driver.run(new AddNewUser(user));

        assertThat(outcome1.events().get(0), is(equalTo(new UserCreated(user, "1001"))));
        assertThat(outcome1.state().user, is(equalTo(user)));

        User updateUser = new User("1001", "Harmeet Singh", 27);
        Outcome<UserEvent, UserState> outcome2 = driver.run(new UpdateUser(updateUser));
        assertThat(outcome2.state().user, is(equalTo(updateUser)));

        User noUser = new User("-1", "", -1);
        Outcome<UserEvent, UserState> outcome3 = driver.run(new DeleteUser(user));
        assertThat(outcome3.state().user, is(equalTo(noUser)));
    }
}
