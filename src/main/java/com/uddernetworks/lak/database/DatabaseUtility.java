package com.uddernetworks.lak.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class DatabaseUtility {

    @FunctionalInterface
    public interface SQLConsumer {
        void accept(PreparedStatement stmt) throws SQLException;
    }

    public static PreparedStatementCallback<Boolean> preparedExecute(SQLConsumer consumer) {
        return stmt -> {
            consumer.accept(stmt);
            return stmt.execute();
        };
    }

    public static void waitFuture(boolean synchronous, CompletableFuture<?> completableFuture) {
        if (synchronous) {
            completableFuture.join();
        }
    }
}
