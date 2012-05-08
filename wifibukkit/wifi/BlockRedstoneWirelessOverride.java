package wifi;

import java.util.Random;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.World;

public interface BlockRedstoneWirelessOverride
{
    void afterBlockRedstoneWirelessAdded(World var1, int var2, int var3, int var4);

    void afterBlockRedstoneWirelessRemoved(World var1, int var2, int var3, int var4);

    boolean beforeBlockRedstoneWirelessActivated(World var1, int var2, int var3, int var4, EntityHuman var5);

    void afterBlockRedstoneWirelessActivated(World var1, int var2, int var3, int var4, EntityHuman var5);

    void afterBlockRedstoneWirelessNeighborChange(World var1, int var2, int var3, int var4, int var5);

    boolean beforeUpdateRedstoneWirelessTick(World var1, int var2, int var3, int var4, Random var5);
}
