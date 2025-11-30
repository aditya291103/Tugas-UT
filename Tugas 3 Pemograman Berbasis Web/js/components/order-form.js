Vue.component('order-form', {
    template: '#tpl-order-form',

    props: ['paket'],

    data() {
        return {
            form: {
                no: "",
                nim: "",
                nama: "",
                ekspedisi: "",
                paket: "",
                tanggalKirim: "",
                total: 0,
                perjalanan: []
            },
            pengirimanList: ["JNE Regular", "JNE Express"],
            detailPaket: null
        };
    },

    mounted() {
        this.generateDO();
        this.setToday();
    },

    methods: {
        generateDO() {
            const year = new Date().getFullYear();
            const tracking = this.$root.state.tracking;
            const count = Object.keys(tracking).length + 1;

            this.form.no = `DO${year}-${String(count).padStart(4, "0")}`;
        },

        setToday() {
            const today = new Date();
            this.form.tanggalKirim = today.toISOString().split("T")[0];
        },

        updatePaketDetail() {
            this.detailPaket = this.paket.find(p => p.kode === this.form.paket);
            if (this.detailPaket) {
                this.form.total = this.detailPaket.harga;
            }
        },

        formatRupiah(v) {
            return v.toLocaleString("id-ID");
        },

        submitOrder() {
            const formEl = this.$el.closest("form") || this.$el;

            if (!formEl.checkValidity()) {
                formEl.reportValidity(); // <-- Munculkan bubble validasi seperti gambar
                return;
            }

            const now = new Date();
            const tahun = now.getFullYear();
            const bulan = String(now.getMonth() + 1).padStart(2, '0');
            const hari = String(now.getDate()).padStart(2, '0');
            const jam = String(now.getHours()).padStart(2, '0');
            const menit = String(now.getMinutes()).padStart(2, '0');
            const detik = String(now.getSeconds()).padStart(2, '0');
            const waktu = `${tahun}-${bulan}-${hari} ${jam}:${menit}:${detik}`;

            const perjalanan = [{
                waktu,
                status: "Diproses",
                keterangan: "Order dibuat"
            }];

            const newDO = {
                [this.form.no]: {
                    nim: this.form.nim,
                    nama: this.form.nama,
                    ekspedisi: this.form.ekspedisi,
                    tanggalKirim: this.form.tanggalKirim,
                    paket: this.form.paket,
                    total: this.form.total,
                    perjalanan
                }
            };

            this.$root.state.tracking.push(newDO);

            showToastSuccess('DO berhasil ditambahkan ✔');

            this.$root.setTab("tracking");
        }
    }
});
