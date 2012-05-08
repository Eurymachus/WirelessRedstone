package net.minecraft.src.wifi.network;

import java.util.Arrays;

public class PacketPayload
{
	public static <T> T[] concat(T[] first, T[] second) {
		  T[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	
	public static int[] concat(int[] first, int[] second) {
		  int[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	
	public static float[] concat(float[] first, float[] second) {
		  float[] result = Arrays.copyOf(first, first.length + second.length);
		  System.arraycopy(second, 0, result, first.length, second.length);
		  return result;
	}
	
	public int[] intPayload = new int[0];
	public float[] floatPayload = new float[0];
	public String[] stringPayload = new String[0];

	public PacketPayload() {}
	public PacketPayload(int intSize, int floatSize, int stringSize) {
		intPayload = new int[intSize];
		floatPayload = new float[floatSize];
		stringPayload = new String[stringSize];
	}

	public void append(PacketPayload other) {
		if(other == null)
			return;

		if(other.intPayload.length > 0)
			this.intPayload = concat(this.intPayload, other.intPayload);
		if(other.floatPayload.length > 0)
			this.floatPayload = concat(this.floatPayload, other.floatPayload);
		if(other.stringPayload.length > 0)
			this.stringPayload = concat(this.stringPayload, other.stringPayload);

	}

	public void append(int[] other) {
		if(other == null || other.length < 0)
			return;

		this.intPayload = concat(this.intPayload, other);
	}

	public void splitTail(IndexInPayload index) {
		PacketPayload payload = new PacketPayload(
				intPayload.length - index.intIndex,
				floatPayload.length - index.floatIndex,
				stringPayload.length - index.stringIndex);

		if(intPayload.length > 0)
			System.arraycopy(intPayload, index.intIndex, payload.intPayload, 0, payload.intPayload.length);
		if(floatPayload.length > 0)
			System.arraycopy(floatPayload, index.floatIndex, payload.floatPayload, 0, payload.floatPayload.length);
		if(stringPayload.length > 0)
			System.arraycopy(stringPayload, index.stringIndex, payload.stringPayload, 0, payload.stringPayload.length);
	}
}
