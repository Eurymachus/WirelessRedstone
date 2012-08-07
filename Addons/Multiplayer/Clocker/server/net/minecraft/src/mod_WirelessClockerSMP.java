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

import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClocker;

public class mod_WirelessClockerSMP extends BaseMod {
	public static BaseMod instance;

	public mod_WirelessClockerSMP() {
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessClocker.wirelessClocker
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			WirelessClocker.wirelessClocker = WirelessClocker.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP";
	}

	@Override
	public void load() {
	}

	@Override
	public void onClientLogin(EntityPlayer entityplayer) {
		WirelessClocker.wirelessClockerConnection
				.onLogin(
						((EntityPlayerMP) entityplayer).playerNetServerHandler.netManager,
						null, mod_WirelessClockerSMP.instance);
	}

	@Override
	public void onPacket250Received(EntityPlayer entityplayer,
			Packet250CustomPayload payload) {
		WirelessClocker.wirelessClockerConnection.onPacketData(entityplayer,
				payload);
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
