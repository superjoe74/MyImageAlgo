package imgAlg;

public class Approximator {
	private int colorPercent;
	private int[][] orgColors;
	private int[] newColors;
	private int[] red;
	private int[] green;
	private int[] blue;
	private int distRed;
	private int distGreen;
	private int distBlue;

	public Approximator() {
		// TODO Auto-generated constructor stub
	}

	private void getReducedColors() {
		newColors = new int[(int) (orgColors[0].length * (colorPercent / (double) 100))];
		for (int i = 0; i < newColors.length; i++) {
			newColors[i] = orgColors[0][i];
		}
		copyColorArray();
		getSortedColors();
	}

	private void getSortedColors() {
		quicksortColor(red, 16);
		quicksortColor(green, 8);
		quicksortColor(blue, 0);
	}

	private void copyColorArray() {
		red = new int[newColors.length];
		green = new int[newColors.length];
		blue = new int[newColors.length];

		for (int i = 0; i < newColors.length; i++) {
			red[i] = newColors[i];
			green[i] = newColors[i];
			blue[i] = newColors[i];
		}
	}

	private void quicksortColor(int[] col, int i) {
		quicksortColor_help(col, i, 0, col.length);
	}

	private void quicksortColor_help(int[] col, int i, int left, int right) {
		int mid = (col[(left + right) / 2] >> i) & 0x000000ff;
		int l = left;
		int r = right;
		while (l < r) {
			while (((col[l] >> i) & 0x000000ff) < mid) {
				++l;
			}
			while (((col[l] >> i) & 0x000000ff) > mid) {
				--r;
			}
			if (l <= r)
				swap(col, l++, r--);
		}
		if (left < r)
			quicksortColor_help(col, i, left, r);
		if (right > l)
			quicksortColor_help(col, i, l, right);
	}

	private int searchColor(int col, int[] search) {
		int l = 0;
		int r = search.length - 1;
		int mid = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (search[mid] < col)
				l = mid + 1;
			else
				r = mid - 1;
		}
		return mid;
	}
	
	private double calculateLowestDistance(int col) {
		int iRed = searchColor(col, red);
		int iGreen = searchColor(col, green);
		int iBlue = searchColor(col, blue);
		double dist = 500;
		int[] comparables = {red[iRed], red[iRed+1], green[iGreen], green[iGreen+1], blue[iBlue], blue[iBlue+1]};
		
		for (int i = 0; i < comparables.length; i++) {
			double currDist = Math.sqrt(  (((col >> 16) & 0x000000ff) - ((comparables[i] >> 16) & 0x000000ff)) * (((col >> 16) & 0x000000ff) - ((comparables[i] >> 16) & 0x000000ff))
										+ (((col >> 8) & 0x000000ff) - ((comparables[i] >> 8) & 0x000000ff)) * (((col >> 8) & 0x000000ff) - ((comparables[i] >> 8) & 0x000000ff))
										+ ((col & 0x000000ff) - (comparables[i] & 0x000000ff)) * ((col & 0x000000ff) - (comparables[i] & 0x000000ff))
										);
			if (currDist < dist) {
				dist = currDist;
			}
		}
		return dist;
	}
}
