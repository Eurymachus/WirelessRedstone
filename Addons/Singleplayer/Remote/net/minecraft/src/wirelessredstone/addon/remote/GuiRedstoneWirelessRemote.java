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
package net.minecraft.src.wirelessredstone.addon.remote;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessDevice;

public class GuiRedstoneWirelessRemote extends GuiRedstoneWirelessDevice {
	protected World world;
	protected EntityPlayer entityplayer;
	protected ItemStack itemstack;

	public GuiRedstoneWirelessRemote(World world, EntityPlayer entityplayer) {
		super();
		this.world = world;
		this.entityplayer = entityplayer;
		this.itemstack = entityplayer.getCurrentEquippedItem();
		this.wirelessDeviceData = WirelessRemote.getRemoteData(this.itemstack
				.getItem().getItemName(), this.itemstack.getItemDamage(),
				this.world, this.entityplayer);
	}

	@Override
	protected void addControls() {
		super.addControls();
		controlList.add(new GuiButtonBoolean(20, (width / 2) - 20,
				(height / 2) + 5, 40, 20, "Pulse", false));
	}

	@Override
	protected String getBackgroundImage() {
		return "/gui/wifi_medium.png";
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);

		if (guibutton.id == 20) {
			ThreadWirelessRemote.pulse(entityplayer, "pulse");
			close();
		}
	}

	@Override
	public void onGuiClosed() {
		if (entityplayer.getCurrentEquippedItem() == null) {
		}
	}
}
