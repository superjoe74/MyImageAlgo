package imgAlg;

public class MyMatrix {
	
	public static double[][] mult(double[][] a, double[][] b) {
		
		int aRows = a.length;
		int aColumns = a[0].length;
		int bColumns = b[0].length;
		
		double[][] c = new double[aRows][bColumns]; 

		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < bColumns; j++) {
				for (int k = 0; k < aColumns; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		
		
		return c;
	}
	
	public static double[] mult(double[][] a, double[] b) {
		
		int aRows = a.length;
		int bRows = b.length;
		
		double[] c = new double[bRows]; 

		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < bRows; j++) {
				c[i] += a[i][j] * b[j];
			}
		}
		
		
		return c;
	}
}
