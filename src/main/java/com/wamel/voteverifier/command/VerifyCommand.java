package com.wamel.voteverifier.command;

import com.wamel.voteverifier.MessageUtil;
import com.wamel.voteverifier.config.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.LinkedList;

public class VerifyCommand extends Command {

    public VerifyCommand() {
        super("추천", null, "cncjs");
    }

    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer player = (ProxiedPlayer) sender;

            LinkedList<ComponentBuilder> messages = MessageUtil.convert(player, Config.cmd_messages);

            for (ComponentBuilder component : messages) {
                player.sendMessage(component.create());
            }
        }
    }

}
