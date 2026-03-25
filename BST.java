import java.util.Scanner;

// ============================================================
//  BINARY SEARCH TREE — Java
//  Data: 100 item dari file PDF
//  Fitur: Tambah, Cari, Hapus, Traversal (In/Pre/Post-order)
// ============================================================

public class BST {

    // ==================== NODE ====================
    static class Node {
        int id;
        String nama;
        Node left, right;

        Node(int id, String nama) {
            this.id    = id;
            this.nama  = nama;
            this.left  = null;
            this.right = null;
        }
    }

    // ==================== BST ====================
    static class BinarySearchTree {
        Node root = null;

        // ---- Tambah Data ----
        private Node insert(Node node, int id, String nama) {
            if (node == null) return new Node(id, nama);
            if      (id < node.id) node.left  = insert(node.left,  id, nama);
            else if (id > node.id) node.right = insert(node.right, id, nama);
            else System.out.println("  [!] ID " + id + " sudah ada, diabaikan.");
            return node;
        }

        void tambahData(int id, String nama) {
            root = insert(root, id, nama);
            System.out.println("  [+] Tambah  -> ID=" + id + "  Nama=" + nama);
        }

        // ---- Cari Data ----
        private Node search(Node node, int id) {
            if (node == null || node.id == id) return node;
            return (id < node.id) ? search(node.left, id) : search(node.right, id);
        }

        void cariData(int id) {
            Node res = search(root, id);
            if (res != null)
                System.out.println("  [v] Ditemukan -> ID=" + res.id + "  Nama=" + res.nama);
            else
                System.out.println("  [x] ID " + id + " tidak ditemukan.");
        }

        // ---- Hapus Data ----
        private Node minNode(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        private Node delete(Node node, int id) {
            if (node == null) return null;
            if      (id < node.id) node.left  = delete(node.left,  id);
            else if (id > node.id) node.right = delete(node.right, id);
            else {
                if (node.left  == null) return node.right;
                if (node.right == null) return node.left;
                Node suc   = minNode(node.right);
                node.id    = suc.id;
                node.nama  = suc.nama;
                node.right = delete(node.right, suc.id);
            }
            return node;
        }

        void hapusData(int id) {
            Node target = search(root, id);
            if (target != null) {
                System.out.println("  [-] Hapus   -> ID=" + id + "  Nama=" + target.nama);
                root = delete(root, id);
            } else {
                System.out.println("  [x] ID " + id + " tidak ditemukan, tidak bisa dihapus.");
            }
        }

        // ---- Traversal (kumpulkan ke List dulu) ----
        private void inorder(Node node, java.util.List<String> list) {
            if (node == null) return;
            inorder(node.left, list);
            list.add("(" + node.id + "," + node.nama + ")");
            inorder(node.right, list);
        }

        private void preorder(Node node, java.util.List<String> list) {
            if (node == null) return;
            list.add("(" + node.id + "," + node.nama + ")");
            preorder(node.left, list);
            preorder(node.right, list);
        }

        private void postorder(Node node, java.util.List<String> list) {
            if (node == null) return;
            postorder(node.left, list);
            postorder(node.right, list);
            list.add("(" + node.id + "," + node.nama + ")");
        }

        // Cetak list dalam format 4 kolom, lebar tiap kolom 20 karakter
        private void printGrid(java.util.List<String> list) {
            int cols = 4;
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%-20s", list.get(i));
                if ((i + 1) % cols == 0) System.out.println();
            }
            if (list.size() % cols != 0) System.out.println();
        }

