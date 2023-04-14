package com.wamel.voteverifier.sql;

import com.wamel.voteverifier.VoteVerifier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FirstJoinDBUtil {

    public static void insert(String uuid) {
        try {
            Connection connection = VoteVerifier.getConnection();

            String insertSQL = "insert into mcu_vote_first_join (uuid) VALUES (?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertSQL);

            preparedStatement1.setString(1, uuid);

            preparedStatement1.executeUpdate();
            preparedStatement1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try {
            Connection connection = VoteVerifier.getConnection();

            String createTableSQL = "create table if not exists mcu_vote_first_join(uuid varchar(50))";
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

            String resetTableSQL = "truncate mcu_vote_first_join";
            PreparedStatement preparedStatement1 = connection.prepareStatement(resetTableSQL);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean isFirstJoin(String uuid) {
        try {
            Connection connection = VoteVerifier.getConnection();

            String selectTableSQL = "select uuid from mcu_vote_first_join";
            PreparedStatement preparedStatement1 = connection.prepareStatement(selectTableSQL);

            ResultSet resultSet = preparedStatement1.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("uuid").equalsIgnoreCase(uuid)) {
                    resultSet.close();
                    preparedStatement1.close();
                    return false;
                }
            }

            resultSet.close();
            preparedStatement1.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
