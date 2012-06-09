package net.minecraft.src.wirelessredstone.smp.network.packet;

/**
 * Extend for new packets
 * 
 * @author Eurymachus
 * 
 */
public class PacketWireless extends PacketUpdate {
	/**
	 * Constructor for Default Wireless Packets
	 * 
	 * @param packetId
	 *            the packet ID used to identify the type of packet data being
	 *            sent or received
	 */
	public PacketWireless(int packetId) {
		super(packetId);
		this.channel = "WIFI";
	}

	/**
	 * Constructor for Default Wireless Packets Used to add payload data to the
	 * packet
	 * 
	 * @param packetId
	 *            the packet ID used to identify the type of packet data being
	 *            sent or received
	 * @param payload
	 *            the new payload to be associated with the packet
	 */
	public PacketWireless(int packetId, PacketPayload payload) {
		super(packetId, payload);
		this.channel = "WIFI";
	}

	@Override
	public String toString() {
		return this.getCommand() + "(" + xPosition + "," + yPosition + ","
				+ zPosition + ")[" + this.getFreq() + "]";
	}

	/**
	 * Retrieves the command from the payload Override to change the index
	 * position
	 * 
	 * @return Returns getStringPayload(0) by default
	 */
	public String getCommand() {
		return this.payload.getStringPayload(0);
	}

	/**
	 * Sets the command in the payload Override to change the index position
	 * 
	 * @param command
	 *            The command to be added
	 */
	public void setCommand(String command) {
		this.payload.setStringPayload(0, command);
	}

	/**
	 * Retrieves the frequency from the payload Override to change the index
	 * position
	 * 
	 * @return Returns getStringPayload(1) by default
	 */
	public String getFreq() {
		return this.payload.getStringPayload(1);
	}

	/**
	 * Sets the command in the payload Override to change the index position
	 * 
	 * @param freq
	 *            The command to be added
	 */
	public void setFreq(Object freq) {
		this.payload.setStringPayload(1, freq.toString());
	}

	/**
	 * Retrieves the frequency from the payload Override to change the index
	 * position
	 * 
	 * @return Returns getStringPayload(1) by default
	 */
	public boolean getState() {
		return this.payload.getBoolPayload(0);
	}

	/**
	 * Sets the command in the payload Override to change the index position
	 * 
	 * @param freq
	 *            The command to be added
	 */
	public void setState(boolean state) {
		this.payload.setBoolPayload(0, state);
	}
}
