package ru.stalser.framework.helpers;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.stalser.framework.utils.Props;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static ru.stalser.framework.helpers.AllureHelper.*;
import static ru.stalser.framework.helpers.SoftAssertHelper.failTestIfLastAssertWasFailed;

@Slf4j
public class DBHelper {

    private Connection connection;
    private Long timeoutTime;

    public void connectStalser() {

        final String url = Props.getString("postgre.stalser.jdbc.url");
        final String login = Props.getString("postgre.stalser.jdbc.login");
        final String password = Props.getString("postgre.stalser.jdbc.password");
        connectPostgre(url, login, password);
    }

    private void connectPostgre(final String url, final String login, final String password) {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            AllureHelper.err(e);
            failTestIfLastAssertWasFailed();
        }
    }

    public void executeUpdate(String sql) {

        executeUpdate(sql, "");
    }

    @Step("Делаю запрос в базу данных. {logMessage}")
    public void executeUpdate(String sql, String logMessage) {

        attachTxt("DB Query", sql);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public List<Map<String, String>> doRequestInToList(String sql) {

        return doRequestInToList(sql, "");
    }

    public List<Map<String, String>> doRequestInToList(String sql, String logMessage) {

        return doRequestInToList(connection, sql, logMessage);
    }

    @Step("Делаю запрос в базу данных. {logMessage}")
    private List<Map<String, String>> doRequestInToList(Connection con, String sql, String logMessage) {

        attachTxt("DB Query", sql);
        List<Map<String, String>> result = new ArrayList<>();
        ResultSet resultSet = null;
        Statement statement;
        try {
            statement = con.createStatement();
            timeoutTime = new Date().getTime() + Props.getLong("database.timeout");
            while (!isTimedOut()) {
                resultSet = statement.executeQuery(sql);
                if (resultSet.isBeforeFirst()) {
                    result = resultSetToListOfMaps(resultSet);
                    break;
                }
            }
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
            statement.close();
        } catch (Exception e) {
            err(e);
        } finally {
            attachHtml("DB Result", Utils.convertListMapToHorizontalHTML(result));
        }

        return result;
    }

    private List<Map<String, String>> resultSetToListOfMaps(ResultSet resultSet) throws SQLException {

        List<Map<String, String>> result = new ArrayList<>();
        ResultSetMetaData md = resultSet.getMetaData();
        int columnCount = md.getColumnCount();

        while (resultSet.next()) {

            LinkedHashMap<String, String> raw = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String column = md.getColumnName(i);
                String cell = resultSet.getString(i);
                raw.put(column, cell);
            }

            result.add(raw);
        }
        return result;
    }

    public Map<String, String> doRequestInToMap(String sql) {

        return doRequestInToMap(sql, "");
    }

    public Map<String, String> doRequestInToMap(String sql, String logMessage) {

        return doRequestInToMap(connection, sql, logMessage);
    }

    @Step("Делаю запрос в базу данных. {logMessage}")
    private Map<String, String> doRequestInToMap(Connection con, String sql, String logMessage) {

        attachTxt("DB Query", sql);
        Map<String, String> resultMap = new HashMap<>();
        ResultSet resultSet = null;
        Statement statement;

        try {
            statement = con.createStatement();
            timeoutTime = new Date().getTime() + Props.getLong("database.timeout");
            while (!isTimedOut()) {
                resultSet = statement.executeQuery(sql);
                if (resultSet.isBeforeFirst()) {
                    resultMap = resultSetToMap(resultSet);
                    break;
                }
            }
            if (Objects.nonNull(resultSet)) {
                resultSet.close();
            }
            statement.close();
        } catch (Exception e) {
            err(e);
        } finally {
            attachMapAsTable("DB Result", resultMap);
        }

        return resultMap;
    }

    private Map<String, String> resultSetToMap(ResultSet resultSet) {

        try {
            Map<String, String> resulMap = new HashMap<>();

            while (resultSet != null && resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    if (resultSet.getMetaData().getColumnTypeName(i).equals("bytea")) {
                        resulMap.put(resultSet.getMetaData().getColumnTypeName(i), new String(resultSet.getBytes(i)));
                    } else {
                        resulMap.put(resultSet.getMetaData().getColumnTypeName(i), resultSet.getString(i));
                    }
                }
            }

            return resulMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean isTimedOut() {

        return timeoutTime < (new Date().getTime());
    }

    public void disconnect() {

        try {
            connection.close();
        } catch (SQLException e) {
            err(e);
        }
    }
}
