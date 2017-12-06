package imgAlg;

import java.util.HashMap;

import javax.swing.JDialog;

public class Approximator {
	private int lastColorIndex;
	private int[][] orgColors;
	private int[] red;
	private int[] green;
	private int[] blue;
	private int iRed;
	private int iGreen;
	private int iBlue;
	private int currentCol;
	private HashMap<Integer, Integer> colorMap;
	private double currentDist;

	public Approximator(int[][] colors) {
		orgColors = colors;
		for (int i = 0; i < orgColors[0].length; i++) {
			colorMap.put(orgColors[0][i], orgColors[0][i]);
		}
	}
	
	private void approximate(int color) {
		
	}

	private void getReducedColors() {
		copyColorArray();
		getSortedColors();
	}

	private void getSortedColors() {
		quicksortColor(red, 16);
		quicksortColor(green, 8);
		quicksortColor(blue, 0);
	}

	private void copyColorArray() {
		red = new int[lastColorIndex + 1];
		green = new int[lastColorIndex + 1];
		blue = new int[lastColorIndex + 1];

		for (int i = 0; i < red.length; i++) {
			red[i] = orgColors[0][i];
			green[i] = orgColors[0][i];
			blue[i] = orgColors[0][i];
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
	
	private void swap(int[] array, int i, int j) {
		int tmp0 = array[i];
		array[i] = array[j];
		array[j] = tmp0;
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
	
	private void getClosestColor(int col) {
		iRed = searchColor(col, red);
		iGreen = searchColor(col, green);
		iBlue = searchColor(col, blue);
		double dist = 500;
		int[] comparables = {red[iRed], red[iRed+1], green[iGreen], green[iGreen+1], blue[iBlue], blue[iBlue+1]};
		
		for (int i = 0; i < comparables.length; i++) {
			double currDist = calculateDistance(col, comparables[i]);
			if (currDist < dist) {
				currentCol = comparables[i];
				dist = currDist;
			}
		}
		
		getColorWithLowestDistance(col);
	}
	
	private double calculateDistance(int orgCol, int targCol) {
		return Math.sqrt((((orgCol >> 16) & 0x000000ff) - ((targCol >> 16) & 0x000000ff)) * (((orgCol >> 16) & 0x000000ff) - ((targCol >> 16) & 0x000000ff))
					   + (((orgCol >> 8) & 0x000000ff) - ((targCol >> 8) & 0x000000ff)) * (((orgCol >> 8) & 0x000000ff) - ((targCol >> 8) & 0x000000ff))
				       + ((orgCol & 0x000000ff) - (targCol & 0x000000ff)) * ((orgCol & 0x000000ff) - (targCol & 0x000000ff))
				       );
	}
	
	private void getColorWithLowestDistance(int col) {
		searchColorPartForward(red, iRed, 16, col);
		searchColorPartForward(green, iGreen, 8, col);
		searchColorPartForward(blue, iBlue, 0, col);
		searchColorPartBackward(red, iRed, 16, col);
		searchColorPartBackward(green, iGreen, 8, col);
		searchColorPartBackward(blue, iBlue, 0, col);
	}
	
	private void searchColorPartForward(int[] color, int index, int shift, int colToReplace) {
		int offset = 0;
		while (((index + 1 + offset) < color.length) && (((color[index + 1 + offset++] >> shift) & 0x000000ff)) < (((colToReplace >> 16) & 0x000000ff) + currentDist)) {
			double newDist = calculateDistance(colToReplace, color[index + 1 + offset]);
			if (newDist < currentDist) {
				currentDist = newDist;
				currentCol = color[index + 1 + offset];
			}
		}
	}
	
	private void searchColorPartBackward(int[] color, int index, int shift, int colToReplace) {
		int offset = 0;
		while (((index + 1 + offset) > -1) && (((color[index + 1 + offset--] >> shift) & 0x000000ff)) < (((colToReplace >> 16) & 0x000000ff) + currentDist)) {
			double newDist = calculateDistance(colToReplace, color[index + 1 + offset]);
			if (newDist < currentDist) {
				currentDist = newDist;
				currentCol = color[index + 1 + offset];
			}
		}
	}
	class ApproxAdjuster extends JDialog{
		public ApproxAdjuster() {
			// TODO Auto-generated constructor stub
		}
	}
}
