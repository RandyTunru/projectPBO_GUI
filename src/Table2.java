import java.sql.Date;
import java.sql.Time;

public class Table2 {
    private String jenis;
    private String noPol;
    private int no_tiket;

    private Date tggl_keluar;
    private Time jam_keluar;

    private int biaya;

    public Table2(String jenis, String noPol, int no_tiket, Date tggl_keluar, Time jam_keluar, int biaya){
        this.jenis = jenis;
        this.noPol = noPol;
        this.no_tiket = no_tiket;
        this.tggl_keluar = tggl_keluar;
        this.jam_keluar = jam_keluar;
        this.biaya = biaya;
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

    public void setTggl_keluar(Date tggl_keluar) {
        this.tggl_keluar = tggl_keluar;
    }

    public Date getTggl_keluar() {
        return tggl_keluar;
    }

    public void setJam_keluar(Time jam_keluar) {
        this.jam_keluar = jam_keluar;
    }

    public Time getJam_keluar() {
        return jam_keluar;
    }

    public void setBiaya(int biaya) {
        this.biaya = biaya;
    }

    public int getBiaya() {
        return biaya;
    }
}
