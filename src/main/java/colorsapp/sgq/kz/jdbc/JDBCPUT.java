package colorsapp.sgq.kz.jdbc;

import spark.Request;

import java.net.*;
import java.sql.*;

public class JDBCPUT {
    private URI dbUri = new URI(System.getenv("JAWSDB_URL"));
    private final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    private final String login = dbUri.getUserInfo().split(":")[0];
    private final String password = dbUri.getUserInfo().split(":")[1];
    private Connection connection;
    private Statement statement;

    public JDBCPUT() throws URISyntaxException {
        try {
            connection = DriverManager.getConnection(url, login, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error SQL Connecting");
        }
    }

    /**
     * Метод обновляет конкретную строку из БД таблицы "combo_colors"
     * столбца "like" и заменяет ее значение на [int - like++].
     *
     * @param request - Аргумент для работы с запросами в БД.
     * @return - Если была ошибка, ответ будет [404].
     * Если ответ положительный, то [200]
     */
    public String putUpdate(Request request) {
        String answer = null;
        int id_col = Integer.parseInt(request.queryParams("id_col"));
        try {
            if (id_col > 0) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM combo_colors WHERE id_col=" +
                        request.queryParams("id_col"));
                while (resultSet.next()) {
                    int like = resultSet.getInt("like") + 1;
                    statement.execute("UPDATE combo_colors SET like=" +
                            like + " WHERE id_col=" +
                            id_col);
                    answer = "Success: +1 id_col="+id_col;
                }
            }
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
}
