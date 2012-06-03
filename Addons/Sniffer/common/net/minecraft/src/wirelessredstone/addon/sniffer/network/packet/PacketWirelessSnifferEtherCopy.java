package net.minecraft.src.wirelessredstone.addon.sniffer.network.packet;

import net.minecraft.src.wirelessredstone.smp.packet.PacketIds;
import net.minecraft.src.wirelessredstone.smp.packet.PacketPayload;

public class PacketWirelessSnifferEtherCopy extends PacketWirelessSniffer {

	public PacketWirelessSnifferEtherCopy()
	{
		super(PacketIds.ETHER);
	}

	public PacketWirelessSnifferEtherCopy(String[] activeFreqs)
	{
		this();
		this.payload = new PacketPayload(0,0,activeFreqs.length,0);
		this.setActiveFreqs(activeFreqs);
	}
	
	public void setActiveFreqs(String[] freqs)
	{
		for (int i = 0; i < freqs.length; ++i)
		{
			this.payload.setStringPayload(i, freqs[i]);
		}
	}
	
	public String[] getActiveFreqs()
	{
		String[] activeFreqs = new String[this.payload.getStringSize()];
		for (int i = 0; i < this.payload.getStringSize(); ++i)
		{
			activeFreqs[i] = this.payload.getStringPayload(i);
		}
		return activeFreqs;
	}

	@Override
	public String toString() {
		return "ActiveFrequencies[" + this.payload.getStringSize() + "]";
	}
}
