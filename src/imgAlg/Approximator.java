package imgAlg;

import java.util.HashMap;

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
	private int lLimit;
	private int rLimit;
	private HashMap<Integer, Integer> colorMap;
	private double currentDist = 500;
	private int currentSquaredDist = 500*500;
	public static int roots;

	public Approximator(int[][] colors) {
		orgColors = colors;
		colorMap = new HashMap<Integer, Integer>();
		resetColormap();
	}
	
	public void approximate() {
		getReducedColors();
		for (int i = lastColorIndex + 1; i < orgColors[0].length; i++) {
			getClosestColor(orgColors[0][i]);
			colorMap.put(orgColors[0][i], currentCol);
		}
	}
	
	public int[][] getOrgColors() {
		return orgColors;
	}

	public void setLastColorIndex(int lastColorIndex) {
		this.lastColorIndex = lastColorIndex;
	}
	
	public int getLastColorIndex() {
		return lastColorIndex;
	}

	public void resetDist() {
		currentDist = 500;
		currentSquaredDist = 500*500;
	}
	
	public void resetColormap() {
		for (int i = 0; i < orgColors[0].length; i++) {
			colorMap.put(orgColors[0][i], orgColors[0][i]);
		}
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

	public HashMap<Integer, Integer> getColorMap() {
		return colorMap;
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
		quicksortColor_help(col, i, 0, col.length-1);
	}

	private void quicksortColor_help(int[] col, int i, int left, int right) {
		int mid = (col[(left + right) / 2] >> i) & 0x000000ff;
		int l = left;
		int r = right;
		while (l < r) {
			while (((col[l] >> i) & 0x000000ff) < mid) {
				++l;
			}
			while (((col[r] >> i) & 0x000000ff) > mid) {
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

	private int searchColor(int col, int[] search, int shift) {
		int l = 0; 
		int r = search.length - 1;
		int mid = 0;
		while (l <= r) {
			mid = (l + r) / 2;
			if (((search[mid] >> shift) & 0xff) < ((col >> shift) & 0xff))
				l = mid + 1;
			else
				r = mid - 1;
		}
		if (mid == search.length - 1) {
			return mid-1;
		}
		else {
			return mid;
		}
	}
	
	private void getClosestColor(int col) { 
		iRed = searchColor(col, red, 16);
		iGreen = searchColor(col, green, 8);
		iBlue = searchColor(col, blue, 0);
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
		++roots; 
		int r = ((orgCol >> 16) & 0xff) - ((targCol >> 16) & 0xff);
		int g = ((orgCol >> 8) & 0xff) - ((targCol >> 8) & 0xff);
		int b = (orgCol & 0xff) - (targCol & 0xff);
		return Math.sqrt(r*r + g*g + b*b);
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
		rLimit = (int) Math.ceil(((colToReplace >> shift) & 0xff) + currentDist);
		lLimit = (int) Math.floor(((colToReplace >> shift) & 0xff) - currentDist);
		
		while (((index + offset) < color.length) && isInRange(color[index + offset], shift)) {
			int newSquaredDist = getSquaredDist(color[index + offset], colToReplace); 
			if (newSquaredDist < currentSquaredDist) {
				currentDist = calculateDistance(colToReplace, color[index + offset]);
				currentCol = color[index + offset];
				currentSquaredDist = newSquaredDist;
				
				rLimit = (int) Math.ceil(((colToReplace >> shift) & 0xff) + currentDist);
				lLimit = (int) Math.floor(((colToReplace >> shift) & 0xff) - currentDist);
			}
			++offset;
		}
	}
	
	private void searchColorPartBackward(int[] color, int index, int shift, int colToReplace) {
		int offset = 0;
		rLimit = (int) Math.ceil(((colToReplace >> shift) & 0xff) + currentDist);
		lLimit = (int) Math.floor(((colToReplace >> shift) & 0xff) - currentDist);
		
		while ((index + offset) >= 0 && isInRange(color[index + offset], shift)) {
			int newSquaredDist = getSquaredDist(color[index + offset], colToReplace); 
			if (newSquaredDist < currentSquaredDist) {
				currentDist = calculateDistance(colToReplace, color[index + offset]);
				currentCol = color[index + offset];
				currentSquaredDist = newSquaredDist;
				
				rLimit = (int) Math.ceil(((colToReplace >> shift) & 0xff) + currentDist);
				lLimit = (int) Math.floor(((colToReplace >> shift) & 0xff) - currentDist);
			}
			--offset;
		}
	}
	
	private int getSquaredDist(int col, int colToReplace) {
		int r = (col >> 16) & 0xff - (colToReplace >> 16) & 0xff;
		int g = (col >> 8) & 0xff - (colToReplace >> 8) & 0xff;
		int b = col & 0xff - colToReplace & 0xff;
		return r*r + g*g + b*b;
	}
	
	private boolean isInRange(int color, int shift) {
		return ((color >> shift) & 0xff) < rLimit && ((color >> shift) & 0xff) > lLimit;
	}
	
}
