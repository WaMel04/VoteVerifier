package com.wamel.voteverifier.config;

import com.wamel.voteverifier.VoteVerifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ConfigManager {

    private static VoteVerifier plugin = VoteVerifier.getInstance();

    public static void createConfig() {
        try {
            File dataFolder = plugin.getDataFolder();

            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File configFile = new File(dataFolder, "config.yml");

            if (!configFile.exists()) {
                FileOutputStream outputStream = new FileOutputStream(configFile);
                InputStream in = plugin.getResourceAsStream("config.yml");
                in.transferTo(outputStream);

                plugin.getLogger().info("§e[VoteVotifier] config를 생성했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createMysqlConfig() {
        try {
            File dataFolder = plugin.getDataFolder();

            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File configFile = new File(dataFolder, "mysql_config.yml");

            if (!configFile.exists()) {
                FileOutputStream outputStream = new FileOutputStream(configFile);
                InputStream in = plugin.getResourceAsStream("mysql_config.yml");
                in.transferTo(outputStream);

                plugin.getLogger().info("§e[VoteVotifier] mysql_config를 생성했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
