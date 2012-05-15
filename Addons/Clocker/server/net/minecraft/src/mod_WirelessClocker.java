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

import net.minecraft.src.clocker.WirelessClocker;
import net.minecraft.src.clocker.WirelessClockerSMP;
import net.minecraft.src.forge.NetworkMod;

public class mod_WirelessClocker extends NetworkMod {

	public boolean wirelessClocker = false;
	public boolean wirelessClockerSMP = false;
	
	NetworkMod instance;
	
	@Override
	public void modsLoaded()
	{
		if (!wirelessClocker && ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			wirelessClocker = WirelessClocker.initialize();
		}
		if (wirelessClocker && !wirelessClockerSMP)
		{
			wirelessClockerSMP = WirelessClockerSMP.initialize();
		}
	}
	
	public mod_WirelessClocker() {
		instance = this;
	}

	@Override
	public void load() {
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
}
