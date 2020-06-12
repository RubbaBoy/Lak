package com.uddernetworks.lak.database;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.lang.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static com.uddernetworks.lak.Utility.getBytesFromUUID;

/**
 * A port of {@link ArgumentPreparedStatementSetter} but for {@link PreparedStatementCallback}.
 */
public class ArgumentPreparedStatementCallback implements PreparedStatementCallback<Boolean> {

    private final Object[] args;

    /**
     * Create a new ArgumentPreparedStatementCallback for the given arguments.
     *
     * @param args the arguments to set
     */
    public ArgumentPreparedStatementCallback(@Nullable Object[] args) {
        this.args = args;
    }

    @Override
    public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; i++) {
                Object arg = this.args[i];

                if (arg instanceof UUID) {
                    arg = getBytesFromUUID((UUID) arg);
                }

                doSetValue(ps, i + 1, arg);
            }
        }
        return null;
    }

    /**
     * Set the value for prepared statements specified parameter index using the passed in value.
     * This method can be overridden by sub-classes if needed.
     *
     * @param ps                the PreparedStatement
     * @param parameterPosition index of the parameter position
     * @param argValue          the value to set
     * @throws SQLException if thrown by PreparedStatement methods
     */
    protected void doSetValue(PreparedStatement ps, int parameterPosition, Object argValue) throws SQLException {
        if (argValue instanceof SqlParameterValue) {
            SqlParameterValue paramValue = (SqlParameterValue) argValue;
            StatementCreatorUtils.setParameterValue(ps, parameterPosition, paramValue, paramValue.getValue());
        } else {
            StatementCreatorUtils.setParameterValue(ps, parameterPosition, SqlTypeValue.TYPE_UNKNOWN, argValue);
        }
    }
}
