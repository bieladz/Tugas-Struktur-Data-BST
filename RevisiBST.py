class Node:
    def __init__(self, id: int, nama: str):
        self.id    = id
        self.nama  = nama
        self.left  = None
        self.right = None


class BinarySearchTree:
    def __init__(self):
        self.root = None

    # ---- Tambah Data ----
    def _insert(self, node, id, nama):
        if node is None:
            return Node(id, nama)
        if id < node.id:
            node.left  = self._insert(node.left,  id, nama)
        elif id > node.id:
            node.right = self._insert(node.right, id, nama)
        else:
            print(f"  [!] ID {id} sudah ada, diabaikan.")
        return node

    def tambah_data(self, id: int, nama: str):
        self.root = self._insert(self.root, id, nama)
        print(f"  [+] Tambah  -> ID={id}  Nama={nama}")

    # ---- Cari Data ----
    def _search(self, node, id):
        if node is None or node.id == id:
            return node
        if id < node.id:
            return self._search(node.left, id)
        return self._search(node.right, id)

    def cari_data(self, id: int):
        result = self._search(self.root, id)
        if result:
            print(f"  [v] Ditemukan -> ID={result.id}  Nama={result.nama}")
        else:
            print(f"  [x] ID {id} tidak ditemukan.")

    # ---- Hapus Data ----
    def _min_node(self, node):
        while node.left:
            node = node.left
        return node

    def _delete(self, node, id):
        if node is None:
            return None
        if id < node.id:
            node.left  = self._delete(node.left,  id)
        elif id > node.id:
            node.right = self._delete(node.right, id)
        else:
            # Node ditemukan
            if node.left is None:  return node.right
            if node.right is None: return node.left
            # Punya 2 anak -> ganti dengan inorder successor
            suc        = self._min_node(node.right)
            node.id    = suc.id
            node.nama  = suc.nama
            node.right = self._delete(node.right, suc.id)
        return node

    def hapus_data(self, id: int):
        target = self._search(self.root, id)
        if target:
            print(f"  [-] Hapus   -> ID={id}  Nama={target.nama}")
            self.root = self._delete(self.root, id)
        else:
            print(f"  [x] ID {id} tidak ditemukan, tidak bisa dihapus.")

    # ---- Traversal ----
    def _inorder(self, node, result):
        if node:
            self._inorder(node.left, result)
            result.append(f"({node.id},{node.nama})")
            self._inorder(node.right, result)

    def _preorder(self, node, result):
        if node:
            result.append(f"({node.id},{node.nama})")
            self._preorder(node.left, result)
            self._preorder(node.right, result)

    def _postorder(self, node, result):
        if node:
            self._postorder(node.left, result)
            self._postorder(node.right, result)
            result.append(f"({node.id},{node.nama})")

    def traversal(self):
        # Helper untuk mencetak dengan format rapi
        def print_pretty(title, data_list):
            print(f"\n  {title}:")
            print("  " + "-" * 80)
            for i, item in enumerate(data_list):
                # {:<20} artinya teks dibuat rata kiri dengan lebar 20 karakter
                print(f"{item:<20}", end="")
                # Pindah baris setiap 4 item agar rapi
                if (i + 1) % 4 == 0:
                    print()
            print(f"\n  " + "-" * 80)

        res_in = []
        self._inorder(self.root, res_in)
        print_pretty("Inorder (Urut ID Ascending)", res_in)

        res_pre = []
        self._preorder(self.root, res_pre)
        print_pretty("Preorder (Root-L-R)", res_pre)

        res_post = []
        self._postorder(self.root, res_post)
        print_pretty("Postorder (L-R-Root)", res_post)

# ---- Dataset 100 item dari file PDF ----

