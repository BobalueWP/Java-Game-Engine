package engine.main;

import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cache {

	private static CopyOnWriteArrayList<Cache> caches = new CopyOnWriteArrayList<>();
	private BufferedImage image;
	private int[] INTarray;
	private byte[] BYTEarray;
	private int Int;
	private int id;
	private String sid = "";

	public Cache(int id) {
		this.id = id;
	}

	public Cache(String id) {
		this.sid = id;
	}

	public void holdInt(int Int) {
		this.Int = Int;
	}

	public int getInt() {
		return Int;
	}

	public void holdIntArray(int[] array) {
		INTarray = array;
	}

	public int[] getIntArray() {
		return INTarray;
	}

	public void holdImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getIID() {
		return id;
	}

	public String getSID() {
		return sid;
	}

	public void addCache() {
		caches.add(this);
	}

	public static Cache getCache(int id) {
		for (Cache cache : caches) if(cache.getIID() == id) return cache;
		return null;
	}

	public static Cache getCache(String id) {

		for (Cache cache : caches) {
			if(cache.getSID()==null) System.err.println("error on string id");
			if(cache.getSID().contains(id)) 
				return cache;
		}
		return null;
	}

	public void holdByteArray(byte[] array) {
		BYTEarray = array;
	}
	
	public int getID() {
		return id;
	}
}
