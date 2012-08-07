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

import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulatorSMP;
import net.minecraft.src.wirelessredstone.addon.triangulator.smp.network.TriangulatorConnection;

public class mod_WirelessTriangulatorSMP extends BaseMod {
	public static BaseMod instance;

	public mod_WirelessTriangulatorSMP() {
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessTriangulatorSMP.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessTriangulator")
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			WirelessTriangulatorSMP.isLoaded = WirelessTriangulatorSMP
					.initialize();
		}
	}

	/*
	 * @Override public boolean onTickInGame(float tick, Minecraft mc) { if
	 * (WirelessTriangulatorSMP.isLoaded) { return
	 * WirelessTriangulatorSMP.tick(mc); } return true; }
	 */

	@Override
	public String getPriorities() {
		return "after:mod_WirelessTriangulator;after:mod_WirelessRedstoneSMP";
	}
	
	@Override
	public void receiveCustomPacket(Packet250CustomPayload payload) {
		WirelessTriangulatorSMP.triangulatorConnection.onPacketData(payload);
    }

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
