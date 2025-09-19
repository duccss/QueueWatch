package org.queuewatch.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import com.zenith.command.api.Command;
import com.zenith.command.api.CommandCategory;
import com.zenith.command.api.CommandContext;
import com.zenith.command.api.CommandUsage;
import com.zenith.discord.Embed;

import static com.zenith.command.brigadier.ToggleArgumentType.getToggle;
import static com.zenith.command.brigadier.ToggleArgumentType.toggle;

import static com.zenith.Globals.MODULE;
import static org.queuewatch.QueueWatchPlugin.PLUGIN_CONFIG;

import org.queuewatch.module.QueueReconnectModule;

public class QueueWatchCommand extends Command {

    @Override
    public CommandUsage commandUsage() {
        return CommandUsage.builder()
            .name("queuewatch")
            .category(CommandCategory.MODULE)
            .description("Enable/disable QueueWatch and configure the role ping")
            .usageLines(
                "<on|off>",
                "role <roleId|off>"
            )
            .build();
    }

    @Override
    public LiteralArgumentBuilder<CommandContext> register() {
        return command("queuewatch")
            // on/off using toggle()
            .then(argument("toggle", toggle()).executes(c -> {
                PLUGIN_CONFIG.queuewatch.enabled = getToggle(c, "toggle");
                MODULE.get(QueueReconnectModule.class).syncEnabledFromConfig();
                c.getSource().getEmbed()
                    .title("QueueWatch " + toggleStrCaps(PLUGIN_CONFIG.queuewatch.enabled))
                    .primaryColor();
                return 1;
            }))

            // role <id|off>
            .then(literal("role")
                .then(argument("value", StringArgumentType.word()).executes(c -> {
                    String v = StringArgumentType.getString(c, "value");
                    boolean turningOff = "off".equalsIgnoreCase(v);
                    PLUGIN_CONFIG.queuewatch.roleId = turningOff ? "" : v;
                    c.getSource().getEmbed()
                        .title(turningOff
                            ? "QueueWatch role ping: OFF"
                            : "QueueWatch role ping updated")
                        .primaryColor();

                    return 1;
                }))
            );
    }

    @Override
    public void defaultEmbed(Embed embed) {
        embed
            .primaryColor()
            .addField("Enabled", toggleStr(PLUGIN_CONFIG.queuewatch.enabled))
            .addField("Role Ping",
                (PLUGIN_CONFIG.queuewatch.roleId == null || PLUGIN_CONFIG.queuewatch.roleId.isBlank())
                    ? "Off"
                    : "<@&" + PLUGIN_CONFIG.queuewatch.roleId + ">");
    }
}

