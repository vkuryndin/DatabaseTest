package org.example.util;

//I think this is not needed now

public final class AppConfig {
    private AppConfig() {
        // Utility class: prevent instantiation
    }

    public static final String DB_URL = "jdbc:postgresql://192.168.179.129:5432/appdb";
    public static final String DB_USER = "appuser";
    public static final String DB_PASS = "Gefsbizd19!";
}
