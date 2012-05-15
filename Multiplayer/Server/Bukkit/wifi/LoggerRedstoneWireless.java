package wifi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import net.minecraft.server.ModLoader;

public class LoggerRedstoneWireless
{
    private static LoggerRedstoneWireless instance;
    private String name = "WirelessRedstone";
    private LoggerRedstoneWireless.LoggerRedstoneWirelessWriter writer;
    private LoggerRedstoneWireless.LogLevel filterLevel;

    public static LoggerRedstoneWireless getInstance(String var0)
    {
        if (instance == null)
        {
            instance = new LoggerRedstoneWireless();
        }

        instance.name = var0;
        return instance;
    }

    public boolean setFilterLevel(String var1)
    {
        if (var1.equals(LoggerRedstoneWireless.LogLevel.DEBUG.name()))
        {
            this.filterLevel = LoggerRedstoneWireless.LogLevel.DEBUG;
            return true;
        }
        else if (var1.equals(LoggerRedstoneWireless.LogLevel.INFO.name()))
        {
            this.filterLevel = LoggerRedstoneWireless.LogLevel.INFO;
            return true;
        }
        else if (var1.equals(LoggerRedstoneWireless.LogLevel.WARNING.name()))
        {
            this.filterLevel = LoggerRedstoneWireless.LogLevel.WARNING;
            return true;
        }
        else if (var1.equals(LoggerRedstoneWireless.LogLevel.ERROR.name()))
        {
            this.filterLevel = LoggerRedstoneWireless.LogLevel.ERROR;
            return true;
        }
        else
        {
            this.filterLevel = LoggerRedstoneWireless.LogLevel.INFO;
            return false;
        }
    }

    private boolean filter(LoggerRedstoneWireless.LogLevel var1)
    {
        return this.filterLevel == LoggerRedstoneWireless.LogLevel.DEBUG ? true : (this.filterLevel == LoggerRedstoneWireless.LogLevel.INFO ? var1 != LoggerRedstoneWireless.LogLevel.DEBUG : (this.filterLevel == LoggerRedstoneWireless.LogLevel.WARNING ? var1 != LoggerRedstoneWireless.LogLevel.DEBUG && var1 != LoggerRedstoneWireless.LogLevel.INFO : (this.filterLevel == LoggerRedstoneWireless.LogLevel.ERROR ? var1 == LoggerRedstoneWireless.LogLevel.ERROR : true)));
    }

    public void write(String var1, LoggerRedstoneWireless.LogLevel var2)
    {
        if (this.filter(var2))
        {
            if (this.writer == null)
            {
                this.writer = new LoggerRedstoneWireless.LoggerRedstoneWirelessWriter();
            }

            this.writer.write(var2.name() + ": " + this.name + ": " + var1);
        }
    }

    public void writeStackTrace(Exception var1)
    {
        if (this.writer == null)
        {
            this.writer = new LoggerRedstoneWireless.LoggerRedstoneWirelessWriter();
        }

        this.writer.writeStackTrace(var1);
        ModLoader.throwException(var1.getMessage(), var1);
    }

    private class LoggerRedstoneWirelessWriter
    {
        private File file;
        private FileWriter fstream;
        private PrintWriter out;

        public LoggerRedstoneWirelessWriter()
        {
            try
            {
                this.file = new File("wirelessRedstone.srv.log");
                this.fstream = new FileWriter(this.file);
                this.out = new PrintWriter(this.fstream);
            }
            catch (IOException var3)
            {
                var3.printStackTrace();
            }
        }

        public void finalize()
        {
            try
            {
                this.out.close();
                this.fstream.close();
            }
            catch (IOException var2)
            {
                var2.printStackTrace();
            }
        }

        public void write(String var1)
        {
            this.out.write("\n" + System.currentTimeMillis() + ":" + var1);
            this.out.flush();
        }

        public void writeStackTrace(Exception var1)
        {
            this.out.write("\n" + System.currentTimeMillis() + ":");
            this.out.flush();
            var1.printStackTrace(this.out);
            this.out.flush();
            var1.printStackTrace();
        }
    }

    public static enum LogLevel
    {
        DEBUG("DEBUG", 0),
        INFO("INFO", 1),
        WARNING("WARNING", 2),
        ERROR("ERROR", 3);

        private static final LoggerRedstoneWireless.LogLevel[] $VALUES = new LoggerRedstoneWireless.LogLevel[]{DEBUG, INFO, WARNING, ERROR};

        private LogLevel(String var1, int var2) {}
    }
}
