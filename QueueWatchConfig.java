package org.queuewatch;

public class QueueWatchConfig {
    public final QueueModuleConfig queuewatch = new QueueModuleConfig();
    public static class QueueModuleConfig {
        public boolean enabled = true;
        public String roleId = "";
        public String triggerMessage = "🌌 You were sent to the queue.\nYou will be reconnected when the server is back online.";
    }
}
