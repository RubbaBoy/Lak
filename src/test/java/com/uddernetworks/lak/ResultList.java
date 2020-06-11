package com.uddernetworks.lak;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultList {

    private final List<String> columns;
    private final Map<String, Object> data;

    public ResultList(ResultSet resultSet) throws SQLException {
        this.columns = new ArrayList<>();
        this.data = new HashMap<>();

        var meta = resultSet.getMetaData();
        if (resultSet.next()) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                var name = meta.getColumnName(i);
                columns.add(name);
                data.put(name, resultSet.getObject(i));
            }
        }
    }

    public <T> T get(int index) {
        return (T) data.get(columns.get(index));
    }

    public <T> T get(String column) {
        return (T) data.get(column);
    }

}
