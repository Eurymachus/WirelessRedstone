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
package net.minecraft.src.wirelessredstone.addon.clocker;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.block.BlockRedstoneWirelessT;
import net.minecraft.src.wirelessredstone.tileentity.TileEntityRedstoneWireless;

public class BlockRedstoneWirelessClocker extends BlockRedstoneWirelessT {

	public BlockRedstoneWirelessClocker(int i, float hardness, float resistance) {
		super(i, hardness, resistance);
		this.setStepSound(Block.soundMetalFootstep);
	}
	
	
	public void toggleState(World world, int i, int j, int k) {
		setState(world,i,j,k, !getState(world,i,j,k));
	}

	@Override
	protected void onBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
		TileEntityRedstoneWirelessClocker entity = (TileEntityRedstoneWirelessClocker)getBlockEntity();
        world.setBlockTileEntity(i, j, k, entity);
        ThreadWirelessClocker.getInstance().addTileEntity(entity);
        super.onBlockRedstoneWirelessAdded(world,i,j,k);
	}

	@Override
	protected void onBlockRedstoneWirelessRemoved(World world, int i, int j, int k) {
        TileEntity entity = world.getBlockTileEntity(i, j, k);
        if ( entity instanceof TileEntityRedstoneWirelessClocker)
        	ThreadWirelessClocker.getInstance().remTileEntity((TileEntityRedstoneWirelessClocker)entity);
        else
        	ThreadWirelessClocker.getInstance().remTileEntity(i,j,k);
        
		super.onBlockRedstoneWirelessRemoved(world,i,j,k);
		
	}

	@Override
	protected boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		return BlockRedstoneWirelessClockerInjector.onBlockRedstoneWirelessActivated(world, i, j, k, entityplayer);
	}

	@Override
	protected void onBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {
		TileEntity entity = world.getBlockTileEntity(i, j, k);
		if ( entity instanceof TileEntityRedstoneWirelessClocker ) {
	    	// It was not removed, can provide power and is indirectly getting powered.
	        if ( l > 0 && (world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i,j,k)) )
	    		((TileEntityRedstoneWirelessClocker)entity).setClockState(true);
	        // There are no powering entities, state is deactivated.
	        else if ( !(world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i,j,k)) )
	        		((TileEntityRedstoneWirelessClocker)entity).setClockState(false);
		}
	}

	@Override
	protected int getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if ( getState(iblockaccess.getBlockMetadata(i, j, k)) ) {
			if(l == 0 || l == 1)
				return WirelessRedstone.spriteTopOn; 
			
			return WirelessClocker.spriteSidesOn;
		} else 
			return getBlockRedstoneWirelessTextureFromSide(l);
	}

	@Override
	protected int getBlockRedstoneWirelessTextureFromSide(int l) {
		if(l == 0 || l == 1)
			return WirelessRedstone.spriteTopOff;
		
		return WirelessClocker.spriteSidesOff;
	}

	@Override
	protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity() {
		return new TileEntityRedstoneWirelessClocker();
	}
}
