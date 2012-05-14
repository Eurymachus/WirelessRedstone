package net.minecraft.src.wifi.network;

import java.util.List;

import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.forge.DimensionManager;
import net.minecraft.src.wifi.LoggerRedstoneWireless;
import net.minecraft.src.wifi.RedstoneEther;
import net.minecraft.src.wifi.RedstoneEtherNode;
import net.minecraft.src.wifi.TileEntityRedstoneWireless;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessR;
import net.minecraft.src.wifi.TileEntityRedstoneWirelessT;

public class PacketHandlerRedstoneWireless {
	
	public static void handlePacket(PacketUpdate packet, EntityPlayerMP player) {
		if ( packet instanceof PacketRedstoneEther ) {
			PacketHandlerInput.handleEther((PacketRedstoneEther)packet, player.worldObj, player);
		}
	}
	
	public static class PacketHandlerOutput {
		private static class PacketHandlerOutputSender implements Runnable {
			private int delay;
			private EntityPlayerMP player;
			private PacketUpdate packet;
			
			public PacketHandlerOutputSender(EntityPlayerMP player, PacketUpdate packet, int delay) {
				this(packet,delay);
				this.player = player;
			}
			
			public PacketHandlerOutputSender(PacketUpdate packet, int delay) {
				this.delay = delay;
				this.packet = packet;
			}

			public void send() {
				Thread thr = new Thread(this);
				thr.setName("WirelessRedstonePacketSender."+packet.toString());
				thr.start();
			}
			
			@Override
			public void run() {
				if ( delay > 0 ) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if ( player == null ) {
					World[] worlds = DimensionManager.getWorlds();
					for (int i = 0; i < worlds.length; i++)
					{
						for (int j = 0; j < worlds[i].playerEntities
								.size(); j++)
						{
							EntityPlayerMP player = (EntityPlayerMP)worlds[i].playerEntities.get(j);

							if (Math.abs(player.posX) <= 16
									&& Math.abs(player.posY) <= 16
									&& Math.abs(player.posZ) <= 16)
								player.playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
						}
					}
				} else {
					player.playerNetServerHandler.netManager.addToSendQueue(packet.getPacket());
				}
			}
		}
		
		public static void sendGuiPacketTo(EntityPlayerMP player, TileEntityRedstoneWireless entity, int delay) {
			PacketOpenWindowRedstoneWireless packet = new PacketOpenWindowRedstoneWireless(entity);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendGuiPacketTo:"+player.username, LoggerRedstoneWireless.LogLevel.DEBUG);
			(new PacketHandlerOutputSender(player,packet,delay)).send();
		}

		public static void sendEtherTileToAll(TileEntityRedstoneWireless entity, World world, int delay)
		{
			PacketRedstoneEther packet = prepareTileEntityPacket((TileEntityRedstoneWireless)entity,world);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTileToAll:"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			(new PacketHandlerOutputSender(packet,delay)).send();
		}
		
		public static void sendEtherTileTo(EntityPlayerMP entityplayermp, TileEntityRedstoneWireless entity, World world, int delay) {
			PacketRedstoneEther packet = prepareTileEntityPacket((TileEntityRedstoneWireless)entity,world);
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTileTo:"+entityplayermp.username+":"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			(new PacketHandlerOutputSender(entityplayermp,packet,delay)).send();
		}
		
		public static void sendEtherNodeTileToAll(RedstoneEtherNode node, int delay) {
			World world = ModLoader.getMinecraftServerInstance().getWorldManager(0);
			TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
			if ( entity instanceof TileEntityRedstoneWireless ) {
				sendEtherTileToAll(
						(TileEntityRedstoneWireless)entity,
						world,
						delay
				);
			}
		}
		
		public static void sendEtherTilesTo(EntityPlayerMP entityplayermp, int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTilesTo"+entityplayermp.username, LoggerRedstoneWireless.LogLevel.DEBUG);

			List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
			PacketRedstoneEther packet;
			World world = ModLoader.getMinecraftServerInstance().getWorldManager(0);
			for ( RedstoneEtherNode node: list ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessR ) {
					sendEtherTileTo(
							entityplayermp,
							(TileEntityRedstoneWirelessR)entity,
							world,
							delay
					);
				}
			}
				
