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

import javax.swing.JOptionPane;

import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSnifferSMP;

public class mod_WirelessSnifferSMP extends NetworkMod {
	NetworkMod instance;

	public mod_WirelessSnifferSMP() {
		if (!ModLoader.isModLoaded("mod_WirelessRedstoneSMP")) {
			JOptionPane.showMessageDialog(null, this.getName() + ":" + this.getVersion() +
					", requires mod_WirelessRedstoneSMP to work.\n" +
					"Please download and install the Wireless Redstone Mod.\n" +
					"(Author Ali4z, Programmer Eurymachus)");
		}
		if (!ModLoader.isModLoaded("mod_WirelessSniffer")) {
			JOptionPane.showMessageDialog(null, this.getName() + ":" + this.getVersion() +
					", requires mod_WirelessSniffer to work.\n" +
					"Please download and install the Wireless Sniffer Addon.\n" +
					"(Author Ali4z, Programmer Eurymachus)");
		} else {
			instance = this;
		}
	}

	@Override
	public void modsLoaded() {
		if (!WirelessSnifferSMP.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstoneSMP")
				&& ModLoader.isModLoaded("mod_WirelessSniffer")) {
			WirelessSnifferSMP.isLoaded = WirelessSnifferSMP.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstoneSMP;after:mod_WirelessSniffer";
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
