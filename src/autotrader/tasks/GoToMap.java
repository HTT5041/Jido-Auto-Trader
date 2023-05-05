package autotrader.tasks;

import com.hazion.api.pathfinder.movement.MovementHandler;
import com.hazion.api.script.Task;
import com.hazion.api.utils.PlayerHelper;
import com.hazion.api.utils.Random;
import com.hazion.api.world.blocks.BlockPos;

public class GoToMap implements Task {

    private BlockPos mapBlockPos = new BlockPos(-5, 69, -91);

    @Override
    public boolean verify() {
        return PlayerHelper.playerFeet().distance(mapBlockPos) > 10;
    }

    @Override
    public int execute() {
        if(MovementHandler.isTraversing()){
            return 100;
        }

        int xRand = Random.nextInt(-2, 2);
        int zRand = Random.nextInt(-2, 2);
        //Pick a random point at the map and go there
        MovementHandler.walkTo(mapBlockPos.add(xRand, 0, zRand));
        return 500;
    }
}
