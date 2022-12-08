
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private TableView<Table1> tableview1;

    @FXML
    private TableView<Table2> tableview2;

    @FXML
    private TableColumn<Table1, String> noPolColumn, jenisColumn;

    @FXML
    private TableColumn<Table1, Integer> noTiketColumn;

    @FXML
    private TableColumn<Table1, Date> tgglMasukColumn;

    @FXML
    private TableColumn<Table1, Time> jamMasukColumn;

    @FXML
    private TableColumn<Table2, String> noPolColumn2, jenisColumn2;

    @FXML
    private TableColumn<Table2, Integer> noTiketColumn2, biayaColumn2;

    @FXML
    private TableColumn<Table2, Date> tgglKeluarColumn2;

    @FXML
    private TableColumn<Table2, Time> jamKeluarColumn2;


    @FXML
    private TextField noPol_Masuk;

    @FXML
    private TextField noPol_Keluar, noTiket, jenisButton, tgglMasuk, jamMasuk, durasiHari, durasiJam, biayaParkir;





    private final String[] jenis = {"Mobil", "Motor"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(jenis);

        noTiketColumn.setCellValueFactory(new PropertyValueFactory<Table1, Integer>("no_tiket"));
        noPolColumn.setCellValueFactory(new PropertyValueFactory<Table1, String>("noPol"));
        jenisColumn.setCellValueFactory(new PropertyValueFactory<Table1, String>("jenis"));
        tgglMasukColumn.setCellValueFactory(new PropertyValueFactory<Table1, Date>("tggl_masuk"));
        jamMasukColumn.setCellValueFactory(new PropertyValueFactory<Table1, Time>("jam_masuk"));

        noTiketColumn2.setCellValueFactory(new PropertyValueFactory<Table2, Integer>("no_tiket"));
        noPolColumn2.setCellValueFactory(new PropertyValueFactory<Table2, String>("noPol"));
        jenisColumn2.setCellValueFactory(new PropertyValueFactory<Table2, String>("jenis"));
        tgglKeluarColumn2.setCellValueFactory(new PropertyValueFactory<Table2, Date>("tggl_keluar"));
        jamKeluarColumn2.setCellValueFactory(new PropertyValueFactory<Table2, Time>("jam_keluar"));
        biayaColumn2.setCellValueFactory(new PropertyValueFactory<Table2, Integer>("biaya"));

        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;

        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFO_MASUK ORDER BY NO_TIKET ASC");
            ObservableList <Table1> info1 = tableview1.getItems();

            while (rs.next()){
                Table1 data1 = new Table1(rs.getString("JENIS"), rs.getString("NOPOL"), rs.getInt("NO_TIKET"), rs.getDate("TGGL_MASUK"), rs.getTime("JAM_MASUK"));
                info1.add(data1);
            }
            tableview1.setItems(info1);

            stmt2 = c.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM INFO_MASUK WHERE BIAYA IS NOT NULL ORDER BY NO_TIKET ASC");
            ObservableList <Table2> info2 = tableview2.getItems();

            while (rs2.next()){
                Table2 data2 = new Table2(rs2.getString("JENIS"), rs2.getString("NOPOL"), rs2.getInt("NO_TIKET"), rs2.getDate("TGGL_KELUAR"), rs2.getTime("JAM_KELUAR"), rs2.getInt("BIAYA"));
                info2.add(data2);
            }
            tableview2.setItems(info2);

        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
            return;
        }
    }

    public void simpan(ActionEvent e){
        Connection c = null;
        Statement stmt = null;

        if (noPol_Masuk.getText().length() == 0 || myChoiceBox.getValue() == null){
            return;
        }

        Info_masuk newKendaraan = new Info_masuk();
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
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFO_MASUK ORDER BY NO_TIKET DESC");
            rs.next();
            ObservableList <Table1> info1 = tableview1.getItems();
            Table1 data1 = new Table1(rs.getString("JENIS"), rs.getString("NOPOL"), rs.getInt("NO_TIKET"), rs.getDate("TGGL_MASUK"), rs.getTime("JAM_MASUK"));
            info1.add(data1);
            tableview1.setItems(info1);
            stmt.close();
            c.commit();
            c.close();
        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
            return;
        }
        System.out.println("Data dimasukkan");
    }


    public void cari(ActionEvent e){
        Connection c = null;
        Statement stmt = null;

        if (noPol_Keluar.getText().length() == 0){
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM INFO_MASUK WHERE NOPOL = '%s' AND BIAYA IS NULL;", noPol_Keluar.getText()));
            rs.next();
            noTiket.setText(String.valueOf(rs.getInt("NO_TIKET")));
            jenisButton.setText(rs.getString("JENIS"));
            tgglMasuk.setText(String.valueOf(rs.getDate("TGGL_MASUK")));
            jamMasuk.setText(String.valueOf(rs.getTime("JAM_MASUK")));
            int[] vars = calcTotalWaktu(rs);
            durasiHari.setText(String.valueOf(vars[0]));
            durasiJam.setText(String.valueOf(vars[1]));
            biayaParkir.setText(String.valueOf(vars[2]));
            stmt.close();
            c.commit();
            c.close();
        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
            return;
        }
    }

    public void keluar(ActionEvent e){
        Connection c = null;
        Statement stmt = null;
        Statement stmt2 = null;
        if (biayaParkir.getText().length() == 0 || noTiket.getText().length() == 0){
            return;
        }

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            stmt2 = c.createStatement();
            stmt.executeUpdate(String.format("UPDATE INFO_MASUK SET TGGL_KELUAR = CURRENT_DATE, JAM_KELUAR = CURRENT_TIME, BIAYA = %d WHERE NO_TIKET = %d;", Integer.parseInt(biayaParkir.getText()), Integer.parseInt(noTiket.getText())));
            ObservableList <Table2> info2 = tableview2.getItems();
            ResultSet rs2 = stmt2.executeQuery(String.format("SELECT * FROM INFO_MASUK WHERE NO_TIKET = %d", Integer.parseInt(noTiket.getText())));
            rs2.next();
            Table2 data2 = new Table2(rs2.getString("JENIS"), rs2.getString("NOPOL"), rs2.getInt("NO_TIKET"), rs2.getDate("TGGL_KELUAR"), rs2.getTime("JAM_KELUAR"), rs2.getInt("BIAYA"));
            info2.add(data2);
            tableview2.setItems(info2);
            stmt.close();
            c.commit();
            c.close();
        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
            return;
        }
    }

