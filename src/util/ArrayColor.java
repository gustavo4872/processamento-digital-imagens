package util;

import java.util.Arrays;
import javafx.scene.paint.Color;

public class ArrayColor {

	public ArrayColor(int arraySize) {
		red = new double[arraySize];
		green = new double[arraySize];
		blue = new double[arraySize];
		opacity = new double[arraySize];
	}
	
	private double[] red;
	private double[] green;
	private double[] blue;
	private double[] opacity;
	private int size;
	
	public void addColor(Color vizinho) {
		red[size] = vizinho.getRed();
		green[size] = vizinho.getGreen();
		blue[size] = vizinho.getBlue();
		opacity[size] = vizinho.getOpacity();
		size++;
	}

	public void sort() {
		Arrays.sort(red);
		Arrays.sort(blue);
		Arrays.sort(green);
		Arrays.sort(opacity);
	}
	
	public Color getMedian() {
		int x = size / 2;
		return new Color(red[x], green[x], blue[x], opacity[x]);
		
	}
}
