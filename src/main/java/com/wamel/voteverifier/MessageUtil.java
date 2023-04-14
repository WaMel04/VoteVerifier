package com.wamel.voteverifier;

import com.wamel.voteverifier.config.Config;
import com.wamel.voteverifier.event.VoteEvent;
import com.wamel.voteverifier.sql.VerifyDBUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.LinkedList;

public class MessageUtil {

    public static LinkedList<ComponentBuilder> convert(ProxiedPlayer player, LinkedList<String> list) {
        LinkedList<ComponentBuilder> results = new LinkedList<>();

        for (String message : list) {
            if (VerifyDBUtil.isVerified(player.getName(), "minelist") == true) {
                message = message.replaceAll("%minelist_state%", Config.minelist_true);
            } else {
                message = message.replaceAll("%minelist_state%", Config.minelist_false);
            }
            if (VerifyDBUtil.isVerified(player.getName(), "minepage") == true) {
                message = message.replaceAll("%minepage_state%", Config.minepage_true);
            } else {
                message = message.replaceAll("%minepage_state%", Config.minepage_false);
            }

            message = message.replaceAll("%minelist_count%", String.valueOf(VoteEvent.minelist_count));
            message = message.replaceAll("%minepage_count%", String.valueOf(VoteEvent.minepage_count));

            message = message.replaceAll("&", "§");

            if (message.contains("<")) {
                String[] splitedMessage = message.split("<");

                ComponentBuilder builder = new ComponentBuilder("");

                int i = 0;
                for (String s : splitedMessage) {
                    if (s.contains("open_url>") && !s.contains("/open_url>")) { // open_url
                        s = s.replaceAll("open_url>", "");

                        String split[] = s.split("\\|\\|");

                        builder.append(split[0]).event(new ClickEvent(ClickEvent.Action.OPEN_URL, split[1])).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(split[2])));
                    } else if (s.contains("/open_url>")) {
                        s = s.replaceAll("/open_url>", "");

                        builder.append(s).event((ClickEvent) null).event((HoverEvent) null);
                    } else {
                        builder.append(s).event((ClickEvent) null).event((HoverEvent) null);
                    }
                    i++;
                }

                results.add(builder);
            } else {
                TextComponent base = new TextComponent(message);

                results.add(new ComponentBuilder(base));
            }
        }

        return results;
    }

}
