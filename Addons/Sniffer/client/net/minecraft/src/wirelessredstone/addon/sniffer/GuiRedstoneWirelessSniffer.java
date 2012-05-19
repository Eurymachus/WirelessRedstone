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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.World;
import net.minecraft.src.mod_WirelessSniffer;
import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.RedstoneWirelessPlayerMem;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.RedstoneWirelessSnifferFreqCoordsMem;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.RedstoneWirelessSnifferPlayerPageNumber;
import net.minecraft.src.wirelessredstone.addon.sniffer.network.PacketHandlerWirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.triangulator.network.PacketHandlerWirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;
import net.minecraft.src.wirelessredstone.data.RedstoneWirelessPlayerEtherCoordsMem;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWifiExit;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessSniffer extends GuiScreen {
	protected EntityPlayer player;
	private World world;
	protected int xSize;
	protected int ySize;
	private int maxEtherNodes = 9999;
	private boolean[] activeFreqs = new boolean[maxEtherNodes+1];
	private int nodeSize = 4;
	private int pageWidth = 50;
	private int pageHeight = 30;
	private ThreadWirelessSniffer thr;
	GuiButtonBoolean nextButton;
	GuiButtonBoolean prevButton;
	
	public GuiRedstoneWirelessSniffer(EntityPlayer player, World world) {
		super();
		this.player = player;
		this.world = world;
		xSize = 256;
		ySize = 200;
		thr = new ThreadWirelessSniffer(this);
	}

	@Override
	public void initGui() {
		nextButton = new GuiButtonBoolean(0, (width/2)+40, (height/2)+75, 40, 20, "Next", true);
		prevButton = new GuiButtonBoolean(1, (width/2)-80, (height/2)+75, 40, 20, "Prev", true);
		nextButton.enabled = (getPage() >= 0 && getPage() < maxEtherNodes/(pageWidth*pageHeight));
		prevButton.enabled = (getPage() > 0 && getPage() <= maxEtherNodes/(pageWidth*pageHeight));
		controlList.add(new GuiButtonWifiExit(100, (((width - xSize)/2)+xSize-13-1), (((height - ySize)/2)+1)));
		controlList.add(nextButton);
		controlList.add(prevButton);
		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		int page = getPage();
		int oldPage = getPage();
		switch(guibutton.id)
		{
			case 0:
				++page;
				break;
			case 1:
				--page;
				break;
			case 100:
				close();
				break;
		}
		if (page > 6) page = 6;
		if (page < 0) page = 0;
		if (oldPage != page) {
			if ((oldPage-page)==-1) {
				setPage(oldPage+1);
			}
			else {
				setPage(oldPage-1);
			}
		}
		if (nextButton.enabled && getPage() == maxEtherNodes/(pageWidth*pageHeight)) {
			nextButton.enabled = false;
		} else nextButton.enabled = true;
		if (prevButton.enabled && getPage() == 0) {
			prevButton.enabled = false;
		} else prevButton.enabled = true;
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
		String drawPage = "Page [" + (this.getPage()+1) + "]";
		fontRenderer.drawString(drawPage, (xSize/2)-(fontRenderer.getStringWidth(drawPage)/2), (height/2)+62, 0x00000000); 
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
		int c = (pageWidth*pageHeight)*getPage();
		for ( int n = 0; n < pageHeight; n++ ) {
			for ( int m = 0; m < pageWidth; m++ ) {
				x = i+(nodeSize*m)+m;
				y = j+(nodeSize*n)+n;
				if (c <= maxEtherNodes && c >= 0)
				{
					if (world.isRemote && mod_WirelessSniffer.wirelessSnifferSMP)
					{
						if (this.getFreqState(c))
							drawRect(x, y, x+nodeSize, y+nodeSize, 0xff00ff00);
						else
							drawRect(x, y, x+nodeSize, y+nodeSize, 0xffff0000);
					}
					else
					{
						if ( RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,Integer.toString(c)) ) {
							drawRect(x, y, x+nodeSize, y+nodeSize, 0xff00ff00);
						} else {
							drawRect(x, y, x+nodeSize, y+nodeSize, 0xffff0000);
						}
					}
					c++;
				}
			}
		}
	}
	
	protected int getPage()
	{
		return RedstoneWirelessSnifferPlayerPageNumber.getInstance(world).getPage(player);
	}
	
	protected void setPage(int pageNumber)
	{
		RedstoneWirelessSnifferPlayerPageNumber.getInstance(world).setFreq(this.player, pageNumber);
	}
	
	public void onGuiClosed() {
		thr.running = false;
	}

	protected boolean getFreqState(int freq)
	{
		if (freq > maxEtherNodes) return false;
		if (this.activeFreqs.length == maxEtherNodes+1)
		{
			return this.activeFreqs[freq];
		}
		else return false;
	}
	
	public void setActiveFreqs(String[] activeFreqs)
	{
		boolean[] newActiveFreqs = new boolean[maxEtherNodes+1];
		int j = 0;
		for(int i = 0; i < maxEtherNodes; ++i)
		{
			if (activeFreqs != null && j < activeFreqs.length && String.valueOf(i).equals(activeFreqs[j]))
			{
				this.activeFreqs[i] = true;
				++j;
			}
			else this.activeFreqs[i] = false;
		}
	}
}