        void traversal() {
            java.util.List<String> res = new java.util.ArrayList<>();

            System.out.println("  Inorder (L-Root-R) :");
            System.out.println("  " + "-".repeat(80));
            res.clear(); inorder(root, res); printGrid(res);

            System.out.println("\n  Preorder (Root-L-R) :");
            System.out.println("  " + "-".repeat(80));
            res.clear(); preorder(root, res); printGrid(res);

            System.out.println("\n  Postorder (L-R-Root) :");
            System.out.println("  " + "-".repeat(80));
            res.clear(); postorder(root, res); printGrid(res);
        }
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {

        BinarySearchTree bst = new BinarySearchTree();

        // ---- Dataset 100 item dari file PDF ----
        Object[][] dataset = {
            {5288,"pensil"},    {5993,"pulpen"},      {8689,"penghapus"}, {8043,"buku"},
            {8699,"sampul"},    {2156,"penggaris"},   {4457,"kertas"},    {8938,"cat"},
            {2618,"stabilo"},   {9033,"mobil"},       {9971,"motor"},     {3874,"becak"},
            {5914,"sepeda"},    {2398,"kereta"},      {3725,"pesawat"},   {5210,"perahu"},
            {7363,"kapal"},     {7631,"rakit"},       {4513,"kipas"},     {5656,"charger"},
            {6453,"peci"},      {8783,"sarung"},      {8194,"sajadah"},   {9783,"smartphone"},
            {3685,"jam"},       {4490,"televisi"},    {8294,"laptop"},    {8563,"komputer"},
            {1070,"mouse"},     {5408,"keyboard"},    {8258,"tablet"},    {9309,"jendela"},
            {1138,"kaca"},      {2751,"pintu"},       {3258,"kompor"},    {6402,"lemari"},
            {7921,"kasur"},     {9781,"ranjang"},     {3818,"bantal"},    {5204,"baju"},
            {6119,"kaos"},      {1928,"celana"},      {4207,"mukena"},    {7255,"jilbab"},
            {5309,"pigura"},    {2897,"antena"},      {8028,"kulkas"},    {1660,"dispenser"},
            {3248,"meja"},      {5641,"kursi"},       {7376,"kemoceng"},  {3525,"sapu"},
            {4492,"gayung"},    {7187,"sabun"},       {1305,"sikat"},     {6602,"shampo"},
            {8153,"botol"},     {3561,"gelas"},       {5082,"piring"},    {7151,"panci"},
            {7524,"wajan"},     {9178,"blender"},     {9817,"galon"},     {4304,"cobek"},
            {6820,"termos"},    {9151,"kran"},        {3482,"selang"},    {3316,"karpet"},
            {5192,"tikar"},     {7572,"keset"},       {7660,"sepatu"},    {9224,"kaos kaki"},
            {5083,"jaket"},     {6362,"piama"},       {6465,"piano"},     {9888,"gitar"},
            {4159,"angklung"},  {4969,"suling"},      {5097,"toples"},    {6271,"parfum"},
            {9250,"sisir"},     {3409,"topi"},        {4577,"gunting"},   {6244,"pisau"},
            {8612,"kaleng"},    {4650,"tisu"},        {6799,"tas"},       {9298,"ikat pinggang"},
            {4361,"korek api"},{4379,"kopi"},         {6928,"gula"},      {3195,"cabai"},
            {5741,"wortel"},    {6852,"timun"},       {8147,"apel"},      {8902,"jeruk"},
            {8967,"tomat"},     {1302,"pisang"},      {2363,"pepaya"},    {6861,"bawang"}
        };

        System.out.println("=".repeat(62));
        System.out.println("  BINARY SEARCH TREE — Java Implementation");
        System.out.println("=".repeat(62));

        // [1] Tambah semua data
        System.out.println("\n>>> [1] TAMBAH " + dataset.length + " DATA\n");
        for (Object[] row : dataset) {
            bst.tambahData((int) row[0], (String) row[1]);
        }

        // [2] Cari beberapa data (ada & tidak ada)
        System.out.println("\n>>> [2] CARI DATA\n");
        int[] ids = {1070, 9888, 6861, 9999};
        for (int id : ids) bst.cariData(id);

        // [3] Hapus beberapa data (ada & tidak ada)
        System.out.println("\n>>> [3] HAPUS DATA\n");
        int[] delIds = {5288, 9033, 6861, 1111};
        for (int id : delIds) bst.hapusData(id);

        // [4] Traversal setelah penghapusan
        System.out.println("\n>>> [4] TRAVERSAL (setelah penghapusan)\n");
        bst.traversal();

        // [5] Menu Interaktif
        System.out.println("\n" + "=".repeat(62));
        System.out.println("  MENU INTERAKTIF");
        System.out.println("=".repeat(62));

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("\n  1. Tambah Data   2. Cari Data");
            System.out.println("  3. Hapus Data    4. Traversal   0. Keluar");
            System.out.print("  Pilihan: ");
            int p = sc.nextInt();
            switch (p) {
                case 1:
                    System.out.print("  ID   : "); int nId = sc.nextInt();
                    System.out.print("  Nama : "); String nNama = sc.next();
                    bst.tambahData(nId, nNama);
                    break;
                case 2:
                    System.out.print("  ID   : "); bst.cariData(sc.nextInt()); break;
                case 3:
                    System.out.print("  ID   : "); bst.hapusData(sc.nextInt()); break;
                case 4:
                    bst.traversal(); break;
                case 0:
                    run = false;
                    System.out.println("  Program selesai."); break;
                default:
                    System.out.println("  Pilihan tidak valid.");
            }
        }
        sc.close();
    }
}