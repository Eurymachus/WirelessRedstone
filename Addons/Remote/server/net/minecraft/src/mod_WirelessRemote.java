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

import net.minecraft.src.forge.NetworkMod;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemote;
import net.minecraft.src.wirelessredstone.addon.remote.WirelessRemoteSMP;
import net.minecraft.src.wirelessredstone.addon.remote.overrides.RedstoneEtherOverrideRemote;

public class mod_WirelessRemote extends NetworkMod
{
	boolean wirelessRemote = false;
	boolean wirelessRemoteSMP = false;
	public static NetworkMod instance;
	
	@Override
	public void modsLoaded()
	{
		if (ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			if (!wirelessRemote) {
				wirelessRemote = WirelessRemote.initialize();
				RedstoneEtherOverrideRemote etherOverrideRemote = new RedstoneEtherOverrideRemote();
				RedstoneEther.getInstance().addOverride(etherOverrideRemote);
			}
		}
		if (wirelessRemote)
		{
			if (!wirelessRemoteSMP)
				wirelessRemoteSMP = WirelessRemoteSMP.initialize();
		}
	}

	@Override
    public String getPriorities()
    {
        return "after:mod_WirelessRedstone";
    }
	
	public mod_WirelessRemote() {
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
