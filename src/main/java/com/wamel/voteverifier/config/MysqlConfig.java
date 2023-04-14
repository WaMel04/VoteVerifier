package com.wamel.voteverifier.config;

import com.wamel.voteverifier.VoteVerifier;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;

public class MysqlConfig {

    private static VoteVerifier plugin = VoteVerifier.getInstance();

    public static Object get(String key) {
        File file = new File(plugin.getDataFolder(), "mysql_config.yml");
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            return configuration.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static final String ip = (String) get("ip");
    public static final String port = (String) get("port");
    public static final String database = (String) get("database");

    public static final String user = (String) get("username");
    public static final String password = (String) get("password");
}
