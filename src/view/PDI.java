package view;

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
	
}
