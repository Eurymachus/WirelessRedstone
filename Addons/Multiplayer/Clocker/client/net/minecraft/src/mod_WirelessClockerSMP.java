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
import net.minecraft.src.wirelessredstone.addon.clocker.WirelessClockerSMP;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessOverride;

public class mod_WirelessClocker extends BaseMod
{
	public BaseMod instance;
	
	@Override
	public void modsLoaded()
	{
		if (!WirelessClocker.isLoaded && ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			WirelessClocker.isLoaded = WirelessClocker.initialize();
		}
		if (WirelessClocker.isLoaded && !WirelessClockerSMP.isLoaded && ModLoader.isModLoaded("mod_WirelessRedstoneClient"))
		{
			WirelessClockerSMP.isLoaded = WirelessClockerSMP.initialize();
		}
	}
	
	public mod_WirelessClocker() 
	{
		instance = this;
	}

	@Override
	public String getPriorities()
	{
		return "after:mod_WirelessRedstone";
	}

	@Override
	public void load() {}

	@Override
	public String getVersion() 
	{
		return "1.0";
	}

	public static void addOverrideToClocker(BlockRedstoneWirelessOverride override) 
	{
		((BlockRedstoneWireless)WirelessClocker.blockClock).addOverride(override);
	}
	
	public static void addGuiOverrideToClocker(GuiRedstoneWirelessOverride override) 
	{
		WirelessClocker.guiClock.addOverride(override);
	}
}
