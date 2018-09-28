package com.colorassistant.utils;

import java.net.URI;
import java.net.URISyntaxException;

public class HerokuKey {
    public static URI dbUri;

    static {
        try {
            dbUri = new URI(System.getenv("JAWSDB_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static final String url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
    public static final String login = dbUri.getUserInfo().split(":")[0];
    public static final String password = dbUri.getUserInfo().split(":")[1];
}
