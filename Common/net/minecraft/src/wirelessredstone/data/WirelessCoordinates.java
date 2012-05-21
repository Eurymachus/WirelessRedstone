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
	
	public WirelessCoordinates(int[] coordinates)
	{
		if (coordinates.length <= 3 && coordinates.length > 0)
		{
			this.x = coordinates[0];
			this.y = coordinates[1];
			this.z = coordinates[2];
		}
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
	
	public WirelessCoordinates getInstance()
	{
		if (this != null)
			return this;
		else return new WirelessCoordinates(0,0,0);
	}
	
	public int[] getCoordinateArray()
	{
		int[] coordArray = {0,0,0};
		if (this.getInstance() != null)
		{
			WirelessCoordinates coords = this.getInstance();
			coordArray[0] = coords.getX();
			coordArray[1] = coords.getY();
			coordArray[2] = coords.getZ();
		}
		return coordArray;
	}
}
