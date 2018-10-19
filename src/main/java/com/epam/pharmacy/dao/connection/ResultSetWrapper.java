package com.epam.pharmacy.dao.connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class ResultSetWrapper {

    private List<Map<String, Object>> result = new ArrayList<>();

    public ResultSetWrapper (ResultSet resultSet) throws SQLException {
        this.convertResultSetToList(resultSet);
    }

    private void convertResultSetToList(ResultSet rs) throws SQLException {
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    result.add(row);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public  List<Map<String, Object>> getResult () {
        return Collections.unmodifiableList(result);
    }

    public boolean isEmpty () {
        return result.isEmpty();
    }

}
