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
package net.minecraft.src.wirelessredstone.tileentity;

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessR;

public class TileEntityRedstoneWirelessR extends TileEntityRedstoneWireless {
	public TileEntityRedstoneWirelessR() {
		super();
		this.blockRedstoneWireless = (BlockRedstoneWireless) WirelessRedstone.blockWirelessR;
	}

	@Override
	public String getInvName() {
		return "Wireless Receiver";
	}

	@Override
	protected void onUpdateEntity() {
		if (!((BlockRedstoneWirelessR) blockRedstoneWireless).hasTicked()) {
			WirelessRedstone.blockWirelessR.updateTick(worldObj,
					getBlockCoord(0), getBlockCoord(1), getBlockCoord(2), null);
		}
	}
}
