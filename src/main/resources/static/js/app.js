import GeneralComponent from "./components/generalComponent.js";
import ProductoComponent from "./components/productoComponent.js";
import impresionComponent from "./components/impresionComponent.js";
import miCuentaComponent from "./components/miCuentaComponent.js";
import controlCuentasComponent from "./components/controlCuentasComponent.js";
import creditosComponent from "./components/creditosComponent.js";

const { createApp } = Vue



document.addEventListener('DOMContentLoaded', function () {



    createApp({
        data() {


            let theme = localStorage.getItem('theme') === 'true'

            let isAdmin = false;
            try {
                isAdmin = document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true';
            } catch (error) {
                console.error('Error al obtener isAdmin:', error);
            }
            const path = window.location.pathname.substring(1) || 'general';
            return {
                currentView: path,
                isAdmin,
                theme
            }
        },
        components: {
            'general': GeneralComponent,
            'producto': ProductoComponent,
            'impresion': impresionComponent,
            'miCuenta': miCuentaComponent,
            'controlCuentas': controlCuentasComponent,
            'creditos': creditosComponent,
        },
        methods: {

            changeView(view) {
                this.currentView = view;
                history.pushState({}, view, `/${view}`);
            },
            logout() {
                fetch('/logout', {
                    method: 'POST'
                })
                    .then(response => {
                        if (response.ok) {
                            window.location.href = '/login?logout';
                        } else {
                            console.error('Error al realizar el logout');

                        }
                    })
                    .catch(error => {
                        console.error('Error al realizar el logout:', error);

                    });
            },
            toggleTheme() {
                this.theme = this.theme === 'darkMode' ? '' : 'darkMode';
                document.documentElement.setAttribute('data-theme', this.theme);
                localStorage.setItem('theme', this.theme);
                this.loadTabulatorStylesheet();
            },
            loadTabulatorStylesheet() {
                const tabulatorStyleSheet = document.getElementById('tabulator-stylesheet');
                tabulatorStyleSheet.href = this.theme === 'darkMode'
                    ? '/node_modules/tabulator-tables/dist/css/tabulator_site_dark.css'
                    : '/node_modules/tabulator-tables/dist/css/tabulator_modern.css';
            }

        },

        mounted() {
            let localTheme = localStorage.getItem('theme') || '';
            this.theme = localTheme;
            document.documentElement.setAttribute('data-theme', localTheme);
            if (localTheme === 'darkMode') {
                // Si el tema es darkMode, asegúrate de que el checkbox esté marcado
                document.querySelector('input[type="checkbox"]').checked = true;
            }
            this.loadTabulatorStylesheet();
        }


    }).mount('#app');


});



/*

 */