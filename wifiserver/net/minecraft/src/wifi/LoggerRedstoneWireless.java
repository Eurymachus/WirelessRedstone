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
package net.minecraft.src.wifi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;

import net.minecraft.src.ModLoader;

public class LoggerRedstoneWireless {
	private static LoggerRedstoneWireless instance;
	private String name;
	private LoggerRedstoneWirelessWriter writer;
	private LogLevel filterLevel;
	
	public static enum LogLevel {
		DEBUG,
		INFO,
		WARNING,
		ERROR
	}
	
	private LoggerRedstoneWireless() {
		name = "WirelessRedstone";
	}
	
	public static LoggerRedstoneWireless getInstance(String name) {
		if ( instance == null ) 
			instance = new LoggerRedstoneWireless();

		instance.name = name;
		
		return instance;
	}
	
	public boolean setFilterLevel(String f) {
		if ( f.equals(LogLevel.DEBUG.name())) {
			filterLevel = LogLevel.DEBUG;
			return true;
		} else if ( f.equals(LogLevel.INFO.name())) {
			filterLevel = LogLevel.INFO;
			return true;
		} else if ( f.equals(LogLevel.WARNING.name())) {
			filterLevel = LogLevel.WARNING;
			return true;
		} else if ( f.equals(LogLevel.ERROR.name())) {
			filterLevel = LogLevel.ERROR;
			return true;
		}
		
		filterLevel = LogLevel.INFO;
		return false;
	}
	
	private boolean filter(LogLevel lvl) {
		if ( filterLevel == LogLevel.DEBUG )
			return true;
		else if ( filterLevel == LogLevel.INFO ) {
			if ( lvl == LogLevel.DEBUG ) return false;
			else return true;
		} else if ( filterLevel == LogLevel.WARNING ) {
			if ( lvl == LogLevel.DEBUG || lvl == LogLevel.INFO ) return false;
			else return true;
		} else if ( filterLevel == LogLevel.ERROR ) {
			if ( lvl == LogLevel.ERROR ) return true;
			else return false;
		} else
			return true;
	}
	

	public void write(String msg, LogLevel lvl) {
		if ( filter(lvl) ) {
			if ( writer == null )
				writer = new LoggerRedstoneWirelessWriter();

			StringBuilder trace = new StringBuilder();
			try {
				throw new Exception();
			} catch(Exception e) {
				StackTraceElement[] c = e.getStackTrace();
				int min = Math.min(3, c.length-1);
				for ( int i = min; i >= 1; i-- ) {
					trace.append(filterClassName(c[i].getClassName())+"."+c[i].getMethodName());
					if ( i > 1 )
						trace.append("->");
				}
			}
			
			writer.write(lvl.name()+": "+name+": "+msg+": "+trace);
		}
	}
	
	public void writeStackTrace(Exception e) {
		if ( writer == null )
			writer = new LoggerRedstoneWirelessWriter();
		
		writer.writeStackTrace(e);
		ModLoader.throwException(e.getMessage(), e);
	}
	
	private class LoggerRedstoneWirelessWriter {
		private File file;
		private FileWriter fstream;
		private PrintWriter out;
		
		public LoggerRedstoneWirelessWriter() {
			try {
				file = new File("wirelessRedstone.srv.log");
				fstream = new FileWriter(file);
				out = new PrintWriter(fstream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void finalize() {
			try {
				out.close();
				fstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void write(String msg) {
			out.write("\n"+System.currentTimeMillis()+":"+msg);
			out.flush();
		}

		public void writeStackTrace(Exception e) {
			out.write("\n"+System.currentTimeMillis()+":");
			out.flush();
			e.printStackTrace(out);
			out.flush();
			e.printStackTrace();
		}
	}
	
	/**
	 * Find the class name from a string.<br>
	 * Returns the string beyond the last period ".".
	 * 
	 * @param name class name
	 * @return Filtered class name.
	 */
	public static String filterClassName(String name) {
		return name.substring(name.lastIndexOf(".")+1);
	}
}
