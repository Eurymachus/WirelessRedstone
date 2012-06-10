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
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessItemStackMem;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWireless;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessTriangulator extends GuiRedstoneWireless {
	protected int xSize;
	protected int ySize;
	protected EntityPlayer player;
	protected ItemStack itemstack;
	protected World world;

	public GuiRedstoneWirelessTriangulator(EntityPlayer entityplayer,
			World world) {
		super();
		xSize = 176;
		ySize = 166;
		this.player = entityplayer;
		this.itemstack = entityplayer.getCurrentEquippedItem();
		this.world = world;
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		Object a = getFreq();
		Object b = a;
		int newFreq, oldFreq;
		try {
			newFreq = Integer.parseInt(a.toString());
			oldFreq = Integer.parseInt(b.toString());
		} catch (NumberFormatException e) {
			return;
		}

		switch (guibutton.id) {
		case 0:
			newFreq++;
			break;
		case 1:
			newFreq--;
			break;
		case 2:
			newFreq += 10;
			break;
		case 3:
			newFreq -= 10;
			break;
		case 4:
			newFreq += 100;
			break;
		case 5:
			newFreq -= 100;
			break;
		case 6:
			newFreq += 1000;
			break;
		case 7:
			newFreq -= 1000;
			break;
		case 100:
			close();
			break;
		}
		if (newFreq != oldFreq) {
			if (newFreq > 9999)
				newFreq -= 10000;
			if (newFreq < 0)
				newFreq += 10000;
			setFreq(Integer.toString(newFreq));
		}
	}

	@Override
	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone.Triangulator: "
							+ this.getClass().toString()).writeStackTrace(e);
		}
	}

	@Override
	public void onGuiClosed() {
		if (player.getCurrentEquippedItem() == null)
			RedstoneWirelessItemStackMem.getInstance(world).remMem(itemstack);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		int m = mc.renderEngine.getTexture("/gui/wifi_small.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(m);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected String getName() {
		return "Wireless Triangulator";
	}

	@Override
	protected String getFreq() {
		return RedstoneWirelessItemStackMem.getInstance(world).getFreq(
				itemstack);
	}

	@Override
	protected void setFreq(String freq) {
		RedstoneWirelessItemStackMem.getInstance(world).addMem(itemstack, freq);
	}
}
