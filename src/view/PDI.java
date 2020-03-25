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
			
			for (int i = 1; i < w; i++) {
				for (int j = 1; j < h; j++) {
					
					double opacity = pr.getColor(i, j).getOpacity();
					
					if (buttonId.equals("Vizinhos Cruz")) {
						vizinhos = vizinhosCruz(imagem, pr, i, j, new ArrayColor(5));						
					}else if (buttonId.equals("Vizinhos X")) {
						vizinhos = vizinhosX(imagem, pr, i, j, new ArrayColor(5));								
					}else {
						vizinhos = vizinhos3x3(imagem, pr, i, j, new ArrayColor(9));												
					}
					
					vizinhos.sort();					
					pw.setColor(i, j, vizinhos.getMiddleColor(opacity));
					
				}
			}
			
			return wi;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		
	}
	
	private static ArrayColor vizinhosCruz(Image imagem, PixelReader pr, int x, int y, ArrayColor newArrayColor) {		
		newArrayColor.addColor(0, pr.getColor(x, y));
		newArrayColor.addColor(1, pr.getColor(x, y+1));
		newArrayColor.addColor(2, pr.getColor(x, y-1));
		newArrayColor.addColor(3, pr.getColor(x+1, y));
		newArrayColor.addColor(4, pr.getColor(x-1, y));
		return newArrayColor;
	}
	
	private static ArrayColor vizinhosX(Image imagem, PixelReader pr, int x, int y, ArrayColor newArrayColor) {
		newArrayColor.addColor(0, pr.getColor(x, y));
		newArrayColor.addColor(1, pr.getColor(x-1, y-1));
		newArrayColor.addColor(2, pr.getColor(x-1, y+1));
		newArrayColor.addColor(3, pr.getColor(x+1, y-1));
		newArrayColor.addColor(4, pr.getColor(x+1, y+1));
		return newArrayColor;
	}
	
	private static ArrayColor vizinhos3x3(Image imagem, PixelReader pr, int x, int y, ArrayColor newArrayColor){		
		newArrayColor.addColor(0, pr.getColor(x-1, y-1));
		newArrayColor.addColor(1, pr.getColor(x-1, y));
		newArrayColor.addColor(2, pr.getColor(x-1, y+1));
		newArrayColor.addColor(3, pr.getColor(x, y-1));
		newArrayColor.addColor(4, pr.getColor(x, y));
		newArrayColor.addColor(5, pr.getColor(x, y+1));
		newArrayColor.addColor(6, pr.getColor(x+1, y-1));
		newArrayColor.addColor(7, pr.getColor(x+1, y));
		newArrayColor.addColor(8, pr.getColor(x+1, y+1));
		return newArrayColor;
	}
	
}
