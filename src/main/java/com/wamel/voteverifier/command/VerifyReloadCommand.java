package com.wamel.voteverifier.command;

import com.wamel.voteverifier.config.Config;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class VerifyReloadCommand extends Command {

    public VerifyReloadCommand() {
        super("vreload", "gm.use");
    }

    public void execute(CommandSender sender, String[] args) {
        Config.reload();
        sender.sendMessage("§e[VoteVotifier] config를 리로드했습니다.");
    }

}
