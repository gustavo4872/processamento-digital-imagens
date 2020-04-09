package view;

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

	public static void getGrafico(Image imagem, BarChart<String, Number> grafico) {
		int [] histograma = histograma(imagem);
		XYChart.Series vlr = new XYChart.Series();
		for (int i = 0; i < histograma.length; i++) {
			vlr.getData().add(new XYChart.Data(i+"",histograma[i]));
		}
		grafico.getData().addAll(vlr);
		
		for(Node n :grafico.lookupAll(".default-color0.chart-bar")) {
			n.setStyle("-fx-bar-fill: red;");
		}

	}
	
	private static int[] histograma(Image imagem) {
		
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
}