DATASET = [
    (5288,"pensil"),    (5993,"pulpen"),      (8689,"penghapus"), (8043,"buku"),
    (8699,"sampul"),    (2156,"penggaris"),   (4457,"kertas"),    (8938,"cat"),
    (2618,"stabilo"),   (9033,"mobil"),       (9971,"motor"),     (3874,"becak"),
    (5914,"sepeda"),    (2398,"kereta"),      (3725,"pesawat"),   (5210,"perahu"),
    (7363,"kapal"),     (7631,"rakit"),       (4513,"kipas"),     (5656,"charger"),
    (6453,"peci"),      (8783,"sarung"),      (8194,"sajadah"),   (9783,"smartphone"),
    (3685,"jam"),       (4490,"televisi"),    (8294,"laptop"),    (8563,"komputer"),
    (1070,"mouse"),     (5408,"keyboard"),    (8258,"tablet"),    (9309,"jendela"),
    (1138,"kaca"),      (2751,"pintu"),       (3258,"kompor"),    (6402,"lemari"),
    (7921,"kasur"),     (9781,"ranjang"),     (3818,"bantal"),    (5204,"baju"),
    (6119,"kaos"),      (1928,"celana"),      (4207,"mukena"),    (7255,"jilbab"),
    (5309,"pigura"),    (2897,"antena"),      (8028,"kulkas"),    (1660,"dispenser"),
    (3248,"meja"),      (5641,"kursi"),       (7376,"kemoceng"),  (3525,"sapu"),
    (4492,"gayung"),    (7187,"sabun"),       (1305,"sikat"),     (6602,"shampo"),
    (8153,"botol"),     (3561,"gelas"),       (5082,"piring"),    (7151,"panci"),
    (7524,"wajan"),     (9178,"blender"),     (9817,"galon"),     (4304,"cobek"),
    (6820,"termos"),    (9151,"kran"),        (3482,"selang"),    (3316,"karpet"),
    (5192,"tikar"),     (7572,"keset"),       (7660,"sepatu"),    (9224,"kaos kaki"),
    (5083,"jaket"),     (6362,"piama"),       (6465,"piano"),     (9888,"gitar"),
    (4159,"angklung"),  (4969,"suling"),      (5097,"toples"),    (6271,"parfum"),
    (9250,"sisir"),     (3409,"topi"),        (4577,"gunting"),   (6244,"pisau"),
    (8612,"kaleng"),    (4650,"tisu"),        (6799,"tas"),       (9298,"ikat pinggang"),
    (4361,"korek api"), (4379,"kopi"),        (6928,"gula"),      (3195,"cabai"),
    (5741,"wortel"),    (6852,"timun"),       (8147,"apel"),      (8902,"jeruk"),
    (8967,"tomat"),     (1302,"pisang"),      (2363,"pepaya"),    (6861,"bawang"),
]


def main():
    bst = BinarySearchTree()

    print("=" * 62)
    print("  BINARY SEARCH TREE — Python Implementation")
    print("=" * 62)

    # [1] Tambah semua data
    print(f"\n>>> [1] TAMBAH {len(DATASET)} DATA\n")
    for id_, nama in DATASET:
        bst.tambah_data(id_, nama)

    # [2] Cari beberapa data
    print("\n>>> [2] CARI DATA\n")
    for id_ in [1070, 9888, 6861, 9999]:
        bst.cari_data(id_)

    # [3] Hapus beberapa data
    print("\n>>> [3] HAPUS DATA\n")
    for id_ in [5288, 9033, 6861, 1111]:
        bst.hapus_data(id_)

    # [4] Traversal
    print("\n>>> [4] TRAVERSAL (setelah penghapusan)\n")
    bst.traversal()

    # [5] Menu interaktif
    print("\n" + "=" * 62)
    print("  MENU INTERAKTIF")
    print("=" * 62)

    while True:
        print("1. Tambah Data") 
        print("2. Cari Data")
        print("3. Hapus Data")
        print("4. Traversal")
        print("5. Keluar")
        pilihan = input("Pilihan: ").strip()

        if pilihan == "1":
            try:
                id_  = int(input("  ID   : "))
                nama = input("  Nama : ").strip()
                bst.tambah_data(id_, nama)
            except ValueError:
                print("  [!] ID harus berupa angka.")
        elif pilihan == "2":
            try:
                bst.cari_data(int(input("  ID   : ")))
            except ValueError:
                print("  [!] ID harus berupa angka.")
        elif pilihan == "3":
            try:
                bst.hapus_data(int(input("  ID   : ")))
            except ValueError:
                print("  [!] ID harus berupa angka.")
        elif pilihan == "4":
            bst.traversal()
        elif pilihan == "0":
            print("  Program selesai.")
            break
        else:
            print("  Pilihan tidak valid.")


if __name__ == "__main__":
    main()
