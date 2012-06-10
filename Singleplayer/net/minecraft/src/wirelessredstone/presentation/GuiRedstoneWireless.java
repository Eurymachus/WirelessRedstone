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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.WirelessDeviceData;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessOverride;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author Eurymachus
 */
public abstract class GuiRedstoneWireless extends GuiScreen {
	/**
	 * Current World
	 */
	protected World world;
	/**
	 * Current Player
	 */
	protected EntityPlayer entityplayer;
	/**
	 * Width
	 */
	protected int xSize;
	/**
	 * Height
	 */
	protected int ySize;
	/**
	 * GUI overrides.
	 */
	protected List<GuiRedstoneWirelessOverride> overrides;

	/**
	 * Constructor.<br>
	 * Sets default width,height and initializes override list object.
	 */
	public GuiRedstoneWireless() {
		super();
		this.xSize = 177;
		this.ySize = 166;
		this.overrides = new ArrayList<GuiRedstoneWirelessOverride>();
	}

	/**
	 * Adds a GUI override to the GUI screen.
	 * 
	 * @param override
	 *            GUI override.
	 */
	public void addOverride(GuiRedstoneWirelessOverride override) {
		this.overrides.add(override);
	}

	/**
	 * Initializes the GUI.<br>
	 * Adds buttons.
	 */
	@Override
	public void initGui() {
		super.initGui();
	}

	/**
	 * Draws the background layer.
	 * 
	 * @param f
	 *            tick partial
	 */
	protected abstract void drawGuiContainerBackgroundLayer(float f);

	/**
	 * Check is Mouse pointer is within the bounds of a given GuiButtonWireless
	 * 
	 * @param button A GuiButtonWireless
	 * @param i mouse X coordinate
	 * @param j mouse Y coordinate
	 * @return
	 */
	private boolean isMouseOverButton(GuiButtonWireless button, int i, int j) {
		if (button != null)
			return button.inBounds(i, j);
		return false;
	}

	/**
	 * Draws the entire GUI to the screen.
	 * 
	 * @param i
	 *            mouse X coordinate
	 * @param j
	 *            mouse Y coordinate
	 * @param f
	 *            tick partial
	 */
	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		for (int control = 0; control < this.controlList.size(); control++) {
			if (this.controlList.get(control) instanceof GuiButtonWireless) {
				GuiButtonWireless button = (GuiButtonWireless) this.controlList
						.get(control);

				if (this.isMouseOverButton(button, i, j)) {
					this.drawToolTip(button, i, j);
				}
			}
		}
	}

	/**
	 * Action listener.<br>
	 * Triggers when a button was clicked on the GUI.<br>
	 * - Runs all override beforeFrequencyChange, exits if premature exit was
	 * returned, skipping the frequency from being set.
	 * 
	 * @param guibutton
	 *            button that was clicked
	 */
	@Override
	protected abstract void actionPerformed(GuiButton guibutton) ;

	/**
	 * Handles keyboard input.<br>
	 * If inventory key is pressed or ESC, close the GUI.
	 */
	@Override
	public void handleKeyboardInput() {
		try {
			super.handleKeyboardInput();

			if (Keyboard.getEventKeyState()) {
				int inventoryKey = 0;
				for (Object o : KeyBinding.keybindArray) {
					if (((KeyBinding) o).keyDescription.equals("key.inventory")) {
						inventoryKey = ((KeyBinding) o).keyCode;
						break;
					}
				}
				if (Keyboard.getEventKey() == inventoryKey
						|| Keyboard.getEventKey() == 28) {
					close();
					return;
				}
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Handles mouse input.<br>
	 * Close GUI on right click.
	 */
	@Override
	public void handleMouseInput() {
		try {
			super.handleMouseInput();
			if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState())
				close();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Closes the GUI.
	 */
	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance(
					"WirelessRedstone: " + this.getClass().toString())
					.writeStackTrace(e);
		}
	}

	/**
	 * Draw a bordered box around a string.<br>
	 * Outer height is always 10, inner 8.
	 * 
	 * @param x1
	 *            screen X coordinate, top left
	 * @param y1
	 *            screen Y coordinate, top left
	 * @param x2
	 *            screen X coordinate, bottom right
	 */
	protected void drawStringBorder(int x1, int y1, int x2) {
		drawRect(x1 - 3, y1 - 3, x2 + 3, y1 + 10, 0xff000000);
		drawRect(x1 - 2, y1 - 2, x2 + 2, y1 + 9, 0xffffffff);
	}

	/**
	 * Draw a tooltip at the mouse pointer when.<br>
	 * Called when the mouse pointer is within button bounds.
	 * 
	 * @param button A GuiButtonWireless
	 * @param x mouse X coordinate
	 * @param y mouse Y coordinate
	 */
	protected void drawToolTip(GuiButtonWireless button, int x, int y) {
		String buttonPopupText = button.getPopupText();
		if (!buttonPopupText.isEmpty()) {
			
			int l1 = fontRenderer.getStringWidth(button.getPopupText());
			int i = 0;
			int j = -10;
			int j2 = (x - i) + 12;
			int l2 = y - j - 12;
			int i3 = l1 + 5;
			int j3 = 8;
	
			zLevel = 300.0F;
			int k3 = 0xf0100010;
			drawGradientRect(j2 - 3, l2 - 4, j2 + i3 + 3, l2 - 3, k3, k3);
			drawGradientRect(j2 - 3, l2 + j3 + 3, j2 + i3 + 3, l2 + j3 + 4, k3, k3);
			drawGradientRect(j2 - 3, l2 - 3, j2 + i3 + 3, l2 + j3 + 3, k3, k3);
			drawGradientRect(j2 - 4, l2 - 3, j2 - 3, l2 + j3 + 3, k3, k3);
			drawGradientRect(j2 + i3 + 3, l2 - 3, j2 + i3 + 4, l2 + j3 + 3, k3, k3);
			int l3 = 0x505000ff;
			int i4 = (l3 & 0xfefefe) >> 1 | l3 & 0xff000000;
			drawGradientRect(j2 - 3, (l2 - 3) + 1, (j2 - 3) + 1, (l2 + j3 + 3) - 1,
					l3, i4);
			drawGradientRect(j2 + i3 + 2, (l2 - 3) + 1, j2 + i3 + 3,
					(l2 + j3 + 3) - 1, l3, i4);
			drawGradientRect(j2 - 3, l2 - 3, j2 + i3 + 3, (l2 - 3) + 1, l3, l3);
			drawGradientRect(j2 - 3, l2 + j3 + 2, j2 + i3 + 3, l2 + j3 + 3, i4, i4);
	
			fontRenderer.drawSplitString(button.getPopupText(), x + 15, y - 1,
					l1 * 2, 0xFFFFFFFF);
			zLevel = 0.0F;
		}
	}

	/**
	 * Draws the strings.<br>
	 * - Name.<br>
	 * - Frequency.
	 */
	protected abstract void drawGuiContainerForegroundLayer() ;

	/**
	 * Always returns false, prevents game from pausing when GUI is open.
	 * 
	 * @return false
	 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Fetches the frequency
	 * 
	 * @return Frequency.
	 */
	protected abstract Object getFreq();

	/**
	 * Sets the frequency
	 * 
	 * @param freq
	 *            frequency.
	 */
	protected abstract void setFreq(String freq);
	
}