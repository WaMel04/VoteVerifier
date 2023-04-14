package com.wamel.voteverifier.sql;

import com.wamel.voteverifier.config.MysqlConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.md_5.bungee.api.ChatColor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static HikariDataSource dataSource;

    public DBConnection() {
        testConnection((HikariDataSource)dataSource(MysqlConfig.user, MysqlConfig.password));
    }

    public DataSource dataSource(String identity, String password) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + MysqlConfig.ip + ":" + MysqlConfig.port + "/" + MysqlConfig.database + "?useUnicode=yes;characterEncoding=utf-8;");
        config.setUsername(identity);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
        dataSource.setMaximumPoolSize(40);
        return (DataSource)dataSource;
    }

    private void testConnection(HikariDataSource ds) {
        try (Connection connection = ds.getConnection()) {
            System.out.println(ChatColor.GREEN + "데이터베이스와 연결되었습니다. 커넥션 풀 크기 :" + ds.getMaximumPoolSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeConnection() {
        if (dataSource.isRunning() ||
                !dataSource.isClosed())
            try {
                dataSource.close();
                System.out.println(ChatColor.RED + "데이터 베이스와의 연결을 종료합니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
