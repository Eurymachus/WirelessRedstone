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
package net.minecraft.src.wirelessredstone.addon.powerc.overrides;

import java.util.Random;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.powerc.PowerConfigurator;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessOverride;

public class BlockRedstoneWirelessROverridePC implements
		BlockRedstoneWirelessOverride {
	@Override
	public boolean beforeBlockRedstoneWirelessAdded(World world, int i, int j,
			int k) {
		return false;
	}

	@Override
	public void afterBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessRemoved(World world, int i,
			int j, int k) {
		return false;
	}

	@Override
	public void afterBlockRedstoneWirelessRemoved(World world, int i, int j,
			int k) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessActivated(World world, int i,
			int j, int k, EntityPlayer entityplayer) {
		if (entityplayer.getCurrentEquippedItem() != null
				&& entityplayer.getCurrentEquippedItem().itemID == PowerConfigurator.itemPowDir.shiftedIndex) {
			return true;
		}
		return false;
	}

	@Override
	public void afterBlockRedstoneWirelessActivated(World world, int i, int j,
			int k, EntityPlayer entityplayer) {
	}

	@Override
	public boolean beforeBlockRedstoneWirelessNeighborChange(World world,
			int i, int j, int k, int l) {
		return false;
	}

	@Override
	public void afterBlockRedstoneWirelessNeighborChange(World world, int i,
			int j, int k, int l) {
	}

	@Override
	public boolean beforeUpdateRedstoneWirelessTick(World world, int i, int j,
			int k, Random random) {
		return false;
	}

	@Override
	public void afterUpdateRedstoneWirelessTick(World world, int i, int j,
			int k, Random random) {
	}
}
