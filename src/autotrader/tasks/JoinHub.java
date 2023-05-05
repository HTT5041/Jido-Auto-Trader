package autotrader.tasks;

import com.hazion.api.hypixel.skyblock.SkyBlock;
import com.hazion.api.hypixel.skyblock.SkyBlockSection;
import com.hazion.api.script.Task;

public class JoinHub implements Task {
    @Override
    public boolean verify() {
        return SkyBlockSection.getCurrentSkyBlockSection() != SkyBlockSection.VILLAGE;
    }

    @Override
    public int execute() {
        SkyBlock.sendSkyBlockHubCommand();
        return 2000;
    }
}
