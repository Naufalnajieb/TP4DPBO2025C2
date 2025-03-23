/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 4
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private String agama;

    public Mahasiswa(String nim, String nama, String jenisKelamin, String agama) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.agama = agama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getNim() {
        return this.nim;
    }
    public String getNama() {
        return this.nama;
    }
    public String getJenisKelamin() {
        return this.jenisKelamin;
    }
    public String getAgama() {
        return this.agama;
    }
}
