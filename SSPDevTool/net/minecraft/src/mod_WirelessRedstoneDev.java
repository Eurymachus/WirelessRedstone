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

import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.ether.RedstoneEther;

public class mod_WirelessRedstoneDev extends BaseMod {

	@Override
	public void modsLoaded()
	{
		if (ModLoader.isModLoaded("mod_WirelessRedstone"))
		{
			RedstoneEtherGui ex = new RedstoneEtherGui();
			RedstoneEther.getInstance().assGui(ex);
			ex.setVisible(true);
			
			ModLoader.addRecipe(new ItemStack(Item.redstone, 64), new Object[] {
	            "#", Character.valueOf('#'), Block.dirt
	        });
			ModLoader.addRecipe(new ItemStack(Block.lever, 64), new Object[] {
	            "##", Character.valueOf('#'), Block.dirt
	        });
			ModLoader.addRecipe(new ItemStack(WirelessRedstone.blockWirelessR, 64), new Object[] {
	            "##","##", Character.valueOf('#'), Block.dirt
	        });
			ModLoader.addRecipe(new ItemStack(WirelessRedstone.blockWirelessT, 64), new Object[] {
	            "##", Character.valueOf('#'), WirelessRedstone.blockWirelessR
	        });
			
			ModLoader.addRecipe(new ItemStack(Item.compass, 4), new Object[] {
	            "# ", " #", Character.valueOf('#'), Item.redstone
	        });
			
			ModLoader.addRecipe(new ItemStack(Block.torchWood, 4), new Object[] {
	            "#", "S", Character.valueOf('#'), Block.dirt, Character.valueOf('S'), Item.stick
	        });
	        
			
			ModLoader.addRecipe(new ItemStack(Item.redstoneRepeater, 64), new Object[] {
	            "##", Character.valueOf('#'), Item.redstone
	        });
			ModLoader.addRecipe(new ItemStack(Block.pistonBase, 64), new Object[] {
	            "##","##", Character.valueOf('#'), Item.redstone
	        });
		}
	}

	@Override
	public String getPriorities()
	{
		return "after:mod_WirelessRedstone";
	}
	
	public mod_WirelessRedstoneDev() {
	}

	@Override
	public String getVersion() {
		return "1.5";
	}

	@Override
	public void load() {
	}
}
