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

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.src.TextureFX;
import net.minecraft.src.mod_WirelessTriangulator;
import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class TextureTriangulatorFX extends TextureFX {
	private Minecraft mc;
	private int compassIconImageData[];
	private double field_4229_i;
	private double field_4228_j;

	public TextureTriangulatorFX(Minecraft minecraft) {
		super(WirelessTriangulator.itemTriang.getIconFromDamage(0));
		compassIconImageData = new int[256];
		mc = minecraft;
		tileImage = 1;
		try {
			BufferedImage bufferedimage = ImageIO.read((net.minecraft.client.Minecraft.class).getResource("/WirelessSprites/triang.png"));
			bufferedimage.getRGB(0, 0, 16, 16, compassIconImageData, 0, 16);
		} catch(IOException e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone.Triangulator").writeStackTrace(e);
		}
	}
	
	public void onTick() {
		for(int i = 0; i < 256; i++) {
			int j = compassIconImageData[i] >> 24 & 0xff;
			int k = compassIconImageData[i] >> 16 & 0xff;
			int l = compassIconImageData[i] >> 8 & 0xff;
			int i1 = compassIconImageData[i] >> 0 & 0xff;
			if(anaglyphEnabled) {
				int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
				int k1 = (k * 30 + l * 70) / 100;
				int l1 = (k * 30 + i1 * 70) / 100;
				k = j1;
				l = k1;
				i1 = l1;
			}
			imageData[i * 4 + 0] = (byte)k;
			imageData[i * 4 + 1] = (byte)l;
			imageData[i * 4 + 2] = (byte)i1;
			imageData[i * 4 + 3] = (byte)j;
		}
		
		double d = 0.0D;
		if(mc.theWorld != null && mc.thePlayer != null) {
			int[] tx = RedstoneEther.getInstance().getClosestActiveTransmitter(
							(int)mc.thePlayer.posX, 
							0,
							(int)mc.thePlayer.posZ, 
							((ItemRedstoneWirelessTriangulator)WirelessTriangulator.itemTriang).freq
					);
			if ( tx == null ) return;
			double d2 = (double)tx[0] - mc.thePlayer.posX;
			double d4 = (double)tx[2] - mc.thePlayer.posZ;
			d = ((double)(mc.thePlayer.rotationYaw - 90F) * 3.1415926535897931D) / 180D - Math.atan2(d4, d2);
		}
		
		double d1;
		for(d1 = d - field_4229_i; d1 < -3.1415926535897931D; d1 += 6.2831853071795862D) { }
		for(; d1 >= 3.1415926535897931D; d1 -= 6.2831853071795862D) { }
		if(d1 < -1D) {
			d1 = -1D;
		}
		if(d1 > 1.0D) {
			d1 = 1.0D;
		}
		field_4228_j += d1 * 0.10000000000000001D;
		field_4228_j *= 0.80000000000000004D;
		field_4229_i += field_4228_j;
		double d3 = Math.sin(field_4229_i);
		double d5 = Math.cos(field_4229_i);
		for(int i2 = -4; i2 <= 4; i2++) {
			int k2 = (int)(8.5D + d5 * (double)i2 * 0.29999999999999999D);
			int i3 = (int)(7.5D - d3 * (double)i2 * 0.29999999999999999D * 0.5D);
			int k3 = i3 * 16 + k2;
			int i4 = 150;
			int k4 = 150;
			int i5 = 150;
			char c = '\377';
			if(anaglyphEnabled) {
				int k5 = (i4 * 30 + k4 * 59 + i5 * 11) / 100;
				int i6 = (i4 * 30 + k4 * 70) / 100;
				int k6 = (i4 * 30 + i5 * 70) / 100;
				i4 = k5;
				k4 = i6;
				i5 = k6;
			}
			imageData[k3 * 4 + 0] = (byte)i4;
			imageData[k3 * 4 + 1] = (byte)k4;
			imageData[k3 * 4 + 2] = (byte)i5;
			imageData[k3 * 4 + 3] = (byte)c;
		}
		
		for(int j2 = -8; j2 <= 16; j2++) {
			int l2 = (int)(8.5D + d3 * (double)j2 * 0.29999999999999999D);
			int j3 = (int)(7.5D + d5 * (double)j2 * 0.29999999999999999D * 0.5D);
			int l3 = j3 * 16 + l2;
			int j4 = j2 < 0 ? 100 : 20;
			int l4 = j2 < 0 ? 100 : 20;
			int j5 = j2 < 0 ? 100 : 255;
			char c1 = '\377';
			if(anaglyphEnabled) {
				int l5 = (j4 * 30 + l4 * 59 + j5 * 11) / 100;
				int j6 = (j4 * 30 + l4 * 70) / 100;
				int l6 = (j4 * 30 + j5 * 70) / 100;
				j4 = l5;
				l4 = j6;
				j5 = l6;
			}
			imageData[l3 * 4 + 0] = (byte)j4;
			imageData[l3 * 4 + 1] = (byte)l4;
			imageData[l3 * 4 + 2] = (byte)j5;
			imageData[l3 * 4 + 3] = (byte)c1;
		}
	}
}
