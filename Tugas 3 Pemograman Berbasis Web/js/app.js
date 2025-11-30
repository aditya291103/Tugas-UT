new Vue({
    el: '#app',
    data() {
        return {
            tab: 'stok',
            isLoading: true,
            state: {
                stok: [],
                tracking: [],
                paket: [],
                pengirimanList: [],
                kategoriList: [],
                upbjjList: []
            }
        };
    },
    async created() {
        const data = await ApiService.getData();
        if (data) {
            this.state = Object.assign(this.state, data);
        }

        const hash = window.location.hash.replace('#', '');
        if (['stok', 'tracking', 'order'].includes(hash)) {
            this.tab = hash;
        }

        window.addEventListener('hashchange', () => {
            const newHash = window.location.hash.replace('#', '');
            if (['stok', 'tracking', 'order'].includes(newHash)) {
                this.tab = newHash;
            }
        });

        this.isLoading = false;
    },
    computed: {
        currentUser() {
            return JSON.parse(sessionStorage.getItem('currentUser')) || {};
        }
    },
    methods: {
        handleCreateStok(item) {
            this.state.stok.push(item);
        },
        handleUpdateStok({ index, item }) {
            Vue.set(this.state.stok, index, item);
        },
        handleDeleteStok(index) {
            this.state.stok.splice(index, 1);
        },
        logout() {
            sessionStorage.clear();
            window.location.href = 'login.html';
        },
        setTab(tab) {
            this.tab = tab;
            window.location.hash = tab;
        }
    }

});
