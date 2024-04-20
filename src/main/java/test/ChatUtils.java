package test;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ChatUtils {
    public static void whisper(Player player, String sender, String message) {
        ChatColor senderColor = sender.equals("Jenny") ? ChatColor.AQUA : ChatColor.GREEN;
        ChatColor messageColor = ChatColor.WHITE;
        player.sendMessage(senderColor + sender + ": " + messageColor + message);
    }

    public static String callOllamaAPI(String prompt) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("127.0.0.0"); // This section controls the http request to the chat-bot api
            String jsonBody = "{\"model\": \"example\", \"prompt\": \"" + prompt.replace("\"", "\\\"") + "\", \"stream\": false}";
            StringEntity entity = new StringEntity(jsonBody);
            post.setEntity(entity);
            post.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(post);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (!jsonObject.has("response")) {
                return "Error: 'response' key not found in API response.";
            }
            return jsonObject.getString("response");
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to retrieve response due to an IOException.";
        } catch (JSONException e) {
            e.printStackTrace();
            return "Failed to parse the response correctly.";
        }
    }
}
