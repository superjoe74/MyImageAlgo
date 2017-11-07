package imgAlg;

public class MyGeoVector {
	
	private int[] data;
	
	public MyGeoVector() {
		data = new int[3];
	}
	
	public MyGeoVector(int x, int y, int z) {
		data = new int[3];
		data[0] = x;
		data[1] = y;
		data[2] = z;
	}

	public void setData(int i, int d) {
		data[i] = d;
	}

	public int[] getData() {
		return data;
	}
}
