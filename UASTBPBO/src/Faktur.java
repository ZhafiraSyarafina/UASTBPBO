import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;


//inheritance
public class Faktur extends DataPembelian{

    //koneksi ke database mysql xampp
    Connection con;
    String url = "jdbc:mysql://localhost:3306/tokobantal";
    String username = "root";
    String password = "";
    
    public String Bantal;
    public Integer pilihPesanan;

    
    Date date = new Date();
    String tanggal = String.format("%tF", date);
        

        @Override
        public void jenisPesanan() {
           System.out.print("Inputkan Jenis Pesanan      = ");
           pilihPesanan = input.nextInt();

        //percabangan
           if(pilihPesanan == 1){
               jenisPesanan = "Bantal";
           }

           else if(pilihPesanan == 2){
               jenisPesanan = "Guling";
           }

           else if(pilihPesanan == 3){
               jenisPesanan = "Paket 1 (1 Bantal 1 Guling)";
           }
           
           else if(pilihPesanan == 4){
               jenisPesanan = "Paket 2 (2 Bantal 1 Guling)";
           }

            
        }

    @Override
    public void hargaBantal() {
        //percabangan
        if(pilihPesanan == 1){
            hargaBantal = 150000;
        }
        else if(pilihPesanan == 2){
            hargaBantal = 150000;
        }
        else if(pilihPesanan == 3){
            hargaBantal = 293000;
        }
        else if(pilihPesanan == 4){
            hargaBantal = 430000;
        }
       
    }

    //Pengolahan Database
public void createDatabase() throws SQLException, ClassNotFoundException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);

        System.out.println("===================================================");
        String text = "=============== DATA PEMESANAN BANTAL===============";
        System.out.println(text.toUpperCase());
        System.out.println("=========================================");

        noFaktur();
        tanggal();
        namaPelanggan();
        noHP();
        alamat();
        jenisPesanan();
        hargaBantal();
        jumlahPesanan();
        System.out.println("\n===============RINGKASAN PEMBELIAN===============");
        System.out.println("Jenis Pesanan                 = " + jenisPesanan);
        System.out.println("Harga Bantal                  = " + hargaBantal);
        totalHarga();

        String sql = "INSERT INTO pemesanan (noPemesanan, tanggalPemesanan, namaPelanggan, noHP, alamat, jenisPesanan, hargaBantal, jumlahPesanan, totalHarga) VALUES ('" + noFaktur + "','" + tanggal + "','" + namaPelanggan + "','" + noHP + "','" + alamat + "','" + jenisPesanan + "','" + hargaBantal + "','" + jumlahPesanan + "', '" + totalHarga + "')";

        try (Statement statement = con.createStatement()) {
            statement.execute(sql);
            System.out.println("Data Berhasil Diinputkan!");
        }
    } catch (SQLException e) {
        System.err.println("\nTerjadi kesalahan input data");
        e.printStackTrace();
    } catch (InputMismatchException e) {
        System.err.println("\nInputlah dengan angka saja");
        e.printStackTrace();
    }
}

//pengolahan database 
public void readDatabase() throws SQLException, ClassNotFoundException {

    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection(url, username, password);

    System.out.println("========================================================");
    String text = "===============MENAMPILKAN DATA PEMBELIAN===============";
    System.out.println(text.toUpperCase());
    System.out.println("========================================================");

    String sql = "SELECT * FROM pemesanan";
    try (Statement statement = con.createStatement();
         ResultSet result = statement.executeQuery(sql)) {

        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        String format1 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
        System.out.printf(format1,"No", "No Pemesanan","Tanggal", "Nama Pelanggan"," no HP", "Alamat", "Jenis Pesanan","Harga Bantal","Jumlah Pesanan","Total Harga");
        System.out.println("\n-----------------------------------------------------------------------------------------------------");

        int i = 1;
        while (result.next()){
            String format2 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
            System.out.printf(format2, i, result.getInt("noPemesanan"), result.getDate("tanggalPemesanan"), result.getString("namaPelanggan"),  result.getString("noHP"), result.getString("alamat"), result.getString("jenisPesanan"), result.getInt("hargaBantal"), result.getInt("jumlahPesanan"), result.getInt("totalHarga"));
            System.out.println("\n-----------------------------------------------------------------------------------------------------");

            i++;
        }
    } catch (SQLException e) {
        System.err.println("Terjadi kesalahan saat membaca data");
        e.printStackTrace();
    }
}

