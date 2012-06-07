package net.minecraft.src.wirelessredstone.addon.sniffer.data;

public class SnifferFreqCoordsMemNode
{
	public int x, y, page;
	public String freq;
	public boolean state;
	
	SnifferFreqCoordsMemNode(int x, int y, int page, String freq, boolean state)
	{
		this.x = x;
		this.y = y;
		this.page = page;
		this.freq = freq;
		this.state = state;
	}
}
