package wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.World;
import wifi.BlockRedstoneWireless;
import wifi.TileEntityRedstoneWireless;
import wifi.TileEntityRedstoneWirelessR;
import wifi.TileEntityRedstoneWirelessT;
import wifi.WirelessRedstone;

public class PacketRedstoneEther extends PacketUpdate
{
    public boolean state;

    public PacketRedstoneEther()
    {
        super(0);
        this.state = false;
    }

    public PacketRedstoneEther(String var1)
    {
        this();
        PacketPayload var2 = new PacketPayload(1, 1, 2);
        var2.stringPayload[0] = "";
        var2.stringPayload[1] = var1;
        var2.intPayload[0] = 0;
        var2.floatPayload[0] = 0.0F;
        this.payload = var2;
    }

    public PacketRedstoneEther(TileEntityRedstoneWireless var1, World var2)
    {
        this();
        this.xPosition = var1.getBlockCoord(0);
        this.yPosition = var1.getBlockCoord(1);
        this.zPosition = var1.getBlockCoord(2);
        PacketPayload var3 = new PacketPayload(1, 1, 2);
        var3.stringPayload[0] = var1.getFreq();

        if (var1 instanceof TileEntityRedstoneWirelessR)
        {
            this.state = ((BlockRedstoneWireless)WirelessRedstone.blockWirelessR).getState(var2, this.xPosition, this.yPosition, this.zPosition);
            var3.stringPayload[1] = "addReceiver";
        }
        else if (var1 instanceof TileEntityRedstoneWirelessT)
        {
            this.state = ((BlockRedstoneWireless)WirelessRedstone.blockWirelessT).getState(var2, this.xPosition, this.yPosition, this.zPosition);
            var3.stringPayload[1] = "addTransmitter";
        }

        var3.intPayload[0] = 0;
        var3.floatPayload[0] = 0.0F;
        this.payload = var3;
    }

    public PacketRedstoneEther(TileEntityRedstoneWireless var1)
    {
		this();
		this.xPosition = var1.getBlockCoord(0);
		this.yPosition = var1.getBlockCoord(1);
		this.zPosition = var1.getBlockCoord(2);
		PacketPayload p = new PacketPayload(1,1,2);
		int[] dataInt = new int[1];
		float[] dataFloat = new float[1];
		String[] dataString = new String[2];
		dataInt[0] = 0;
		dataFloat[0] = 0;
		dataString[0] = var1.getFreq();
		dataString[1] = "fetchTile";
		p.intPayload = dataInt;
		p.floatPayload = dataFloat;
		p.stringPayload = dataString;
		this.payload = p;
    }

    public String toString()
    {
        return this.payload.stringPayload[1] + "(" + this.xPosition + "," + this.yPosition + "," + this.zPosition + ")[" + this.payload.stringPayload[0] + "]:" + this.state;
    }

    public String getFreq()
    {
        return this.payload.stringPayload[0];
    }

    public String getCommand()
    {
        return this.payload.stringPayload[1];
    }

    public void setPosition(int var1, int var2, int var3)
    {
        this.xPosition = var1;
        this.yPosition = var2;
        this.zPosition = var3;
    }

    public void setFreq(Object var1)
    {
        this.payload.stringPayload[0] = var1.toString();
    }

    public void setState(boolean var1)
    {
        this.state = var1;
    }

    public void readData(DataInputStream var1) throws IOException
    {
        super.readData(var1);
        this.state = var1.readBoolean();
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        super.writeData(var1);
        var1.writeBoolean(this.state);
    }
}
