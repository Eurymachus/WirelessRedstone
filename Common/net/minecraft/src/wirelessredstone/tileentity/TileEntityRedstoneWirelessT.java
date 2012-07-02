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

public class TileEntityRedstoneWirelessT extends TileEntityRedstoneWireless {
	public TileEntityRedstoneWirelessT() {
		super();
		this.blockRedstoneWireless = (BlockRedstoneWireless) WirelessRedstone.blockWirelessT;
	}

	@Override
	public void updateEntity() {
		boolean prematureExit = false;
		for (TileEntityRedstoneWirelessOverride override : overrides) {
			if (override.beforeUpdateEntity(this))
				prematureExit = true;
		}

		if (!prematureExit) {
			String freq = getFreq().toString();
			if (!oldFreq.equals(freq) || firstTick) {
				onUpdateEntity();
				blockRedstoneWireless.changeFreq(worldObj, getBlockCoord(0),
						getBlockCoord(1), getBlockCoord(2), oldFreq, freq);
				oldFreq = freq;
				if (firstTick)
					firstTick = false;
			}
		}

		for (TileEntityRedstoneWirelessOverride override : overrides) {
			override.afterUpdateEntity(this);
		}
	}

	@Override
	public String getInvName() {
		return "Wireless Transmitter";
	}

	@Override
	protected void onUpdateEntity() {
	}
}
