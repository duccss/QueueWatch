package org.queuewatch;

import com.zenith.plugin.api.Plugin;
import com.zenith.plugin.api.PluginAPI;
import com.zenith.plugin.api.ZenithProxyPlugin;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.queuewatch.command.QueueWatchCommand;
import org.queuewatch.module.QueueReconnectModule;

@Plugin(
    id = "queuewatch-plugin",
    version = BuildConstants.VERSION,
    description = "Auto reconnect plugin for constantiams queue.",
    url = "https://github.com/duccss",
    authors = {"ducpai"},
    mcVersions = {"1.21.4"}
)
public class QueueWatchPlugin implements ZenithProxyPlugin {
    public static ComponentLogger LOG;
    public static QueueWatchConfig PLUGIN_CONFIG;

    @Override
    public void onLoad(PluginAPI pluginAPI) {
        LOG = pluginAPI.getLogger();
        LOG.info("QueueWatch Plugin loading...");
        PLUGIN_CONFIG = pluginAPI.registerConfig("queuewatch", QueueWatchConfig.class);
        pluginAPI.registerModule(new QueueReconnectModule());
        pluginAPI.registerCommand(new QueueWatchCommand());
        LOG.info("QueueWatch Plugin loaded.");
    }
}
