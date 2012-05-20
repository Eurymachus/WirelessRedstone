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

import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.overrides.RedstoneEtherOverrideSSP;
import net.minecraft.src.wirelessredstone.presentation.RenderBlockRedstoneWireless;

/**
 * Wireless Redstone ModLoader initializing class.
 * 
 * @author ali4z
 */
public class mod_WirelessRedstone extends BaseMod
{
	
	/**
	 * Instance object.
	 */
	public static BaseMod instance;
	
	/**
	 * Adds a Block override to the Receiver.
	 * 
	 * @param override Block override
	 */
	public static void addOverrideToReceiver(BlockRedstoneWirelessOverride override) 
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+WirelessRedstone.blockWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)WirelessRedstone.blockWirelessR).addOverride(override);
	}
	
	/**
	 * Adds a Block override to the Transmitter.
	 * 
	 * @param override Block override
	 */
	public static void addOverrideToTransmitter(BlockRedstoneWirelessOverride override)
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+WirelessRedstone.blockWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		((BlockRedstoneWireless)WirelessRedstone.blockWirelessT).addOverride(override);
	}

	/**
	 * Adds a GUI override to the Receiver.
	 * 
	 * @param override GUI override
	 */
	public static void addGuiOverrideToReceiver(GuiRedstoneWirelessOverride override)
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+WirelessRedstone.guiWirelessR.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		WirelessRedstone.guiWirelessR.addOverride(override);
	}

	/**
	 * Adds a GUI override to the Transmitter.
	 * 
	 * @param override GUI override
	 */
	public static void addGuiOverrideToTransmitter(GuiRedstoneWirelessOverride override)
	{
		LoggerRedstoneWireless.getInstance("Wireless Redstone").write("Override added to "+WirelessRedstone.guiWirelessT.getClass().toString()+": "+override.getClass().toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
		WirelessRedstone.guiWirelessT.addOverride(override);
	}
	/**
	 * Constructor sets the instance.
	 */
	public mod_WirelessRedstone()
	{
		instance = this;
		WirelessRedstone.initialize();
		RedstoneEtherOverrideSSP etherOverride = new RedstoneEtherOverrideSSP();
		RedstoneEther.getInstance().addOverride(etherOverride);
	}
	
	/**
	 * Contains the mod's version.
	 */
	@Override
	public String getVersion()
	{
		return "1.6";
	}

	@Override
    public boolean renderWorldBlock(RenderBlocks var1, IBlockAccess var2, int var3, int var4, int var5, Block var6, int var7)
    {
        return true;//RenderBlockRedstoneWireless.renderBlockRedstoneWireless(var1, var2, var3, var4, var5, var6, var7);
    }

	/**
	 * Returns the mod's name.
	 */
	@Override
	public String toString()
	{
		return "WirelessRedstone "+getVersion();
	}
	
	@Override
	public void load()
	{
	}
}
