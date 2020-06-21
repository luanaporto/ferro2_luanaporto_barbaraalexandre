package albunsfx;

import albunsfx.db.dal.AlbumDAL;
import albunsfx.db.entidade.Album;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PesqAlbumController implements Initializable {

    @FXML
    private HBox pnpesquisa;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;
    @FXML
    private TableView<Album> tabela;
    @FXML
    private TableColumn<Album, Integer> colid;
    @FXML
    private TableColumn<Album, String> coltitulo;
    @FXML
    private TableColumn<Album, String> colartista;
    @FXML
    private TableColumn<Album, Integer> colrating;
    
    private static Album album = null;

    public static Album getAlbum() {
        return album;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        coltitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista.setCellValueFactory(new PropertyValueFactory("artista"));
        colrating.setCellValueFactory(new PropertyValueFactory("rating"));
        
        estadoOriginal();
    }
    
    private void estadoOriginal() {
        pnpesquisa.setDisable(false);
        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btbuscar.setDisable(true);
        txbusca.requestFocus();

        carregaTabela("");
    }
    
    private void carregaTabela(String filtro) {
        AlbumDAL dal = new AlbumDAL();
        List<Album> res = dal.get(filtro);
        ObservableList<Album> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper(al.al_titulo) like '%"+txbusca.getText().toUpperCase()+"%' or upper(art.art_nome) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkCadAlbumMusica(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadMusicaAlbum.fxml")));
        stage.setScene(scene);
        stage.setTitle("Album Música");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }

    @FXML
    private void clkBtCancelar(ActionEvent event) {
        pnpesquisa.getScene().getWindow().hide();
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btconfirmar.setDisable(false);
            album = new Album();
            AlbumDAL dal = new AlbumDAL();
            album = dal.get(tabela.getSelectionModel().getSelectedIndex());
        }
    }
    
}
