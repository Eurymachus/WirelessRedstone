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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_PowerConfigurator;
import net.minecraft.src.wirelessredstone.addon.powerc.network.packet.PacketPowerConfigGui;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWirelessR;

public class ItemRedstoneWirelessPowerDirector extends Item {

	protected ItemRedstoneWirelessPowerDirector(int i) {
		super(i);
		maxStackSize = 1;
		setMaxDamage(64);
	}
	
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);

		if ( tileentity instanceof TileEntityRedstoneWirelessR )
		{
			if (mod_PowerConfigurator.powerConfigSMP)
			{
				PacketPowerConfigGui pPCGui = new PacketPowerConfigGui(i, j, k);
				EntityPlayerMP player = (EntityPlayerMP)entityplayer;
				player.playerNetServerHandler.netManager.addToSendQueue(pPCGui.getPacket());
				itemstack.damageItem(1, entityplayer);
				return true;
			}
			else
			{
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean isFull3D() {
		return true;
	}
}
