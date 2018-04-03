package colorsapp.sgq.kz;

import colorsapp.sgq.kz.jdbc.JDBCGET;
import colorsapp.sgq.kz.jdbc.JDBCPUT;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args){
        port(getHerokuAssignedPort());
        get("/", (request, response) -> "Server status: Online");

        /**
         * Запрос GET.
         * Устанавливает в локальную БД всю сетевую БД.
         * HOST -> LOCAL
         *
         * https://example.com/colors
         */
        get("/colors", (request, response) -> new JDBCGET().getColors());

        /**
         * Запрос GET.
         * Обновляет локальную БД до сетевой.
         * HOST [Update 6] -> LOCAL [Update 3],
         * где "Update" - это последнее обновление БД.
         * (В таблицах "combo_colors" и "update" заголовок называется "check")
         *
         * https://example.com/check ?
         * & update - Последнее обновление БД [int].
         */
        get("/check", (request, response) -> new JDBCGET().getUpdate(request));

        /**
         * Запрос GET.
         * Устанавливает версию обновлений в локальной БД
         *
         * https://example.com/update
         */
        get("/update", (request, response) -> new JDBCGET().getCheck());

        /**
         * Запрос PUT.
         * Повышает рейтинг (like) на +1.
         *
         * https://example.com/like ?
         * & id_col - Уникальный номер строки из таблицы "combo_colors" [int].
         */
//        put("/like", (request, response) -> new JDBCPUT().putUpdate(request));
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
