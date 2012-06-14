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
package net.minecraft.src.wirelessredstone.addon.clocker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.GuiButton;
import net.minecraft.src.wirelessredstone.addon.clocker.overrides.GuiRedstoneWirelessClockerOverride;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessInventory;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessClocker extends GuiRedstoneWirelessInventory {
	private boolean isScrolling;
	protected List<GuiRedstoneWirelessClockerOverride> clockerOverrides;

	public GuiRedstoneWirelessClocker() {
		super();
		clockerOverrides = new ArrayList();
	}

	public void addOverride(GuiRedstoneWirelessClockerOverride clockerOverride) {
		clockerOverrides.add(clockerOverride);
	}

	@Override
	public String getBackgroundImage() {
		return "/gui/wifi_large.png";
	}

	@Override
	protected void addControls() {
		super.addControls();

		controlList.add(new GuiButton(8, (width / 2) + 1, (height / 2) + 45,
				24, 20, "+10"));
		controlList.add(new GuiButton(9, (width / 2) - 25, (height / 2) + 45,
				24, 20, "-10"));
		controlList.add(new GuiButton(10, (width / 2) + 28, (height / 2) + 45,
				24, 20, "+s"));
		controlList.add(new GuiButton(11, (width / 2) - 53, (height / 2) + 45,
				24, 20, "-s"));
		controlList.add(new GuiButton(12, (width / 2) + 56, (height / 2) + 45,
				24, 20, "+m"));
		controlList.add(new GuiButton(13, (width / 2) - 81, (height / 2) + 45,
				24, 20, "-m"));
	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);

		GL11.glDisable(2896 /* GL_LIGHTING */);
		drawStringBorder(
				(width / 2)
						- (fontRenderer.getStringWidth(((TileEntityRedstoneWirelessClocker) inventory)
								.getClockFreq() + "") / 2),
				(height / 2) + 30,
				(width / 2)
						+ (fontRenderer
								.getStringWidth(((TileEntityRedstoneWirelessClocker) inventory)
										.getClockFreq() + "") / 2));
		fontRenderer
				.drawString(
						((TileEntityRedstoneWirelessClocker) inventory)
								.getClockFreq() + "",
						(width / 2)
								- (fontRenderer
										.getStringWidth(((TileEntityRedstoneWirelessClocker) inventory)
												.getClockFreq() + "") / 2),
						(height / 2) + 30, 0x404040);
		GL11.glEnable(2896 /* GL_LIGHTING */);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);

		int oldClockFreq = ((TileEntityRedstoneWirelessClocker) inventory)
				.getClockFreq();
		int clockFreq = ((TileEntityRedstoneWirelessClocker) inventory)
				.getClockFreq();
		switch (guibutton.id) {
		case 8:
			clockFreq += 10;
			break;
		case 9:
			clockFreq -= 10;
			break;
		case 10:
			clockFreq += 1000;
			break;
		case 11:
			clockFreq -= 1000;
			break;
		case 12:
			clockFreq += 60000;
			break;
		case 13:
			clockFreq -= 60000;
			break;
		}

		if (clockFreq > 2000000000)
			clockFreq = 2000000000;
		if (clockFreq < 200)
			clockFreq = 200;

		// Clock frequency changed.

		boolean prematureExit = false;
		for (GuiRedstoneWirelessClockerOverride override : clockerOverrides) {
			if (override
					.beforeFrequencyChange(inventory, oldClockFreq, clockFreq))
				prematureExit = true;
		}
		if (prematureExit)
			return;
		if (oldClockFreq != clockFreq) {
			((TileEntityRedstoneWirelessClocker) inventory)
					.setClockFreq(clockFreq);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j, float f) {
		super.drawGuiContainerForegroundLayer(i, j, f);

		drawStringBorder(
				(xSize / 2)
						- (fontRenderer
								.getStringWidth("Clocking Frequency (ms)") / 2),
				97,
				(xSize / 2)
						+ (fontRenderer
								.getStringWidth("Clocking Frequency (ms)") / 2));
		fontRenderer.drawString("Clocking Frequency (ms)", (xSize / 2)
				- (fontRenderer.getStringWidth("Clocking Frequency (ms)") / 2),
				97, 0x404040);
	}
}
