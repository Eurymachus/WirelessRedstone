package wifi;

import net.minecraft.server.ItemStack;

public class TileEntityRedstoneWirelessT extends TileEntityRedstoneWireless
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
            ((BlockRedstoneWirelessT)WirelessRedstone.blockWirelessT).changeFreq(this.world, this.getBlockCoord(0), this.getBlockCoord(1), this.getBlockCoord(2), this.oldFreq, var1);
            this.oldFreq = var1;

            if (this.firstTick)
            {
                this.firstTick = false;
            }
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getName()
    {
        return "Wireless Transmitter";
    }
}
