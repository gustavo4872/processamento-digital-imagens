package view;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.sun.corba.se.impl.orbutil.graph.Node;

public class PDIController {
	
	//Cabeçalho
	@FXML Text labelR;
	@FXML Text labelG;
	@FXML Text labelB;
	@FXML Text labelXY;	
	
	//Corpo - Imagens
	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageView3;
	private Image img1;
	private Image img2;
	private Image img3;
	
	//Menu: tons de cinza
	@FXML TextField prctR;
	@FXML TextField prctG;
	@FXML TextField prctB;
	
	//Menu: limiarização
	@FXML Slider slider;
	
	//Menu: adição/subtração
	@FXML Slider sliderImg1;
	@FXML Slider sliderImg2;
	
	//Menu: ruídos
	@FXML ToggleGroup vizinhos;
	@FXML RadioButton vizinhosCruz;
	@FXML RadioButton vizinhosX;
	@FXML RadioButton vizinhos3x3;
	
	@FXML
	public void initialize() {
		vizinhos = new ToggleGroup();
		vizinhosCruz.setToggleGroup(vizinhos);
		vizinhosX.setToggleGroup(vizinhos);
		vizinhos3x3.setToggleGroup(vizinhos);
	}
	
	@FXML
	public void abreImagem1() {
		img1 = abreImagem(imageView1, img1);
	}
	
	@FXML
	public void abreImagem2() {
		img2 = abreImagem(imageView2, img2);
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
				   "D:\\Program Files (x86)\\Eclipse\\workspace\\processamento-digital-imagens\\src\\imgs")); 
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
	
	private Image abreImagem(ImageView imageView, Image image) {
		File f = selecionaImagem();
		if (f!=null) {
			image = new Image(f.toURI().toString());
			imageView.setImage(image);
			imageView.setFitWidth(image.getWidth());
			imageView.setFitHeight(image.getHeight());
			return image;
		}
		return null;
	}
	
	@FXML
	public void rasterImg(MouseEvent evt){
		 ImageView iw = (ImageView)evt.getTarget();
		 if(iw.getImage()!=null) {
			 verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
			 labelXY.setText("X,Y:"+(int)evt.getX()+","+(int)evt.getY());
		 }
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
	public void salvar() {
		if (img3 != null) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png"));
			File file = fileChooser.showSaveDialog(null);
			if (file!=null) {
				BufferedImage bImg = SwingFXUtils.fromFXImage(img3, null);
				try {
					ImageIO.write(bImg, "PNG", file);
				} catch (Exception e) {
					e.printStackTrace();
				}						
			}
		}
	}
	
	@FXML
	public void cinzaAritmetica() {
		img3 = PDI.cinzaMediaAritmetica(imageView1.getImage(), 0, 0, 0);
		atualizaImage3();
	}
	
	@FXML
	public void cinzaPonderada() {
		img3 = PDI.cinzaMediaAritmetica(imageView1.getImage(), 
				Integer.parseInt(prctR.getText()), 
				Integer.parseInt(prctG.getText()), 
				Integer.parseInt(prctB.getText()));
		atualizaImage3();
	}
	
	@FXML
	public void limiarizacao() {		
		img3 = PDI.limiarizacao(imageView1.getImage(), slider.getValue()/255.0);
		atualizaImage3();
	}
	
	@FXML
	public void negativa() {		
		img3 = PDI.negativa(imageView1.getImage());
		atualizaImage3();
	}
	
	@FXML
	public void eliminaRuidos() {	
		img3 = PDI.eliminaRuidos(imageView1.getImage(), ((RadioButton) vizinhos.getSelectedToggle()).getText());
		atualizaImage3();
	}
	
	@FXML
	public void adicao() {
		img3 = PDI.adicao(imageView1.getImage(), imageView2.getImage(), sliderImg1.getValue()/100.0, sliderImg2.getValue()/100.0);
		atualizaImage3();
	}
	
	@FXML
	public void subtracao() {
		img3 = PDI.subtracao(imageView1.getImage(), imageView2.getImage());
		atualizaImage3();
	}
	
	private int x1, y1, x2, y2;
	
	@FXML
	public void onMousePressed(MouseEvent evt){
		 ImageView iw = (ImageView)evt.getTarget();
		 if(iw.getImage()!=null) {
			 x1 = (int)evt.getX();
			 y1 = (int)evt.getY();
		 }
	}
	
	@FXML
	public void onMouseReleased(MouseEvent evt){
		 ImageView iw = (ImageView)evt.getTarget();
		 if(iw.getImage()!=null) {
			 x2 = (int)evt.getX();
			 y2 = (int)evt.getY();
			 img3 = PDI.desafio(iw.getImage(), Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2));
			 atualizaImage3();
		 }
		 
	}
	
	@FXML
	public void abreModalHistograma(ActionEvent event) {
		try {
			Stage stage = new Stage(); 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Histograma.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histograma");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((javafx.scene.Node)event.getSource()).getScene().getWindow());
			stage.show();
			HistogramaController controller = (HistogramaController)loader.getController();
		
			if (img1!=null) {
				PDI.getGrafico(img1, controller.grafico1);				
			}
			if (img2!=null) {
				PDI.getGrafico(img2, controller.grafico2);
			}
			if (img3!=null) {
				PDI.getGrafico(img3, controller.grafico3);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}