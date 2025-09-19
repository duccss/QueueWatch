package org.queuewatch.module;

import com.github.rfresh2.EventConsumer;
import com.zenith.Proxy;
import com.zenith.discord.Embed;
import com.zenith.event.chat.SystemChatEvent;
import com.zenith.module.api.Module;

import java.util.List;

import static com.github.rfresh2.EventConsumer.of;
import static com.zenith.Globals.DISCORD;
import static org.queuewatch.QueueWatchPlugin.PLUGIN_CONFIG;

public class QueueReconnectModule extends Module {
    @Override
    public boolean enabledSetting() {
        return PLUGIN_CONFIG.queuewatch.enabled;
    }

    @Override
    public List<EventConsumer<?>> registerEvents() {
        return List.of(of(SystemChatEvent.class, this::handleSystemChat));
    }

    private void handleSystemChat(SystemChatEvent event) {
        final String msg = event.message().trim();
        if (msg.equals(PLUGIN_CONFIG.queuewatch.triggerMessage)) {
            sendDiscordAlert("Queue message detected. Disconnecting.");
            Proxy.getInstance().disconnect("Triggered queue message");
        }
    }

    private void sendDiscordAlert(String message) {
        String roleId = PLUGIN_CONFIG.queuewatch.roleId;
        if (roleId != null && !roleId.isBlank()) {
            DISCORD.sendMessage("<@&" + roleId + ">");
        }

        Embed embed = new Embed()
            .title("QueueWatch Alert")
            .description(message)
            .primaryColor();

        DISCORD.sendEmbedMessage(embed);
    }
}
