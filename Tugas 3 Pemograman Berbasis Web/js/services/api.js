const ApiService = {
    async getData() {
        try {
            const res = await fetch("data/dataBahanAjar.json");
            const json = await res.json();

            return {
                stok: json.stok || [],
                tracking: json.tracking || [],
                paket: json.paket || [],
                pengirimanList: json.pengirimanList || [],
                
                // Ambil langsung dari JSON
                kategoriList: json.kategoriList || [],
                upbjjList: json.upbjjList || []
            };

        } catch (err) {
            console.error("Gagal load API:", err);
            alert("Gagal memuat data. Pastikan file JSON tersedia.");
            return null;
        }
    }
};
