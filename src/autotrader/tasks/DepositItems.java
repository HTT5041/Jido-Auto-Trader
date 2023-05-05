package autotrader.tasks;

import autotrader.Settings;
import com.hazion.api.Blocks;
import com.hazion.api.Containers;
import com.hazion.api.Inventory;
import com.hazion.api.Raycasting;
import com.hazion.api.camera.Camera;
import com.hazion.api.hypixel.skyblock.SkyBlock;
import com.hazion.api.hypixel.skyblock.SkyBlockLocation;
import com.hazion.api.input.Input;
import com.hazion.api.peer.world.inventory.MenuType;
import com.hazion.api.peer.world.level.block.state.BlockState;
import com.hazion.api.script.Task;
import com.hazion.api.utils.PlayerHelper;

public class DepositItems implements Task {
    private int getFirstSlot(){
        return Containers.getMenuType() == MenuType.GENERIC_9x3? 27 : 54;
    }

    @Override
    public boolean verify() {
        //If the inventory contains our item then execute the deposit code
        return Inventory.contains(z -> z.getCustomName().equalsIgnoreCase(Settings.itemName));
    }

    @Override
    public int execute() {
        if(SkyBlockLocation.getCurrentSkyBlockLocation() != SkyBlockLocation.PRIVATE_ISLAND){
            //Goto private island
            SkyBlock.sendSkyBlockHomeCommand();
            return 2000;
        }

        if(Containers.isContainerVisible()){
            //If the chest container is visible
            Containers.getItemSlots().stream()
                    //Filter for the item (ignoring the chest slots)
                    .filter(slot -> slot.hasItem() && slot.getItemStack().getCustomName().equalsIgnoreCase(Settings.itemName)
                            && slot.getIndex() >= getFirstSlot())
                    .findFirst()
                    //Quick move it to chest
                    .ifPresent(Containers::quickmove);
            return 150;
        }

        //Find closest chest that we can look at and reach
        BlockState closestChest = Blocks.getClosest(z -> z.getName().equalsIgnoreCase("chest")
                && Raycasting.look(z, PlayerHelper.getBlockReachDistance()) != null
                , (int) PlayerHelper.getBlockReachDistance());

        if(closestChest != null) {
            //Look at chest
            if(Camera.turnToBlock(closestChest.getBlockPosition())){
                //Open chest
                Input.CLICK_RIGHT.click();
                return 500;
            }
        }


        return 500;
    }
}
