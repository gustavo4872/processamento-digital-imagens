package view;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import util.ArrayColor;

public class PDI {
	
	private static final int RED = 0;
	private static final int BLUE = 1;
	private static final int GREEN = 2;
	
	
	public static Image cinzaMediaAritmetica(Image imagem, int pcR, int pcG, int pcB) {

		try {

			int w = (int)imagem.getWidth();
			int h = (int)imagem.getHeight();

			PixelReader pr = imagem.getPixelReader(); //Lê pixel
			WritableImage wi = new WritableImage(w,h); //Cria imagem
			PixelWriter pw = wi.getPixelWriter(); //Modifica pixel

			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color corA = pr.getColor(i,j);
					double media = (corA.getRed()+corA.getGreen()+corA.getBlue())/3;
					if(pcR != 0 || pcG != 0 || pcB != 0)
						media = (corA.getRed()*pcR + corA.getGreen()*pcG +corA.getBlue()*pcB)/100;
					Color corN = new Color(media, media, media, corA.getOpacity());
					pw.setColor(i, j, corN);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Image limiarizacao(Image imagem, double limiar) {

		try {

			int w = (int) imagem.getWidth();
			int h = (int) imagem.getHeight();

			PixelReader pr = imagem.getPixelReader(); //Lê pixel
			WritableImage wi = new WritableImage(w,h); //Cria imagem
			PixelWriter pw = wi.getPixelWriter(); //Modifica pixel

			Color corA;
			Color corN;

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {

					corA = pr.getColor(i, j);

					if ((corA.getRed()) >= limiar) { //CorA.getRed retorna um valor de 0 a 1, enquanto limiar é um valor de 0 a 255
						corN = new Color(1, 1, 1, corA.getOpacity());
					}else {
						corN = new Color(0, 0, 0, corA.getOpacity());
					}

					pw.setColor(i, j, corN);
				}
			}

			return wi;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Image negativa(Image imagem) {

		try {

			int w = (int) imagem.getWidth();
			int h = (int) imagem.getHeight();

			PixelReader pr = imagem.getPixelReader(); //Lê pixel
			WritableImage wi = new WritableImage(w,h); //Cria imagem
			PixelWriter pw = wi.getPixelWriter(); //Modifica pixel

			Color corA;
			Color corN;

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {

					corA = pr.getColor(i, j);
					corN = new Color(
							1 - corA.getRed(),
							1 - corA.getGreen(), 
							1 - corA.getBlue(), 
							corA.getOpacity());					
					pw.setColor(i, j, corN);
				}
			}

			return wi;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Image eliminaRuidos(Image imagem, String buttonId) {
	
		try {

			int w = (int) imagem.getWidth();
			int h = (int) imagem.getHeight();

			PixelReader pr = imagem.getPixelReader(); //Lê pixel
			WritableImage wi = new WritableImage(w,h); //Cria imagem
			PixelWriter pw = wi.getPixelWriter(); //Modifica pixel
			
			w--;
			h--;
			
			ArrayColor vizinhos;
			
			if (buttonId.equals("Vizinhos Cruz")) {
				for (int x = 1; x < w; x++) {
					for (int y = 1; y < h; y++) {
						vizinhos = new ArrayColor(5);
						vizinhos.addColor(pr.getColor(x-1, y));
						vizinhos.addColor(pr.getColor(x, y-1));
						vizinhos.addColor(pr.getColor(x, y));
						vizinhos.addColor(pr.getColor(x, y+1));						
						vizinhos.addColor(pr.getColor(x+1, y));						
						vizinhos.sort();					
						pw.setColor(x, y, vizinhos.getMedian());
						
					}	
				}					
										
			}else if (buttonId.equals("Vizinhos X")) {
				for (int x = 1; x < w; x++) {
					for (int y = 1; y < h; y++) {
						vizinhos = new ArrayColor(5);
						vizinhos.addColor(pr.getColor(x-1, y-1));
						vizinhos.addColor(pr.getColor(x-1, y+1));
						vizinhos.addColor(pr.getColor(x, y));
						vizinhos.addColor(pr.getColor(x+1, y-1));
						vizinhos.addColor(pr.getColor(x+1, y+1));
						vizinhos.sort();					
						pw.setColor(x, y, vizinhos.getMedian());
					}	
				}								
			}else {
				for (int x = 1; x < w; x++) {
					for (int y = 1; y < h; y++) {
						vizinhos = new ArrayColor(9);
						vizinhos.addColor(pr.getColor(x-1, y-1));
						vizinhos.addColor(pr.getColor(x-1, y));
						vizinhos.addColor(pr.getColor(x-1, y+1));
						vizinhos.addColor(pr.getColor(x, y-1));
						vizinhos.addColor(pr.getColor(x, y));
						vizinhos.addColor(pr.getColor(x, y+1));
						vizinhos.addColor(pr.getColor(x+1, y-1));
						vizinhos.addColor(pr.getColor(x+1, y));
						vizinhos.addColor(pr.getColor(x+1, y+1));
						vizinhos.sort();					
						pw.setColor(x, y, vizinhos.getMedian());
					}	
				}												
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}
	
	public static Image adicao(Image imagem1, Image imagem2, double prcnt1, double prcnt2) {
		
		try {

			int w1 = (int)imagem1.getWidth();
			int h1 = (int)imagem1.getHeight();
			
			int w2 = (int)imagem2.getWidth();
			int h2 = (int)imagem2.getHeight();			
			
			int w = Math.min(w1, w2);
			int h = Math.min(h1, h2);
			
			PixelReader pr1 = imagem1.getPixelReader();
			PixelReader pr2 = imagem2.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					
					Color corImg1 = pr1.getColor(i, j);
					Color corImg2 = pr2.getColor(i, j);
					
					double r = (corImg1.getRed()*prcnt1)+(corImg2.getRed()*prcnt2);
					double g = (corImg1.getGreen()*prcnt1)+(corImg2.getGreen()*prcnt2);
					double b = (corImg1.getBlue()*prcnt1)+(corImg2.getBlue()*prcnt2);
					
					r = r > 1 ? 1 : r;
					g = g > 1 ? 1 : g;
					b = b > 1 ? 1 : b;
					
					Color newColor = new Color(r, g, b, corImg1.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image subtracao(Image imagem1, Image imagem2) {

		try {

			int w1 = (int)imagem1.getWidth();
			int h1 = (int)imagem1.getHeight();
			
			int w2 = (int)imagem2.getWidth();
			int h2 = (int)imagem2.getHeight();			
			
			int w = Math.min(w1, w2);
			int h = Math.min(h1, h2);
			
			PixelReader pr1 = imagem1.getPixelReader();
			PixelReader pr2 = imagem2.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					
					Color corImg1 = pr1.getColor(i, j);
					Color corImg2 = pr2.getColor(i, j);
					
					double r = (corImg1.getRed())-(corImg2.getRed());
					double g = (corImg1.getGreen())-(corImg2.getGreen());
					double b = (corImg1.getBlue())-(corImg2.getBlue());
					
					r = r < 0 ? 0 : r;
					g = g < 0 ? 0 : g;
					b = b < 0 ? 0 : b;
					
					Color newColor = new Color(r, g, b, corImg1.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Image desafio(Image imagem1, int minX, int minY, int maxX, int maxY) {
		
		try {

			int w = (int)imagem1.getWidth();
			int h = (int)imagem1.getHeight();
		
			PixelReader pr = imagem1.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {					
					Color corImg = pr.getColor(i, j);									
					pw.setColor(i, j, corImg);
				}
			}
			
			Color corSquare = new Color(0, 0, 0, 1);
			
			for (int i = minX; i < maxX; i++) {				
				pw.setColor(i, minY, corSquare);
				pw.setColor(i, maxY, corSquare);
			}
			
			for (int i = minY; i < maxY; i++) {				
				pw.setColor(minX, i, corSquare);
				pw.setColor(maxX, i, corSquare);
			}
						
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void getGrafico(Image imagem, BarChart<String, Number> grafico) {
		int [] histograma = histogramaUnico(imagem);
		XYChart.Series vlr = new XYChart.Series();
		for (int i = 0; i < histograma.length; i++) {
			vlr.getData().add(new XYChart.Data(i+"",histograma[i]));
		}
		grafico.getData().addAll(vlr);
		
		for(Node n :grafico.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: red;");
		}

	}

	private static int[] histograma(Image imagem, int op) {		
		
		int[] qt = new int[256];
		PixelReader pr = imagem.getPixelReader();

		int w = (int)imagem.getWidth();
		int h = (int)imagem.getHeight();

		if (op == RED) {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				}
			}
		}else if (op == GREEN) {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
				}
			}
		}else if (op == BLUE) {
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
				}
			}
		}
		return qt;		
	}
	
	private static int[] histogramaUnico(Image imagem) {		
		int[] qt = new int[256];
		PixelReader pr = imagem.getPixelReader();

		int w = (int)imagem.getWidth();
		int h = (int)imagem.getHeight();

		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				qt[(int)(pr.getColor(i, j).getRed()*255)]++;
				qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
				qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
			}
		}	
		return qt;		
	}
	
	private static int[] histogramaAc(int[] histograma) {
		int total = 0;
		int[] histogramaAc = new int[histograma.length];		
		for (int i = 0; i < histogramaAc.length; i++) {			
			histogramaAc[i] = total + histograma[i];
			total = histogramaAc[i];
		}
		return histogramaAc;
	}

	public static Image equalizacaoHistograma(Image imagem, boolean todos) {
		
		int w = (int)imagem.getWidth();
		int h = (int)imagem.getHeight();
	
		PixelReader pr = imagem.getPixelReader();
		WritableImage wi = new WritableImage(w,h);
		PixelWriter pw = wi.getPixelWriter();

		int[] hR = histograma(imagem, RED);
		int[] hG = histograma(imagem, GREEN);
		int[] hB = histograma(imagem, BLUE);
		
		int[] histAcR = histogramaAc(hR);
		int[] histAcG = histogramaAc(hG);
		int[] histAcB = histogramaAc(hB);
		
		int qtTonsRed = 255;
		int qtTonsGreen = 255;
		int qtTonsBlue = 255;
		
		double minR = 0; 
		double minG = 0;
		double minB = 0;
		
		if (!todos) {
			qtTonsRed = qtTons(hR);
			qtTonsGreen = qtTons(hG);
			qtTonsBlue = qtTons(hB);
			
			minR = pontoMin(hR); 
			minG = pontoMin(hG);
			minB = pontoMin(hB);
		}
		
		double n = w*h;
		
		for (int i = 1; i < w; i++) {
			for (int j = 1; j < h; j++) {
				Color oldColor = pr.getColor(i, j);
				
				double acR = histAcR[(int)(oldColor.getRed()*255)];
				double acG = histAcG[(int)(oldColor.getGreen()*255)];
				double acB = histAcB[(int)(oldColor.getBlue()*255)];
				
				double pxR = ((qtTonsRed-1)/n)*acR;
				double pxG = ((qtTonsGreen-1)/n)*acG;
				double pxB = ((qtTonsBlue-1)/n)*acB;
				
				double corR = (minR+pxR)/255;
				double corG = (minG+pxB)/255;
				double corB = (minB+pxG)/255;
				//System.out.println(minG + " " + pxB);
				Color newColor = new Color(corR, corG, corB, oldColor.getOpacity());
				pw.setColor(i, j, newColor);
			}
		}
		return wi;
	}
	
	private static int qtTons(int[] histograma) {
		int qt = 255;
		for (int i = 0; i < histograma.length; i++) {
			if (histograma[i] == 0) {
				qt--;				
			}
		}
		return qt;
	}
	
	private static int pontoMin(int[] histograma) {
		for (int i = 0; i < histograma.length; i++) {
			if (histograma[i]>0) {
				return i;
			}
		}
		return 0;
	}
	
	public static void identificarCores(int x1, int y1, int x2, int y2, Image image) {
		
		PixelReader pr = image.getPixelReader();
		Set<String> cores = new HashSet<String>();
		
		for (int i = x1; i < x2; i++) {
			for (int j = y1; j < y2; j++) {
				Color color = pr.getColor(i, j);		
				cores.add((int)(color.getRed()*255)+","+(int)(color.getGreen()*255)+","+(int)(color.getBlue()*255));
			}
		}
		JOptionPane.showMessageDialog(null, cores.size() + " cor(es) encontrada(s)!");
	}
	
	public static Image grade(Image image, int distancia) {
		
		try {

			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {					
					Color corImg = pr.getColor(i, j);									
					pw.setColor(i, j, corImg);
				}
			}
			
			Color corN = new Color(255/255, 0/255, 0/255, 1);
			
			int sizeGrade = distancia*7;
			
			if (Math.min(sizeGrade, w) == sizeGrade) {
				int begginX = (w - sizeGrade)/2;				
				for (int i = begginX; i <= w - begginX; i= i + distancia) {
					for (int j = 0; j < h; j++) {
						pw.setColor(i-1, j, corN);
						pw.setColor(i, j, corN);
						pw.setColor(i+1, j, corN);
					}
				}
				
				return wi;
			}
			JOptionPane.showMessageDialog(null, "Distância inválida!");
			return wi;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image efeitoZebrado(Image image, int colunas) {
		
		try {

			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();

			int tamanhoColuna = (int) (Math.ceil((float)w/(float)colunas));			
			int aux = tamanhoColuna;
			boolean zebrado = true;
			System.out.println(tamanhoColuna);
			Color corA;
			Color corN;
			
			for(int i=0; i<w; i++) {
				
				if (i>aux) {					
					aux += tamanhoColuna;
					zebrado = !zebrado;
				}
				
				if (zebrado) {
					for (int j = 0; j < h; j++) {
						corA = pr.getColor(i, j);
						double media = (corA.getRed() + corA.getGreen() + corA.getBlue())/3;
						corN = new Color(media, media, media, corA.getOpacity());
						pw.setColor(i, j, corN);
					}
				}else {
					for (int j = 0; j < h; j++) {						 
						corA = pr.getColor(i, j);
						pw.setColor(i, j, corA);
					}
				}				
			}
			
			return wi;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
