package wifi;

import java.util.Random;

import net.minecraft.server.ItemStack;

public class TileEntityRedstoneWirelessR extends TileEntityRedstoneWireless
{
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        String var1 = this.getFreq();

        if (!this.oldFreq.equals(var1) || this.firstTick)
        {
            ((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).changeFreq(this.world, this.getBlockCoord(0), this.getBlockCoord(1), this.getBlockCoord(2), this.oldFreq, var1);
            this.oldFreq = var1;

            if (this.firstTick)
            {
                this.firstTick = false;
            }
        }

        if (!((BlockRedstoneWirelessR)WirelessRedstone.blockWirelessR).hasTicked())
        {
            WirelessRedstone.blockWirelessR.a(this.world, this.getBlockCoord(0), this.getBlockCoord(1), this.getBlockCoord(2), (Random)null);
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "Wireless Receiver";
    }
}
