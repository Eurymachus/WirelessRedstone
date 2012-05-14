package wifi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import net.minecraft.server.World;
import wifi.network.PacketHandlerRedstoneWireless;

public class RedstoneEther
{
    private Map ether = new HashMap();
    private int currentWorldHash = 0;
    private static RedstoneEther instance;
    private JFrame gui;

    public static RedstoneEther getInstance()
    {
        if (instance == null)
        {
            instance = new RedstoneEther();
        }

        return instance;
    }

    public void assGui(JFrame var1)
    {
        this.gui = var1;
    }

    public synchronized void addTransmitter(World var1, int var2, int var3, int var4, String var5)
    {
        synchronized (this)
        {
            try
            {
                LoggerRedstoneWireless.getInstance("RedstoneEther").write("addTransmitter(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
                this.checkWorldHash(var1);

                if (!this.freqIsset(var5))
                {
                    this.createFreq(var5);
                }

                RedstoneEtherNode var7 = new RedstoneEtherNode(var2, var3, var4);
                var7.freq = var5;
                ((RedstoneEtherFrequency)this.ether.get(var5)).addTransmitter(var7);
                PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherNodeTileToAll(var7, 0);

                if (this.gui != null)
                {
                    this.gui.repaint();
                }
            }
            catch (Exception var9)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var9);
            }
        }
    }

    public synchronized void remTransmitter(World var1, int var2, int var3, int var4, String var5)
    {
        synchronized (this)
        {
            try
            {
                LoggerRedstoneWireless.getInstance("RedstoneEther").write("remTransmitter(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
                this.checkWorldHash(var1);

                if (this.freqIsset(var5))
                {
                    ((RedstoneEtherFrequency)this.ether.get(var5)).remTransmitter(var1, var2, var3, var4);

                    if (((RedstoneEtherFrequency)this.ether.get(var5)).count() == 0)
                    {
                        this.ether.remove(var5);
                    }
                }

                if (this.gui != null)
                {
                    this.gui.repaint();
                }
            }
            catch (Exception var9)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var9);
            }
        }
    }

    public synchronized void addReceiver(World var1, int var2, int var3, int var4, String var5)
    {
        synchronized (this)
        {
            try
            {
                LoggerRedstoneWireless.getInstance("RedstoneEther").write("addReceiver(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
                this.checkWorldHash(var1);

                if (!this.freqIsset(var5))
                {
                    this.createFreq(var5);
                }

                RedstoneEtherNode var7 = new RedstoneEtherNode(var2, var3, var4);
                var7.freq = var5;
                ((RedstoneEtherFrequency)this.ether.get(var5)).addReceiver(var7);
                PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherNodeTileToAll(var7, 0);

                if (this.gui != null)
                {
                    this.gui.repaint();
                }
            }
            catch (Exception var9)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var9);
            }
        }
    }

    public synchronized void remReceiver(World var1, int var2, int var3, int var4, String var5)
    {
        synchronized (this)
        {
            try
            {
                LoggerRedstoneWireless.getInstance("RedstoneEther").write("remReceiver(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
                this.checkWorldHash(var1);

                if (this.freqIsset(var5))
                {
                    ((RedstoneEtherFrequency)this.ether.get(var5)).remReceiver(var2, var3, var4);

                    if (((RedstoneEtherFrequency)this.ether.get(var5)).count() == 0)
                    {
                        this.ether.remove(var5);
                    }
                }

                if (this.gui != null)
                {
                    this.gui.repaint();
                }
            }
            catch (Exception var9)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var9);
            }
        }
    }

    private void checkWorldHash(World var1)
    {
        if (var1 != null && var1.hashCode() != this.currentWorldHash)
        {
            this.ether = new HashMap();
            this.currentWorldHash = var1.hashCode();
        }

        if (this.gui != null)
        {
            this.gui.repaint();
        }
    }

    private void createFreq(String var1)
    {
        this.ether.put(var1, new RedstoneEtherFrequency());
    }

    private boolean freqIsset(Object var1)
    {
        return this.ether.containsKey(var1);
    }

    public synchronized boolean getFreqState(World var1, String var2)
    {
        synchronized (this)
        {
            return !this.freqIsset(var2) ? false : ((RedstoneEtherFrequency)this.ether.get(var2)).getState(var1);
        }
    }

    public synchronized void setTransmitterState(World var1, int var2, int var3, int var4, String var5, boolean var6)
    {
        synchronized (this)
        {
            try
            {
                LoggerRedstoneWireless.getInstance("RedstoneEther").write("setTransmitterState(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ", " + var6 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);

                if (this.freqIsset(var5))
                {
                    ((RedstoneEtherFrequency)this.ether.get(var5)).setTransmitterState(var1, var2, var3, var4, var6);
                    PacketHandlerRedstoneWireless.PacketHandlerOutput.sendEtherFrequencyTilesToAll((RedstoneEtherFrequency)this.ether.get(var5), WirelessRedstone.stateUpdate);
                }

                if (this.gui != null)
                {
                    this.gui.repaint();
                }
            }
            catch (Exception var10)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var10);
            }
        }
    }

    public synchronized int[] getClosestActiveTransmitter(int var1, int var2, int var3, String var4)
    {
        synchronized (this)
        {
            return this.freqIsset(var4) ? ((RedstoneEtherFrequency)this.ether.get(var4)).getClosestActiveTransmitter(var1, var2, var3) : null;
        }
    }

    public synchronized int[] getClosestTransmitter(int var1, int var2, int var3, String var4)
    {
        synchronized (this)
        {
            return this.freqIsset(var4) ? ((RedstoneEtherFrequency)this.ether.get(var4)).getClosestTransmitter(var1, var2, var3) : null;
        }
    }

    public static float pythagoras(int[] var0, int[] var1)
    {
        double var2 = 0.0D;
        int var4;

        if (var0.length <= var1.length)
        {
            for (var4 = 0; var4 < var0.length; ++var4)
            {
                var2 += Math.pow((double)(var0[var4] - var1[var4]), 2.0D);
            }
        }
        else
        {
            for (var4 = 0; var4 < var1.length; ++var4)
            {
                var2 += Math.pow((double)(var0[var4] - var1[var4]), 2.0D);
            }
        }

        return (float)Math.sqrt(var2);
    }

    public synchronized List getRXNodes()
    {
        synchronized (this)
        {
            LinkedList var2 = new LinkedList();

            try
            {
                HashMap var3 = (HashMap)((HashMap)this.ether).clone();
                Iterator var4 = var3.values().iterator();

                while (var4.hasNext())
                {
                    RedstoneEtherFrequency var5 = (RedstoneEtherFrequency)var4.next();
                    var2.addAll(var5.rxs.values());
                }
            }
            catch (Exception var7)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
            }

            return var2;
        }
    }

    public synchronized List getTXNodes()
    {
        synchronized (this)
        {
            LinkedList var2 = new LinkedList();

            try
            {
                HashMap var3 = (HashMap)((HashMap)this.ether).clone();
                Iterator var4 = var3.values().iterator();

                while (var4.hasNext())
                {
                    RedstoneEtherFrequency var5 = (RedstoneEtherFrequency)var4.next();
                    var2.addAll(var5.txs.values());
                }
            }
            catch (Exception var7)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
            }

            return var2;
        }
    }

    public synchronized Map getLoadedFrequencies()
    {
        synchronized (this)
        {
            HashMap var2 = new HashMap();

            try
            {
                HashMap var3 = (HashMap)((HashMap)this.ether).clone();
                Iterator var4 = var3.keySet().iterator();

                while (var4.hasNext())
                {
                    String var5 = (String)var4.next();
                    var2.put(var5, Integer.valueOf(((RedstoneEtherFrequency)var3.get(var5)).count()));
                }
            }
            catch (Exception var7)
            {
                LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
            }

            return var2;
        }
    }

    public static boolean isBlockLoaded(World var0, int var1, int var2, int var3)
    {
        LoggerRedstoneWireless.getInstance("RedstoneEther").write("isBlockLoaded(world, " + var1 + ", " + var2 + ", " + var3 + "):[" + (var0.getTypeId(var1, var2, var3) != 0) + "&" + (var0.getTileEntity(var1, var2, var3) != null) + "]", LoggerRedstoneWireless.LogLevel.DEBUG);
        return var0.getTypeId(var1, var2, var3) != 0 && var0.getTileEntity(var1, var2, var3) != null;
    }
}
