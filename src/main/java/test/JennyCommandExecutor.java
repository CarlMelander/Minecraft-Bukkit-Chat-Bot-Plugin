package test;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class JennyCommandExecutor implements CommandExecutor {
    private final Set<UUID> activeSessions = new HashSet<>();

    public JennyCommandExecutor() {
    }

    public Set<UUID> getActiveSessions() {
        return activeSessions;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can interact with Jenny.");
            return true;
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("end")) {
            if (activeSessions.contains(player.getUniqueId())) {
                activeSessions.remove(player.getUniqueId());
                ChatUtils.whisper(player, "Jenny", "Goodbye. Type /jenny anytime to speak again.");
            } else {
                player.sendMessage(ChatColor.RED + "You don't have an active chat session with Jenny.");
            }
            return true;
        }

        if (!activeSessions.contains(player.getUniqueId())) {
            activeSessions.add(player.getUniqueId());
            ChatUtils.whisper(player, "Jenny", "Hello! My name is Jenny. Use the command '/jenny end' to end this conversation at any time.");
            return true;
        }

        if (args.length > 0) {
            String prompt = String.join(" ", args);
            String response = ChatUtils.callOllamaAPI(prompt);
            ChatUtils.whisper(player, "Jenny", response);
        } else {
            ChatUtils.whisper(player, "Jenny", "Please type your message or use '/jenny end' to end this conversation.");
        }

        return true;
    }
}
