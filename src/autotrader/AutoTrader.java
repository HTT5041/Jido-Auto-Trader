package autotrader;

import autotrader.tasks.*;
import com.hazion.api.peer.network.chat.Component;
import com.hazion.api.script.Manifest;
import com.hazion.api.script.Script;
import com.hazion.api.script.Task;

import java.util.ArrayList;

@Manifest(name = "Auto Trader", description = "Automatically completes player trades for items", version = 1, author = "HTT5041")
public class AutoTrader implements Script {

    private ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public void onStart() {
        Settings.pricePerItem = 500;
        Settings.minPlayerCount = 5;
        Settings.itemName = "cobblestone";
        Settings.chatMessages.add("example 1");
        Settings.chatMessages.add("example 2");
        Settings.chatMessages.add("example 3");
        Settings.chatMessages.add("example 4");
        Settings.chatMessages.add("example 5");
        Settings.minLobbyWait = 100;
        Settings.maxLobbyWait = 120;
        Settings.maxChatMessageCooldown = 60;
        Settings.minChatMessageCooldown = 40;

        //On island tasks
        tasks.add(new DepositItems());

        //Hub tasks
        tasks.add(new JoinHub());
        tasks.add(new SwapLobbies());
        tasks.add(new GoToMap());
        tasks.add(new ExecuteTrade());
        tasks.add(new WaitAtMap());
    }

    @Override
    public int poll() {
        for(Task t : tasks){
            if(t.verify()){
                return t.execute();
            }
        }
        return 100;
    }

    @Override
    public void onChatMessage(Component component, int i, int i1, boolean b) {
        //Click trade button when a player offers
        if(component.getString().contains("has sent you a trade request.")){
            component.executeClickEvent();
        }
    }
}
