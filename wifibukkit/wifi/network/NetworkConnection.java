package wifi.network;

import forge.MessageManager;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import net.minecraft.server.NetServerHandler;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet1Login;

public class NetworkConnection implements INetworkConnections
{
    public void onPacketData(NetworkManager var1, String var2, byte[] var3)
    {
        DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var3));

        try
        {
            NetServerHandler var5 = (NetServerHandler)var1.getNetHandler();
            int var6 = var4.read();

            switch (var6)
            {
                case 0:
                    PacketRedstoneEther var7 = new PacketRedstoneEther();
                    var7.readData(var4);
                    PacketHandlerRedstoneWireless.handlePacket(var7, var5.getPlayerEntity());
            }
        }
        catch (Exception var8)
        {
            var8.printStackTrace();
        }
    }

    public void onConnect(NetworkManager var1) {}

    public void onLogin(NetworkManager var1, Packet1Login var2)
    {
        MessageManager.getInstance().registerChannel(var1, this, "WIFI");
        NetServerHandler var3 = (NetServerHandler)var1.getNetHandler();
        PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherTilesTo(var3.getPlayerEntity(), 2000);
    }

    public void onDisconnect(NetworkManager var1, String var2, Object[] var3) {}
}
