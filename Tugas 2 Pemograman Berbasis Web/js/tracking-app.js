new Vue({
    el: '#app-trk',
    data() {
        return {
            pengirimanList: (typeof app !== 'undefined' ? app.$data.pengirimanList : []),
            paket: (typeof app !== 'undefined' ? app.$data.paket : []),
            tracking: (typeof app !== 'undefined' ? (app.$data.tracking ? Object.assign({}, app.$data.tracking) : {}) : {}),

            form: {
                nim: '',
                nama: '',
                ekspedisi: '',
                paket: '',
                tanggalKirim: (new Date()).toISOString().slice(0, 10)
            }
        };
    },
    computed: {
        selectedPaket() {
            return this.paket.find(p => p.kode === this.form.paket) || null;
        },
        // generate next DO: DO{YYYY}-{seq:4}
        nextDONo() {
            const year = (new Date()).getFullYear();
            // collect existing DO belonging to year
            const keys = Object.keys(this.tracking).filter(k => k.startsWith('DO' + year));
            let seq = 1;
            if (keys.length) {
                // find highest sequence
                const last = keys.map(k => parseInt(k.split('-')[1], 10)).sort((a, b) => b - a)[0];
                seq = last + 1;
            }
            return `DO${year}-${String(seq).padStart(4, '0')}`;
        }
    },
    methods: {
        addDO() {
            const code = this.nextDONo;
            const payload = {
                nim: this.form.nim,
                nama: this.form.nama,
                status: 'Diproses',
                ekspedisi: this.form.ekspedisi,
                tanggalKirim: this.form.tanggalKirim,
                paket: this.form.paket,
                total: this.selectedPaket ? this.selectedPaket.harga : 0,
                perjalanan: [
                    { waktu: new Date().toLocaleString('sv-SE'), keterangan: 'Order dibuat' }
                ]
            };
            // simpan
            // pakai set agar data reaktif & UI otomatis update
            this.$set(this.tracking, code, payload);
            // reset minimal form (nim/nama left)
            this.form.nim = '';
            this.form.nama = '';
            this.form.paket = '';
            this.form.ekspedisi = '';
            showToastSuccess('DO ' + code + ' berhasil ditambahkan ✔');
        },
        statusClass(d) {
            if (d.status === 'Terkirim') return 'bg-success text-white';
            if (d.status === 'Diproses' || d.status === 'Dalam Perjalanan') return 'bg-info text-white';
            return 'bg-secondary text-white';
        }
    }
});
