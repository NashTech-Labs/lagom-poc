package com.knoldus.usercrud.user.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.knoldus.usercrud.user.impl.UserEvent.UserCreated;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * Created by harmeet on 31/1/17.
 */
public class UserEventProcessor extends ReadSideProcessor<UserEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventProcessor.class);

    private final CassandraSession session;
    private final CassandraReadSide readSide;
    private PreparedStatement writeUsers;

    @Inject
    public UserEventProcessor(final CassandraSession session, final CassandraReadSide readSide) {
        this.session = session;
        this.readSide = readSide;
    }

    @Override
    public PSequence<AggregateEventTag<UserEvent>> aggregateTags() {
        LOGGER.info(" aggregateTags method ... ");
        return TreePVector.singleton(UserEventTag.INSTANCE);
    }

    @Override
    public ReadSideHandler<UserEvent> buildHandler() {
        LOGGER.info(" buildHandler method ... ");
        return readSide.<UserEvent>builder("users_offset")
                .setGlobalPrepare(this::createTable)
                .setPrepare(evtTag -> prepareWriteUser())
                .setEventHandler(UserCreated.class, this::processPostAdded)
                .build();
    }

    private CompletionStage<Done> createTable() {
        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS users ( " +
                        "id TEXT, name TEXT, age INT, PRIMARY KEY(id))"
        );
    }

    private CompletionStage<Done> prepareWriteUser() {
        return session.prepare(
                "INSERT INTO users (id, name, age) VALUES (?, ?, ?)"
        ).thenApply(ps -> {
            setWriteUsers(ps);
            return Done.getInstance();
        });
    }

    private void setWriteUsers(PreparedStatement statement) {
        this.writeUsers = statement;
    }

    private CompletionStage<List<BoundStatement>> processPostAdded(UserCreated event) {
        BoundStatement bindWriteUser = writeUsers.bind();
        bindWriteUser.setString("id", event.user.id);
        bindWriteUser.setString("name", event.user.name);
        bindWriteUser.setInt("age", event.user.age);
        return CassandraReadSide.completedStatements(Arrays.asList(bindWriteUser));
    }

}
