package albunsfx;

import albunsfx.db.dal.AlbumDAL;
import albunsfx.db.dal.AlbumMusicaDAL;
import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.entidade.Album;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.Genero;
import albunsfx.db.entidade.Musica;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CadMusicaAlbumController implements Initializable {

    @FXML
    private Button btconfirmar;
    @FXML
    private Button btcancelar;
    @FXML
    private AnchorPane pndados;
    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txdescricao;
    @FXML
    private ImageView foto;
    @FXML
    private JFXComboBox<Artista> cbartista;
    @FXML
    private JFXComboBox<Genero> cbgenero;
    @FXML
    private JFXTextField txano;
    @FXML
    private JFXTextField txtitulo;
    @FXML
    private JFXTextField txrating;
    @FXML
    private Text txtrating;
    @FXML
    private HBox pnpesquisa;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private Button btnova;
    @FXML
    private TableView<Musica> tabela1;
    @FXML
    private TableColumn<Musica, String> coltitulo1;
    @FXML
    private TableColumn<Musica, String> colartista1;
    @FXML
    private Button btir;
    @FXML
    private Button btvoltar;
    @FXML
    private TableView<Musica> tabela2;
    @FXML
    private TableColumn<Musica, String> coltitulo2;
    @FXML
    private TableColumn<Musica, String> colartista2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        coltitulo1.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista1.setCellValueFactory(new PropertyValueFactory("artista"));
        coltitulo2.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista2.setCellValueFactory(new PropertyValueFactory("artista"));
        
        
        estadoOriginal();
    }
    
    private void estadoOriginal()
    {
        foto.setImage(new Image("images/pasta-de-musica.png"));
        pndados.setDisable(false);
        pnpesquisa.setDisable(false);
        
        btconfirmar.setDisable(false);
        btcancelar.setDisable(false);
        btbuscar.setDisable(false);
        btnova.setDisable(false);
        btir.setDisable(true);
        btvoltar.setDisable(true);
        
        carregarCombos();
        
        Album a = new Album();
        if(PesqAlbumController.getAlbum() != null)
        {
            a = PesqAlbumController.getAlbum();
            txid.setText("" + a.getId());
            txtitulo.setText(a.getTitulo());
            txano.setText("" + a.getAno());
            txdescricao.setText(a.getDescricao());
            txrating.setText("" + a.getRating());
            cbgenero.getSelectionModel().select(0);
            cbgenero.getSelectionModel().select(a.getGenero());
            cbartista.getSelectionModel().select(0);
            cbartista.getSelectionModel().select(a.getArtista());
            foto.setImage(new Image(new AlbumDAL().getFoto(a.getId())));
            
            //musicas que não estão no album
            carregaTabela1("am.al_id<>"+a.getId());
            //apenas músicas do album
            carregaTabela2("am.al_id="+a.getId());
        }
        else
        {
            ObservableList<Node> componentes = pndados.getChildren(); //”limpa” os componentes
            for (Node n : componentes) {
                if (n instanceof TextInputControl) // textfield, textarea e htmleditor
                {
                    ((TextInputControl) n).setText("");
                }
                if (n instanceof ComboBox) {
                    ((ComboBox) n).getItems().clear();
                }
            }
            //musicas que não estão no album
            carregaTabela1("");
            //apenas músicas do album
            //carregaTabela2("");
        }
    }
    
    private void carregaTabela1(String filtro) {
        AlbumMusicaDAL dal = new AlbumMusicaDAL();
        List<Musica> res = dal.get(filtro);
        ObservableList<Musica> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela1.setItems(modelo);
    }
    
    private void carregaTabela2(String filtro) {
        AlbumMusicaDAL dal = new AlbumMusicaDAL();
        List<Musica> res = dal.get(filtro);
        ObservableList<Musica> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela2.setItems(modelo);
    }
    
    private void carregarCombos() {
        ArtistaDAL adal = new ArtistaDAL();
        cbartista.setItems(FXCollections.observableArrayList(adal.get("")));
        
        GeneroDAL gdal = new GeneroDAL();
        cbgenero.setItems(FXCollections.observableArrayList(gdal.get("")));
    }

    @FXML
    private void clkBtConfirmar(MouseEvent event) {
        int cod,ano,rating;
        try {
            cod = Integer.parseInt(txid.getText());
        } catch (Exception e) {
            cod = 0;
        }
        try {
            ano = Integer.parseInt(txano.getText());
        } catch (Exception e) {
            ano = 0;
        }
        try {
            rating = Integer.parseInt(txrating.getText());
        } catch (Exception e) {
            rating = 0;
        }
        Album al = new Album(cod, txtitulo.getText(), ano, txdescricao.getText(), rating,
                cbgenero.getSelectionModel().getSelectedItem(), cbartista.getSelectionModel().getSelectedItem());
        AlbumDAL dal = new AlbumDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (al.getId() == 0) // novo cadastro
        {   
            if (!dal.salvar(al, imageToInputStream(foto.getImage()))) {
                a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                 a.showAndWait();
            }
        } else //alteração de cadastro
        if (!dal.alterar(al, imageToInputStream(foto.getImage()))) {
            a.setContentText("Problemas ao Alterar: "+Banco.getConexao().getMensagemErro());
             a.showAndWait();
        }
        AlbumMusicaDAL amdal = new AlbumMusicaDAL();
        amdal.alterar(al.getId(), tabela1.getItems());
        pndados.getScene().getWindow().hide();
    }
    
    private InputStream imageToInputStream(Image image) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream=null;
        try {
            ImageIO.write(bImage, "jpg", outputStream);
            byte[] res = outputStream.toByteArray();
            inputStream = new ByteArrayInputStream(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    @FXML
    private void clkBtCancelar(MouseEvent event) {
        pndados.getScene().getWindow().hide();
    }

    @FXML
    private void clkFoto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
        File file = fileChooser.showOpenDialog(null);
        foto.setImage(new Image(file.toURI().toString()));
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela1("upper(a.al_titulo) like '%"+txbusca.getText().toUpperCase()+"%' or upper(m.mus_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkBtNova(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CadMusicas.fxml")));
        stage.setScene(scene);
        stage.setTitle("Músicas");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
    }

    @FXML
    private void clkTabela1(MouseEvent event) {
        if (tabela1.getSelectionModel().getSelectedIndex() >= 0) {
            btir.setDisable(false);
        }
    }

    @FXML
    private void clkBtIr(MouseEvent event) {
        tabela2.getItems().add(tabela1.getSelectionModel().getSelectedItem());
        tabela1.getItems().remove(tabela1.getSelectionModel().getSelectedItem());
        
        btir.setDisable(true);
    }

    @FXML
    private void clkBtVoltar(MouseEvent event) {
        
        tabela1.getItems().add(tabela2.getSelectionModel().getSelectedItem());
        tabela2.getItems().remove(tabela2.getSelectionModel().getSelectedItem());
        
        btvoltar.setDisable(true);
    }

    @FXML
    private void clkTabela2(MouseEvent event) {
        if (tabela2.getSelectionModel().getSelectedIndex() >= 0) {
            btvoltar.setDisable(false);
        }
    }
    
}
