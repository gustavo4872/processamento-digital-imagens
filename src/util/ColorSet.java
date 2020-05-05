package util;

import java.util.Objects;

public class ColorSet {
	
	public ColorSet(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;		
	}
	
	double r, g, b;

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorSet color = (ColorSet) o;
        return (r == color.r && g == color.g && b == color.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(r+""+g+""+b);
    }
    
    @Override
    public String toString() {    	
    	return "\n{"+(int)(r*255)+", "+(int)(g*255)+", "+(int)(b*255)+"}";
    }
    
}
