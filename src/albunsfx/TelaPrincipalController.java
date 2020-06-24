package albunsfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TelaPrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clkFechar(ActionEvent event) {
        // perguntar antes
        Platform.exit();
    }

    @FXML
    private void clkCadArtista(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadArtista.fxml")));
        stage.setScene(scene);
        stage.setTitle("Artistas");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    
    @FXML
    private void clkCadGenero(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadGenero.fxml")));
        stage.setScene(scene);
        stage.setTitle("Gêneros");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    
    @FXML
    private void clkCadTipoArtista(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadTipoArtista.fxml")));
        stage.setScene(scene);
        stage.setTitle("Tipos de Artista");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    
    @FXML
    private void clkCadMusicas(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadMusicas.fxml")));
        stage.setScene(scene);
        stage.setTitle("Músicas");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    
    @FXML
    private void clkCadAlbum(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadAlbum.fxml")));
        stage.setScene(scene);
        stage.setTitle("Albuns");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
    
    @FXML
    private void clkPesqAlbum(ActionEvent event) throws IOException {
         //executar a janela de cadastro
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("PesqAlbum.fxml")));
        stage.setScene(scene);
        stage.setTitle("Pesquisa Album");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }
}
