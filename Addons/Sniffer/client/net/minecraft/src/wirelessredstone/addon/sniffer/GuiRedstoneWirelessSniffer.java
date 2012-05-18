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
package net.minecraft.src.wirelessredstone.addon.sniffer;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWifiExit;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessSniffer extends GuiScreen {
	protected int xSize;
	protected int ySize;
	private int nodeSize = 4;
	private int pageWidth = 50;
	private int pageHeight = 30;
	private ThreadWirelessSniffer thr;
	protected int page;
	protected int[] freqCoords = new int[3];
	boolean next = false;
	boolean prev = false;
	
	public GuiRedstoneWirelessSniffer() {
		super();
		xSize = 256;
		ySize = 200;
		page = 0;
		thr = new ThreadWirelessSniffer(this);
	}

	@Override
	public void initGui() {
		if (page >= 0 && page < 6) next = true; else next = false;
		if (page > 0 && page <= 6) prev = true; else prev = false;
		controlList.add(new GuiButtonWifiExit(100, (((width - xSize)/2)+xSize-13-1), (((height - ySize)/2)+1)));
		controlList.add(new GuiButtonBoolean(0, (width/2)-0, (height/2)+75, 40, 20, "Next", true));
		controlList.add(new GuiButtonBoolean(1, (width/2)-40, (height/2)+75, 40, 20, "Prev", false));
		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		int page = getPage();
		int oldPage = getPage();
		switch(guibutton.id)
		{
			case 0:
				page++;
				break;
			case 1:
				page--;
				break;
			case 100:
				close();
				break;
		}
		if (page > (9999/((pageWidth*pageHeight)))) page = (9999/((pageWidth*pageHeight)));
		if (page < 0) page = 0;
		if (oldPage != page){
			setPage(page);
			initGui();
		}
	}

	public void close() {
		try {
			mc.displayGuiScreen(null);
			mc.setIngameFocus();
		} catch ( Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone.Sniffer: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	@Override
	public void drawScreen(int i, int j, float f) {
		drawDefaultBackground();

		int m = mc.renderEngine.getTexture("/gui/wifi_xlarge.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(m);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);;
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0F);
		GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(2896 /*GL_LIGHTING*/);
		GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		drawFrequencies(4, 24);
		fontRenderer.drawString("Wireless Sniffer", (xSize/2)-(fontRenderer.getStringWidth("Wireless Sniffer")/2), 6, 0x404040);
		GL11.glPopMatrix();

		
		super.drawScreen(i, j, f);
		GL11.glEnable(2896 /*GL_LIGHTING*/);
		GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	}
	public boolean doesGuiPauseGame(){
		return false;
	}
	
	private void drawFrequencies(int i, int j) {
		int x, y;
		int c = (pageWidth*pageHeight)*page;
		ModLoader.getMinecraftInstance().thePlayer.addChatMessage("Page: " + this.page);
		for ( int n = 0; n < pageHeight; n++ ) {
			for ( int m = 0; m < pageWidth; m++ ) {
				x = i+(nodeSize*m)+m;
				y = j+(nodeSize*n)+n;
				if (c <= 9999 && c >= 0) {
					if ( RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,Integer.toString(c)) ) {
						drawRect(x, y, x+nodeSize, y+nodeSize, 0xff00ff00);
					} else {
						drawRect(x, y, x+nodeSize, y+nodeSize, 0xffff0000);
					}
					c++;
				}
			}
		}
	}
	
	protected int getPage()
	{
		return this.page;
	}
	
	protected void setPage(int pageNumber)
	{
		this.page = pageNumber;
	}
	
	public void onGuiClosed() {
		thr.running = false;
	}
}
