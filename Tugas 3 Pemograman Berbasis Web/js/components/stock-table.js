Vue.component('ba-stock-table', {
  template: '#tpl-stock',
  props: ['stok', 'kategoriList', 'upbjjList'], //data yang dikirim dari Vue utama (file utama yang memuat JSON)

  data() {
    return {
      localStok: [],
      filters: {
        upbjj: '',
        kategori: '',
        stockFlag: ''
      },
      sortBy: '',
      modalOpen: false,
      editIndex: null,
      form: {
        kode: '',
        judul: '',
        kategori: '',
        upbjj: '',
        lokasiRak: '',
        harga: '',
        qty: '',
        safety: '',
        catatanHTML: ''
      },
      deleteConfirmOpen: false,
      deleteIndex: null
    };
  },

  watch: {
    stok: {
      handler(newVal) {
        this.localStok = JSON.parse(JSON.stringify(newVal));
      },
      immediate: true,
      deep: true
    },
    'filters.upbjj'() {
      this.filters.kategori = '';
    }
  },

  computed: {
    kategoriOptions() {
      if (!this.filters.upbjj) return this.kategoriList;
      
      const set = new Set();
      this.localStok.forEach(s => {
        if (s.upbjj === this.filters.upbjj) {
          set.add(s.kategori);
        }
      });
      return [...set];
    },

    displayed() {
      let out = [...this.localStok];

      // Filter
      if (this.filters.upbjj)
        out = out.filter(s => s.upbjj === this.filters.upbjj);

      if (this.filters.kategori)
        out = out.filter(s => s.kategori === this.filters.kategori);

      if (this.filters.stockFlag === 'belowSafety')
        out = out.filter(s => s.qty < s.safety);

      if (this.filters.stockFlag === 'zero')
        out = out.filter(s => s.qty === 0);

      // Sorting
      const s = this.sortBy;
      if (s === 'judul') out.sort((a, b) => a.judul.localeCompare(b.judul));
      if (s === 'qty_desc') out.sort((a, b) => b.qty - a.qty);
      if (s === 'qty_asc') out.sort((a, b) => a.qty - b.qty);
      if (s === 'harga_desc') out.sort((a, b) => b.harga - a.harga);
      if (s === 'harga_asc') out.sort((a, b) => a.harga - b.harga);

      return out;
    }
  },

  methods: {
    resetFilters() {
      this.filters = {
        upbjj: '',
        kategori: '',
        stockFlag: ''
      };
      this.sortBy = '';
    },

    openAdd() {
      this.editIndex = null;
      this.form = {
        kode: '',
        judul: '',
        kategori: '',
        upbjj: '',
        lokasiRak: '',
        harga: '',
        qty: '',
        safety: '',
        catatanHTML: ''
      };
      this.modalOpen = true;
    },

    openEdit(i) {
      this.editIndex = i;
      this.form = JSON.parse(JSON.stringify(this.localStok[i]));
      this.modalOpen = true;
    },

    askDelete(i) {
      this.deleteIndex = i;
      this.form = JSON.parse(JSON.stringify(this.localStok[i]));
      this.deleteConfirmOpen = true;
    },

    confirmDelete() {
  if (this.deleteIndex !== null) {
    this.$emit("delete-stok", this.deleteIndex);
    showToastSuccess("Data berhasil dihapus ✔");
  }
  this.closeDelete();
},

    closeDelete() {
      this.deleteConfirmOpen = false;
      this.deleteIndex = null;
    },

    saveItem() {
      if (!this.form.kode ||
          !this.form.judul ||
          !this.form.kategori ||
          !this.form.upbjj ||
          !this.form.lokasiRak ||
          this.form.harga === '' ||
          this.form.qty === '' ||
          this.form.safety === '') {
        alert("Semua field required wajib diisi!");
        return;
      }

      // Create / Update
      //this.$emit() mengirim event ke Vue Utama
      if (this.editIndex === null) {
        this.$emit("create-stok", { ...this.form });
        showToastSuccess("Data berhasil ditambahkan ✔");
      } else {
        this.$emit("update-stok", {
          index: this.editIndex,
          item: { ...this.form }
        });
        showToastSuccess("Data berhasil diperbarui ✔");
      }

      this.closeModal();
    },

    closeModal() {
      this.modalOpen = false;
    }
  }
});
