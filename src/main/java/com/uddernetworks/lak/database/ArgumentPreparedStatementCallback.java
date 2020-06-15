package com.uddernetworks.lak.database;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A port of {@link ArgumentPreparedStatementSetter} but for {@link PreparedStatementCallback}.
 */
public class ArgumentPreparedStatementCallback implements PreparedStatementCallback<Boolean> {

    private final PreparedStatementSetter setter;

    /**
     * Created a {@link ArgumentPreparedStatementCallback} from a {@link PreparedStatementSetter}.
     * @param setter The {@link PreparedStatementSetter} to create from
     */
    public ArgumentPreparedStatementCallback(PreparedStatementSetter setter) {
        this.setter = setter;
    }

    @Override
    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        setter.setValues(ps);
        return ps.execute();
    }
}
