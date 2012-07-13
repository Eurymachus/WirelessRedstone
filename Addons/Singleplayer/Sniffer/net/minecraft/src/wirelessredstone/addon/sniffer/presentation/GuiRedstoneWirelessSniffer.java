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
package net.minecraft.src.wirelessredstone.addon.sniffer.presentation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.GuiButton;
import net.minecraft.src.wirelessredstone.WirelessRedstone;
import net.minecraft.src.wirelessredstone.addon.sniffer.WirelessSniffer;
import net.minecraft.src.wirelessredstone.addon.sniffer.data.WirelessSnifferData;
import net.minecraft.src.wirelessredstone.addon.sniffer.overrides.GuiRedstoneWirelessSnifferOverride;
import net.minecraft.src.wirelessredstone.overrides.GuiRedstoneWirelessDeviceOverride;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonBoolean;
import net.minecraft.src.wirelessredstone.presentation.GuiButtonWirelessExit;
import net.minecraft.src.wirelessredstone.presentation.GuiRedstoneWirelessDevice;

public class GuiRedstoneWirelessSniffer extends GuiRedstoneWirelessDevice {
	private int nodeSize = 4;
	private int pageWidth = 50;
	private int pageHeight = 30;
	GuiButtonBoolean nextButton;
	GuiButtonBoolean prevButton;
	private boolean[] activeFreqs;
	private List<GuiRedstoneWirelessDeviceOverride> snifferOverrides;

	public GuiRedstoneWirelessSniffer() {
		super();
		snifferOverrides = new ArrayList();
		xSize = 256;
		ySize = 200;
	}

	@Override
	protected void addControls() {
		int currentPage = this.getPage();
		nextButton = new GuiButtonBoolean(0, (width / 2) + 40,
				(height / 2) + 75, 40, 20, "Next", true, "Next Page");
		prevButton = new GuiButtonBoolean(1, (width / 2) - 80,
				(height / 2) + 75, 40, 20, "Prev", true, "Previous Page");
		nextButton.enabled = (currentPage >= 0 && currentPage < WirelessRedstone.maxEtherFrequencies
				/ (pageWidth * pageHeight));
		prevButton.enabled = (currentPage > 0 && currentPage <= WirelessRedstone.maxEtherFrequencies
				/ (pageWidth * pageHeight));
		controlList.add(new GuiButtonWirelessExit(100, (((width - xSize) / 2)
				+ xSize - 13 - 1), (((height - ySize) / 2) + 1)));
		controlList.add(nextButton);
		controlList.add(prevButton);
	}

	@Override
	public void initGui() {
		super.initGui();
	}

	public void addSnifferOverride(GuiRedstoneWirelessDeviceOverride override) {
		snifferOverrides.add(override);
	}

	private int getPage() {
		return ((WirelessSnifferData) this.wirelessDeviceData).getPage();
	}

	private void setPage(int pageNumber) {
		boolean prematureExit = false;
		for (GuiRedstoneWirelessDeviceOverride override : snifferOverrides) {
			if (((GuiRedstoneWirelessSnifferOverride) override).beforeSetPage(
					this.wirelessDeviceData, pageNumber))
				prematureExit = true;
		}

		if (!prematureExit) {
			((WirelessSnifferData) this.wirelessDeviceData).setPage(pageNumber);
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		int page = this.getPage();
		int oldPage = this.getPage();
		switch (guibutton.id) {
		case 0:
			++page;
			break;
		case 1:
			--page;
			break;
		case 100:
			close();
			break;
		}
		if (page > 6)
			page = 6;
		if (page < 0)
			page = 0;
		if (oldPage != page) {
			if ((oldPage - page) == -1) {
				this.setPage(oldPage + 1);
			} else {
				this.setPage(oldPage - 1);
			}
		}

		boolean prematureExit = false;
		for (GuiRedstoneWirelessDeviceOverride override : snifferOverrides) {
			if (((GuiRedstoneWirelessSnifferOverride) override).beforeSetPage(
					wirelessDeviceData, this.getPage()))
				prematureExit = true;
		}

		if (!prematureExit) {
			if (nextButton.enabled
					&& this.getPage() == WirelessRedstone.maxEtherFrequencies
							/ (pageWidth * pageHeight)) {
				nextButton.enabled = false;
			} else
				nextButton.enabled = true;
			if (prevButton.enabled && this.getPage() == 0) {
				prevButton.enabled = false;
			} else
				prevButton.enabled = true;
		}
	}

	@Override
	protected String getBackgroundImage() {
		return "/gui/wifi_xlarge.png";
	}

	private void drawFrequencies(int i, int j) {
		int x, y;
		int c = (pageWidth * pageHeight) * this.getPage();
		for (int n = 0; n < pageHeight; n++) {
			for (int m = 0; m < pageWidth; m++) {
				x = i + (nodeSize * m) + m;
				y = j + (nodeSize * n) + n;
				if (c <= WirelessRedstone.maxEtherFrequencies && c >= 0) {
					if (getFreqState(Integer.toString(c)))
						drawRect(x, y, x + nodeSize, y + nodeSize, 0xff00ff00);
					else {
						drawRect(x, y, x + nodeSize, y + nodeSize, 0xffff0000);
					}
				}
				c++;
			}
		}
	}

	@Override
	public void onGuiClosed() {
		boolean prematureExit = false;

		for (GuiRedstoneWirelessDeviceOverride override : snifferOverrides) {
			if (((GuiRedstoneWirelessSnifferOverride) override)
					.beforeGuiClosed(wirelessDeviceData))
				prematureExit = true;
		}

		if (!prematureExit)
			WirelessSniffer.deactivateSniffer(world, entityplayer);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j, float f) {
		drawFrequencies(4, 24);
		String drawPage = "Page [" + (this.getPage() + 1) + "]";
		fontRenderer.drawString(drawPage,
				(xSize / 2) - (fontRenderer.getStringWidth(drawPage) / 2),
				(height / 2) + 62, 0x00000000);
		// drawFreqPopup(i, j);
	}

	private void drawFreqPopup(int i, int j) {
		int freq = this.getFreqMO(i, j);
		String sfreq = this.getFreqString(freq);
		int stringWidth = this.fontRenderer.getStringWidth(sfreq);
		this.drawGradientRect(i, j - 12, i + stringWidth + 3, j, -1073741824,
				-1073741824);
		this.fontRenderer.drawStringWithShadow(sfreq, i + 2, j - 10, -1);
	}

	public int getFreqMO(int i, int j) {
		int width = (i - ((this.width - xSize) / 2 + 8)) / 6;
		int height = (j - ((this.height - ySize) / 2 + 24)) / 6;
		return width >= 0 && width < 40 && height >= 0 && height < 25 ? this
				.getPage() * 1000 + 1 + height * 40 + width : 0;
	}

	public String getFreqString(int freq) {
		return Integer.toString(freq);
	}

	protected boolean getFreqState(String freq) {
		try {
			int i = Integer.parseInt(freq);
			if (i > WirelessRedstone.maxEtherFrequencies)
				return false;
			if (this.activeFreqs[i]) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void setActiveFreqs(String[] activeFreqs) {
		boolean[] newActiveFreqs = new boolean[WirelessRedstone.maxEtherFrequencies];
		int j = 0;
		for (int i = 0; i < WirelessRedstone.maxEtherFrequencies; ++i) {
			if (activeFreqs != null && j < activeFreqs.length
					&& String.valueOf(i).equals(activeFreqs[j])) {
				newActiveFreqs[i] = true;
				++j;
			} else
				newActiveFreqs[i] = false;
		}
		this.activeFreqs = newActiveFreqs;
	}
}
