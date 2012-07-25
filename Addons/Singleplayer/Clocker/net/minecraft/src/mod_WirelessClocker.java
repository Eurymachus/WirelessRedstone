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

public class mod_WirelessClocker extends BaseMod {
	public BaseMod instance;

	public mod_WirelessClocker() {
		/*
		 * if (!ModLoader.isModLoaded("mod_WirelessRedstone")) {
		 * JOptionPane.showMessageDialog(null, this.getName() + ":" +
		 * this.getVersion() + ", requires mod_WirelessRedstone to work.\n" +
		 * "Please download and install the Wireless Redstone Mod.\n" +
		 * "(Author Ali4z, Programmer Eurymachus)"); }
		 */
		instance = this;
	}

	@Override
	public void modsLoaded() {
		if (!WirelessClocker.isLoaded
				&& ModLoader.isModLoaded("mod_WirelessRedstone")) {
			WirelessClocker.isLoaded = WirelessClocker.initialize();
		}
	}

	@Override
	public String getPriorities() {
		return "after:mod_WirelessRedstone";
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
