package albunsfx;

import albunsfx.db.dal.AlbumDAL;
import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.entidade.Album;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.Genero;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

public class CadAlbumController implements Initializable {

    @FXML
    private Button btnovo;
    @FXML
    private Button btalterar;
    @FXML
    private Button btapagar;
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
    private JFXTextField txrating;
    @FXML
    private JFXComboBox<Artista> cbartista;
    @FXML
    private JFXComboBox<Genero> cbgenero;
    @FXML
    private JFXTextField txano;
    @FXML
    private JFXTextField txtitulo;
    @FXML
    private HBox pnpesquisa;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private TableView<Album> tabela;
    @FXML
    private TableColumn<Album, Integer> colid;
    @FXML
    private TableColumn<Album, String> coltitulo;
    @FXML
    private TableColumn<Album, String> colartista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // vincular as colunas da tabela aos beans
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        coltitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista.setCellValueFactory(new PropertyValueFactory("artista"));
        
        // configurar os componentes, preparando para edição/busca/fechamento da janela         
        estadoOriginal();
    }
    
    private void estadoOriginal() {
        foto.setImage(new Image("images/album.png"));
        pnpesquisa.setDisable(false);
        pndados.setDisable(true);

        btconfirmar.setDisable(true);
        btcancelar.setDisable(false);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        btnovo.setDisable(false);

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

        carregaTabela("");
        carregarCombos();
    }
    
    private void estadoEdicao() {     // carregar os componentes da tela (listbox, combobox, ...)
        // p.e. : carregaEstados();
        pnpesquisa.setDisable(true);
        pndados.setDisable(false);
        btconfirmar.setDisable(false);
        btnovo.setDisable(true);
        btapagar.setDisable(true);
        btalterar.setDisable(true);
        txtitulo.requestFocus(); //o cursor vai para o textfield de titulo
    }
    
    private void carregaTabela(String filtro) {
        AlbumDAL dal = new AlbumDAL();
        List<Album> res = dal.get(filtro);
        ObservableList<Album> modelo;
        modelo = FXCollections.observableArrayList(res);
        tabela.setItems(modelo);
    }
    
    private void carregarCombos() {
        ArtistaDAL adal = new ArtistaDAL();
        cbartista.setItems(FXCollections.observableArrayList(adal.get("")));
        
        GeneroDAL gdal = new GeneroDAL();
        cbgenero.setItems(FXCollections.observableArrayList(gdal.get("")));
    }

    @FXML
    private void clkBtNovo(ActionEvent event) {
        estadoEdicao();
    }

    @FXML
    private void clkBtAlterar(ActionEvent event) {
        Album a = tabela.getSelectionModel().getSelectedItem();
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
        estadoEdicao();
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            AlbumDAL dal = new AlbumDAL();
            Album al;
            al = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(al);
            carregaTabela("");
        }
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) {
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
       
        estadoOriginal();
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
    private void clkBtCancelar(ActionEvent event) {
        if(!pndados.isDisabled())  // encontra em estado de edição
            estadoOriginal();
        else
            pndados.getScene().getWindow().hide();
    }

    @FXML
    private void clkBtBuscar(ActionEvent event) {
        carregaTabela("upper(al_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }

    @FXML
    private void clkTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
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
    
}
