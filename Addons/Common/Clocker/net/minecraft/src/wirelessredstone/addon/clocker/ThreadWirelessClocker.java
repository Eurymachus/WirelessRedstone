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

import java.util.LinkedList;
import java.util.List;

import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class ThreadWirelessClocker implements Runnable {
	private boolean running;
	private List<TileEntityRedstoneWirelessClocker> entities;
	private static ThreadWirelessClocker instance;

	private ThreadWirelessClocker() {
		entities = new LinkedList<TileEntityRedstoneWirelessClocker>();
	}

	public static ThreadWirelessClocker getInstance() {
		if (instance == null) {
			instance = new ThreadWirelessClocker();
		}

		return instance;
	}

	private void stop() {
		running = false;
		for (TileEntityRedstoneWirelessClocker entity : entities) {
			if (entity != null)
				((BlockRedstoneWirelessClocker) WirelessClocker.blockClock)
						.setState(entity.worldObj, entity.getBlockCoord(0),
								entity.getBlockCoord(1),
								entity.getBlockCoord(2), false);
		}
	}

	private void start() {
		if (!running) {
			Thread thr = new Thread(this);
			thr.setName("WirelessClockerThread");
			thr.start();
		}
	}

	public void remTileEntity(int i, int j, int k) {
		TileEntityRedstoneWirelessClocker remEntity = null;
		for (TileEntityRedstoneWirelessClocker entity : entities) {
			if (entity.getBlockCoord(0) == i && entity.getBlockCoord(1) == j
					&& entity.getBlockCoord(2) == k) {
				remEntity = entity;
				break;
			}
		}
		if (remEntity != null)
			remTileEntity(remEntity);
	}

	public void remTileEntity(TileEntityRedstoneWirelessClocker entity) {
		int index = entities.indexOf(entity);
		if (index >= 0 && index < entities.size())
			entities.remove(index);

		if (entities.size() == 0)
			stop();
	}

	public void addTileEntity(TileEntityRedstoneWirelessClocker entity) {
		int index = entities.indexOf(entity);
		if (index == -1)
			entities.add(entity);
		start();
	}

	@Override
	public void run() {
		running = true;
		int ticker = 0;
		int minTicker = 10;
		while (running) {
			for (TileEntityRedstoneWirelessClocker entity : entities) {
				if (entity != null && entity.getClockState()
						&& ticker % entity.getClockFreq() == 0) {
					((BlockRedstoneWirelessClocker) WirelessClocker.blockClock)
							.toggleState(entity.worldObj,
									entity.getBlockCoord(0),
									entity.getBlockCoord(1),
									entity.getBlockCoord(2));
				}
			}

			ticker += minTicker;
			if (ticker > 200000000)
				ticker = 0;

			try {
				Thread.sleep(minTicker);
			} catch (InterruptedException e) {
				LoggerRedstoneWireless.getInstance("WirelessRedstone.Clocker")
						.writeStackTrace(e);
			}
		}
	}
}
