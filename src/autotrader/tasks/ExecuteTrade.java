package autotrader.tasks;

import autotrader.Settings;
import com.hazion.api.Chat;
import com.hazion.api.Containers;
import com.hazion.api.hypixel.skyblock.SkyBlockTrade;
import com.hazion.api.peer.world.inventory.MenuType;
import com.hazion.api.screen.Signs;
import com.hazion.api.script.Task;

public class ExecuteTrade implements Task {

    private int lastNumItems = 0;

    @Override
    public boolean verify() {
        return Containers.getMenuType() == MenuType.GENERIC_9x5 || Signs.isSignScreenVisible();
    }

    @Override
    public int execute() {
        if(Signs.isSignScreenVisible()){
            //Calculate the offer price
            int priceOffer = Math.round(lastNumItems * Settings.pricePerItem);
            //Set and save the text
            Signs.setText(String.valueOf(priceOffer));
            Signs.save();
            return 500;
        }

        //Fetch the number if desired items in the trade window
        int numItems = Containers.getItemStackCount(z -> z.getCustomName().equalsIgnoreCase(Settings.itemName));

        //If the number of items has not changed and is not zero (after 2 second delay which is at end)
        if(numItems == lastNumItems && numItems != 0){
            //If we have made our offer, accept the trade
            if(Containers.getSlotIndex(0).hasItem()){
                //Click accept trade button
                Containers.pickup(Containers.getSlotIndex(39));
                return 1000;
            }
            //Pickup coin offer item
            Containers.pickup(Containers.getSlotIndex(36));
            return 500;
        }
        //Clear our old offer
        if(Containers.getSlotIndex(0).hasItem()){
            //Coins in slot
            Containers.pickup(Containers.getSlotIndex(0));
            return 200;
        }
        lastNumItems = numItems;
        //Short delay so that we don't spam the shit out of the sign
        return 2000;
    }
}
