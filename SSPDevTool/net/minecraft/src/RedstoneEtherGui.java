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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.minecraft.src.wirelessredstone.RedstoneEther;
import net.minecraft.src.wirelessredstone.RedstoneEtherNode;
import net.minecraft.src.wirelessredstone.WirelessRedstone;

public class RedstoneEtherGui extends JFrame {
	private static final long serialVersionUID = 1L;
	private RedstoneEtherGuiPanel etherPanel;
	private RedstoneEtherNodeGuiPanel nodePanel;

	public RedstoneEtherGui() {
		setTitle("Ether GUI");
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(500,600));
		etherPanel = new RedstoneEtherGuiPanel();
		panel.add(etherPanel);
		nodePanel = new RedstoneEtherNodeGuiPanel();
		panel.add(nodePanel);
		
		etherPanel.assNodePanel(nodePanel);
		
		this.add(panel);
		pack();
	}
	

	
	private class RedstoneEtherGuiPanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		private Map<Point[],RedstoneEtherNode> bounds;
		private RedstoneEtherNodeGuiPanel nodePanel;

		public RedstoneEtherGuiPanel() {
			setPreferredSize(new Dimension(500,200));
			addMouseListener(this);
			setBackground(Color.white);
			setBorder(BorderFactory.createLineBorder(Color.black));
			bounds = new HashMap<Point[],RedstoneEtherNode>();
		}
		
		public void assNodePanel(RedstoneEtherNodeGuiPanel nodePanel) {
			this.nodePanel = nodePanel;
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			drawActiveFrequencies(g2);
			drawNodes(g2);
		}
		
		private void drawActiveFrequencies(Graphics2D g2) {
			Map<String,Integer> list = RedstoneEther.getInstance().getLoadedFrequencies();
			g2.drawString("Active Freqs:", 5, 20);
			int n = 3;
			for ( String i : list.keySet() ) {
				 if ( RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,i) )
						g2.setColor(Color.GREEN);
					else
						g2.setColor(Color.RED);
				 g2.drawString(i+": "+list.get(i), 5, n*10);
				 n++;
			}
			g2.setColor(Color.BLACK);
		}
		
		private void drawNodes(Graphics2D g2) {
			List<RedstoneEtherNode> rxs;
			List<RedstoneEtherNode> txs;

			synchronized(RedstoneEther.getInstance()) {
				rxs = RedstoneEther.getInstance().getRXNodes();
				txs = RedstoneEther.getInstance().getTXNodes();
			}
			bounds = new HashMap<Point[],RedstoneEtherNode>();
			
			g2.drawString("RXes:", 100, 20);
			int n = 3;
			for ( RedstoneEtherNode node : rxs ) {
				if ( RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,node.freq) )
					g2.setColor(Color.GREEN);
				else
					g2.setColor(Color.RED);
				g2.drawString("("+node.i+","+node.j+","+node.k+"):["+node.freq+"]", 100, n*10);
				
				Point[] bound = {new Point(100, (n-1)*10), new Point(299,(n-1)*10+9)};
				bounds.put(bound, node);
				
				n++;
			}
			g2.setColor(Color.BLACK);

			g2.drawString("TXes:", 300, 20);
			n = 3;
			for ( RedstoneEtherNode node : txs ) {
				if ( node.state )
					g2.setColor(Color.GREEN);
				else
					g2.setColor(Color.RED);
				
				g2.drawString("("+node.i+","+node.j+","+node.k+"):["+node.freq+"]", 300, n*10);
				
				Point[] bound = {new Point(300, (n-1)*10), new Point(500,(n-1)*10+9)};
				bounds.put(bound, node);
				
				n++;
			}
			g2.setColor(Color.BLACK);
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if ( nodePanel == null ) return;
			
			for ( Point[] bound : bounds.keySet() ) {
				if ( arg0.getPoint().x > bound[0].x && arg0.getPoint().x < bound[1].x && arg0.getPoint().y > bound[0].y && arg0.getPoint().y < bound[1].y ) {
					RedstoneEtherNode node = bounds.get(bound);
					if ( RedstoneEther.getInstance().getRXNodes().indexOf(node) > -1 )
						nodePanel.setSelectedNode(node,1);
					else
						nodePanel.setSelectedNode(node,2);
					return;
				}
			}

			nodePanel.setSelectedNode(null,0);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) { }

		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mousePressed(MouseEvent arg0) { }

		@Override
		public void mouseReleased(MouseEvent arg0) { }
	}
	
	private class RedstoneEtherNodeGuiPanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		private RedstoneEtherNode selectedNode;
		private int selectedNodeType;

		public RedstoneEtherNodeGuiPanel() {
			setPreferredSize(new Dimension(500,400));
			addMouseListener(this);
			setBackground(Color.white);
			setBorder(BorderFactory.createLineBorder(Color.black));
		}
		
		public void setSelectedNode(RedstoneEtherNode node, int type) {
			this.selectedNode = node;
			selectedNodeType = type;
			this.repaint();
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			drawNode(g2);
		}
		
		public void drawNode(Graphics2D g2) {
			if ( selectedNode != null ) {
				g2.drawString("Node: ("+selectedNode.i+","+selectedNode.j+","+selectedNode.k+")", 10, 20);
				
				if ( selectedNodeType == 1 ) 
					g2.drawString("Type: Receiver", 20, 60);
				else
					g2.drawString("Type: Transmitter", 20, 60);
				
				g2.drawString("Frequency: "+selectedNode.freq, 20, 80);
				
				DateFormat formatter = new SimpleDateFormat("hh:mm:ss.SSS");
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(selectedNode.time);
				g2.drawString("Timestamp: "+formatter.format(calendar.getTime()), 20, 100);

				if ( selectedNodeType == 2 ) 
					g2.drawString("Node State: "+selectedNode.state, 20, 120);
				
				g2.drawString("Frequency State: "+RedstoneEther.getInstance().getFreqState(ModLoader.getMinecraftInstance().theWorld,selectedNode.freq), 20, 140);
				
				g2.drawString("Block ID: "+ModLoader.getMinecraftInstance().theWorld.getBlockId(selectedNode.i, selectedNode.j, selectedNode.k), 20, 160);
				
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillRect(20, 200, 50, 20);
				g2.setColor(Color.BLACK);
				g2.drawString("Delete", 20, 215);
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if ( arg0.getPoint().x > 20 && arg0.getPoint().x < 70 && arg0.getPoint().y > 200 && arg0.getPoint().y < 220 ) {
				if ( selectedNodeType == 1) {
					RedstoneEther.getInstance().remReceiver(
							ModLoader.getMinecraftInstance().theWorld, 
							selectedNode.i, 
							selectedNode.j, 
							selectedNode.k, 
							selectedNode.freq
					);
					WirelessRedstone.blockWirelessR.dropBlockAsItem(
							ModLoader.getMinecraftInstance().theWorld, 
							selectedNode.i, 
							selectedNode.j, 
							selectedNode.k, 
							1,
							ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(selectedNode.i, selectedNode.j, selectedNode.k)
					);
				} else if ( selectedNodeType == 2 ) {
					RedstoneEther.getInstance().remTransmitter(
							ModLoader.getMinecraftInstance().theWorld, 
							selectedNode.i, 
							selectedNode.j, 
							selectedNode.k, 
							selectedNode.freq
					);
					WirelessRedstone.blockWirelessT.dropBlockAsItem(
							ModLoader.getMinecraftInstance().theWorld, 
							selectedNode.i, 
							selectedNode.j, 
							selectedNode.k, 
							1,
							ModLoader.getMinecraftInstance().theWorld.getBlockMetadata(selectedNode.i, selectedNode.j, selectedNode.k)
					);
				}
				
				ModLoader.getMinecraftInstance().theWorld.setBlockWithNotify(
						selectedNode.i, 
						selectedNode.j, 
						selectedNode.k, 
						0
				);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) { }

		@Override
		public void mouseExited(MouseEvent arg0) { }

		@Override
		public void mousePressed(MouseEvent arg0) { }

		@Override
		public void mouseReleased(MouseEvent arg0) { }
	}
}
