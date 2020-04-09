package util;

import java.util.Arrays;
import javafx.scene.paint.Color;

public class ArrayColor {

	public ArrayColor(int arraySize) {
		this.red = new double[arraySize];
		this.green = new double[arraySize];
		this.blue = new double[arraySize];
		this.opacity = new double[arraySize];
	}
	
	private double[] red;
	private double[] green;
	private double[] blue;
	private double[] opacity;
	private int size;
	
	public void addColor(Color vizinho) {		
		this.red[size] = vizinho.getRed();
		this.green[size] = vizinho.getGreen();
		this.blue[size] = vizinho.getBlue();
		this.opacity[size] = vizinho.getOpacity();
		this.size++;		
	}

	public void sort() {
		Arrays.sort(this.red);
		Arrays.sort(this.blue);
		Arrays.sort(this.green);
		Arrays.sort(this.opacity);
	}
	
	public Color getMedian() {
		int x = this.size / 2;
		return new Color(red[x], green[x], blue[x], opacity[x]);		
	}
	
}