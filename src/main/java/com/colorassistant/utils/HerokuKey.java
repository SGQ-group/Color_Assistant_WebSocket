/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
