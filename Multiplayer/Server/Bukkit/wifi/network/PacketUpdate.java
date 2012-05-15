package wifi.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public class PacketUpdate extends WifiPacket
{
    private int packetId;
    public PacketPayload payload;

    public PacketUpdate() {}

    public PacketUpdate(int var1, PacketPayload var2)
    {
        this(var1);
        this.payload = var2;
    }

    public PacketUpdate(int var1)
    {
        this.packetId = var1;
        this.isChunkDataPacket = true;
    }

    public static void writeString(String var0, DataOutputStream var1) throws IOException
    {
        if (var0.length() > 32767)
        {
            throw new IOException("String too big");
        }
        else
        {
            var1.writeShort(var0.length());
            var1.writeChars(var0);
        }
    }

    public static String readString(DataInputStream var0, int var1) throws IOException
    {
        short var2 = var0.readShort();

        if (var2 > var1)
        {
            throw new IOException("Received string length longer than maximum allowed (" + var2 + " > " + var1 + ")");
        }
        else if (var2 < 0)
        {
            throw new IOException("Received string length is less than zero! Weird string!");
        }
        else
        {
            StringBuilder var3 = new StringBuilder();

            for (int var4 = 0; var4 < var2; ++var4)
            {
                var3.append(var0.readChar());
            }

            return var3.toString();
        }
    }

    public void writeData(DataOutputStream var1) throws IOException
    {
        var1.writeInt(this.xPosition);
        var1.writeInt(this.yPosition);
        var1.writeInt(this.zPosition);

        if (this.payload == null)
        {
            var1.writeInt(0);
            var1.writeInt(0);
            var1.writeInt(0);
        }
        else
        {
            var1.writeInt(this.payload.intPayload.length);
            var1.writeInt(this.payload.floatPayload.length);
            var1.writeInt(this.payload.stringPayload.length);
            int[] var2 = this.payload.intPayload;
            int var3 = var2.length;
            int var4;

            for (var4 = 0; var4 < var3; ++var4)
            {
                int var5 = var2[var4];
                var1.writeInt(var5);
            }

            float[] var6 = this.payload.floatPayload;
            var3 = var6.length;

            for (var4 = 0; var4 < var3; ++var4)
            {
                float var8 = var6[var4];
                var1.writeFloat(var8);
            }

            String[] var7 = this.payload.stringPayload;
            var3 = var7.length;

            for (var4 = 0; var4 < var3; ++var4)
            {
                String var9 = var7[var4];
                var1.writeUTF(var9);
            }
        }
    }

    public void readData(DataInputStream var1) throws IOException
    {
        this.xPosition = var1.readInt();
        this.yPosition = var1.readInt();
        this.zPosition = var1.readInt();
        this.payload = new PacketPayload();
        this.payload.intPayload = new int[var1.readInt()];
        this.payload.floatPayload = new float[var1.readInt()];
        this.payload.stringPayload = new String[var1.readInt()];
        int var2;

        for (var2 = 0; var2 < this.payload.intPayload.length; ++var2)
        {
            this.payload.intPayload[var2] = var1.readInt();
        }

        for (var2 = 0; var2 < this.payload.floatPayload.length; ++var2)
        {
            this.payload.floatPayload[var2] = var1.readFloat();
        }

        for (var2 = 0; var2 < this.payload.stringPayload.length; ++var2)
        {
            this.payload.stringPayload[var2] = var1.readUTF();
        }
    }

    public boolean targetExists(World var1)
    {
        return var1.isLoaded(this.xPosition, this.yPosition, this.zPosition);
    }

    public TileEntity getTarget(World var1)
    {
        return var1.getTileEntity(this.xPosition, this.yPosition, this.zPosition);
    }

    public int getID()
    {
        return this.packetId;
    }
}
