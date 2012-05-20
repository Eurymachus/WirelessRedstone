package net.minecraft.src.wirelessredstone.data;

public class WirelessCoordinates
{
	int x, y, z;
	
	public WirelessCoordinates(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public int getZ()
	{
		return this.z;
	}

	public void setX(int posX) {
		this.x = posX;
	}

	public void setY(int posY) {
		this.y = posY;
	}

	public void setZ(int posZ) {
		this.z = posZ;
	}
}
