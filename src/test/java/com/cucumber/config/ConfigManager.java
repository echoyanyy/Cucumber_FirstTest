package com.cucumber.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    private static Properties defaultProperties = null;

    static {
        defaultProperties = new Properties();
        try {
            defaultProperties.load(new FileReader("config/default.properties"));
            File customConfigFile = new File("config/custom.properties");
            if (customConfigFile.exists()) {
                defaultProperties.load(new FileReader(customConfigFile));
            }
        } catch (IOException exception) {
            System.err.println("未找到配置文件");
        }

        if (!defaultProperties.isEmpty()) {
            for (Object key : defaultProperties.keySet()) {
                if (System.getProperty((String)key) != null) {
                    defaultProperties.setProperty((String)key, System.getProperty((String)key));
                }
            }
        }

    }

    public ConfigManager() {

    }

    public String get(String key) {
        return defaultProperties.getProperty(key);
    }
}
