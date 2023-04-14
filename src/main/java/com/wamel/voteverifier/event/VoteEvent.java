package com.wamel.voteverifier.event;

import com.vexsoftware.votifier.bungee.events.VotifierEvent;
import com.vexsoftware.votifier.model.Vote;
import com.wamel.voteverifier.MessageUtil;
import com.wamel.voteverifier.config.Config;
import com.wamel.voteverifier.sql.FirstJoinDBUtil;
import com.wamel.voteverifier.sql.VerifyDBUtil;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.LinkedList;

public class VoteEvent implements Listener {

    public static Integer minelist_count = 0;
    public static Integer minepage_count = 0;

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (FirstJoinDBUtil.isFirstJoin(player.getUniqueId().toString()) == true) {
            LinkedList<ComponentBuilder> messages = MessageUtil.convert(player, Config.first_join_messages);

            for (ComponentBuilder component : messages) {
                player.sendMessage(component.create());
            }

            FirstJoinDBUtil.insert(player.getUniqueId().toString());
        } else {
            if (VerifyDBUtil.isVerified(player.getName(), "minelist") == false || VerifyDBUtil.isVerified(player.getName(), "minepage") == false) {
                LinkedList<ComponentBuilder> messages = MessageUtil.convert(player, Config.join_messages);

                for (ComponentBuilder component : messages) {
                    player.sendMessage(component.create());
                }
            }
        }
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();

        if (vote.getUsername() != "") {
            String name = vote.getUsername();
            String address = vote.getServiceName();

            if (address.contains("MineList")) {
                VerifyDBUtil.insert(name, "minelist");
                minelist_count++;
            } else if (address.contains("Minepage")) {
                VerifyDBUtil.insert(name, "minepage");
                minepage_count++;
            }
        }
    }

}
