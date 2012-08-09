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

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.smp.network.SnifferConnection;

public class mod_WirelessSnifferSMP extends BaseMod {
	public static BaseMod instance;

	@Override
	public void modsLoaded() {
		if (!WirelessSniffer.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			WirelessSniffer.isLoaded = WirelessSniffer.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP";
	}

	public mod_WirelessSnifferSMP() {
		instance = this;
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public void onClientLogin(EntityPlayer entityplayer) {
		WirelessRedstone.registerConnHandler(entityplayer,
				new SnifferConnection(entityplayer, WirelessSniffer.channel),
				mod_WirelessSnifferSMP.instance);
	}

	@Override
	public void onPacket250Received(EntityPlayer entityplayer,
			Packet250CustomPayload payload) {
		WirelessRedstone.handlePacket(WirelessSniffer.channel, entityplayer,
				payload);
	}

	@Override
	public boolean onTickInGame(MinecraftServer mcServer) {
		return WirelessSniffer.onTickInGame(mcServer);
	}
}
