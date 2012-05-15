package wifi.network;

import java.util.Arrays;

public class PacketPayload
{
    public int[] intPayload = new int[0];
    public float[] floatPayload = new float[0];
    public String[] stringPayload = new String[0];

    public static Object[] concat(Object[] var0, Object[] var1)
    {
        Object[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static int[] concat(int[] var0, int[] var1)
    {
        int[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public static float[] concat(float[] var0, float[] var1)
    {
        float[] var2 = Arrays.copyOf(var0, var0.length + var1.length);
        System.arraycopy(var1, 0, var2, var0.length, var1.length);
        return var2;
    }

    public PacketPayload() {}

    public PacketPayload(int var1, int var2, int var3)
    {
        this.intPayload = new int[var1];
        this.floatPayload = new float[var2];
        this.stringPayload = new String[var3];
    }

    public void append(PacketPayload var1)
    {
        if (var1 != null)
        {
            if (var1.intPayload.length > 0)
            {
                this.intPayload = concat(this.intPayload, var1.intPayload);
            }

            if (var1.floatPayload.length > 0)
            {
                this.floatPayload = concat(this.floatPayload, var1.floatPayload);
            }

            if (var1.stringPayload.length > 0)
            {
                this.stringPayload = (String[])concat((Object[])this.stringPayload, (Object[])var1.stringPayload);
            }
        }
    }

    public void append(int[] var1)
    {
        if (var1 != null && var1.length >= 0)
        {
            this.intPayload = concat(this.intPayload, var1);
        }
    }

    public void splitTail(IndexInPayload var1)
    {
        PacketPayload var2 = new PacketPayload(this.intPayload.length - var1.intIndex, this.floatPayload.length - var1.floatIndex, this.stringPayload.length - var1.stringIndex);

        if (this.intPayload.length > 0)
        {
            System.arraycopy(this.intPayload, var1.intIndex, var2.intPayload, 0, var2.intPayload.length);
        }

        if (this.floatPayload.length > 0)
        {
            System.arraycopy(this.floatPayload, var1.floatIndex, var2.floatPayload, 0, var2.floatPayload.length);
        }

        if (this.stringPayload.length > 0)
        {
            System.arraycopy(this.stringPayload, var1.stringIndex, var2.stringPayload, 0, var2.stringPayload.length);
        }
    }
}
