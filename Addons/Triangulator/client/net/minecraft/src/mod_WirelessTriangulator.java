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

import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulator;
import net.minecraft.src.wirelessredstone.addon.triangulator.WirelessTriangulatorSMP;

public class mod_WirelessTriangulator extends BaseMod {
	public static boolean wirelessTriangulator;
	public static boolean wirelessTriangulatorSMP;
	public static BaseMod instance;
	
	@Override
	public void modsLoaded()
	{
		if (ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			if (!wirelessTriangulator) wirelessTriangulator = WirelessTriangulator.initialize();
		}
		if (ModLoader.isModLoaded("mod_WirelessRedstoneClient"))
		{
			if (!wirelessTriangulatorSMP) wirelessTriangulatorSMP = WirelessTriangulatorSMP.initialize();
		}
	}

	@Override
	public String getPriorities()
	{
		return "after:mod_WirelessRedstone";
	}
	
	public mod_WirelessTriangulator() {
		instance = this;
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "0.5";
	}
}
