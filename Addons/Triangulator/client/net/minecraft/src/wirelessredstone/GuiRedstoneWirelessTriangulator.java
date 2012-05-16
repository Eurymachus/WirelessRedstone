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
package net.minecraft.src.wirelessredstone;

import java.util.ArrayList;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWifiExit;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessTriangulator extends GuiScreen {
	protected int xSize;
	protected int ySize;
	
	public GuiRedstoneWirelessTriangulator() {
		super();
		xSize = 176;
		ySize = 166;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initGui() {
        controlList = new ArrayList();
		controlList.add(new GuiButton(0, (width/2)+10, (height/2)-20, 20, 20, "+"));
		controlList.add(new GuiButton(1, (width/2)-30, (height/2)-20, 20, 20, "-"));
		controlList.add(new GuiButton(2, (width/2)+32, (height/2)-20, 20, 20, "+10"));
		controlList.add(new GuiButton(3, (width/2)-52, (height/2)-20, 20, 20, "-10"));
		controlList.add(new GuiButton(4, (width/2)+54, (height/2)-20, 26, 20, "+100"));
		controlList.add(new GuiButton(5, (width/2)-80, (height/2)-20, 26, 20, "-100"));
		controlList.add(new GuiButton(6, (width/2)+48, (height/2)-42, 32, 20, "+1000"));
		controlList.add(new GuiButton(7, (width/2)-80, (height/2)-42, 32, 20, "-1000"));
		controlList.add(new GuiButtonWifiExit(100, (((width - xSize)/2)+xSize-13-1), (((height - ySize)/2)+1)));
		super.initGui();
	}
	
	protected void actionPerformed(GuiButton guibutton) {
		Object a = ((ItemRedstoneWirelessTriangulator)WirelessTriangulator.itemTriang).freq;
		Object b = ((ItemRedstoneWirelessTriangulator)WirelessTriangulator.itemTriang).freq;
		int newFreq, oldFreq;
		try {
			newFreq = Integer.parseInt(a.toString());
			oldFreq = Integer.parseInt(b.toString());
		} catch(NumberFormatException e) {
			return;
		}
		
		switch ( guibutton.id ) {
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
		if ( newFreq != oldFreq) {
			if ( newFreq > 9999 ) newFreq -= 10000;
			if ( newFreq < 0 ) newFreq += 10000;
			((ItemRedstoneWirelessTriangulator)WirelessTriangulator.itemTriang).freq = Integer.toString(newFreq);
		}
	}

	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone.Triangulator: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();
        
		int m = mc.renderEngine.getTexture("/gui/wifi_small.png");
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
		
		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth("Wireless Triangulator")/2),
				6,
				(xSize/2)+(fontRenderer.getStringWidth("Wireless Triangulator")/2)
		);
		fontRenderer.drawString("Wireless Triangulator", (xSize/2)-(fontRenderer.getStringWidth("Wireless Triangulator")/2), 6, 0x404040);
		
		drawStringBorder(
				(xSize/2)-(fontRenderer.getStringWidth("Frequency")/2),
				32,
				(xSize/2)+(fontRenderer.getStringWidth("Frequency")/2)
		);
		fontRenderer.drawString("Frequency", (xSize/2)-(fontRenderer.getStringWidth("Frequency")/2), 32, 0x404040);

		
		String freq = ((ItemRedstoneWirelessTriangulator)WirelessTriangulator.itemTriang).freq;
		drawStringBorder(
				(xSize/2) - (fontRenderer.getStringWidth(freq+"") / 2),
				(ySize/2)-35,
				(xSize/2) + (fontRenderer.getStringWidth(freq+"") / 2)
		);
		fontRenderer.drawString(freq+"", (xSize/2) - (fontRenderer.getStringWidth(freq+"") / 2), (ySize/2)-35, 0x404040);

		GL11.glPopMatrix();
		super.drawScreen(i, j, f);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
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
	
	public boolean doesGuiPauseGame(){
		return false;
	}
}
