/* Saya Naufal Fakhri Al-Najieb dengan NIM 2309648 mengerjakan Tugas Praktikum 4
dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin. */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

public class Menu extends JFrame{

    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();
        // atur ukuran window
        window.setSize(800, 600);
        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);
        // isi window
        window.setContentPane(window.MainPanel);
        // Ubah warna background
        window.MainPanel.setBackground(new Color(102, 205, 170)); // Warna Tosca)
        // tampilkan window
        window.setVisible(true);
        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;

    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private JPanel MainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JRadioButton Islam;
    private JRadioButton Kristen;
    private JRadioButton Katolik;
    private JRadioButton Hindu;
    private JRadioButton Budha;
    private JRadioButton Khonghucu;
    private ButtonGroup AgamaGroup;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel Agama;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // isi listMahasiswa
        populateList();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(Color.BLUE);

        // atur isi combo box
        jenisKelaminComboBox.addItem("Laki-laki");
        jenisKelaminComboBox.addItem("Perempuan");

        // Atur Isi Radio Button untuk Agama
        //Kelompokkan Kedalam Group Agama
        AgamaGroup = new ButtonGroup();
        AgamaGroup.add(Islam);
        AgamaGroup.add(Kristen);
        AgamaGroup.add(Katolik);
        AgamaGroup.add(Hindu);
        AgamaGroup.add(Budha);
        AgamaGroup.add(Khonghucu);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ambil index baris yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // Pastikan selectedIndex valid
                if (selectedIndex >= 0 && selectedIndex < listMahasiswa.size()) {

                    // ubah selectedIndex menjadi baris tabel yang diklik
                    Mahasiswa mhs = listMahasiswa.get(selectedIndex);

                    // simpan value textfield dan combo box
                    nimField.setText(mhs.getNim());
                    namaField.setText(mhs.getNama());
                    jenisKelaminComboBox.setSelectedItem(mhs.getJenisKelamin());
                    // ubah isi textfield dan combo box
                    jenisKelaminComboBox.setSelectedItem(mhs.getJenisKelamin());

                    // **Setel radio button berdasarkan agama yang tersimpan**
                    switch (mhs.getAgama()) {
                        case "Islam": Islam.setSelected(true); break;
                        case "Kristen": Kristen.setSelected(true); break;
                        case "Katolik": Katolik.setSelected(true); break;
                        case "Hindu": Hindu.setSelected(true); break;
                        case "Budha": Budha.setSelected(true); break;
                        case "Khonghucu": Khonghucu.setSelected(true); break;
                    }

                    // ubah button "Add" menjadi "Update"
                    addUpdateButton.setText("Update");
                    // tampilkan button delete
                    deleteButton.setVisible(true);
                }
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No.", "NIM", "Nama", "Jenis Kelamin", "Agama"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        for (int i = 0; i < listMahasiswa.size(); i++) {
            Object[] row = new Object[5];
            row[0] = i + 1;
            row[1] = listMahasiswa.get(i).getNim();
            row[2] = listMahasiswa.get(i).getNama();
            row[3] = listMahasiswa.get(i).getJenisKelamin();
            row[4] = listMahasiswa.get(i).getAgama();
            temp.addRow(row);
        }
        return temp;
    }

    public void insertData() {
        // ambil value dari textfield dan combo-box
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
        String agama = getSelectedAgama();

        // tambahkan data ke dalam list
        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, agama));

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
    }

    public void updateData() {
        if (selectedIndex >= 0 && selectedIndex < listMahasiswa.size()) {
            // ambil data dari form
            String nim = nimField.getText();
            String nama = namaField.getText();
            String jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
            String agama = getSelectedAgama();

            // ubah data mahasiswa di list
            Mahasiswa mhs = listMahasiswa.get(selectedIndex);
            mhs.setNim(nim);
            mhs.setNama(nama);
            mhs.setJenisKelamin(jenisKelamin);
            mhs.setAgama(agama);

            // update tabel
            mahasiswaTable.setModel(setTable());
            // bersihkan form
            clearForm();
            // feedback
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!");
        }
    }

    public void deleteData() {
        if (selectedIndex >= 0 && selectedIndex < listMahasiswa.size()) {
            // Tampilkan konfirmasi sebelum menghapus data
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin menghapus data ini?",
                    "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            // Jika user memilih "Yes", hapus data
            if (confirm == JOptionPane.YES_OPTION) {
                // hapus data dari list
                listMahasiswa.remove(selectedIndex);
                // update tabel
                mahasiswaTable.setModel(setTable());
                // bersihkan form
                clearForm();
                // feedback
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            }

        }
        else{
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        AgamaGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2311119", "Muhammad Radhi Maulana", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2304879", "Muhammad Alvinza", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2301093", "Marco Henrik Abineno", "Laki-laki", "Kristen"));
        listMahasiswa.add(new Mahasiswa("2301410", "Nuansa Bening Aura Jelita", "Perempuan", "Islam"));
        listMahasiswa.add(new Mahasiswa("2305274", "Zakiyah Hasanah", "Perempuan", "Islam"));
        listMahasiswa.add(new Mahasiswa("2310978", "Haniel Septian Putra Alren", "Laki-laki", "Kristen"));
        listMahasiswa.add(new Mahasiswa("2300330", "Muhammad Fathan Putra", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2304934", "Zaki Adam", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2300484", "Julian Dwi Satrio", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2301102", "Abdurrahman Rauf Budiman", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2312120", "Natasha Adinda Cantika", "Perempuan", "Islam"));
        listMahasiswa.add(new Mahasiswa("2304330", "Hanif Ahmad Syauqi", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2304742", "Muhammad Akhtar Rizki Ramadha", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2304820", "Kasyful Haq Bachariputra", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2310850", "Muhammad Naufal Arbanin", "Laki-laki", "Islam"));
        listMahasiswa.add(new Mahasiswa("2301579", "Jovan Rius Hulus", "Laki-laki", "Kristen"));
        listMahasiswa.add(new Mahasiswa("2309357", "Meisya Amalia", "Perempuan", "Islam"));
    }

    private String getSelectedAgama() {
        if (Islam.isSelected()) {
            return "Islam";
        }
        else if (Kristen.isSelected()) {
            return "Kristen";
        }
        else if (Katolik.isSelected()){
            return "Katolik";
        }
        else if (Hindu.isSelected()) {
            return "Hindu";
        }
        else if (Budha.isSelected()) {
            return "Budha";
        }
        else if (Khonghucu.isSelected()) {
            return "Khonghucu";
        }
        else{
            return "";
        }
    }
}