//pengolahan database 
public void updateDatabase() throws SQLException, ClassNotFoundException {
    System.out.println("=================================================");
    String text = "===============UBAH DATA PEMBELIAN===============";
    System.out.println(text.toUpperCase());
    System.out.println("=================================================");

    try (Scanner choice = new Scanner(System.in)) {
        try {
            readDatabase();

            System.out.print("Masukkan No Pemesanan yang Akan diubah : ");
            noFaktur = Integer.parseInt(choice.nextLine());

            String sql = "SELECT * FROM pemesanan WHERE noPemesanan = " + noFaktur;

            try (Statement statement = con.createStatement();
                 ResultSet result = statement.executeQuery(sql)) {

                if (result.next()) {
                    System.out.print("Nama Pelanggan [" + result.getString("namaPelanggan") + "]\t: ");
                    namaPelanggan = choice.nextLine();
                    System.out.print("Nomor HP [" + result.getString("noHP") + "]\t: ");
                    noHP = choice.nextLine();
                    System.out.print("Alamat [" + result.getString("alamat") + "]\t: ");
                    alamat = choice.nextLine();

                    // Update data
                    sql = "UPDATE pemesanan SET namaPelanggan='" + namaPelanggan + "', noHP='" + noHP + "', alamat='" + alamat + "' WHERE noPemesanan=" + noFaktur;

                    if (statement.executeUpdate(sql) > 0) {
                        System.out.println("Data Berhasil diperbarui!");
                        statement.close();
                    }
                }
            }
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan Ubah data");
            e.printStackTrace();
            choice.nextLine(); // Mengonsumsi karakter newline
        }

    }
}


//pengolahan database 
public void deleteDatabase() throws SQLException, ClassNotFoundException {
    System.out.println("==================================================");
    String text = "===============HAPUS DATA PEMBELIAN===============";
    System.out.println(text.toUpperCase());
    System.out.println("==================================================");
    
    try (Scanner inputan = new Scanner (System.in)) {
        readDatabase();
        
        //exception
        try{
            
            System.out.print("Ketik nomor pemesanan yang akan dihapus : ");
            noFaktur = Integer.parseInt(inputan.nextLine());
            
            String sql = "DELETE FROM pemesanan WHERE nopemesanan = "+ noFaktur;
            Statement statement = con.createStatement();
            
            if(statement.executeUpdate(sql) > 0){
                System.out.println("Berhasil menghapus data dengan nomor pemesanan ("+noFaktur+")");
                statement.close();
                
            }
        }
        catch(SQLException e){
             System.out.println("Terjadi kesalahan dalam menghapus data barang");
             System.err.println(e.getMessage());
             }
    } catch (NumberFormatException e) {
        e.printStackTrace();
    }
    
}

//pengolahan database 
public void searchDatabase() throws SQLException, ClassNotFoundException {
    System.out.println("=================================================");
    String text = "===============CARI DATA PEMBELIAN===============";
    System.out.println(text.toUpperCase());
    System.out.println("=================================================");

    Class.forName("com.mysql.cj.jdbc.Driver");
    con = DriverManager.getConnection(url, username, password);

    
    try (Scanner inputan = new Scanner (System.in)) {
        System.out.print("Masukkan Nomor Pemesanan : ");
        
        String keyword = inputan.nextLine();
        Statement statement = con.createStatement();
        String sql = "SELECT * FROM pemesanan WHERE nopemesanan LIKE '%"+keyword+"%'";
        ResultSet result = statement.executeQuery(sql);
        
        tanggal();

        //exception
        try{
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        String format1 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
        System.out.printf(format1,"No", "No Pemesanan","Tanggal", "Nama Pelanggan"," no HP", "Alamat", "Jenis Pesanan","Harga Bantal","Jumlah Pesanan","Total Harga");
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
         
        int i=1;
        while(result.next()){
            String format2 = "|%-2s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n";
            System.out.printf(format2, i, result.getInt("noPemesanan"),result.getDate("tanggalPemesanan"), result.getString("namaPelanggan"), result.getString("noHP"), result.getString("alamat"),result.getString("jenisPesanan"),result.getInt("hargaBantal"),result.getInt("jumlahPesanan"),result.getInt("totalHarga"));
            System.out.println("\n-----------------------------------------------------------------------------------------------------");

            i++;
            
        }
        System.out.println("Berhasil mencari data pemesanan"); 
          input.close();    
          statement.close();
        }
        catch(SQLException e){
             System.out.println("Terjadi kesalahan dalam mencari data pemesanan");
             System.err.println(e.getMessage());
        }        inputan.nextLine(); // Mengonsumsi karakter newline
    } catch (NumberFormatException e) {
        e.printStackTrace();
    }
}
    



//pengolahan database 
public void resetDatabase() throws SQLException, ClassNotFoundException {
    System.out.println("==================================================");
    String text = "===============RESET DATA PEMBELIAN===============";
    System.out.println(text.toUpperCase());
    System.out.println("==================================================");
    
    readDatabase();
    
    //exception
    try{
        
        String sql = "DELETE FROM pemesanan";
        Statement statement = con.createStatement();
        
        if(statement.executeUpdate(sql) > 0){
            System.out.println("Berhasil mereset data pemesanan");
        }
    }
    catch(SQLException e){
         System.out.println("Terjadi kesalahan dalam mereset data pemesanan");
         System.err.println(e.getMessage());
         }
    
    }
}


