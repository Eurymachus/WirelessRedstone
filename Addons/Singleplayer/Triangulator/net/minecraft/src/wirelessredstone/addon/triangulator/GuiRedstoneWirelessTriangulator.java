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
package net.minecraft.src.wirelessredstone.addon.triangulator;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.addon.triangulator.data.WirelessTriangulatorData;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessDevice;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessInventory;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessTriangulator extends GuiRedstoneWirelessDevice {

	public GuiRedstoneWirelessTriangulator(WirelessTriangulatorData data, EntityPlayer entityplayer,
			World world) {
		super();
		xSize = 176;
		ySize = 166;
		this.world = world;
		this.entityplayer = entityplayer;
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		this.wirelessDeviceData = data;
	}

	@Override
	public void onGuiClosed() {
		if (entityplayer.getCurrentEquippedItem() == null) {}
	}

	@Override
	protected String getBackgroundImage() {
		return "/gui/wifi_small.png";
	}
}
