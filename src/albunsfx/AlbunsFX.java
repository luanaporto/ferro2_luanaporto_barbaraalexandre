package albunsfx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import albunsfx.db.util.Banco;

public class AlbunsFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("AlbunsFX");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        if(Banco.conectar())
           launch(args);
        else
        {
            JOptionPane.showMessageDialog(null, 
                    "Problemas ao conectar: "+Banco.getConexao().getMensagemErro());
        }
    }
    
}
