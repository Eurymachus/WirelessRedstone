/*    
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package net.minecraft.src;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.WirelessRedstoneSMP;
import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClockerSMP;
import net.minecraft.src.wirelessredstone.addon.clocker.smp.network.WirelessClockerConnection;

public class mod_WirelessClockerSMP extends BaseMod {
	public static BaseMod instance;

	public mod_WirelessClockerSMP() {
		/*
		 * if (!ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
		 * JOptionPane.showMessageDialog(null, this.getName() + ":" +
		 * this.getVersion() + ", requires mod_WirelessRedstoneSMP to work.\n" +
		 * "Please download and install the Wireless Redstone Mod.\n" +
		 * "(Author Ali4z, Programmer Eurymachus)"); } if
		 * (!ModLoader.isModLoaded("mod_WirelessClocker")) {
		 * JOptionPane.showMessageDialog(null, this.getName() + ":" +
		 * this.getVersion() + ", requires mod_WirelessClocker to work.\n" +
		 * "Please download and install the Wireless Clocker Addon.\n" +
		 * "(Author Ali4z, Programmer Eurymachus)"); }
		 */
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessClockerSMP.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")
				&& ModLoader.isModLoaded("mod_WirelessClocker")) {
			WirelessClockerSMP.isLoaded = WirelessClockerSMP.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP;after:mod_WirelessClocker";
	}

	@Override
	public void load() {

	}

	@Override
	public void serverConnect(NetClientHandler netHandler) {
		WirelessRedstoneSMP.registerConnHandler(new WirelessClockerConnection(
				WirelessRedstone.getPlayer(), WirelessClockerSMP.channel),
				mod_WirelessClockerSMP.instance);
	}

	@Override
	public void receiveCustomPacket(Packet250CustomPayload payload) {
		WirelessRedstoneSMP.handlePacket(WirelessClockerSMP.channel, payload);
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
