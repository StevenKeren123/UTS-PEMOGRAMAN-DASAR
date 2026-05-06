import java.util.Scanner;

public class UTS {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int jumlahPelanggan = 0;
        while (jumlahPelanggan < 3) {
            System.out.print("Masukkan jumlah pelanggan (minimal 3): ");
            jumlahPelanggan = sc.nextInt();
            sc.nextLine();
            if (jumlahPelanggan < 3) {
                System.out.println("  [!] Jumlah pelanggan harus minimal 3. Silakan coba lagi.\n");
            }
        }
        String[] namaPelanggan  = new String[jumlahPelanggan];
        String[] namaProvider   = new String[jumlahPelanggan];
        String[] jenisPulsa     = new String[jumlahPelanggan];
        int[]    jumlahPembelian = new int[jumlahPelanggan];   // banyak item pulsa per pelanggan
        // nominal pilihan
        int[] pilihanNominal = {100000, 50000, 25000, 15000};      
        for (int i = 0; i < jumlahPelanggan; i++) {
            System.out.println("\n========================================");
            System.out.println(" DATA PELANGGAN KE-" + (i + 1));
            System.out.println("========================================");
            System.out.print("Nama Pelanggan  : ");
            namaPelanggan[i] = sc.nextLine();
            System.out.print("Nama Provider   : ");
            namaProvider[i] = sc.nextLine();
            System.out.print("Jenis Pulsa (reguler/data/sms): ");
            jenisPulsa[i] = sc.nextLine();
            // Tampilkan pilihan nominal
            System.out.println("Pilihan Nominal Harga:");
            for (int k = 0; k < pilihanNominal.length; k++) {
                System.out.printf("  [%d] Rp %,d%n", (k + 1), pilihanNominal[k]);
            }       
            int qty = 0;
            while (qty < 1) {
                System.out.print("Berapa banyak pulsa yang dibeli? : ");
                qty = sc.nextInt();
                if (qty < 1) System.out.println("  [!] Minimal beli 1 pulsa.");
            }
            jumlahPembelian[i] = qty;
            sc.nextLine();
        }     
        int totalSlot = 0;
        for (int i = 0; i < jumlahPelanggan; i++) totalSlot += jumlahPembelian[i];
        int[]    nominalArray  = new int[totalSlot];   // array 1 dimensi nominal
        int[]    startIdx      = new int[jumlahPelanggan]; // indeks awal tiap pelanggan 
        int cursor = 0;
        for (int i = 0; i < jumlahPelanggan; i++) {
            startIdx[i] = cursor;
            cursor += jumlahPembelian[i];
        }       
        System.out.println("\n========================================");
        System.out.println("   PILIHAN NOMINAL PER PELANGGAN");
        System.out.println("========================================");
        for (int i = 0; i < jumlahPelanggan; i++) {
            System.out.println("\nPelanggan: " + namaPelanggan[i]);
            System.out.println("Pilihan Nominal:");
            for (int k = 0; k < pilihanNominal.length; k++) {
                System.out.printf("  [%d] Rp %,d%n", (k + 1), pilihanNominal[k]);
            }
            for (int j = 0; j < jumlahPembelian[i]; j++) {
                int pilih = 0;
                while (pilih < 1 || pilih > 4) {
                    System.out.printf("  Pilih nominal ke-%d (1-4): ", (j + 1));
                    pilih = new Scanner(System.in).nextInt();
                    if (pilih < 1 || pilih > 4)
                        System.out.println("  [!] Pilihan tidak valid, masukkan 1-4.");
                }
                nominalArray[startIdx[i] + j] = pilihanNominal[pilih - 1];
            }
        }     
        final int BIAYA_ADMIN = 2000;
        System.out.println("\n\n╔══════════════════════════════════════════╗");
        System.out.println("║         STRUK PEMBELIAN PULSA            ║");
        System.out.println("╚══════════════════════════════════════════╝");
        double grandTotal = 0;
        for (int i = 0; i < jumlahPelanggan; i++) {
            // Hitung subtotal nominal
            int subtotal = 0;
            for (int j = 0; j < jumlahPembelian[i]; j++) {
                subtotal += nominalArray[startIdx[i] + j];
            }
            int totalDenganAdmin = subtotal + BIAYA_ADMIN;
            double pctCashback;
            String labelCashback;
            if (totalDenganAdmin >= 200000) {
                pctCashback   = 0.15;
                labelCashback = "15%";
            } else if (totalDenganAdmin >= 50000) {
                pctCashback   = 0.10;
                labelCashback = "10%";
            } else {
                pctCashback   = 0.00;
                labelCashback = "0%";
            }
            double nilaiCashback = totalDenganAdmin * pctCashback;
            double totalBayar    = totalDenganAdmin - nilaiCashback;
            grandTotal          += totalBayar;          
            System.out.println("\n------------------------------------------");
            System.out.printf("  Pelanggan  : %s%n", namaPelanggan[i]);
            System.out.printf("  Provider   : %s%n", namaProvider[i]);
            System.out.printf("  Jenis Pulsa: %s%n", jenisPulsa[i]);
            System.out.println("  Detail Pembelian:");
            for (int j = 0; j < jumlahPembelian[i]; j++) {
                System.out.printf("    Item %d    : Rp %,.0f%n",
                        (j + 1), (double) nominalArray[startIdx[i] + j]);
            }
            System.out.println("  ........................................");
            System.out.printf("  Subtotal   : Rp %,.0f%n", (double) subtotal);
            System.out.printf("  Biaya Admin: Rp %,.0f%n", (double) BIAYA_ADMIN);
            System.out.printf("  Total+Admin: Rp %,.0f%n", (double) totalDenganAdmin);
            System.out.printf("  Cashback   : %s  → -Rp %,.0f%n", labelCashback, nilaiCashback);
            System.out.println("  ........................................");
            System.out.printf("  TOTAL BAYAR: Rp %,.0f%n", totalBayar);
        }
        System.out.println("\n==========================================");
        System.out.printf("  GRAND TOTAL SEMUA PELANGGAN: Rp %,.0f%n", grandTotal);
        System.out.println("==========================================\n");

        sc.close();
    }
}