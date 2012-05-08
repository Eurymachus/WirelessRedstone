package net.minecraft.src.wifi.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.packets.ForgePacket;

public class PacketUpdate extends WifiPacket
{
	private int packetId;
	
    public PacketPayload payload;

    public PacketUpdate() {}
    
    public PacketUpdate(int packetId, PacketPayload payload)
    {
    	this(packetId);
    	this.payload = payload;
    }

    public PacketUpdate(int packetId)
    {
    	this.packetId = packetId;
		this.isChunkDataPacket = true;
	}

    /**
     * Writes a String to the DataOutputStream
     */
    public static void writeString(String par0Str, DataOutputStream par1DataOutputStream) throws IOException
    {
        if (par0Str.length() > 32767)
        {
            throw new IOException("String too big");
        }
        else
        {
            par1DataOutputStream.writeShort(par0Str.length());
            par1DataOutputStream.writeChars(par0Str);
        }
    }
    
    /**
     * Reads a string from a packet
     */
    public static String readString(DataInputStream par0DataInputStream, int par1) throws IOException
    {
        short var2 = par0DataInputStream.readShort();

        if (var2 > par1)
        {
            throw new IOException("Received string length longer than maximum allowed (" + var2 + " > " + par1 + ")");
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
                var3.append(par0DataInputStream.readChar());
            }

            return var3.toString();
        }
    }
    
	@Override
	public void writeData(DataOutputStream data) throws IOException {

        data.writeInt(this.xPosition);
        data.writeInt(this.yPosition);
        data.writeInt(this.zPosition);

        // No payload means no data
        if(this.payload == null) {
        	data.writeInt(0);
        	data.writeInt(0);
        	data.writeInt(0);
        	return;
        }

        data.writeInt(this.payload.intPayload.length);
        data.writeInt(this.payload.floatPayload.length);
        data.writeInt(this.payload.stringPayload.length);

        for(int intData : this.payload.intPayload)
        	data.writeInt(intData);
        for(float floatData : this.payload.floatPayload)
        	data.writeFloat(floatData);
        for(String stringData : this.payload.stringPayload)
        	data.writeUTF(stringData);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {

		this.xPosition = data.readInt();
		this.yPosition = data.readInt();
		this.zPosition = data.readInt();

		this.payload = new PacketPayload();

		this.payload.intPayload = new int[data.readInt()];
		this.payload.floatPayload = new float[data.readInt()];
		this.payload.stringPayload = new String[data.readInt()];

        for(int i = 0; i < this.payload.intPayload.length; i++)
        	this.payload.intPayload[i] = data.readInt();
        for(int i = 0; i < this.payload.floatPayload.length; i++)
        	this.payload.floatPayload[i] = data.readFloat();
        for(int i = 0; i < this.payload.stringPayload.length; i++)
        	this.payload.stringPayload[i] = data.readUTF();
	}
	
	public boolean targetExists(World world)
	{
		return world.blockExists(this.xPosition, this.yPosition, this.zPosition);
	}
	
	public TileEntity getTarget(World world)
	{
		return world.getBlockTileEntity(this.xPosition, this.yPosition, this.zPosition);
	}
	
	@Override
	public int getID() 
	{
		return this.packetId;
	}
}
