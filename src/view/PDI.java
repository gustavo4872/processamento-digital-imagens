package view;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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
	
}
