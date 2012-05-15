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
package net.minecraft.src.wirelessredstone.addon.powerc;

import java.util.ArrayList;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.wirelessredstone.addon.powerc.network.PacketHandlerPowerConfig;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWifi;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWifiExit;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessPowerDirector extends GuiScreen {
	protected TileEntityRedstoneWirelessR inventory;
	protected int xSize;
	protected int ySize;
	
	public GuiRedstoneWirelessPowerDirector(TileEntityRedstoneWirelessR tileentity) {
		super();
		inventory = tileentity;
		xSize = 177;
		ySize = 166;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initGui() {
        controlList = new ArrayList();
		controlList.add(new GuiButtonBoolean(0, (width/2)-60, (height/2)-42, 20, 20, "N", inventory.isPoweringDirection(3), "North Face"));
		controlList.add(new GuiButtonBoolean(1, (width/2)-40, (height/2)-42, 20, 20, "E", inventory.isPoweringDirection(4), "East Face"));
		controlList.add(new GuiButtonBoolean(2, (width/2)-20, (height/2)-42, 20, 20, "S", inventory.isPoweringDirection(2), "South Face"));
		controlList.add(new GuiButtonBoolean(3, (width/2), (height/2)-42, 20, 20, "W", inventory.isPoweringDirection(5), "West Face"));
		controlList.add(new GuiButtonBoolean(4, (width/2)+20, (height/2)-42, 20, 20, "U", inventory.isPoweringDirection(0), "Upward Face"));
		controlList.add(new GuiButtonBoolean(5, (width/2)+40, (height/2)-42, 20, 20, "D", inventory.isPoweringDirection(1), "Downward Face"));
		

		controlList.add(new GuiButtonBoolean(6, (width/2)-60, (height/2), 20, 20, "N", inventory.isPoweringIndirectly(3), "North Face"));
		controlList.add(new GuiButtonBoolean(7, (width/2)-40, (height/2), 20, 20, "E", inventory.isPoweringIndirectly(4), "East Face"));
		controlList.add(new GuiButtonBoolean(8, (width/2)-20, (height/2), 20, 20, "S", inventory.isPoweringIndirectly(2), "South Face"));
		controlList.add(new GuiButtonBoolean(9, (width/2), (height/2), 20, 20, "W", inventory.isPoweringIndirectly(5), "West Face"));
		controlList.add(new GuiButtonBoolean(10, (width/2)+20, (height/2), 20, 20, "U", inventory.isPoweringIndirectly(0), "Upward Face"));
		controlList.add(new GuiButtonBoolean(11, (width/2)+40, (height/2), 20, 20, "D", inventory.isPoweringIndirectly(1), "Downward Face"));
		
		controlList.add(new GuiButtonWifiExit(100, (((width - xSize)/2)+xSize-13-1), (((height - ySize)/2)+1)));
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if ( inventory instanceof TileEntityRedstoneWirelessR ) {
			int dir = -1;
			int indir = -1;
			switch ( guibutton.id ) {
				case 0: // N
					dir = 3;
					break;
				case 1: // E
					dir = 4;
					break;
				case 2: // S
					dir = 2;
					break;
				case 3: // W
					dir = 5;
					break;
				case 4: // U
					dir = 0;
					break;
				case 5: // D
					dir = 1;
					break;
				case 6: // N
					indir = 3;
					break;
				case 7: // E
					indir = 4;
					break;
				case 8: // S
					indir = 2;
					break;
				case 9: // W
					indir = 5;
					break;
				case 10: // U
					indir = 0;
					break;
				case 11: // D
					indir = 1;
					break;
				case 100:
					close();
					break;
			}
			if ( dir >= 0 ) {
				if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
					PacketHandlerPowerConfig.PacketHandlerOutput.sendPowerConfigPacket(
							"Power Direction",
							inventory.xCoord, inventory.yCoord, inventory.zCoord, dir);
				}
				inventory.flipPowerDirection(dir);
				notifyNeighbors();
				initGui();
			}
			if ( indir >= 0 ) {
				if (ModLoader.getMinecraftInstance().theWorld.isRemote) {
					PacketHandlerPowerConfig.PacketHandlerOutput.sendPowerConfigPacket(
							"Indirect Power",
							inventory.xCoord, inventory.yCoord, inventory.zCoord, indir);
				}
				inventory.flipIndirectPower(indir);
				notifyNeighbors();
				initGui();
			}
		}
	}

	
	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone.PowerConfigurator: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	private void notifyNeighbors() {
		int i = inventory.getBlockCoord(0);
		int j = inventory.getBlockCoord(1);
		int k = inventory.getBlockCoord(2);
		BlockRedstoneWireless.notifyNeighbors(inventory.worldObj, i, j, k);
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		int m = mc.renderEngine.getTexture("/gui/wifi_medium.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(m);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		GL11.glPushMatrix();
		GL11.glTranslatef((width - xSize) / 2, (height - ySize) / 2, 0.0F);
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);


        for (int control = 0; control < this.controlList.size(); control++)
        {
            if (this.controlList.get(control) instanceof GuiButtonWifi)
            {
            	GuiButtonWifi button = (GuiButtonWifi)this.controlList.get(control);

	            if (this.isMouseOverButton(button, i, j))
	            {
	                this.drawToolTip(button, i, j);
	            }
            }
        }
		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth(inventory.getInvName())/2),
				6,
				(xSize/2)+(fontRenderer.getStringWidth(inventory.getInvName())/2)
		);
		fontRenderer.drawString(inventory.getInvName(), (xSize/2)-(fontRenderer.getStringWidth(inventory.getInvName())/2), 6, 0x404040);

		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth("Power Direction")/2),
				28,
				(xSize/2)+(fontRenderer.getStringWidth("Power Direction")/2)
		);
		fontRenderer.drawString("Power Direction", (xSize/2)-(fontRenderer.getStringWidth("Power Direction")/2), 28, 0x404040);

		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth("Indirect Power")/2),
				70,
				(xSize/2)+(fontRenderer.getStringWidth("Indirect Power")/2)
		);
		fontRenderer.drawString("Indirect Power", (xSize/2)-(fontRenderer.getStringWidth("Indirect Power")/2), 70, 0x404040);
		
		GL11.glPopMatrix();
		super.drawScreen(i, j, f);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	}
	
	private void drawToolTip(GuiButtonWifi button, int i, int j)
	{
		this.drawGradientRect(button.xPosition, button.yPosition, i, j, 20, 50);
		fontRenderer.drawSplitString(button.getPopupText(), button.xPosition, button.yPosition, 16, 0);
	}

	private boolean isMouseOverButton(GuiButtonWifi var23, int i, int j) {
		if (var23 != null)
			return var23.inBounds(i, j);
		return false;
	}

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
	
	@Override
	public boolean doesGuiPauseGame(){
		return false;
	}
}
