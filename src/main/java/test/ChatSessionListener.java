package test;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;
import java.util.Set;
import java.util.UUID;

public class ChatSessionListener implements Listener {
    private final Set<UUID> activeSessions;

    public ChatSessionListener(Set<UUID> activeSessions) {
        this.activeSessions = activeSessions;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        if (activeSessions.contains(playerId)) {
            event.setCancelled(true);  // Prevent normal chat processing
            String message = event.getMessage();

            if (message.startsWith("/")) {
                return; // Allow commands to process normally
            }

            // Display player's own message first as a whisper
            ChatUtils.whisper(player, "You", message);

            // Then send the player's message to the API and display Jenny's response
            String response = ChatUtils.callOllamaAPI(message);
            ChatUtils.whisper(player, "Jenny", response);
        }
    }
}
