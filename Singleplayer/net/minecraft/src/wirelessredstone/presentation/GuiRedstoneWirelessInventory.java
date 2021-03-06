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
package net.minecraft.src.wirelessredstone.presentation;

import net.minecraft.src.GuiButton;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessInventoryOverride;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author ali4z & Eurymachus
 */
public abstract class GuiRedstoneWirelessInventory extends GuiRedstoneWireless {
	/**
	 * Associated TileEntity
	 */
	protected TileEntityRedstoneWireless inventory;

	/**
	 * Constructor.<br>
	 * Sets default width and height.
	 */
	public GuiRedstoneWirelessInventory() {
		super();
		this.xSize = 177;
		this.ySize = 166;
	}

	/**
	 * Associates a TileEntity to the GUI.
	 * 
	 * @param tileentity
	 *            TileEntity to be associated
	 */
	public void assTileEntity(TileEntityRedstoneWireless tileentity) {
		inventory = tileentity;
	}

	/**
	 * Compares the Gui inventory with an external inventory
	 * 
	 * Used to check whether a player is accessing a particular inventory
	 * 
	 * @param tileentity
	 *            the Inventory to compare
	 * @return true if x, y, z of inventories are the same or false if not.
	 */
	public boolean compareInventory(TileEntityRedstoneWireless tileentity) {
		if (this.inventory != null) {
			if (this.inventory.xCoord == tileentity.xCoord
					&& this.inventory.yCoord == tileentity.yCoord
					&& this.inventory.zCoord == tileentity.zCoord) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void addControls() {
		controlList.add(new GuiButtonWireless(0, (width / 2) + 10,
				(height / 2) - 20, 20, 20, "+"));
		controlList.add(new GuiButtonWireless(1, (width / 2) - 30,
				(height / 2) - 20, 20, 20, "-"));
		controlList.add(new GuiButtonWireless(2, (width / 2) + 32,
				(height / 2) - 20, 20, 20, "+10"));
		controlList.add(new GuiButtonWireless(3, (width / 2) - 52,
				(height / 2) - 20, 20, 20, "-10"));
		controlList.add(new GuiButtonWireless(4, (width / 2) + 54,
				(height / 2) - 20, 26, 20, "+100"));
		controlList.add(new GuiButtonWireless(5, (width / 2) - 80,
				(height / 2) - 20, 26, 20, "-100"));
		controlList.add(new GuiButtonWireless(6, (width / 2) + 48,
				(height / 2) - 42, 32, 20, "+1000"));
		controlList.add(new GuiButtonWireless(7, (width / 2) - 80,
				(height / 2) - 42, 32, 20, "-1000"));

		controlList.add(new GuiButtonWirelessExit(100, (((width - xSize) / 2)
				+ xSize - 13 - 1), (((height - ySize) / 2) + 1)));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		try {
			Object a = getFreq();
			Object b = getFreq();
			int freq, oldFreq;
			try {
				freq = Integer.parseInt(a.toString());
				oldFreq = Integer.parseInt(b.toString());
			} catch (NumberFormatException e) {
				return;
			}

			switch (guibutton.id) {
			case 0:
				freq++;
				break;
			case 1:
				freq--;
				break;
			case 2:
				freq += 10;
				break;
			case 3:
				freq -= 10;
				break;
			case 4:
				freq += 100;
				break;
			case 5:
				freq -= 100;
				break;
			case 6:
				freq += 1000;
				break;
			case 7:
				freq -= 1000;
				break;
			case 100:
				close();
				break;
			}
			if (freq > 9999)
				freq -= 10000;
			if (freq < 0)
				freq += 10000;

			boolean prematureExit = false;
			for (GuiRedstoneWirelessOverride override : overrides) {
				if (((GuiRedstoneWirelessInventoryOverride) override)
						.beforeFrequencyChange(inventory, oldFreq, freq))
					prematureExit = true;
			}

			if (oldFreq != freq)
				setFreq(Integer.toString(freq));

			if (prematureExit)
				return;

		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * TileEntity name.
	 * 
	 * @return Name.
	 */
	@Override
	protected String getGuiName() {
		return inventory.getInvName();
	}

	/**
	 * Fetches the frequency from the TileEntity
	 * 
	 * @return Frequency.
	 */
	@Override
	protected Object getFreq() {
		return inventory.getFreq();
	}

	/**
	 * Sets the frequency in the TileEntity.
	 * 
	 * @param freq
	 *            frequency.
	 */
	@Override
	protected void setFreq(String freq) {
		inventory.setFreq(freq);
	}
}
