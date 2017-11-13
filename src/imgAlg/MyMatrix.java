package imgAlg;

public class MyMatrix {
	
	double[][] data;
	
	public MyMatrix(double[][] data) {
		this.data = data;
	}
	
	public MyMatrix mult(MyMatrix b) {
		
		int aRows = data.length;
		int aColumns = data[0].length;
		int bColumns = b.getData()[0].length;
		
		double[][] c = new double[aRows][bColumns]; 

		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < bColumns; j++) {
				for (int k = 0; k < aColumns; k++) {
					c[i][j] += data[i][k] * b.getData()[k][j];
				}
			}
		}
		MyMatrix m = new MyMatrix(c);
		
		return m;
	}
	
	public MyGeoVector mult(MyGeoVector b) {
		
		int aColumns = data.length;
		
		MyGeoVector c = new MyGeoVector(); 

		for (int i = 0; i < aColumns; i++) {
			for (int j = 0; j < b.getData().length; j++) {
				c.setData(i, (int) (c.getData()[i] + (data[i][j]*b.getData()[j])));  
			}
		}
		
		
		return c;
	}
	
	public void printi() {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data.length; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public static MyMatrix getTranslationMatrix(int x, int y) {
		double[][] d = {{1,0,-x},{0,1,-y},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
	
	public static MyMatrix getRotationMatrix(int a) {
		double radian = Math.toRadians(a);
		double[][] d = {{Math.cos(-radian),-Math.sin(-radian),0},{Math.sin(-radian),Math.cos(-radian),0},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
	
	public static MyMatrix getNeutralMatrix() {
		double[][] d = {{1,0,0},{0,1,0},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
	
	public static MyMatrix getScaleMatrix(double x, double y) {
		double[][] d = {{1/x,0,0},{0,1/y,0},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
	
	public static MyMatrix xShearMatrix(double x) {
		double[][] d = {{1,-x,0},{0,1,0},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
	
	public static MyMatrix yShearMatrix(double y) {
		double[][] d = {{1,0,0},{-y,1,0},{0,0,1}};
		MyMatrix m = new MyMatrix(d);
		return m;
	}
}
