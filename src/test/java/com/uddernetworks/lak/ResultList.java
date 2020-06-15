package com.uddernetworks.lak;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResultList {

    private final int size;
    private final List<String> columns;
    private final Iterator<Map<String, Object>> iterator;
    private Map<String, Object> currData;

    public ResultList(ResultSet resultSet) throws SQLException {
        this.columns = new ArrayList<>();
        var data = new ArrayList<Map<String, Object>>();

        var meta = resultSet.getMetaData();
        var columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            var name = meta.getColumnName(i);
            columns.add(name);
        }

        while (resultSet.next()) {
            var map = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                map.put(meta.getColumnName(i), resultSet.getObject(i));
            }
            data.add(map);
        }

        size = data.size();
        iterator = data.iterator();
    }

    public int size() {
        return size;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public void next() {
        currData = iterator.next();
    }

    public Map<String, Object> getCurrent() {
        return currData;
    }

    public <T> T get(int index) {
        return (T) currData.get(columns.get(index));
    }

    public <T> T get(String column) {
        return (T) currData.get(column);
    }

    @Override
    public String toString() {
        return "ResultList{" +
                "columns=" + columns +
                ", iterator=" + iterator +
                ", currData=" + currData +
                '}';
    }
}
