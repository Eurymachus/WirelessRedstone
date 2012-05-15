package wifi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

public class ConfigStoreRedstoneWireless
{
    private static ConfigStoreRedstoneWireless instance;
    private Map conf;
    private Properties prop = new Properties();
    private File file = new File("wirelessRedstoneServer.properties");
    private String name = "WirelessRedstone";

    private ConfigStoreRedstoneWireless()
    {
        this.loadProperties();
    }

    public static ConfigStoreRedstoneWireless getInstance(String var0)
    {
        if (!var0.equals("WirelessRedstone"))
        {
            var0 = "WirelessRedstone." + var0;
        }

        if (instance == null)
        {
            instance = new ConfigStoreRedstoneWireless();

            if (!LoggerRedstoneWireless.getInstance(var0).setFilterLevel((String)instance.get("Log.Level", String.class, LoggerRedstoneWireless.LogLevel.INFO.name())))
            {
                LoggerRedstoneWireless.getInstance(var0).write("Unable to parse Log.Level. Valid settings are ERROR,WARNING,INFO,DEBUG.", LoggerRedstoneWireless.LogLevel.WARNING);
            }
        }

        instance.name = var0;
        return instance;
    }

    public Object get(String var1, Class var2, Object var3) throws IllegalArgumentException
    {
        try
        {
            if (this.conf.containsKey(this.name + "." + var1))
            {
                String var4 = (String)this.conf.get(this.name + "." + var1);
                LoggerRedstoneWireless.getInstance(this.name).write(var1 + "=" + var4, LoggerRedstoneWireless.LogLevel.DEBUG);

                if (var2 == Boolean.class)
                {
                    return Boolean.valueOf(Boolean.parseBoolean(var4));
                }
                else if (var2 == Integer.class)
                {
                    return Integer.valueOf(Integer.parseInt(var4));
                }
                else if (var2 == String.class)
                {
                    return var4;
                }
                else if (var2 == Double.class)
                {
                    return Double.valueOf(Double.parseDouble(var4));
                }
                else if (var2 == Character.class)
                {
                    return Character.valueOf(((String)this.conf.get(var4)).toCharArray()[0]);
                }
                else
                {
                    throw new IllegalArgumentException(var2.toString());
                }
            }
            else
            {
                LoggerRedstoneWireless.getInstance(this.name).write(var1 + " not found, restoring to default:" + var3.toString(), LoggerRedstoneWireless.LogLevel.WARNING);
                this.conf.put(this.name + "." + var1, var3.toString());
                this.saveProperties();
                return var3;
            }
        }
        catch (Exception var5)
        {
            LoggerRedstoneWireless.getInstance(this.name).writeStackTrace(var5);
            LoggerRedstoneWireless.getInstance(this.name).write("Problem with " + var1 + ", restoring to default:" + var3.toString(), LoggerRedstoneWireless.LogLevel.WARNING);
            this.conf.put(this.name + "." + var1, var3.toString());
            this.saveProperties();
            return var3;
        }
    }

    private void loadProperties()
    {
        this.conf = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        LoggerRedstoneWireless.getInstance(this.name).write("Loading Properties.", LoggerRedstoneWireless.LogLevel.INFO);
        File var1 = new File(this.file.getName());

        try
        {
            if (var1.canRead())
            {
                Properties var2 = this.prop;

                synchronized (this.prop)
                {
                    this.prop.load(new FileInputStream(var1));
                    Set var3 = this.prop.entrySet();
                    Iterator var4 = var3.iterator();

                    while (var4.hasNext())
                    {
                        Entry var5 = (Entry)var4.next();
                        this.conf.put(var5.getKey().toString(), var5.getValue().toString());
                    }
                }
            }
            else
            {
                if (var1.exists())
                {
                    throw new IOException(this.name + ": Unable to handle Properties file!");
                }

                LoggerRedstoneWireless.getInstance(this.name).write("Properties file not found, creating.", LoggerRedstoneWireless.LogLevel.INFO);

                if (this.saveProperties())
                {
                    ;
                }

                this.loadProperties();
            }
        }
        catch (FileNotFoundException var8)
        {
            LoggerRedstoneWireless.getInstance(this.name).writeStackTrace(var8);
        }
        catch (IOException var9)
        {
            LoggerRedstoneWireless.getInstance(this.name).writeStackTrace(var9);
        }
    }

    private boolean saveProperties()
    {
        File var1 = new File(this.file.getName());

        try
        {
            if (!var1.exists())
            {
                var1.createNewFile();
            }

            if (var1.canWrite())
            {
                Properties var2 = this.prop;

                synchronized (this.prop)
                {
                    this.prop.load(new FileInputStream(var1));
                    LoggerRedstoneWireless.getInstance(this.name).write("Saving Properties.", LoggerRedstoneWireless.LogLevel.INFO);
                    Iterator var3 = this.conf.keySet().iterator();

                    while (var3.hasNext())
                    {
                        String var4 = (String)var3.next();
                        this.prop.setProperty(var4, (String)this.conf.get(var4));
                    }

                    this.prop.store(new FileOutputStream(var1), "WirelessRedstone Properties");
                    return true;
                }
            }

            throw new IOException(this.name + ": Unable to handle Properties file!");
        }
        catch (FileNotFoundException var7)
        {
            LoggerRedstoneWireless.getInstance(this.name).writeStackTrace(var7);
        }
        catch (IOException var8)
        {
            LoggerRedstoneWireless.getInstance(this.name).writeStackTrace(var8);
        }

        return false;
    }
}
