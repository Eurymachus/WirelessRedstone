package wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import wifi.TileEntityRedstoneWireless;
import wifi.TileEntityRedstoneWirelessR;
import wifi.TileEntityRedstoneWirelessT;

public class PacketOpenWindowRedstoneWireless extends PacketUpdate
{
    public boolean firstTick;

    public PacketOpenWindowRedstoneWireless()
    {
        super(1);
        this.firstTick = true;
    }

    public PacketOpenWindowRedstoneWireless(TileEntityRedstoneWireless var1)
    {
        this();
        this.xPosition = var1.getBlockCoord(0);
        this.yPosition = var1.getBlockCoord(1);
        this.zPosition = var1.getBlockCoord(2);
        PacketPayload var2 = new PacketPayload(1, 1, 2);
        this.firstTick = var1.firstTick;
        var2.stringPayload[0] = var1.currentFreq;
        var2.stringPayload[1] = var1.getFreq();

        if (var1 instanceof TileEntityRedstoneWirelessR)
        {
            var2.intPayload[0] = 0;
        }
        else if (var1 instanceof TileEntityRedstoneWirelessT)
        {
            var2.intPayload[0] = 1;
        }

        var2.floatPayload[0] = 0.0F;
        this.payload = var2;
    }

    public String toString()
    {
        return this.payload.intPayload[0] + ":(" + this.xPosition + "," + this.yPosition + "," + this.zPosition + ")[" + this.payload.stringPayload[0] + "]";
    }

    public int getType()
    {
        return this.payload.intPayload[0];
    }

    public String getCurrentFreq()
    {
        return this.payload.stringPayload[0];
    }

    public String getOldFreq()
    {
        return this.payload.stringPayload[1];
    }

    public void readData(DataInputStream var1) throws IOException
    {
        super.readData(var1);
        this.firstTick = var1.readBoolean();
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        super.writeData(var1);
        var1.writeBoolean(this.firstTick);
    }
}
