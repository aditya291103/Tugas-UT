new Vue({
    el: '#stokApp',   //Inisialisasi Vue. menghubungkan data dengan tampilan.
    data() {
        return {
            // ambil data dari global `app` yang ada di dataBahanAjar.js
            upbjjList: (typeof app !== 'undefined' ? app.$data.upbjjList : []),
            kategoriList: (typeof app !== 'undefined' ? app.$data.kategoriList : []),
            stok: (typeof app !== 'undefined' ? JSON.parse(JSON.stringify(app.$data.stok)) : []),

            // filter reactive
            filters: {
                upbjj: '',
                kategori: '',
                stockFlag: ''
            },
            sortBy: '',

            // modal add/edit
            modalOpen: false,
            editIndex: null,
            form: this.emptyForm()
        };
    },
    created() {
        // nothing
    },
    watch: {
        'filters.upbjj'(newVal, oldVal) {
            // kosongkan kategori tiap kali ganti UT-Daerah
            this.filters.kategori = '';
        }
    },
    methods: {
        emptyForm() {
            return { kode: '', judul: '', kategori: '', upbjj: '', lokasiRak: '', harga: '', qty: '', safety: '', catatanHTML: '' };
        },
        resetFilters() {
            this.filters.upbjj = '';
            this.filters.kategori = '';
            this.filters.stockFlag = '';
            this.sortBy = '';
        },
        openAdd() {
            this.editIndex = null;
            this.form = this.emptyForm();
            this.modalOpen = true;
        },
        openEdit(idx) {
            this.editIndex = idx;
            this.form = Object.assign({}, this.stok[idx]);
            this.modalOpen = true;
        },
        closeModal() {
            this.modalOpen = false;
        },
        saveItem() {
            if (this.editIndex === null) {
                this.stok.push(Object.assign({}, this.form));   //Menambah data baru ke array stok.
                showToastSuccess('Bahan ajar berhasil ditambahkan ✔');
            } else {
                Vue.set(this.stok, this.editIndex, Object.assign({}, this.form)); //Mengupdate item pada posisi tertentu.
                showToastSuccess('Perubahan berhasil disimpan ✔');
            }
            this.closeModal();

        },
        // sort comparator
        compare(a, b) {
            const s = this.sortBy;
            if (!s) return 0;
            if (s === 'judul') return a.judul.localeCompare(b.judul);
            if (s === 'qty_desc') return b.qty - a.qty;
            if (s === 'qty_asc') return a.qty - b.qty;
            if (s === 'harga_desc') return b.harga - a.harga;
            if (s === 'harga_asc') return a.harga - b.harga;
            return 0;
        }
    },
    computed: {
        // kategori dependent options: hanya muncul ketika filters.upbjj set
        kategoriOptions() {
            // Jika UT-Daerah belum dipilih → tampilkan semua kategori
            if (!this.filters.upbjj) return this.kategoriList;

            // Jika UT-Daerah dipilih → filter kategori sesuai stok pada daerah itu
            const cats = new Set();
            this.stok.forEach(s => {
                if (s.upbjj === this.filters.upbjj) cats.add(s.kategori);
            });

            return Array.from(cats);
        },
        watch: {
            'filters.upbjj'(newVal, oldVal) {
                this.filters.kategori = ''; // reset kategori
            }
        },
        // displayed data: apply filters then sort
        displayed() {
            // apply filters (note: this is computed and cached by Vue unless dependencies change)
            let out = this.stok.filter(s => {
                if (this.filters.upbjj && s.upbjj !== this.filters.upbjj) return false;
                if (this.filters.kategori && s.kategori !== this.filters.kategori) return false;
                if (this.filters.stockFlag === 'belowSafety' && !(s.qty < s.safety)) return false;
                if (this.filters.stockFlag === 'zero' && !(s.qty === 0)) return false;
                return true;
            });
            // sort
            out.sort((a, b) => this.compare(a, b));
            return out;
        }
    }
});
