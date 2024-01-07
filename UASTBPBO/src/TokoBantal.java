import java.util.HashMap;      //import untuk menampilkan data hashmap yang digunakan untuk menampilkan menu
import java.util.Scanner;      //import untuk menginputkan data 
import java.sql.*;             //import untuk menghubungkan program dengan database
import java.util.Date;         //import untuk menampilkan data tanggal real time

// merupakan driver class
public class TokoBantal {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private static final String CAPTCHA = "VisualStudioCode";

    static Connection con;        //Untuk menyambungkan dengan database dibuat static
    static Date date = new Date();


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner inputan = new Scanner(System.in);
        boolean loginSuccessful = false;
        boolean inputValid = false;
        String choice;

        Faktur transaksi = new Faktur();

        String url = "jdbc:mysql://localhost:3306/tokobantal";
        String username = "root";
        String password = "";

        do {
            System.out.println("|===================== LOGIN =====================|");
            if (login(inputan)) {
                String generatedCaptcha = generateCaptcha();
                System.out.println("Captcha: " + generatedCaptcha);

                if (validateCaptcha(inputan, generatedCaptcha)) {
                    loginSuccessful = true;
                } else {
                    System.out.println("Captcha salah. Silakan login kembali.");
                }
            } else {
                System.out.println("Username atau password salah. Silakan login kembali.");
            }

        } while (!loginSuccessful);
        
                System.out.println("\nNama Mahasiswa      =  Zhafira Syarafina");
                System.out.println("NIM Mahasiswa       =  2211523016");
                System.out.println("Jurusan Mahasiswa   =  Sistem Informasi");
                System.out.println("Fakultas Mahasiswa  =  Teknologi Informasi");

                //exception
        //koneksi database
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Class Driver ditemukan");
        }
        catch(ClassNotFoundException ex) {
            System.err.println("Driver Error");
            System.exit(0);
            }
                
        catch(SQLException e){
            System.out.println("Tidak berhasil koneksi");
            }


        do {
                    System.out.println("|========================================================|");
                    System.out.println("|                    TOKO BANTAL FIRA                    |");
                    System.out.println("|========================================================|");
                    System.out.println("|            Tgl: "+date+"           |");
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                    System.out.println("|                          *MENU*                        |");
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
                    System.out.println("|               1. Pemesanan Bantal                      |");
                    System.out.println("|               2. Menapilkan Data Pesanan               |");
                    System.out.println("|               3. Update Data Pesanan                   |");
                    System.out.println("|               4. Hapus Data Pesanan                    |");
                    System.out.println("|               5. Cari Data Pesanan                     |");
                    System.out.println("|               6. Hapus Seluruh Data Pesanan            |"); 
                    System.out.println("|               7. Keluar                                |");
                    System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");         
                    System.out.print("                  Pilih menu (1-7):");


                    choice = inputan.next();
                    switch (choice) {
                        case "1":
                            HashMap<String, Double> daftarBarang = new HashMap<>();
                            daftarBarang.put("Bantal", 150000.0);
                            daftarBarang.put("Guling", 150000.0);
                            daftarBarang.put("Paket 1 (1 Bantal 1 Guling)", 293000.0);
                            daftarBarang.put("Paket 2 (2 Bantal 1 Guling)", 430000.0);
                            
                            System.out.println("=======================================================================================================");
                            System.out.println("\nDaftar Pilihan Bantal \t : " + daftarBarang.entrySet());
                            System.out.println("Ukuran Hashmap \t\t : " + daftarBarang.size());
                            System.out.println("=======================================================================================================");
                            System.out.println("\n");
                            transaksi.createDatabase();
                            break;

                        case "2":
                            transaksi.readDatabase();
                            break;

                        case "3":
                            transaksi.updateDatabase();
                            break;

                        case "4":
                            transaksi.deleteDatabase();
                            break;

                        case "5" :
                            transaksi.searchDatabase();
                            break;

                        case "6" :
                            transaksi.resetDatabase();
                            break;

                        case "7" :
                            System.out.println("|                    PROGRAM  SELESAI                    |");
                            System.out.println("|========================================================|");
                            System.exit(0);  // Menghentikan program dengan kode keluar 0
                            break; 

                        default:
                            System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
                            break;  
                    }
                
       
            try {     

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage() + "\n");
                inputan.nextLine(); // Membersihkan newline
            }
        } while (!inputValid);

        inputan.close();
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Username: ");
        String enteredUsername = scanner.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scanner.nextLine();
        return USERNAME.equals(enteredUsername) && PASSWORD.equals(enteredPassword);
    }

    private static String generateCaptcha() {
        return CAPTCHA;
    }

    private static boolean validateCaptcha(Scanner scanner, String generatedCaptcha) {
        System.out.print("Masukkan Captcha: ");
        String enteredCaptcha = scanner.nextLine();
        return generatedCaptcha.equalsIgnoreCase(enteredCaptcha);
    }

}
