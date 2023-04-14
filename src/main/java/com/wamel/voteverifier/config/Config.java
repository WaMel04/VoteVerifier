package com.wamel.voteverifier.config;

import com.wamel.voteverifier.VoteVerifier;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class Config {

    private static VoteVerifier plugin = VoteVerifier.getInstance();

    public static LinkedList<String> first_join_messages = getMessages("first_join_messages");
    public static LinkedList<String> cmd_messages = getMessages("cmd_messages");
    public static LinkedList<String> join_messages = getMessages("join_messages");

    private static LinkedList<String> getMessages(String key) {
        LinkedList<String> list = new LinkedList<>();

        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            ArrayList<String> arrayList = (ArrayList<String>) configuration.getList(key);
            for (String message : arrayList) {
                list.add(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static Object get(String key) {
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            return configuration.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String minelist_true = (String) get("minelist_true");
    public static String minelist_false = (String) get("minelist_false");
    public static String minepage_true = (String) get("minepage_true");
    public static String minepage_false = (String) get("minepage_false");
    
    public static void reload() {
        first_join_messages = getMessages("first_join_messages");
        cmd_messages = getMessages("cmd_messages");

        minelist_true = (String) get("minelist_true");
        minelist_false = (String) get("minelist_false");
        minepage_true = (String) get("minepage_true");
        minepage_false = (String) get("minepage_false");
    }
}
