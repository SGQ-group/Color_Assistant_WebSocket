package com.colorassistant.utils;

public class SQLStatement {

    public static String getColors() {
        return "SELECT * FROM combo_colors";
    }

    public static String getColorsCheck() {
        return "SELECT * FROM combo_colors WHERE `check`=?";
    }

    public static String getUpdate() {
        return "SELECT * FROM `update`";
    }
}
