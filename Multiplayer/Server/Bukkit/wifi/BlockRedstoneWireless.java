package wifi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.BlockContainer;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public abstract class BlockRedstoneWireless extends BlockContainer
{
    private List overrides = new ArrayList();

    protected BlockRedstoneWireless(int var1)
    {
        super(var1, Material.ORIENTABLE);
    }

    public void addOverride(BlockRedstoneWirelessOverride var1)
    {
        this.overrides.add(var1);
    }

    public void setState(World var1, int var2, int var3, int var4, boolean var5)
    {
        byte var6 = 0;

        if (var5)
        {
            var6 = 1;
        }

        try
        {
            var1.setData(var2, var3, var4, var6);
            var1.notify(var2, var3, var4);
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    public boolean getState(World var1, int var2, int var3, int var4)
    {
        int var5 = 0;

        try
        {
            var5 = var1.getData(var2, var3, var4);
        }
        catch (Exception var7)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
        }

        return this.getState(var5);
    }

    public boolean getState(int var1)
    {
        return (var1 & 1) == 1;
    }

    public String getFreq(World var1, int var2, int var3, int var4)
    {
        try
        {
            TileEntity var5 = var1.getTileEntity(var2, var3, var4);

            if (var5 == null)
            {
                return null;
            }

            if (var5 instanceof TileEntityRedstoneWireless)
            {
                return ((TileEntityRedstoneWireless)var5).getFreq();
            }
        }
        catch (Exception var6)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var6);
        }

        return null;
    }

    public abstract void changeFreq(World var1, int var2, int var3, int var4, String var5, String var6);

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onPlace(World var1, int var2, int var3, int var4)
    {
        try
        {
            TileEntityRedstoneWireless var5 = (TileEntityRedstoneWireless)this.a_();
            var1.setTileEntity(var2, var3, var4, var5);
            this.onBlockRedstoneWirelessAdded(var1, var2, var3, var4);
            Iterator var6 = this.overrides.iterator();

            while (var6.hasNext())
            {
                BlockRedstoneWirelessOverride var7 = (BlockRedstoneWirelessOverride)var6.next();
                var7.afterBlockRedstoneWirelessAdded(var1, var2, var3, var4);
            }
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    protected abstract void onBlockRedstoneWirelessAdded(World var1, int var2, int var3, int var4);

    /**
     * Called whenever the block is removed.
     */
    public void remove(World var1, int var2, int var3, int var4)
    {
        try
        {
            this.onBlockRedstoneWirelessRemoved(var1, var2, var3, var4);
            Iterator var5 = this.overrides.iterator();

            while (var5.hasNext())
            {
                BlockRedstoneWirelessOverride var6 = (BlockRedstoneWirelessOverride)var5.next();
                var6.afterBlockRedstoneWirelessRemoved(var1, var2, var3, var4);
            }

            var1.q(var2, var3, var4);
        }
        catch (Exception var7)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
        }
    }

    protected abstract void onBlockRedstoneWirelessRemoved(World var1, int var2, int var3, int var4);

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int getDropType(int var1, Random var2, int var3)
    {
        return this.id;
    }

    /**
     * Called upon block activation (left or right click on the block.). The three integers represent x,y,z of the
     * block.
     */
    public boolean interact(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        try
        {
            boolean var6 = true;
            Iterator var7 = this.overrides.iterator();

            while (var7.hasNext())
            {
                BlockRedstoneWirelessOverride var8 = (BlockRedstoneWirelessOverride)var7.next();
                var6 = var8.beforeBlockRedstoneWirelessActivated(var1, var2, var3, var4, var5);

                if (var6)
                {
                    return false;
                }
            }

            boolean var11 = false;
            var11 = this.onBlockRedstoneWirelessActivated(var1, var2, var3, var4, var5);
            Iterator var12 = this.overrides.iterator();

            while (var12.hasNext())
            {
                BlockRedstoneWirelessOverride var9 = (BlockRedstoneWirelessOverride)var12.next();
                var9.afterBlockRedstoneWirelessActivated(var1, var2, var3, var4, var5);
            }

            return var11;
        }
        catch (Exception var10)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var10);
            return false;
        }
    }

    protected abstract boolean onBlockRedstoneWirelessActivated(World var1, int var2, int var3, int var4, EntityHuman var5);

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void doPhysics(World var1, int var2, int var3, int var4, int var5)
    {
        try
        {
            this.onBlockRedstoneWirelessNeighborChange(var1, var2, var3, var4, var5);
            Iterator var6 = this.overrides.iterator();

            while (var6.hasNext())
            {
                BlockRedstoneWirelessOverride var7 = (BlockRedstoneWirelessOverride)var6.next();
                var7.afterBlockRedstoneWirelessNeighborChange(var1, var2, var3, var4, var5);
            }
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    protected abstract void onBlockRedstoneWirelessNeighborChange(World var1, int var2, int var3, int var4, int var5);

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean a()
    {
        return false;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean isPowerSource()
    {
        return true;
    }

    protected abstract int getBlockRedstoneWirelessTexture(IBlockAccess var1, int var2, int var3, int var4, int var5);

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int a(int var1)
    {
        try
        {
            return this.getBlockRedstoneWirelessTextureFromSide(var1);
        }
        catch (Exception var3)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var3);
            return 0;
        }
    }

    protected abstract int getBlockRedstoneWirelessTextureFromSide(int var1);

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity a_()
    {
        return this.getBlockRedstoneWirelessEntity();
    }

    protected abstract TileEntityRedstoneWireless getBlockRedstoneWirelessEntity();

    public static void notifyNeighbors(World var0, int var1, int var2, int var3)
    {
        var0.applyPhysics(var1, var2, var3, 0);
        var0.applyPhysics(var1 - 1, var2, var3, 0);
        var0.applyPhysics(var1 + 1, var2, var3, 0);
        var0.applyPhysics(var1, var2 - 1, var3, 0);
        var0.applyPhysics(var1, var2 + 1, var3, 0);
        var0.applyPhysics(var1, var2, var3 - 1, 0);
        var0.applyPhysics(var1, var2, var3 + 1, 0);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void a(World var1, int var2, int var3, int var4, Random var5)
    {
        try
        {
            Iterator var6 = this.overrides.iterator();

            while (var6.hasNext())
            {
                BlockRedstoneWirelessOverride var7 = (BlockRedstoneWirelessOverride)var6.next();

                if (var7.beforeUpdateRedstoneWirelessTick(var1, var2, var3, var4, var5))
                {
                    return;
                }
            }

            this.updateRedstoneWirelessTick(var1, var2, var3, var4, var5);
        }
        catch (Exception var8)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var8);
        }
    }

    protected abstract void updateRedstoneWirelessTick(World var1, int var2, int var3, int var4, Random var5);

    /**
     * Is this block powering the block on the specified side
     */
    public boolean a(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        try
        {
            if (var1 instanceof World)
            {
                return this.isRedstoneWirelessPoweringTo((World)var1, var2, var3, var4, var5);
            }
        }
        catch (Exception var7)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
        }

        return false;
    }

    protected abstract boolean isRedstoneWirelessPoweringTo(World var1, int var2, int var3, int var4, int var5);

    /**
     * Is this block indirectly powering the block on the specified side
     */
    public boolean d(World var1, int var2, int var3, int var4, int var5)
    {
        try
        {
            return this.isRedstoneWirelessIndirectlyPoweringTo(var1, var2, var3, var4, var5);
        }
        catch (Exception var7)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
            return false;
        }
    }

    protected abstract boolean isRedstoneWirelessIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5);
}
