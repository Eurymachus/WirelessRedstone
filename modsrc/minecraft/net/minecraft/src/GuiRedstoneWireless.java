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
package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * Wireless Redstone GUI screen.
 * 
 * @author ali4z
 */
public abstract class GuiRedstoneWireless extends GuiScreen {
	/**
	 * Associated TileEntity
	 */
	protected TileEntityRedstoneWireless inventory;
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
        overrides = new ArrayList<GuiRedstoneWirelessOverride>();
	}
	
	/**
	 * Associates a TileEntity to the GUI.
	 * 
	 * @param tileentity TileEntity to be associated
	 */
	public void assTileEntity(TileEntityRedstoneWireless tileentity) {
		inventory = tileentity;
	}
	
	/**
	 * Adds a GUI override to the GUI screen.
	 * 
	 * @param override GUI override.
	 */
	public void addOverride(GuiRedstoneWirelessOverride override) {
		overrides.add(override);
	}
	
	/**
	 * Initializes the GUI.<br>
	 * Adds buttons.
	 */
	@Override
	public void initGui() {
		controlList.add(new GuiButton(0, (width/2)+10, (height/2)-20, 20, 20, "+"));
		controlList.add(new GuiButton(1, (width/2)-30, (height/2)-20, 20, 20, "-"));
		controlList.add(new GuiButton(2, (width/2)+32, (height/2)-20, 20, 20, "+10"));
		controlList.add(new GuiButton(3, (width/2)-52, (height/2)-20, 20, 20, "-10"));
		controlList.add(new GuiButton(4, (width/2)+54, (height/2)-20, 26, 20, "+100"));
		controlList.add(new GuiButton(5, (width/2)-80, (height/2)-20, 26, 20, "-100"));
		controlList.add(new GuiButton(6, (width/2)+48, (height/2)-42, 32, 20, "+1000"));
		controlList.add(new GuiButton(7, (width/2)-80, (height/2)-42, 32, 20, "-1000"));
		
		controlList.add(new GuiWifiExitButton(100, (((width - xSize)/2)+xSize-13-1), (((height - ySize)/2)+1)));
		super.initGui();
	}

	/**
	 * Draws the background layer.
	 * @param f tick partial
	 */
    protected abstract void drawGuiContainerBackgroundLayer(float f);

	/**
	 * Draws the entire GUI to the screen.
	 * 
	 * @param i mouse X coordinate
	 * @param j mouse Y coordinate
	 * @param f tick partial
	 */
	@Override
	public void drawScreen(int i, int j, float f) {
		try {
			drawDefaultBackground();
			int k = (width - xSize) / 2;
			int l = (height - ySize) / 2;
			drawGuiContainerBackgroundLayer(f);
			
			GL11.glPushMatrix();
			GL11.glTranslatef(k, l, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(2896 /*GL_LIGHTING*/);
			GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
			drawGuiContainerForegroundLayer();
			GL11.glPopMatrix();
			
			super.drawScreen(i, j, f);
			
			GL11.glEnable(2896 /*GL_LIGHTING*/);
			GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
			
			GL11.glDisable(2896 /*GL_LIGHTING*/);
			drawStringBorder(
					(width/2)-(fontRenderer.getStringWidth(getFreq()+"")/2),
					(height/2)-35,
					(width/2)+(fontRenderer.getStringWidth(getFreq()+"")/2)
			);
			
			fontRenderer.drawString(getFreq()+"", (width/2) - (fontRenderer.getStringWidth(getFreq()+"") / 2), (height/2)-35, 0x404040);
			GL11.glEnable(2896 /*GL_LIGHTING*/);
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}

	/**
	 * Action listener.<br>
	 * Triggers when a button was clicked on the GUI.<br>
	 * - Runs all override beforeFrequencyChange, exits if premature exit was returned, skipping the frequency from being set.
	 * 
	 * @param guibutton button that was clicked
	 */
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		try {
			Object a = getFreq();
			Object b = getFreq();
			int freq, oldFreq;
			try {
				freq = Integer.parseInt(a.toString());
				oldFreq = Integer.parseInt(b.toString());
			} catch(NumberFormatException e) {
				return;
			}
			
			switch ( guibutton.id ) {
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
			if ( freq > 9999 ) freq -= 10000;
			if ( freq < 0 ) freq += 10000;

			boolean prematureExit = false;
			for (GuiRedstoneWirelessOverride override: overrides ) {
				if ( override.beforeFrequencyChange(inventory, oldFreq, freq) )
					prematureExit = true;
			}
			if ( prematureExit ) return;
			
			if ( oldFreq != freq )
				setFreq(Integer.toString(freq));
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Handles keyboard input.<br>
	 * If inventory key is pressed or ESC, close the GUI.
	 */
	@Override
	public void handleKeyboardInput() {
		try {
			super.handleKeyboardInput();
			
			if(Keyboard.getEventKeyState()) {
				int inventoryKey = 0;
				for ( Object o: KeyBinding.keybindArray ) {
					if ( ((KeyBinding)o).keyDescription.equals("key.inventory") ) {
						inventoryKey = ((KeyBinding)o).keyCode;
						break;
					}
				}
				if( Keyboard.getEventKey() == inventoryKey || Keyboard.getEventKey() == 28 ) {
					close();
					return;
				}
			}
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
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
			if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState() )
				close();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Closes the GUI.
	 */
	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	/**
	 * Draw a bordered box around a string.<br>
	 * Outer height is always 10, inner 8.
	 * 
	 * @param x1 screen X coordinate, top left
	 * @param y1 screen Y coordinate, top left
	 * @param x2 screen X coordinate, bottom right
	 */
	protected void drawStringBorder(int x1, int y1, int x2) {
		drawRect(
				x1-3,
				y1-3,
				x2+3,
				y1+10,
				0xff000000
		);
		drawRect(
				x1-2,
				y1-2,
				x2+2,
				y1+9,
				0xffffffff
		);
	}
	
	/**
	 * Draws the strings.<br>
	 * - Name.<br>
	 * - Frequency.
	 */
	protected void drawGuiContainerForegroundLayer(){
		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth(getName())/2),
				6,
				(xSize/2)+(fontRenderer.getStringWidth(getName())/2)
		);
		fontRenderer.drawString(getName(), (xSize/2)-(fontRenderer.getStringWidth(getName())/2), 6, 0x404040);

		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth("Frequency")/2),
				32,
				(xSize/2)+(fontRenderer.getStringWidth("Frequency")/2)
		);
		fontRenderer.drawString("Frequency", (xSize/2)-(fontRenderer.getStringWidth("Frequency")/2), 32, 0x404040);
	}
	
	/**
	 * Always returns false, prevents game from pausing when GUI is open.
	 * @return false
	 */
	@Override
	public boolean doesGuiPauseGame(){
		return false;
	}
	
	/**
	 * TileEntity name.
	 * @return Name.
	 */
	protected String getName() {
		return inventory.getInvName();
	}
	/**
	 * Fetches the frequency from the TileEntity
	 * @return Frequency.
	 */
	protected Object getFreq() {
		return inventory.getFreq();
	}
	/**
	 * Sets the frequency in the TileEntity.
	 * 
	 * @param freq frequency.
	 */
	protected void setFreq(String freq) {
		inventory.setFreq(freq);
	}
}