//    public void cari2(ActionEvent e){
//        if ()
//
//    }

    public static int[] calcTotalWaktu(ResultSet rs){
        int[] vars = {0 , 0 , 0};
        Connection c = null;
        Statement stmt1 = null;
        Statement stmt2 = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/parkingsystem", "postgres", "@Randy2003");
            c.setAutoCommit(false);
            stmt1 = c.createStatement();
            stmt2 = c.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT CURRENT_DATE");
            rs1.next();
            ResultSet rs2 = stmt2.executeQuery("SELECT CURRENT_TIME");
            rs2.next();
            int tgglMasuk = Integer.parseInt(String.valueOf(rs.getDate("TGGL_MASUK")).substring(8));
            int tgglKeluar = Integer.parseInt(String.valueOf(rs1.getDate("current_date")).substring(8));
            vars[0] = tgglKeluar - tgglMasuk;
            System.out.println(vars[0]);
            String time1 = "18:00:00";
            String time2 = "7:30:50";

            // Creating a SimpleDateFormat object
            // to parse time in the format HH:MM:SS
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("HH:mm:ss");

            // Parsing the Time Period
            Date date1 =  simpleDateFormat.parse(String.valueOf(rs.getTime("JAM_MASUK")));
            Date date2 =  simpleDateFormat.parse(String.valueOf(rs2.getTime("CURRENT_TIME")));

            // Calculating the difference in milliseconds
            long differenceInMilliSeconds
                    = Math.abs(date2.getTime() - date1.getTime());

            // Calculating the difference in Hours
            int differenceInHours
                    = ((int) differenceInMilliSeconds / (60 * 60 * 1000))
                    % 24;
            vars[1] = differenceInHours;
            System.out.println(vars[1]);

            if ((differenceInHours) > 2){
                differenceInHours = 2;
            }
            if (rs.getString("JENIS").equals("Motor")) {
                vars[2] = 3000 + differenceInHours * 2000;
            } else {
                vars[2] = 4000 + differenceInHours * 3000;
            }
            System.out.println(vars[2]);
            stmt1.close();
            stmt2.close();
            c.commit();
            c.close();
        }catch (Exception err){
            System.err.println( err.getClass().getName()+": "+ err.getMessage() );
        }
        return vars;
    }
}
