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

import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSnifferSMP;

public class mod_WirelessSniffer extends BaseMod
{
	
	@Override
	public void modsLoaded()
	{
		if (!WirelessSniffer.isLoaded && ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			WirelessSniffer.isLoaded = WirelessSniffer.initialize();
		}
		if (WirelessSniffer.isLoaded && !WirelessSnifferSMP.isLoaded && ModLoader.isModLoaded("mod_WirelessRedstoneClient"))
		{
			WirelessSnifferSMP.isLoaded = WirelessSnifferSMP.initialize();
		}
	}

	@Override
	public String getPriorities()
	{
		return "after:mod_WirelessRedstone";
	}
	
	public mod_WirelessSniffer()
	{
	}

	@Override
	public void load() {
	}
	
	@Override
	public String getVersion() {
		return "1.0";
	}
}
