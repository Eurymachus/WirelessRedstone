package wifi;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import wifi.network.PacketHandlerRedstoneWireless;

public class BlockRedstoneWirelessR extends BlockRedstoneWireless
{
    private boolean initialSchedule;

    protected BlockRedstoneWirelessR(int var1)
    {
        super(var1);
        this.c(1.0F);
        this.a(Block.i);
        this.a(true);
        this.initialSchedule = true;
    }

    public boolean hasTicked()
    {
        return !this.initialSchedule;
    }

    public void changeFreq(World var1, int var2, int var3, int var4, String var5, String var6)
    {
        RedstoneEther.getInstance().remReceiver(var1, var2, var3, var4, var5);
        RedstoneEther.getInstance().addReceiver(var1, var2, var3, var4, var6);
        this.a(var1, var2, var3, var4, (Random)null);
    }

    protected void onBlockRedstoneWirelessAdded(World var1, int var2, int var3, int var4)
    {
        RedstoneEther.getInstance().addReceiver(var1, var2, var3, var4, this.getFreq(var1, var2, var3, var4));
        var1.applyPhysics(var2, var3, var4, this.id);
        this.a(var1, var2, var3, var4, (Random)null);
    }

    protected void onBlockRedstoneWirelessRemoved(World var1, int var2, int var3, int var4)
    {
        RedstoneEther.getInstance().remReceiver(var1, var2, var3, var4, this.getFreq(var1, var2, var3, var4));
        var1.applyPhysics(var2, var3, var4, this.id);
    }

    protected boolean onBlockRedstoneWirelessActivated(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        TileEntity var6 = var1.getTileEntity(var2, var3, var4);

        if (var6 instanceof TileEntityRedstoneWirelessR)
        {
            PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo((EntityPlayer)var5, (TileEntityRedstoneWirelessR)var6, 0);
        }

        return true;
    }

    protected void onBlockRedstoneWirelessNeighborChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 != this.id)
        {
            this.a(var1, var2, var3, var4, (Random)null);
        }
    }

    protected int getBlockRedstoneWirelessTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.getState(var1.getData(var2, var3, var4)) ? (var5 != 0 && var5 != 1 ? WirelessRedstone.spriteROn : WirelessRedstone.spriteTopOn) : this.getBlockRedstoneWirelessTextureFromSide(var5);
    }

    protected int getBlockRedstoneWirelessTextureFromSide(int var1)
    {
        return var1 != 0 && var1 != 1 ? WirelessRedstone.spriteROff : WirelessRedstone.spriteTopOff;
    }

    protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity()
    {
        return new TileEntityRedstoneWirelessR();
    }

    protected void updateRedstoneWirelessTick(World var1, int var2, int var3, int var4, Random var5)
    {
        if (this.initialSchedule)
        {
            this.initialSchedule = false;
        }

        if (var1 != null)
        {
            String var6 = this.getFreq(var1, var2, var3, var4);
            boolean var7 = this.getState(var1, var2, var3, var4);
            boolean var8 = RedstoneEther.getInstance().getFreqState(var1, var6);

            if (var8 != var7)
            {
                this.setState(var1, var2, var3, var4, var8);
                var1.notify(var2, var3, var4);
                notifyNeighbors(var1, var2, var3, var4);
                TileEntity var9 = var1.getTileEntity(var2, var3, var4);

                if (var9 instanceof TileEntityRedstoneWireless)
                {
                    PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWireless)var9, var1, 0);
                }
            }
        }
    }

    protected boolean isRedstoneWirelessPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getTileEntity(var2, var3, var4);
        return !(var6 instanceof TileEntityRedstoneWireless) ? false : ((TileEntityRedstoneWireless)var6).isPoweringDirection(var5) && this.getState(var1, var2, var3, var4);
    }

    protected boolean isRedstoneWirelessIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        TileEntity var6 = var1.getTileEntity(var2, var3, var4);
        return var6 instanceof TileEntityRedstoneWireless ? (!((TileEntityRedstoneWireless)var6).isPoweringIndirectly(var5) ? false : this.a(var1, var2, var3, var4, var5)) : false;
    }
}
