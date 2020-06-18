package albunsfx;

import albunsfx.db.dal.ArtistaDAL;
import albunsfx.db.dal.GeneroDAL;
import albunsfx.db.dal.MusicaDAL;
import albunsfx.db.entidade.Artista;
import albunsfx.db.entidade.Genero;
import albunsfx.db.entidade.Musica;
import albunsfx.db.util.Banco;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class CadMusicasController implements Initializable {

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
    private TableView<Musica> tabela;
    @FXML
    private JFXTextField txid;
    @FXML
    private JFXTextField txtitulo;
    @FXML
    private JFXTextField txurl;
    @FXML
    private ImageView foto;
    @FXML
    private JFXTextField txduracao;
    @FXML
    private JFXComboBox<Artista> cbartista;
    @FXML
    private JFXComboBox<Genero> cbgenero;
    @FXML
    private HBox pnpesquisa;
    @FXML
    private JFXTextField txbusca;
    @FXML
    private Button btbuscar;
    @FXML
    private TableColumn<Musica, Integer> colid;
    @FXML
    private TableColumn<Musica, String> coltitulo;
    @FXML
    private TableColumn<Musica, String> colartista;
    @FXML
    private TableColumn<Musica, String> colgenero;
    @FXML
    private TableColumn<Musica, Double> colduracao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colid.setCellValueFactory(new PropertyValueFactory("id"));
        coltitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        colartista.setCellValueFactory(new PropertyValueFactory("artista"));
        colgenero.setCellValueFactory(new PropertyValueFactory("genero"));
        colduracao.setCellValueFactory(new PropertyValueFactory("duracao"));
        
        estadoOriginal();
    }
    
    private void estadoOriginal() {
        foto.setImage(new Image("images/music-note.png"));
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
        txtitulo.requestFocus(); //o cursor vai para o txtitulo de titulo
    }
    
    private void carregaTabela(String filtro) {
        MusicaDAL dal = new MusicaDAL();
        List<Musica> res = dal.get(filtro);
        ObservableList<Musica> modelo;
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
        Musica m = tabela.getSelectionModel().getSelectedItem();
        txid.setText("" + m.getId());
        txtitulo.setText(m.getTitulo());
        txurl.setText(m.getUrl());
        txduracao.setText(""+m.getDuracao());
        cbartista.getSelectionModel().select(m.getArtista());
        cbgenero.getSelectionModel().select(m.getGenero());
        foto.setImage(new Image("images/music-note.png"));
        estadoEdicao(); 
    }

    @FXML
    private void clkBtApagar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Confirma a exclusão");
        if (a.showAndWait().get() == ButtonType.OK) {
            MusicaDAL dal = new MusicaDAL();
            Musica m;
            m = tabela.getSelectionModel().getSelectedItem();
            dal.apagar(m);
            carregaTabela("");
        }
    }

    @FXML
    private void clkBtConfirmar(ActionEvent event) {
        int cod;
        double duracao;
        try {
            cod = Integer.parseInt(txid.getText());
        } catch (Exception e) {
            cod = 0;
        }
        try {
            duracao = Double.parseDouble(txduracao.getText());
        } catch (Exception e) {
            duracao = 0;
        }
        Musica m = new Musica(cod, txtitulo.getText(), duracao, txurl.getText(), 
                cbartista.getSelectionModel().getSelectedItem(),cbgenero.getSelectionModel().getSelectedItem());
        MusicaDAL dal = new MusicaDAL();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (m.getId() == 0) // novo cadastro
        {   
            if (!dal.salvar(m)) {
                a.setContentText("Problemas ao Gravar: "+Banco.getConexao().getMensagemErro());
                 a.showAndWait();
            }
        } else //alteração de cadastro
        if (!dal.alterar(m)) {
            a.setContentText("Problemas ao Alterar: "+Banco.getConexao().getMensagemErro());
             a.showAndWait();
        }
       
        estadoOriginal();
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
        carregaTabela("upper(mus_titulo) like '%"+txbusca.getText().toUpperCase()+"%'");
    }
    
    @FXML
    private void clkTabela(MouseEvent event) {
        if (tabela.getSelectionModel().getSelectedIndex() >= 0) {
            btalterar.setDisable(false);
            btapagar.setDisable(false);
        }
    }
    
}
