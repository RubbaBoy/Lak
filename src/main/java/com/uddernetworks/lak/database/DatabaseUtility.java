package com.uddernetworks.lak.database;

import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.PreparedStatementCallback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtility {

    @FunctionalInterface
    interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    public static PreparedStatementCallback<Boolean> preparedExecute(SQLConsumer<PreparedStatement> consumer) {
        return stmt -> {
            consumer.accept(stmt);
            return stmt.execute();
        };
    }
}
