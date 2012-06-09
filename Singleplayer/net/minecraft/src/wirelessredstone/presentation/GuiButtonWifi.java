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
package net.minecraft.src.wirelessredstone.presentation;

import net.minecraft.src.GuiButton;

/**
 * A GUI Button<br>
 * PopupText is the mouseover text
 * 
 * @author Eurymachus
 * 
 */
public class GuiButtonWifi extends GuiButton {
	private String popupText;

	public GuiButtonWifi(int i, int j, int k, int l, int i1, String s,
			String popupText) {
		super(i, j, k, l, i1, s);
		this.popupText = popupText;
	}

	public GuiButtonWifi(int i, int j, int k, int l, int i1, String s) {
		super(i, j, k, l, i1, s);
	}

	public boolean inBounds(int x, int y) {
		boolean flag = x >= xPosition && y >= yPosition
				&& x < xPosition + width && y < yPosition + height;
		return flag;
	}

	public String getPopupText() {
		return this.popupText;
	}
}
