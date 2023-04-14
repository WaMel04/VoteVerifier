package com.wamel.voteverifier.sql;

import com.wamel.voteverifier.VoteVerifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerifyDBUtil {

    public static void insert(String name, String site) {
        try {
            if (isVerified(name, site))
                return;

            Connection connection = VoteVerifier.getConnection();

            String insertSQL = "insert into mcu_vote (name, site) VALUES (?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertSQL);

            preparedStatement1.setString(1, name);

            preparedStatement1.setString(2, site);

            preparedStatement1.executeUpdate();
            preparedStatement1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try {
            Connection connection = VoteVerifier.getConnection();

            String createTableSQL = "create table if not exists mcu_vote(name varchar(20), site varchar(20))";
            PreparedStatement preparedStatement1 = connection.prepareStatement(createTableSQL);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetTable() {
        try {
            Connection connection = VoteVerifier.getConnection();

            String resetTableSQL = "truncate mcu_vote";
            PreparedStatement preparedStatement1 = connection.prepareStatement(resetTableSQL);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isVerified(String name, String site) {
        try {
            Connection connection = VoteVerifier.getConnection();

            String selectTableSQL = "select site from mcu_vote where name = ?";
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectTableSQL);
            preparedStatement1.setString(1, name);

            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("site").equalsIgnoreCase(site)) {
                    resultSet.close();
                    preparedStatement1.close();
                    return true;
                }
            }

            resultSet.close();
            preparedStatement1.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
