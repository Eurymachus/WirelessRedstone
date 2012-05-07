package wifi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import net.minecraft.server.World;

public class RedstoneEtherFrequency
{
    public Map txs = new TreeMap();
    private RedstoneEtherFrequency.ReadWriteLock txLock = new RedstoneEtherFrequency.ReadWriteLock((RedstoneEtherFrequency.NamelessClass577352161)null);
    public Map rxs = new TreeMap();
    private RedstoneEtherFrequency.ReadWriteLock rxLock = new RedstoneEtherFrequency.ReadWriteLock((RedstoneEtherFrequency.NamelessClass577352161)null);

    public boolean getState(World var1)
    {
        boolean var2 = false;

        try
        {
            LinkedList var3 = new LinkedList();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("getState(world)", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.txLock.readLock();
            Iterator var4 = this.txs.values().iterator();
            RedstoneEtherNode var5;

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();

                if (!RedstoneEther.isBlockLoaded(var1, var5.i, var5.j, var5.k))
                {
                    LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("getState(world): " + var5 + " not loaded. Removing", LoggerRedstoneWireless.LogLevel.WARNING);
                    var3.add(var5);
                }
                else if (!var2 && var5.state)
                {
                    var2 = true;
                    break;
                }
            }

            this.txLock.readUnlock();
            var4 = var3.iterator();

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                this.remTransmitter(var1, var5.i, var5.j, var5.k);
            }
        }
        catch (InterruptedException var6)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var6);
        }

        return var2;
    }

    public void setTransmitterState(World var1, int var2, int var3, int var4, boolean var5)
    {
        try
        {
            if (!this.txs.containsKey(new RedstoneEtherNode(var2, var3, var4)))
            {
                return;
            }

            this.txLock.readLock();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("setTransmitterState(world, " + var2 + ", " + var3 + ", " + var4 + ", " + var5 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
            ((RedstoneEtherNode)this.txs.get(new RedstoneEtherNode(var2, var3, var4))).state = var5;
            this.txLock.readUnlock();
            this.updateReceivers(var1);
        }
        catch (InterruptedException var7)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var7);
        }
    }

    public void addTransmitter(RedstoneEtherNode var1)
    {
        try
        {
            this.txLock.writeLock();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("addTransmitter(" + var1.toString() + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.txs.put(var1, var1);
            this.txLock.writeUnlock();
        }
        catch (InterruptedException var3)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var3);
        }
    }

    public void addReceiver(RedstoneEtherNode var1)
    {
        try
        {
            this.rxLock.writeLock();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("addTransmitter(" + var1.toString() + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.rxs.put(var1, var1);
            this.rxLock.writeUnlock();
        }
        catch (InterruptedException var3)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var3);
        }
    }

    public void remTransmitter(World var1, int var2, int var3, int var4)
    {
        try
        {
            if (!this.txs.containsKey(new RedstoneEtherNode(var2, var3, var4)))
            {
                return;
            }

            this.txLock.writeLock();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("remTransmitter(world, " + var2 + ", " + var3 + ", " + var4 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.txs.remove(new RedstoneEtherNode(var2, var3, var4));
            this.txLock.writeUnlock();
            this.updateReceivers(var1);
        }
        catch (InterruptedException var6)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var6);
        }
    }

    public void remReceiver(int var1, int var2, int var3)
    {
        try
        {
            if (!this.rxs.containsKey(new RedstoneEtherNode(var1, var2, var3)))
            {
                return;
            }

            this.rxLock.writeLock();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("remReceiver(" + var1 + ", " + var2 + ", " + var3 + ")", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.rxs.remove(new RedstoneEtherNode(var1, var2, var3));
            this.rxLock.writeUnlock();
        }
        catch (InterruptedException var5)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var5);
        }
    }

    private void updateReceivers(World var1)
    {
        try
        {
            LinkedList var2 = new LinkedList();
            new LinkedList();
            LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("updateReceivers(world)", LoggerRedstoneWireless.LogLevel.DEBUG);
            this.rxLock.readLock();
            Iterator var4 = this.rxs.values().iterator();
            RedstoneEtherNode var5;

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();

                if (!RedstoneEther.isBlockLoaded(var1, var5.i, var5.j, var5.k))
                {
                    LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("updateReceivers(world): " + var5 + " not loaded. Removing", LoggerRedstoneWireless.LogLevel.WARNING);
                    var2.add(var5);
                }
                else
                {
                    WirelessRedstone.blockWirelessR.a(var1, var5.i, var5.j, var5.k, (Random)null);
                }
            }

            this.rxLock.readUnlock();
            var4 = var2.iterator();

            while (var4.hasNext())
            {
                var5 = (RedstoneEtherNode)var4.next();
                this.remReceiver(var5.i, var5.j, var5.k);
            }
        }
        catch (InterruptedException var6)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var6);
        }
    }

    public int count()
    {
        return this.rxs.size() + this.txs.size();
    }

    public int[] getClosestActiveTransmitter(int var1, int var2, int var3)
    {
        try
        {
            int[] var4 = new int[3];
            int[] var5 = new int[] {var1, var2, var3};
            int[] var6 = new int[3];
            boolean var7 = true;
            float var8 = 0.0F;
            this.txLock.readLock();
            Iterator var9 = this.txs.values().iterator();

            while (var9.hasNext())
            {
                RedstoneEtherNode var10 = (RedstoneEtherNode)var9.next();

                if (var10.state)
                {
                    if (var7)
                    {
                        var4 = new int[] {var10.i, var10.j, var10.k};
                        var8 = RedstoneEther.pythagoras(var5, var4);
                        var7 = false;
                    }
                    else
                    {
                        var6[0] = var10.i;
                        var6[1] = var10.j;
                        var6[2] = var10.k;

                        if (var8 > RedstoneEther.pythagoras(var5, var6))
                        {
                            var4[0] = var10.i;
                            var4[1] = var10.j;
                            var4[2] = var10.k;
                        }
                    }
                }
            }

            this.txLock.readUnlock();

            if (var7)
            {
                return null;
            }
            else
            {
                return var4;
            }
        }
        catch (InterruptedException var11)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var11);
            return null;
        }
    }

    public int[] getClosestTransmitter(int var1, int var2, int var3)
    {
        try
        {
            int[] var4 = new int[3];
            int[] var5 = new int[] {var1, var2, var3};
            int[] var6 = new int[3];
            boolean var7 = true;
            float var8 = 0.0F;
            this.txLock.readLock();
            Iterator var9 = this.txs.values().iterator();

            while (var9.hasNext())
            {
                RedstoneEtherNode var10 = (RedstoneEtherNode)var9.next();

                if (var7)
                {
                    var4 = new int[] {var10.i, var10.j, var10.k};
                    var8 = RedstoneEther.pythagoras(var5, var4);
                    var7 = false;
                }
                else
                {
                    var6[0] = var10.i;
                    var6[1] = var10.j;
                    var6[2] = var10.k;

                    if (var8 > RedstoneEther.pythagoras(var5, var6))
                    {
                        var4[0] = var10.i;
                        var4[1] = var10.j;
                        var4[2] = var10.k;
                    }
                }
            }

            this.txLock.readUnlock();

            if (var7)
            {
                return null;
            }
            else
            {
                return var4;
            }
        }
        catch (InterruptedException var11)
        {
            LoggerRedstoneWireless.getInstance("WirelessRedstone: " + this.getClass().toString()).writeStackTrace(var11);
            return null;
        }
    }

    private class ReadWriteLock
    {
        private int readers;
        private int writers;
        private int writeReq;

        private ReadWriteLock()
        {
            this.readers = 0;
            this.writers = 0;
            this.writeReq = 0;
        }

        public synchronized void readLock() throws InterruptedException
        {
            while (this.writers > 0 || this.writeReq > 0)
            {
                LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("readLock", LoggerRedstoneWireless.LogLevel.INFO);
                this.wait();
            }

            ++this.readers;
        }

        public synchronized void readUnlock()
        {
            --this.readers;
            this.notifyAll();
        }

        public synchronized void writeLock() throws InterruptedException
        {
            ++this.writeReq;

            while (this.readers > 0 || this.writers > 0)
            {
                LoggerRedstoneWireless.getInstance("RedstoneEtherFrequency").write("writeLock", LoggerRedstoneWireless.LogLevel.INFO);
                this.wait();
            }

            ++this.writers;
            --this.writeReq;
        }

        public synchronized void writeUnlock()
        {
            --this.writers;
            this.notifyAll();
        }

        ReadWriteLock(RedstoneEtherFrequency.NamelessClass577352161 var2)
        {
            this();
        }
    }

    static class NamelessClass577352161
    {
    }
}
