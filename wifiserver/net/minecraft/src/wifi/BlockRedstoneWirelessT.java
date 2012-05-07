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
package net.minecraft.src.wifi;

import java.util.Random;

import net.minecraft.src.*;
import net.minecraft.src.wifi.network.PacketHandlerRedstoneWireless;

/**
 * Redstone Wireless Transmitter
 * 
 * @author ali4z
 *
 */
public class BlockRedstoneWirelessT extends BlockRedstoneWireless {
	// Constructor.
	public BlockRedstoneWirelessT(int i, float hardness) {
		super(i);
		setHardness(hardness);
		setStepSound(Block.soundMetalFootstep);
	}
 

	@Override
	public void setState(World world, int i, int j, int k, boolean state) {
		super.setState(world, i, j, k, state);
		
		String freq = getFreq(world, i, j, k);
		RedstoneEther.getInstance().setTransmitterState(world, i, j, k, freq, state);
	}

	
	
	@Override
	public void changeFreq(World world, int i, int j, int k, String oldFreq, String freq) {
		// Remove transmitter from current frequency on the ether.
		RedstoneEther.getInstance().remTransmitter(world, i,j,k, oldFreq);
		
		// Add transmitter to the ether on new frequency.
		RedstoneEther.getInstance().addTransmitter(world,i,j,k, freq);
		RedstoneEther.getInstance().setTransmitterState(world, i, j, k, freq, getState(world,i,j,k));
	}



	@Override
	protected void onBlockRedstoneWirelessAdded(World world, int i, int j, int k) {
		RedstoneEther.getInstance().addTransmitter(world,i,j,k, getFreq(world,i,j,k));

		onBlockRedstoneWirelessNeighborChange(world, i, j, k, Block.redstoneWire.blockID);
	}



	@Override
	protected void onBlockRedstoneWirelessRemoved(World world, int i, int j, int k) {
		RedstoneEther.getInstance().remTransmitter(world, i,j,k, getFreq(world,i,j,k));
	}



	@Override
	protected boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		TileEntity tileentity = world.getBlockTileEntity(i, j, k);

		
		if ( tileentity instanceof TileEntityRedstoneWirelessT ) {
			PacketHandlerRedstoneWireless.PacketHandlerOutput.sendGuiPacketTo((EntityPlayerMP)entityplayer, (TileEntityRedstoneWirelessT)tileentity, 0);
		}
		
		return true;
	}



	@Override
	protected void onBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l) {
    	if ( l == this.blockID ) return;
    	
		// It was not removed, can provide power and is indirectly getting powered.
		if ( l > 0 && !getState(world,i,j,k) && (world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i,j,k)) )
			setState(world, i, j, k, true);
		// There are no powering entities, state is deactivated.
		else if ( getState(world,i,j,k) && !(world.isBlockGettingPowered(i, j, k) || world.isBlockIndirectlyGettingPowered(i,j,k)) )
			setState(world, i, j, k, false);
	}



	@Override
	protected int getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		if ( getState(iblockaccess.getBlockMetadata(i, j, k)) ) {
			if(l == 0 || l == 1) {
				return WirelessRedstone.spriteTopOn;
			}
			return WirelessRedstone.spriteTOn;
		} else {
			return getBlockRedstoneWirelessTextureFromSide(l);
		}
	}



	@Override
	protected int getBlockRedstoneWirelessTextureFromSide(int l) {
		if(l == 0 || l == 1) {
			return WirelessRedstone.spriteTopOff;
		}
		return WirelessRedstone.spriteTOff;
	}



	@Override
	protected TileEntityRedstoneWireless getBlockRedstoneWirelessEntity() {
		return new TileEntityRedstoneWirelessT();
	}



	@Override
	protected void updateRedstoneWirelessTick(World world, int i, int j, int k, Random random) {}



	@Override
	protected boolean isRedstoneWirelessPoweringTo(World world, int i, int j, int k, int l) {
		return false;
	}



	@Override
	protected boolean isRedstoneWirelessIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		return false;
	}
}
