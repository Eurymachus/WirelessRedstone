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
package net.minecraft.src.wifiremote;

import java.util.ArrayList;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.wifi.GuiButtonBoolean;
import net.minecraft.src.wifi.GuiRedstoneWireless;

import org.lwjgl.opengl.GL11;

public class GuiRedstoneWirelessRemote extends GuiRedstoneWireless {
	protected EntityPlayer player;
	protected int i;
	protected int j;
	protected int k;
	protected ItemStack itemstack;
	protected World world;
	
	public GuiRedstoneWirelessRemote(ItemStack itemstack, World world, EntityPlayer entityplayer, int i, int j, int k) {
		super();
		player = entityplayer;
		this.i = i;
		this.j = j;
		this.k = k;
		this.itemstack = itemstack;
		this.world = world;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initGui() {
		super.initGui();

		controlList.add(new GuiButtonBoolean(20, (width/2)-20, (height/2)+5, 40, 20, "Pulse", false));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		super.actionPerformed(guibutton);
		
		if ( guibutton.id ==  20 ) {
			ThreadWirelessRemote.pulse(player,world);
			close();
		}
	}
	
	@Override
	public void onGuiClosed() {
		if ( player.getCurrentEquippedItem() == null )
			MemRedstoneWirelessRemote.getInstance(world).remMem(itemstack.hashCode());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		int a = mc.renderEngine.getTexture("/gui/wifi_medium.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(a);
		int b = (width - xSize) / 2;
		int c = (height - ySize) / 2;
		drawTexturedModalRect(b, c, 0, 0, xSize, ySize);
	}

	@Override
	protected String getName() {
		return "Wireless Remote";
	}
	@Override
	protected String getFreq() {
		return MemRedstoneWirelessRemote.getInstance(world).getFreq(itemstack.hashCode());
	}
	@Override
	protected void setFreq(String freq) {
		MemRedstoneWirelessRemote.getInstance(world).setFreq(itemstack.hashCode(),freq);
	}
}