package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.wirelessredstone.presentation.TileEntityRedstoneWirelessRenderer;

import org.lwjgl.opengl.GL11;

public class TileEntityRedstoneWirelessClockerRenderer extends TileEntityRedstoneWirelessRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f) {
		super.renderTileEntityAt(tileentity, d, d1, d2, f);
		float f4 = 0.01F;

		GL11.glPushMatrix();
			GL11.glTranslatef((float)d+0.5F, (float)d1+0.58F, (float)d2+1F+f4);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);
			
			renderClockFreq((TileEntityRedstoneWirelessClocker)tileentity);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float)d+1F+f4, (float)d1+0.58F, (float)d2+0.5F);
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);
			
			renderClockFreq((TileEntityRedstoneWirelessClocker)tileentity);
		GL11.glPopMatrix();
	
		GL11.glPushMatrix();
			GL11.glTranslatef((float)d+0.5F, (float)d1+0.58F, (float)d2-f4);
			GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, 1F * f4);
			
			renderClockFreq((TileEntityRedstoneWirelessClocker)tileentity);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float)d-f4, (float)d1+0.58F, (float)d2+0.5F);
			GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(f4, -f4, f4);
			GL11.glNormal3f(0.0F, 0.0F, -1F * f4);
			
			renderClockFreq((TileEntityRedstoneWirelessClocker)tileentity);
		GL11.glPopMatrix();
	}
	
	private void renderClockFreq(TileEntityRedstoneWirelessClocker tileentity) {
		FontRenderer fontrenderer = getFontRenderer();
		String s = tileentity.getClockFreqString()+"";
		GL11.glDepthMask(false);
		fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2,  50, 0);
		GL11.glDepthMask(true);
		//GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
