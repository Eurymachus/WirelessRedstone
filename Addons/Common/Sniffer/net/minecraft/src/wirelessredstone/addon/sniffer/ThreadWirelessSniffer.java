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

import net.minecraft.src.wirelessredstone.data.LoggerRedstoneWireless;

public class ThreadWirelessSniffer implements Runnable {
	protected GuiRedstoneWirelessSniffer gui;
	public boolean running;

	public ThreadWirelessSniffer(GuiRedstoneWirelessSniffer gui) {
		this.gui = gui;
	}

	@Override
	public void run() {
		running = true;
		while (running) {
			gui.initGui();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				LoggerRedstoneWireless.getInstance("WirelessRedstone.Sniffer")
						.writeStackTrace(e);
			}
		}
	}
}
