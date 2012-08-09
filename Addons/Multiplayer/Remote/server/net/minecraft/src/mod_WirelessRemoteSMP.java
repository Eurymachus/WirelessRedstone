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
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.smp.network.WirelessRemoteConnection;

public class mod_WirelessRemoteSMP extends BaseMod {
	public static BaseMod instance;

	public mod_WirelessRemoteSMP() {
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessRemote.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			WirelessRemote.isLoaded = WirelessRemote.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP";
	}

	@Override
	public void load() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.src.BaseMod#onClientLogin(net.minecraft.src.EntityPlayer)
	 */
	@Override
	public void onClientLogin(EntityPlayer entityplayer) {
		WirelessRedstone
				.registerConnHandler(entityplayer,
						new WirelessRemoteConnection(entityplayer,
								WirelessRemote.channel),
						mod_WirelessRemoteSMP.instance);
	}

	@Override
	public void onPacket250Received(EntityPlayer entityplayer,
			Packet250CustomPayload payload) {
		WirelessRedstone.handlePacket(WirelessRemote.channel, entityplayer,
				payload);
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
