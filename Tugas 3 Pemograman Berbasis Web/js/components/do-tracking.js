Vue.component('do-tracking', {
    template: '#tpl-tracking',

    props: ['trackingRaw', 'paket', 'pengirimanList'],

    data() {
        return {
            query: "",
            notFound: false,
            resultList: [],
            modalDetail: false,
            modalStatus: false,
            selected: null,
            formStatus: { status: "", keterangan: "" },
            tracking: {}
        };
    },
    
    //Jika data tracking berubah → format ulang jadi object map 
    watch: {
        trackingRaw: {
            immediate: true,
            handler(newVal) {
                this.tracking = this.convertToMap(newVal);
                this.showAllData();
            }
        }
    },

    created() {
        this.tracking = this.convertToMap(this.trackingRaw);

        this.resultList = Object.entries(this.tracking)
            .map(([no, d]) => this.computeProgress({ no, ...d }));
    },

    methods: {
        // FORMAT JSON AGAR SUPPORT ARRAY & OBJECT ⬇⬇
        convertToMap(input) {
            const map = {};

            if (Array.isArray(input)) {
                input.forEach(obj => {
                    const key = Object.keys(obj)[0];
                    map[key] = obj[key];
                });
            }
            else if (typeof input === 'object' && input !== null) {
                for (const key in input) {
                    map[key] = input[key];
                }
            }

            return map;
        },

        showAllData() {
            this.resultList = Object.entries(this.tracking)
                .map(([no, d]) => ({ no, ...d }));
        },

        formatTanggal(t) {
            return new Date(t).toLocaleDateString('id-ID', {
                day: '2-digit', month: 'long', year: 'numeric'
            });
        },
        formatRupiah(v) {
            return v.toLocaleString('id-ID');
        },

        // 🔍 SEARCH
        searchDO() {
            const q = this.query.toUpperCase();
            this.notFound = false;

            const list = Object.entries(this.tracking)
                .filter(([no, d]) =>
                    no.toUpperCase().includes(q) ||
                    d.nim.toUpperCase().includes(q)
                )
                .map(([no, d]) => ({ no, ...d }));

            this.resultList = list;
            this.notFound = list.length === 0;
        },

        resetSearch() {
            this.query = "";
            this.showAllData();
            this.notFound = false;
        },

        // 🟦 Status progress sederhana
        computeProgress(data) {
            const perjalanan = data.perjalanan || [];
            const last = perjalanan.length
                ? perjalanan[perjalanan.length - 1].keterangan.toLowerCase()
                : "";

            let cfg = { label: "Diproses" };
            if (last.includes("selesai")) cfg = { label: "Terkirim" };
            else if (last.includes("antar")) cfg = { label: "Sedang Diantar" };

            return { ...data, progress: cfg };
        },

        // 📌 Modal Detail
        openDetail(no) {
            const data = { no, ...this.tracking[no] };

            // Tambah status default ke tiap perjalanan yang tidak memiliki status
            const perjalanan = data.perjalanan.map(p => ({
                ...p,
                status: p.status || data.status || "Diproses"
            }));

            this.selected = {
                ...data,
                perjalanan
            };

            this.modalDetail = true;
        },
        closeDetail() {
            this.modalDetail = false;
        },

        // 📝 Modal Status
        openAddStatus() {
            this.formStatus = { keterangan: "", keterangan: "" };
            this.modalStatus = true;
        },
        closeStatus() {
            this.modalStatus = false;
        },

        saveStatus() {
            const now = new Date();
            const tahun = now.getFullYear();
            const bulan = String(now.getMonth() + 1).padStart(2, '0');
            const hari = String(now.getDate()).padStart(2, '0');
            const jam = String(now.getHours()).padStart(2, '0');
            const menit = String(now.getMinutes()).padStart(2, '0');
            const detik = String(now.getSeconds()).padStart(2, '0');
            const waktu = `${tahun}-${bulan}-${hari} ${jam}:${menit}:${detik}`;

            this.selected.perjalanan.push({
                waktu,
                status: this.formStatus.status,
                keterangan: this.formStatus.keterangan
            });

            this.tracking[this.selected.no] = this.selected;

            this.selected = this.computeProgress(this.selected);
            this.formStatus = { status: "", keterangan: "" };
            this.modalStatus = false;
        }


    }
});
