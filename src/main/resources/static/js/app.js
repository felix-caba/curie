import GeneralComponent from "./components/generalComponent.js";
import ProductoComponent from "./components/productoComponent.js";
import impresionComponent from "./components/impresionComponent.js";



document.addEventListener('DOMContentLoaded', function () {
    const app = Vue.createApp({
        data() {
            return {
                currentView: 'general',
                isAdmin: document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true'
            };
        },
        components: {
            'general': GeneralComponent,
            'producto': ProductoComponent,
            'impresion': impresionComponent
        },
        methods: {
            changeView(view) {



                this.currentView = view;

            }
        }
    });
    app.mount('#app');
});
