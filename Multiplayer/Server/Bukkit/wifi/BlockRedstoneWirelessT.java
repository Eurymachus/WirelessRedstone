package wifi;

import java.util.Random;
import net.minecraft.server.Block;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IBlockAccess;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import wifi.network.PacketHandlerRedstoneWireless;

public class BlockRedstoneWirelessT extends BlockRedstoneWireless
{
    protected BlockRedstoneWirelessT(int var1, float var2, float var3)
    {
        super(var1);
        this.c(var2);
        this.b(var3);
        this.a(Block.i);
    }

    public void setState(World var1, int var2, int var3, int var4, boolean var5)
    {
        super.setState(var1, var2, var3, var4, var5);
        String var6 = this.getFreq(var1, var2, var3, var4);
        RedstoneEther.getInstance().setTransmitterState(var1, var2, var3, var4, var6, var5);
    }

    public void changeFreq(World var1, int var2, int var3, int var4, String var5, String var6)
    {
        RedstoneEther.getInstance().remTransmitter(var1, var2, var3, var4, var5);
        RedstoneEther.getInstance().addTransmitter(var1, var2, var3, var4, var6);
        RedstoneEther.getInstance().setTransmitterState(var1, var2, var3, var4, var6, this.getState(var1, var2, var3, var4));
    }

    protected void onBlockRedstoneWirelessAdded(World var1, int var2, int var3, int var4)
    {
        RedstoneEther.getInstance().addTransmitter(var1, var2, var3, var4, this.getFreq(var1, var2, var3, var4));
        this.onBlockRedstoneWirelessNeighborChange(var1, var2, var3, var4, Block.REDSTONE_WIRE.id);
    }

    protected void onBlockRedstoneWirelessRemoved(World var1, int var2, int var3, int var4)
    {
        RedstoneEther.getInstance().remTransmitter(var1, var2, var3, var4, this.getFreq(var1, var2, var3, var4));
    }

    protected boolean onBlockRedstoneWirelessActivated(World var1, int var2, int var3, int var4, EntityHuman var5)
    {
        TileEntity var6 = var1.getTileEntity(var2, var3, var4);

        if (var6 instanceof TileEntityRedstoneWirelessT)
        {
            PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo((EntityPlayer)var5, (TileEntityRedstoneWirelessT)var6, 0);
        }

        return true;
    }

    protected void onBlockRedstoneWirelessNeighborChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 != this.id)
        {
            if (var5 > 0 && !this.getState(var1, var2, var3, var4) && (var1.isBlockPowered(var2, var3, var4) || var1.isBlockIndirectlyPowered(var2, var3, var4)))
            {
                this.setState(var1, var2, var3, var4, true);
            }
            else if (this.getState(var1, var2, var3, var4) && !var1.isBlockPowered(var2, var3, var4) && !var1.isBlockIndirectlyPowered(var2, var3, var4))
            {
                this.setState(var1, var2, var3, var4, false);
            }
        }
    }

    protected int getBlockRedstoneWirelessTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return this.getState(var1.getData(var2, var3, var4)) ? (var5 != 0 && var5 != 1 ? WirelessRedstone.spriteTOn : WirelessRedstone.spriteTopOn) : this.getBlockRedstoneWirelessTextureFromSide(var5);
    }

    protected int getBlockRedstoneWirelessTextureFromSide(int var1)
    {
        return var1 != 0 && var1 != 1 ? WirelessRedstone.spriteTOff : WirelessRedstone.spriteTopOff;
    }

    protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity()
    {
        return new TileEntityRedstoneWirelessT();
    }

    protected void updateRedstoneWirelessTick(World var1, int var2, int var3, int var4, Random var5) {}

    protected boolean isRedstoneWirelessPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        return false;
    }

    protected boolean isRedstoneWirelessIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5)
    {
        return false;
    }
}
