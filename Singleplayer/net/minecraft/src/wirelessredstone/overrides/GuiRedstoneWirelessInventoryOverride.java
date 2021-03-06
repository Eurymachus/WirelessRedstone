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
package net.minecraft.src.wirelessredstone.overrides;

import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * GUI override.<br>
 * Used for injecting code into existing Wireless Inventory GUI screens.<br>
 * Useful for addons that changes the mechanics of existing GUIs.<br>
 * NOTE: All methods must be implemented, content is optional.
 * 
 * @author ali4z
 * 
 */
public interface GuiRedstoneWirelessInventoryOverride extends
		GuiRedstoneWirelessOverride {
	/**
	 * Triggers before the frequency is changed.
	 * 
	 * @param entity
	 *            TileEntity
	 * @param oldFreq
	 *            old frequency
	 * @param newFreq
	 *            new frequency
	 * @return Exits prematurely if true, skipping existing code.
	 */
	public boolean beforeFrequencyChange(TileEntityRedstoneWireless entity,
			Object oldFreq, Object newFreq);
}
