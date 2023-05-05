package autotrader.tasks;

import autotrader.Settings;
import com.hazion.api.Game;
import com.hazion.api.hypixel.skyblock.SkyBlockLobby;
import com.hazion.api.script.Task;

public class SwapLobbies implements Task {

    public static boolean doSwap = false;
    private boolean hasSwapped = false;

    @Override
    public boolean verify() {
        return doSwap;
    }

    @Override
    public int execute() {
        if(hasSwapped){
            if(Game.getLevel() != null){
                if(SkyBlockLobby.getCurrentPlayers().size() >= Settings.minPlayerCount){
                    hasSwapped = false;
                    doSwap = false;
                    return 50;
                }
            }
            return 200;
        }

        if(!SkyBlockLobby.isMenuOpen()){
            SkyBlockLobby.open();
            return 500;
        }

        if(SkyBlockLobby.hasRandomHub()){
            SkyBlockLobby.selectLobby(SkyBlockLobby.Lobby.RANDOM_HUB);
            hasSwapped = true;
            return 500;
        }

        return 1000;
    }
}
