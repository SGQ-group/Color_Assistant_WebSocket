package colorsapp.sgq.kz.jdbc;

import com.google.gson.Gson;
import spark.Request;

import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBCGET {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private Statement statement;

    public JDBCGET() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    /**
     * Метод возвращает все строки из таблицы в сетевой БД "combo_colors".
     *
     * @return - Возвращает обработанный ответ в формате Json.
     * @version 1.0
     */
    public String getColors() {
        String answer;
        try {
            answer = new Gson()
                    .toJson(getAnswerList());
        } catch (Exception e) {
            answer = null;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return answer;
    }

    /**
     * Метод проверяет старую локальную БД пользователя.
     * Если у пользователя старая локальная БД,
     * то будет возращени новая из сети.
     *
     * @param request - Аргумент для работы с запросами в БД.
     * @return - Возвращает обработанный ответ в формате Json.
     * @version 1.0
     */
    public String getUpdate(Request request) {
        String answer = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `update`");
            while (resultSet.next()) {
                int update = Integer.parseInt(request
                        .queryParams("update"));
                int check = resultSet.getInt("check");
                if (check != update &&
                        check > update &&
                        0 < update)
                    answer = String.valueOf(getAnswerList(update+1, check+1).size());
//                    answer = new Gson()
//                            .toJson(getAnswerList(update+1, check+1));
            }
        } catch (Exception e) {
            answer = "eRROR";
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return answer;
    }

    /**
     * Метод берет с таблицы "update" столбец первой строки "check"
     * и выводит ее в виде ответа.
     *
     * @return - Возвращает последнюю версию обновления сетевой БД.
     */
    public String getCheck() {
        String answer = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `update`");
            while (resultSet.next()) {
                HashMap<String, String> answerMap = new HashMap<>();
                answerMap.put("check", resultSet.getString("check"));
                    answer = new Gson()
                            .toJson(answerMap);
            }
        } catch (Exception e) {
            answer = "Error";
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return answer;
    }

    /**
     * Метод выводит всю таблицу в БД "combo-colors".
     * Не эффективна для обновления БД,
     * но хорошо подходит для первого заполнениня локальной БД.
     *
     * @return - Возвращает ArrayList, после обработки ответа с БД.
     * @throws SQLException
     */
    private ArrayList<HashMap<String, String>> getAnswerList() throws SQLException {
        ArrayList<HashMap<String, String>> answerList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM combo_colors");
        while (resultSet.next()) {
            HashMap<String, String> answerMap = new HashMap<>();
            answerMap.put("id_col", resultSet.getString("id_col"));
            answerMap.put("col_1", resultSet.getString("col_1"));
            answerMap.put("col_2", resultSet.getString("col_2"));
            answerMap.put("col_3", resultSet.getString("col_3"));

            // Если четвертый цвет возвращает NULL, то мы его не записываем
            if (resultSet.getString("col_4") == null)
                answerMap.put("col_4", resultSet.getString("col_4"));

            // Если пятый цвет возвращает NULL, то мы его не записываем
            if (resultSet.getString("col_5") == null)
                answerMap.put("col_5", resultSet.getString("col_5"));
            answerMap.put("like", resultSet.getString("like"));
            answerMap.put("check", resultSet.getString("check"));
            answerList.add(answerMap);
        }
        return answerList;
    }

    /**
     * Метод обновляет локальную БД.
     * Использует меньше трафика, чем метод getAnswerList().
     *
     * @param update - Текущее обновление локальной БД пользователя.
     * @param check  - Последнее обновление сетевой БД.
     * @return - Возвращает ArrayList, после обработки ответа с БД.
     * @throws SQLException
     */
    private ArrayList<HashMap<String, String>> getAnswerList(int update, int check) throws SQLException {
        ArrayList<HashMap<String, String>> answerList = new ArrayList<>();
//        for (int i = update; i < check; i++) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM combo_colors WHERE `check`=" + 2);
            while (resultSet.next()) {
                HashMap<String, String> answerMap = new HashMap<>();
                answerMap.put("id_col", resultSet.getString("id_col"));
                answerMap.put("col_1", resultSet.getString("col_1"));
                answerMap.put("col_2", resultSet.getString("col_2"));
                answerMap.put("col_3", resultSet.getString("col_3"));

                // Если четвертый цвет возвращает NULL, то мы его не записываем.
                if (resultSet.getString("col_4") == null)
                    answerMap.put("col_4", resultSet.getString("col_4"));

                // Если пятый цвет возвращает NULL, то мы его не записываем.
                if (resultSet.getString("col_5") == null)
                    answerMap.put("col_5", resultSet.getString("col_5"));
                answerMap.put("like", resultSet.getString("like"));
                answerMap.put("check", resultSet.getString("check"));
                answerList.add(answerMap);
            }
//        }
        return answerList;
    }
}
