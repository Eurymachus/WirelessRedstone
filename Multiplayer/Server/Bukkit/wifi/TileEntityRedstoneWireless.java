package wifi;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;
import wifi.network.PacketPayload;
import wifi.network.PacketRedstoneEther;

public abstract class TileEntityRedstoneWireless extends TileEntity implements IInventory
{
    public boolean firstTick = true;
    public String oldFreq;
    public String currentFreq;
    protected boolean[] powerRoute;
    protected boolean[] indirPower;

    public TileEntityRedstoneWireless()
    {
        this.firstTick = true;
        this.oldFreq = "";
        this.currentFreq = "0";
        this.firstTick = false;
        this.flushPowerRoute();
        this.flushIndirPower();
    }

	@Override
	public ItemStack[] getContents()
	{
		return null;
	}

	@Override
	public void setMaxStackSize(int arg0)
	{
	}
	
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSize()
    {
        return 0;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getItem(int var1)
    {
        return null;
    }

    public String getFreq()
    {
        return this.currentFreq;
    }

    public void setFreq(String var1)
    {
        try
        {
            this.currentFreq = var1;

            if (this.world != null)
            {
                this.q_();
            }
        }
        catch (Exception var3)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var3);
        }
    }

    public int getBlockCoord(int var1)
    {
        switch (var1)
        {
            case 0:
                return this.x;

            case 1:
                return this.y;

            case 2:
                return this.z;

            default:
                return 0;
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public abstract void q_();

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack splitStack(int var1, int var2)
    {
        return null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int var1, ItemStack var2)
    {
        this.update();
    }

    /**
     * Returns the name of the inventory.
     */
    public abstract String getName();

    public boolean isPoweringDirection(int var1)
    {
        return var1 < 6 ? this.powerRoute[var1] : false;
    }

    public void flipPowerDirection(int var1)
    {
        if (this.isPoweringIndirectly(var1) && this.powerRoute[var1])
        {
            this.flipIndirectPower(var1);
        }

        this.powerRoute[var1] = !this.powerRoute[var1];
    }

    public void flushPowerRoute()
    {
        this.powerRoute = new boolean[6];

        for (int var1 = 0; var1 < this.powerRoute.length; ++var1)
        {
            this.powerRoute[var1] = true;
        }
    }

    public void flipIndirectPower(int var1)
    {
        if (!this.isPoweringDirection(var1) && !this.indirPower[var1])
        {
            this.flipPowerDirection(var1);
        }

        this.indirPower[var1] = !this.indirPower[var1];
    }

    public boolean isPoweringIndirectly(int var1)
    {
        return var1 < 6 ? this.indirPower[var1] : false;
    }

    public void flushIndirPower()
    {
        this.indirPower = new boolean[6];

        for (int var1 = 0; var1 < this.indirPower.length; ++var1)
        {
            this.indirPower[var1] = true;
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void a(NBTTagCompound var1)
    {
        try
        {
            super.a(var1);
            NBTTagList var2 = var1.getList("Frequency");
            NBTTagCompound var3 = (NBTTagCompound)var2.get(0);
            this.currentFreq = var3.getString("freq");
            NBTTagList var4 = var1.getList("PowerRoute");

            if (var4.size() == 6)
            {
                for (int var5 = 0; var5 < var4.size(); ++var5)
                {
                    NBTTagCompound var6 = (NBTTagCompound)var4.get(var5);
                    this.powerRoute[var5] = var6.getBoolean("b");
                }
            }
            else
            {
                this.flushPowerRoute();
                this.b(var1);
            }

            NBTTagList var9 = var1.getList("IndirPower");

            if (var9.size() == 6)
            {
                for (int var10 = 0; var10 < var9.size(); ++var10)
                {
                    NBTTagCompound var7 = (NBTTagCompound)var9.get(var10);
                    this.indirPower[var10] = var7.getBoolean("b");
                }
            }
            else
            {
                this.flushIndirPower();
                this.b(var1);
            }
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void b(NBTTagCompound var1)
    {
        try
        {
            super.b(var1);
            NBTTagList var2 = new NBTTagList();
            NBTTagCompound var3 = new NBTTagCompound();
            var3.setString("freq", this.currentFreq.toString());
            var2.add(var3);
            var1.set("Frequency", var2);
            NBTTagList var4 = new NBTTagList();

            for (int var5 = 0; var5 < this.powerRoute.length; ++var5)
            {
                NBTTagCompound var6 = new NBTTagCompound();
                var6.setBoolean("b", this.powerRoute[var5]);
                var4.add(var6);
            }

            var1.set("PowerRoute", var4);
            NBTTagList var9 = new NBTTagList();

            for (int var10 = 0; var10 < this.indirPower.length; ++var10)
            {
                NBTTagCompound var7 = new NBTTagCompound();
                var7.setBoolean("b", this.indirPower[var10]);
                var9.add(var7);
            }

            var1.set("IndirPower", var9);
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getMaxStackSize()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean a(EntityHuman var1)
    {
        try
        {
            return this.world.getTileEntity(this.x, this.y, this.z) != this ? false : var1.e((double)this.x + 0.5D, (double)this.y + 0.5D, (double)this.z + 0.5D) <= 64.0D;
        }
        catch (Exception var3)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var3);
            return false;
        }
    }

    public void f() {}

    public void g() {}

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack splitWithoutUpdate(int var1)
    {
        return null;
    }

    /**
     * Overriden in a sign to provide the text
     */
    public Packet d()
    {
        return this.getUpdatePacket();
    }

    public Packet getUpdatePacket()
    {
        return (new PacketRedstoneEther(this)).getPacket();
    }
}
