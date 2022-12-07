import java.sql.Date;
import java.sql.Time;

public class Table1 {
    private String jenis;
    private String noPol;
    private int no_tiket;
    private Date tggl_masuk;
    private Time jam_masuk;

    public Table1(String jenis, String noPol, int no_tiket, Date tggl_masuk, Time jam_masuk){
        this.jenis = jenis;
        this.noPol = noPol;
        this.no_tiket = no_tiket;
        this.tggl_masuk = tggl_masuk;
        this.jam_masuk = jam_masuk;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenis(){
        return jenis;
    }

    public void setNoPol(String noPol) {
        this.noPol = noPol;
    }

    public String getNoPol() {
        return noPol;
    }

    public void setNo_tiket(int no_tiket) {
        this.no_tiket = no_tiket;
    }

    public int getNo_tiket() {
        return no_tiket;
    }

    public void setTggl_masuk(Date tggl_masuk) {
        this.tggl_masuk = tggl_masuk;
    }

    public Date getTggl_masuk() {
        return tggl_masuk;
    }

    public void setJam_masuk(Time jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public Time getJam_masuk() {
        return jam_masuk;
    }
}
