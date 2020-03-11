package view;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.io.File;

public class PDIController {
	
	@FXML Text labelR;
	@FXML Text labelG;
	@FXML Text labelB;
	
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageView3;
	
	
	private Image img1;
	private Image img2;
	private Image img3;
	
	
	@FXML
	public void abreImagem1() {
		abreImagem(imageView1, img1);
	}
	
	@FXML
	public void abreImagem2() {
		abreImagem(imageView2, img2);
	}
	
	private void atualizaImage3() {
		imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());
	}
	
	private File selecionaImagem() {
		   FileChooser fileChooser = new FileChooser();
		   fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		   fileChooser.setInitialDirectory(new File(
				   "C:/Users/Gusta/Pictures/imgs")); 
		   File imgSelec = fileChooser.showOpenDialog(null);
		   try {
			   if (imgSelec != null) {
			    return imgSelec;
			   }
		   } catch (Exception e) {
			e.printStackTrace();
		   }
		   return null;
	}
	
	private void abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if (f!=null) {
			image = new Image(f.toURI().toString());
			imageView.setImage(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
		}
	}
	
	@FXML
	  public void rasterImg(MouseEvent evt){
		 ImageView iw = (ImageView)evt.getTarget();
		 if(iw.getImage()!=null)
			 verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
	  }
	
	private void verificaCor(Image img, int x, int y){
		  try {
				Color cor = img.getPixelReader().getColor(x-1, y-1);
				labelR.setText("R: "+(int) (cor.getRed()*255));
				labelG.setText("G: "+(int) (cor.getGreen()*255));
				labelB.setText("B: "+(int) (cor.getBlue()*255));
			} catch (Exception e) {
				//e.printStackTrace();
			}
	  }
	
	@FXML
	public void cinzaAritmetica() {
		img3 = PDI.cinzaMediaAritmetica(img1, 0, 0, 0);
		atualizaImage3();
	}
}
