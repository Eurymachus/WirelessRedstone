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

import net.minecraft.src.wirelessredstone.WirelessRedstone;

/**
 * Wireless Redstone ModLoader initializing class.
 * 
 * @author ali4z
 */
public class mod_WirelessRedstone extends BaseMod {

	/**
	 * Instance object.
	 */
	public static BaseMod instance;

	/**
	 * Constructor sets the instance.
	 */
	public mod_WirelessRedstone() {
		instance = this;
		WirelessRedstone.initialize();
	}

	/**
	 * Contains the mod's version.
	 */
	@Override
	public String getVersion() {
		return "1.6";
	}

	@Override
	public boolean renderWorldBlock(RenderBlocks var1, IBlockAccess var2,
			int var3, int var4, int var5, Block var6, int var7) {
		return true;// RenderBlockRedstoneWireless.renderBlockRedstoneWireless(var1,
					// var2, var3, var4, var5, var6, var7);
	}

	/**
	 * Returns the mod's name.
	 */
	@Override
	public String toString() {
		return "WirelessRedstone " + getVersion();
	}

	@Override
	public void load() {
	}
}
