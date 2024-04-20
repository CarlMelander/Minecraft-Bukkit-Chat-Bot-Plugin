package test;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatBotPlugin extends JavaPlugin {

    private JennyCommandExecutor jennyExecutor;

    @Override
    public void onEnable() {
        jennyExecutor = new JennyCommandExecutor();
        getCommand("jenny").setExecutor(jennyExecutor);
        getServer().getPluginManager().registerEvents(new ChatSessionListener(jennyExecutor.getActiveSessions()), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("MyPlugin has been disabled.");
    }
}

