package autotrader.tasks;

import autotrader.Settings;
import com.hazion.api.Game;
import com.hazion.api.script.Task;
import com.hazion.api.utils.Random;

public class WaitAtMap implements Task {

    private long swapAt = 0L;
    private long chatMessageCooldown = 0L;

    private String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890!";

    private String getRandomMessage(){
        int randIndex = Random.nextInt(0, Settings.chatMessages.size());
        StringBuilder message = new StringBuilder(Settings.chatMessages.get(randIndex));

        if(Settings.appendRandomChars){
            message.append(" ");
            for(int i = 0; i < 8; i++){
                message.append(alphabet.charAt(Random.nextInt(alphabet.length())));
            }
        }
        return message.toString();
    }

    @Override
    public boolean verify() {
        return true;
    }

    @Override
    public int execute() {
        if(swapAt == 0){
            //Calculate the time at which we should swap
            swapAt = System.currentTimeMillis() + (Random.nextLong(Settings.minLobbyWait, Settings.maxLobbyWait) * 1000);
            return 50;
        }

        if(System.currentTimeMillis() >= swapAt){
            //Swap lobbies
            swapAt = 0;
            SwapLobbies.doSwap = true;
            chatMessageCooldown = 0L;
            return 50;
        }

        if(System.currentTimeMillis() >= chatMessageCooldown){
            //Send our chat message
            Game.sendChatMessage(getRandomMessage());
            chatMessageCooldown = System.currentTimeMillis() + (Random.nextLong(Settings.minChatMessageCooldown, Settings.maxChatMessageCooldown) * 1000);
        }

        return 500;
    }
}
