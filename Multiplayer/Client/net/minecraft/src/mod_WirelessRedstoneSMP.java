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

import net.minecraft.src.forge.MinecraftForge;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;
import net.minecraft.src.wirelessredstone.smp.client.BlockRedstoneWirelessOverrideClient;
import net.minecraft.src.wirelessredstone.smp.client.GuiRedstoneWirelessOverrideClient;
import net.minecraft.src.wirelessredstone.smp.client.RedstoneEtherOverrideClient;
import net.minecraft.src.wirelessredstone.smp.client.network.NetworkConnection;

public class mod_WirelessRedstoneSMP extends BaseMod
{

	public static BaseMod instance;
	
	@Override
	public void modsLoaded()
	{
		if(ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
	    	MinecraftForge.registerConnectionHandler(new NetworkConnection());
			
			GuiRedstoneWirelessOverrideClient GUIOverride = new GuiRedstoneWirelessOverrideClient();
			mod_WirelessRedstone.addGuiOverrideToReceiver(GUIOverride);
			mod_WirelessRedstone.addGuiOverrideToTransmitter(GUIOverride);
	
			BlockRedstoneWirelessOverrideClient blockOverride = new BlockRedstoneWirelessOverrideClient();
			mod_WirelessRedstone.addOverrideToReceiver(blockOverride);
			mod_WirelessRedstone.addOverrideToTransmitter(blockOverride);
			
			RedstoneEtherOverrideClient etherOverrideSMP = new RedstoneEtherOverrideClient();
			RedstoneEther.getInstance().addOverride(etherOverrideSMP);
		}
	}

	@Override
	public String getPriorities()
	{
		return "after:mod_WirelessRedstone";
	}
	
	public mod_WirelessRedstoneSMP() {
		instance = this;
	}
	
	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public void load() {
	}
}
