package wifi;

public class RedstoneEtherNode implements Comparable
{
    public int i;
    public int j;
    public int k;
    public boolean state;
    public String freq;
    public long time;

    public RedstoneEtherNode(int var1, int var2, int var3)
    {
        this.i = var1;
        this.j = var2;
        this.k = var3;
        this.state = false;
        this.freq = "0";
        this.time = System.currentTimeMillis();
    }

    public int compareTo(RedstoneEtherNode var1)
    {
        return var1.i == this.i ? (var1.j == this.j ? (var1.k == this.k ? 0 : this.k - var1.k) : this.j - var1.j) : this.i - var1.i;
    }

    public boolean equals(Object var1)
    {
        return !(var1 instanceof RedstoneEtherNode) ? false : ((RedstoneEtherNode)var1).i == this.i && ((RedstoneEtherNode)var1).j == this.j && ((RedstoneEtherNode)var1).k == this.k;
    }

    public String toString()
    {
        return this.time + ":[" + this.freq + "]:(" + this.i + "," + this.j + "," + this.k + "):" + this.state;
    }

    public int compareTo(Object var1)
    {
        return this.compareTo((RedstoneEtherNode)var1);
    }
}
