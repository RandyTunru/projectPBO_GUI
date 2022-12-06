
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private TextField noPol_Masuk;

    private final String[] jenis = {"Mobil", "Motor"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(jenis);
    }

    public void simpan(ActionEvent e){
        Connection c = null;
        Statement stmt = null;

        Info_masuk newKendaraan = new Info_masuk();
        if (noPol_Masuk.getText() == null){
            return;
        }

        if (myChoiceBox.getValue() == null){
            return;
        }

        newKendaraan.noPol = noPol_Masuk.getText();
        newKendaraan.jenis = myChoiceBox.getValue();
        System.out.println(newKendaraan.noPol);
        System.out.println(newKendaraan.jenis);
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt.executeUpdate(String.format("INSERT INTO INFO_MASUK (NOPOL, JENIS) VALUES ('%s', '%s');", newKendaraan.noPol, newKendaraan.jenis));
            stmt.close();
            c.commit();
            c.close();
        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
            return;
        }
        System.out.println("Data dimasukkan");
    }
}
