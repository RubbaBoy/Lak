package com.uddernetworks.lak.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.uddernetworks.lak.Utility.getBytesFromUUID;

@Component
public class DatabaseUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtility.class);

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


    /**
     * Returns an object array from varargs
     *
     * @param args The args
     * @return The args
     */
    public static <T> T[] args(T... args) {
        return args;
    }

    /**
     * Creates a {@link PreparedStatementSetter} from given varargs.
     *
     * @param args The args to set in the prepared statement
     * @return The {@link PreparedStatementSetter}
     */
    public static PreparedStatementSetter queryArgs(Object... args) {
        return new ArgumentPreparedStatementSetter(transformArgs(args));
    }

    /**
     * Creates a {@link ArgumentPreparedStatementCallback} from given varargs. This auto converts {@link UUID}s to byte
     * arrays via {@link com.uddernetworks.lak.Utility#getBytesFromUUID(UUID)}.
     *
     * @param args The args to set in the prepared statement
     * @return The {@link ArgumentPreparedStatementCallback}
     */
    public static ArgumentPreparedStatementCallback executeArgs(Object... args) {
        return new ArgumentPreparedStatementCallback(queryArgs(args));
    }

    public static Object[] transformArgs(Object... args) {
        return Arrays.stream(args).map(arg -> {
            if (arg instanceof UUID) {
                return getBytesFromUUID((UUID) arg);
            }
            return arg;
        }).toArray(Object[]::new);
    }
}
