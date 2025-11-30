Vue.directive('tooltip', {
  inserted(el, binding) {
    el.setAttribute('title', binding.value || '');
    el.setAttribute('data-bs-toggle', 'tooltip');

    new bootstrap.Tooltip(el, {
      html: true, // ⬅️ ini yang mengizinkan HTML ditampilkan
      sanitize: false // hanya jika konten HTML sudah aman
    });
  },
  update(el, binding) {
    el.setAttribute('title', binding.value || '');
  }
});

Vue.component('status-badge', {
  props: ['qty', 'safety', 'catatanHTML'],
  template: `
    <span
      :class="badgeClass"
      v-tooltip="catatanHTML"
      style="cursor:pointer"
    >
      {{ statusText }}
    </span>
  `,
  computed: {
    statusText() {
      if (this.qty === 0) return "Kosong";
      if (this.qty < this.safety) return "Menipis";
      return "Aman";
    },
    badgeClass() {
      return {
        'badge bg-success': this.qty >= this.safety,
        'badge bg-warning text-dark': this.qty < this.safety && this.qty > 0,
        'badge bg-danger': this.qty === 0
      }
    }
  }
});
