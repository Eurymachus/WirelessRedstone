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

import java.util.HashMap;
import java.util.Map;
import net.minecraft.src.wirelessredstone.addon.limitedsignal.WirelessLimitedSignal;

public class mod_WirelessLimitedSignal extends BaseMod {
	public static BaseMod instance;
	public Map<String, Boolean> ticking;
	public static boolean wirelessLimitedSignal;

	@Override
	public void modsLoaded() {
		if (ModLoader.isModLoaded("mod_WirelessRedstone")) {
			if (!wirelessLimitedSignal)
				WirelessLimitedSignal.initialize();
		}
	}

	public mod_WirelessLimitedSignal() {
		instance = this;
		ticking = new HashMap<String, Boolean>();
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
		return "0.1";
	}

	public void addTicking(int i, int j, int k) {
		ticking.put(i + "," + j + "," + k, true);
	}

	public boolean isTicking(int i, int j, int k) {
		return ticking.containsKey(i + "," + j + "," + k);
	}

	public void remTicking(int i, int j, int k) {
		ticking.remove(i + "," + j + "," + k);
	}
}