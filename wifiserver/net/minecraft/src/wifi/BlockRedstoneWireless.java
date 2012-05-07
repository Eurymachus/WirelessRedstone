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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.src.BlockContainer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class BlockRedstoneWireless extends BlockContainer {
	private List<BlockRedstoneWirelessOverride> overrides;
	
	public BlockRedstoneWireless(int i) {
		super(i, Material.circuits);
		overrides = new ArrayList<BlockRedstoneWirelessOverride>();
	}

	public void addOverride(BlockRedstoneWirelessOverride override) {
		overrides.add(override);
	}
	
	
	// Stores state in metadata.
	public void setState(World world, int i, int j, int k, boolean state) {
		int meta = 0;
		if ( state )
			meta = 1;
		
		// Store meta.
		try {
			world.setBlockMetadataWithNotify(i, j, k, meta);
			world.markBlockNeedsUpdate(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	
	// Gets state from metadata.
	public boolean getState(World world, int i, int j, int k) {
		int meta = 0;
		try {
			meta = world.getBlockMetadata(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		
		// Returns true if the last bit in meta is 1.
		return getState(meta) ; 
	}
	public boolean getState(int meta) {
		return ( meta & 1 ) == 1; 
	}
	
	// Gets frequency.
	public String getFreq(World world, int i, int j, int k) {
		try {
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if ( tileentity == null) return null;
			
			if ( tileentity instanceof TileEntityRedstoneWireless )
				return ((TileEntityRedstoneWireless)tileentity).getFreq();
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return null;
	}
	// Changes the frequency.
	public abstract void changeFreq(World world, int i, int j, int k, String oldFreq, String freq);
	
	
	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		try {
			TileEntityRedstoneWireless entity = (TileEntityRedstoneWireless)getBlockEntity();
	        world.setBlockTileEntity(i, j, k, entity);
	        
			onBlockRedstoneWirelessAdded(world,i,j,k);
			
			// Run overrides.
			for ( BlockRedstoneWirelessOverride override: overrides) {
				override.afterBlockRedstoneWirelessAdded(world, i, j, k);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	protected abstract void onBlockRedstoneWirelessAdded(World world, int i, int j, int k);
	
	@Override
	public void onBlockRemoval(World world, int i, int j, int k) {
		try {
			onBlockRedstoneWirelessRemoved(world,i,j,k);
			
			// Run overrides.
			for ( BlockRedstoneWirelessOverride override: overrides) {
				override.afterBlockRedstoneWirelessRemoved(world, i, j, k);
			}

			world.removeBlockTileEntity(i, j, k);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	protected abstract void onBlockRedstoneWirelessRemoved(World world, int i, int j, int k);
	
	@Override
	public int idDropped(int i, Random random, int i1) {
		return this.blockID;
	}
	
	@Override
	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer) {
		try {			
			boolean prematureExit = true;
			// Run overrides.
			for ( BlockRedstoneWirelessOverride override: overrides) {
				prematureExit = override.beforeBlockRedstoneWirelessActivated(world, i, j, k, entityplayer);
				if ( prematureExit ) return false;
			}
			
			boolean output = false;
			output = onBlockRedstoneWirelessActivated(world,i,j,k,entityplayer);
	
	
			// Run overrides.
			for ( BlockRedstoneWirelessOverride override: overrides) {
				override.afterBlockRedstoneWirelessActivated(world, i, j, k, entityplayer);
			}
			
			return output;
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
			return false;
		}
	}
	
	protected abstract boolean onBlockRedstoneWirelessActivated(World world, int i, int j, int k, EntityPlayer entityplayer);
	
	@Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l) {
		try {
			onBlockRedstoneWirelessNeighborChange(world,i,j,k,l);
			
			// Run overrides.
			for ( BlockRedstoneWirelessOverride override: overrides) {
				override.afterBlockRedstoneWirelessNeighborChange(world, i, j, k, l);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	protected abstract void onBlockRedstoneWirelessNeighborChange(World world, int i, int j, int k, int l);
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
    public boolean canProvidePower() {
        return true;
    }
	/*
	@Override
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		try {
			return getBlockRedstoneWirelessTexture(iblockaccess,i,j,k,l);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
			return 0;
		}
	}*/
	protected abstract int getBlockRedstoneWirelessTexture(IBlockAccess iblockaccess, int i, int j, int k, int l);

	@Override
	public int getBlockTextureFromSide(int i) {
		try {
			return getBlockRedstoneWirelessTextureFromSide(i);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
			return 0;
		}
	}
	protected abstract int getBlockRedstoneWirelessTextureFromSide(int l);

	public TileEntity getBlockEntity() {
		return getBlockRedstoneWirelessEntity();
	}
	
	protected abstract TileEntityRedstoneWireless getBlockRedstoneWirelessEntity();
	
	public static void notifyNeighbors(World world, int i, int j, int k) {
		world.notifyBlocksOfNeighborChange(i, j, k, 0);
		world.notifyBlocksOfNeighborChange(i-1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i+1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i, j-1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j+1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j, k-1, 0);
		world.notifyBlocksOfNeighborChange(i, j, k+1, 0);
	}
	/*
	@Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {}
	*/
	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		try {
			for ( BlockRedstoneWirelessOverride override: overrides) {
				if ( override.beforeUpdateRedstoneWirelessTick(world, i, j, k, random) )
					return;
			}
			updateRedstoneWirelessTick(world,i,j,k,random);	
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
	}
	protected abstract void updateRedstoneWirelessTick(World world, int i, int j, int k, Random random);
	
	
	@Override
    public boolean isPoweringTo(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		try {
			if ( iblockaccess instanceof World ) {
				return isRedstoneWirelessPoweringTo((World) iblockaccess, i, j, k, l);
			}
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return false;
	}
	protected abstract boolean isRedstoneWirelessPoweringTo(World world, int i, int j, int k, int l);
	

	@Override
    public boolean isIndirectlyPoweringTo(World world, int i, int j, int k, int l) {
		try {
			return isRedstoneWirelessIndirectlyPoweringTo(world, i, j, k, l);
		} catch (Exception e) {
			LoggerRedstoneWireless.getInstance("WirelessRedstone: "+this.getClass().toString()).writeStackTrace(e);
		}
		return false;
    }
	protected abstract boolean isRedstoneWirelessIndirectlyPoweringTo(World world, int i, int j, int k, int l);
}
