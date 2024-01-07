import java.text.SimpleDateFormat;
import java.util.Date;             //import untuk menampilkan data tanggal real time
import java.util.Scanner;


//Class DataPembelian yang mengimplementasikan interface DataPemesanan
public class DataPembelian implements DataPemesanan{

    public Integer noFaktur;
    public String namaPelanggan;
    public String noHP;
    public String alamat;
    public String jenisPesanan;
    public Integer hargaBantal;
    public Integer jumlahPesanan;
    public Integer totalHarga;
    public String Bantal;
    public Integer pilihPesanan;

    Scanner input = new Scanner(System.in);

    //constructor no parameter
    public void tanggal()
    {
        Date date = new Date();
        SimpleDateFormat tanggal = new SimpleDateFormat("EEEE, dd MMM yyyy");
        System.out.println("Tanggal Transaksi           = " + tanggal.format(date));
    }

    public void waktu()
    {
        Date time = new Date();    
        SimpleDateFormat waktu = new SimpleDateFormat("HH:mm:ss");  
        System.out.println("Waktu Transaksi             = " + waktu.format(time));
    }
    
    //constructor no parameter
    public DataPembelian(){
        }
    

    @Override
    public void noFaktur() {
        System.out.print("Inputkan No Faktur          = ");
        noFaktur = input.nextInt();
    }

    @Override
    public void namaPelanggan() {
        System.out.print("Inputkan Nama Pelanggan     = ");
        namaPelanggan = input.next();
    }

    @Override
       public void noHP() {
        System.out.print("Inputkan No HP              = ");
        noHP = input.next();
    }

    @Override
        public void alamat() {
        System.out.print("Inputkan Alamat Pelanggan   = ");
        alamat = input.next();
    }


    @Override
    public void jenisPesanan() {
       System.out.print("Inputkan Jenis Bantal        = ");
       jenisPesanan = input.next();
    }

    @Override
    public void hargaBantal() {
        System.out.print("Harga Bantal                = ");
        hargaBantal = input.nextInt();
        
    }

    @Override
    public void jumlahPesanan() {
        System.out.print("Inputkan Jumlah Barang      = ");
        jumlahPesanan = input.nextInt();
    }

    @Override
    public void totalHarga() {
        totalHarga = hargaBantal * jumlahPesanan;
        System.out.println("Total Harga                 = " + totalHarga);
    }
}