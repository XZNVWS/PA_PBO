# 💰 Sistem Keuangan Pribadi

Aplikasi manajemen keuangan pribadi berbasis **Java OOP**, dibangun
mengikuti roadmap modul 2 sampai 8.

---

## 🗂️ Struktur File (Modul 2–4)

```
src/main/java/id/my/keuangan/
│
├── model/
│   ├── ITransaksiable.java   ← Interface kontrak transaksi
│   ├── Transaksi.java        ← Abstract superclass (Modul 2, 3, 4)
│   ├── Pemasukan.java        ← Subclass (extends Transaksi)
│   ├── Pengeluaran.java      ← Subclass (extends Transaksi)
│   └── Kategori.java         ← Enum kategori keuangan
│
├── manager/
│   ├── TransaksiManager.java ← Kelola & simpan transaksi
│   └── LaporanManager.java   ← Generate laporan keuangan
│
└── app/
    └── MainApp.java          ← Entry point + demo OOP M2–4
```

---

## 📚 Pemetaan Modul OOP

| Modul | Konsep | Implementasi di project ini |
|-------|--------|-----------------------------|
| **2** | Class, Constructor, Property | `Transaksi`, `Pemasukan`, `Pengeluaran`, `Kategori` |
| **3** | Encapsulation, getter/setter + validasi | Semua field `private`, setter dengan `if` validasi |
| **4** | Inheritance, `extends`, `super()` | `Pemasukan extends Transaksi`, `Pengeluaran extends Transaksi` |

---

## 🚀 Cara Menjalankan

### Prasyarat
- Java 17+
- Maven 3.8+

### Kompilasi & Jalankan
```bash
mvn compile exec:java
```

### Output yang Diharapkan
```
✓ Transaksi berhasil ditambahkan: A1B2C3D4
...
╔══════════════════════════════════════════╗
║       RINGKASAN KEUANGAN PRIBADI         ║
╠══════════════════════════════════════════╣
║  Total Pemasukan  : Rp 10.500.000        ║
║  Total Pengeluaran: Rp  1.500.000        ║
╠══════════════════════════════════════════╣
║  Saldo Bersih     : Rp  9.000.000        ║
║  Status           : (+) SURPLUS          ║
╚══════════════════════════════════════════╝
```

---

## 🔜 Rencana Modul Berikutnya

- **Modul 5** — Polymorphism: method `tampilkanDetail()` dan `getDampakSaldo()` 
  dipanggil via referensi parent `Transaksi`
- **Modul 6** — Abstract Class & Interface: tambah `IExportable`, abstract method baru
- **Modul 7** — Exception Handling: custom exception `TransaksiException`, `SaldoMinusException`
- **Modul 8** — Collections & Generics: `GenericRepository<T>`, sorting, filtering lanjut