			list = RedstoneEther.getInstance().getTXNodes();
			for ( RedstoneEtherNode node: list ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessT ) {
					sendEtherTileTo(
							entityplayermp,
							(TileEntityRedstoneWirelessT)entity,
							world,
							delay
					);
				}
			}
		}
		
		public static void sendEtherTilesToAll(int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherTilesToAll", LoggerRedstoneWireless.LogLevel.DEBUG);

			List<RedstoneEtherNode> list = RedstoneEther.getInstance().getRXNodes();
			PacketRedstoneEther packet;
			World world = ModLoader.getMinecraftServerInstance().getWorldManager(0);
			for ( RedstoneEtherNode node: list ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessR ) {
					sendEtherTileToAll(
							(TileEntityRedstoneWirelessR)entity,
							world,
							delay
					);
				}
			}
				
			list = RedstoneEther.getInstance().getTXNodes();
			for ( RedstoneEtherNode node: list ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessT ) {
					sendEtherTileToAll(
							(TileEntityRedstoneWirelessT)entity,
							world,
							delay
					);
				}
			}
		}
		
		public static void sendEtherFrequencyTilesToAll(List<RedstoneEtherNode> txs, List<RedstoneEtherNode> rxs, int delay) {
			LoggerRedstoneWireless.getInstance("PacketHandlerOutput").write("sendEtherFrequencyTilesToAll", LoggerRedstoneWireless.LogLevel.DEBUG);

			PacketRedstoneEther packet;
			World world = ModLoader.getMinecraftServerInstance().getWorldManager(0);
			for ( RedstoneEtherNode node: rxs ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessR ) {
					sendEtherTileToAll(
							(TileEntityRedstoneWirelessR)entity,
							world,
							delay
					);
				}
			}

			for ( RedstoneEtherNode node: txs ) {
				TileEntity entity = world.getBlockTileEntity(node.i, node.j, node.k);
				if ( entity instanceof TileEntityRedstoneWirelessT ) {
					sendEtherTileToAll(
							(TileEntityRedstoneWirelessT)entity,
							world,
							delay
					);
				}
			}
		}
	}
	
	private static class PacketHandlerInput {
		public static void handleEther(PacketRedstoneEther packet, World world, EntityPlayerMP player)
		{
			LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:"+player.username+":"+packet.toString(), LoggerRedstoneWireless.LogLevel.DEBUG);
			
			if ( packet.getCommand().equals("changeFreq") )
			{
				TileEntity entity = world.getBlockTileEntity(packet.xPosition, packet.yPosition, packet.zPosition);
				if ( entity instanceof TileEntityRedstoneWireless )
				{
					int dFreq = Integer.parseInt(packet.getFreq());
					int oldFreq = Integer.parseInt(((TileEntityRedstoneWireless)entity).getFreq().toString());
					
					((TileEntityRedstoneWireless)entity).setFreq(Integer.toString(oldFreq+dFreq));
					entity.onInventoryChanged();
					world.markBlockNeedsUpdate(packet.xPosition, packet.yPosition, packet.zPosition);
					PacketHandlerOutput.sendEtherTileToAll((TileEntityRedstoneWireless)entity,world,0);
				}
			}
			
			else if ( packet.getCommand().equals("addTransmitter") )
			{
				LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:"+player.username+":"+packet.toString(), LoggerRedstoneWireless.LogLevel.INFO);
				RedstoneEther.getInstance().addTransmitter(world, packet.xPosition, packet.yPosition, packet.zPosition, packet.getFreq());
			}
			
			else if ( packet.getCommand().equals("setTransmitterState") )
			{
				LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:"+player.username+":"+packet.toString(), LoggerRedstoneWireless.LogLevel.INFO);
				RedstoneEther.getInstance().setTransmitterState(world, packet.xPosition, packet.yPosition, packet.zPosition, packet.getFreq(), packet.getState());
			}
			
			else if ( packet.getCommand().equals("remTransmitter") )
			{
				LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:"+player.username+":"+packet.toString(), LoggerRedstoneWireless.LogLevel.INFO);
				RedstoneEther.getInstance().remTransmitter(world, packet.xPosition, packet.yPosition, packet.zPosition, packet.getFreq());
			}
			else
			{
				LoggerRedstoneWireless.getInstance("PacketHandlerInput").write("handleEther:"+player.username+":"+packet.toString()+"UNKNOWN COMMAND", LoggerRedstoneWireless.LogLevel.WARNING);
			}
		}
	}
	
	private static PacketRedstoneEther prepareTileEntityPacket(TileEntityRedstoneWireless entity, World world) {
		PacketRedstoneEther out = new PacketRedstoneEther(entity, world);
		return out;
	}
}
