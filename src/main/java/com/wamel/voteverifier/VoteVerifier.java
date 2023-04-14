package com.wamel.voteverifier;

import com.wamel.voteverifier.command.VerifyCommand;
import com.wamel.voteverifier.command.VerifyReloadCommand;
import com.wamel.voteverifier.config.ConfigManager;
import com.wamel.voteverifier.event.VoteEvent;
import com.wamel.voteverifier.sql.DBConnection;
import com.wamel.voteverifier.sql.FirstJoinDBUtil;
import com.wamel.voteverifier.sql.VerifyDBUtil;
import net.md_5.bungee.api.plugin.Plugin;

import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public final class VoteVerifier extends Plugin {

    private static VoteVerifier instance;
    private static Connection connection;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.createConfig();
        ConfigManager.createMysqlConfig();

        try {
            connection = new DBConnection().getConnection();
            getLogger().info("§e[VoteVerifier] mysql db에 접속했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        VerifyDBUtil.createTable();
        FirstJoinDBUtil.createTable();
        TimeSchedulerInit();

        getProxy().getPluginManager().registerListener(this, new VoteEvent());

        getProxy().getPluginManager().registerCommand(this, new VerifyCommand());
        getProxy().getPluginManager().registerCommand(this, new VerifyReloadCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static VoteVerifier getInstance() {
        return instance;
    }

    public static Connection getConnection() { return connection; }

    private void TimeSchedulerInit() {
        getProxy().getScheduler().schedule(this, new Runnable() {
            @Override
            public void run() {
                LocalTime now = LocalTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedNow = now.format(formatter);

                if (formattedNow.equalsIgnoreCase("00:00:00")) {
                    VerifyDBUtil.resetTable();

                    VoteEvent.minelist_count = 0;
                    VoteEvent.minepage_count = 0;
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
