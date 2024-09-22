import {loadTabla} from '../vistaProductos/tabla.js';
import {iniciarMaterialesTabla} from "../vistaProductos/materialesTabla.js";
import {iniciarAuxiliaresTabla} from "../vistaProductos/auxiliaresTabla.js";
import {iniciarReactivosTabla} from "../vistaProductos/reactivoTabla.js";






export default {

    data() {
        return {
            isAdmin: document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true'
        }
    },

    template: `
      
    <section class="service-sectionFather">

    <div class="app-header-navigation">
        <div class="tabs">

            <a href="#" class="tab-link active" data-section="reactivos">Reactivos</a>
            <a href="#" class="tab-link" data-section="auxiliares">Auxiliares</a>
            <a href="#" class="tab-link" data-section="materiales">Materiales</a>

        </div>
    </div>

    <section id="reactivos" class="service-section active">
        <h1>Lista de Reactivos</h1>
        <div class="search-containerReactivos">
            <input type="text" id="searchInputReactivos" placeholder="Buscar...">
            <button id="searchButtonReactivos">Buscar</button>
            <button id="cancelSearchButtonReactivos">Cancelar</button>
        </div>
        <div class="table-container">
            <table border="1">
                <thead>
                <tr>
                    <th v-if="isAdmin">ID</th>
                    <th>Nombre</th>
                    <th>Cantidad</th>
                    <th>Stock Mínimo</th>
                    <th>Formato</th>
                    <th>Grado Pureza</th>
                    <th>Fecha Caducidad</th>
                    <th>Localización</th>
                    <th>Ubicación</th>
                    <th>Riesgos</th>
                    <th v-if="isAdmin" >Acciones</th>

                </tr>
                </thead>

                <tbody id="reagentTableBody"></tbody>
              
            </table>
        </div>
        <br>
        <div class="botonesAccionesTabla">
            <button class="butonAccionTabla" id="loadMoreButtonReact">Adelante </button>
            <button class="butonAccionTabla Crea" v-if="isAdmin" id="crearReactivoButton">  Crear Reactivo</button>
            <button class="butonAccionTabla" id="loadPreviousButtonReact">Atrás</button>
        </div>
    </section>
    <section id="auxiliares" class="service-section">
        <h1>Lista de Auxiliares</h1>
        <div class="search-containerAuxiliares">
            <input type="text" id="searchInputAuxiliares" placeholder="Buscar...">
            <button id="searchButtonAuxiliares">Buscar</button>
            <button id="cancelSearchButtonAuxiliares">Cancelar</button>
        </div>
        <div class="table-container">
            <table border="1">
                <thead>
                <tr>
                    <th v-if="isAdmin">ID</th>
                    <th>Nombre</th>
                    <th>Cantidad</th>
                    <th>Stock Mínimo</th>
                    <th>Formato</th>
                    <th>Localización</th>
                    <th>Ubicación</th>
                    <th v-if="isAdmin">Acciones</th>

                </tr>
                </thead>
                <tbody id="auxiliarTableBody"></tbody>
            </table>
        </div>
        <br>
        <div class="botonesAccionesTabla">

            <button class="butonAccionTabla" id="loadMoreButtonAux">Adelante</button>
            <button class="butonAccionTabla Crea" v-if="isAdmin" id="crearAuxiliarButton">Crear Auxiliar</button>
            <button class="butonAccionTabla" id="loadPreviousButtonAux">Atrás</button>

        </div>
    </section>
    <section id="materiales" class="service-section">
        <h1>Lista de Auxiliares</h1>
        <div class="search-containerMateriales">
            <input type="text" id="searchInputMateriales" placeholder="Buscar...">
            <button id="searchButtonMateriales">Buscar</button>
            <button id="cancelSearchButtonMateriales">Cancelar</button>
        </div>
        <div class="table-container">
            <table border="1">
                <thead>
                <tr>

                    <th v-if="isAdmin">ID</th>
                    <th>Nombre</th>
                    <th>Cantidad</th>
                    <th>Stock Mínimo</th>

                    <th>Subcategoria</th>
                    <th>Numero Serie</th>
                    <th>Descripcion</th>

                    <th>Fecha Compra</th>
                    <th>Fecha Caducidad</th>

                    <th>Localización</th>
                    <th>Ubicación</th>

                    <th v-if="isAdmin">Acciones</th>

                </tr>
                </thead>
                <tbody id="materialTableBody"></tbody>
            </table>
        </div>
        <br>
        <div class="botonesAccionesTabla">
            <button class="butonAccionTabla" id="loadMoreButtonMat">Adelante</button>
            <button class="butonAccionTabla Crea" v-if="isAdmin" id="crearMaterialButton">Crear Material</button>
            <button class="butonAccionTabla" id="loadPreviousButtonMat">Atrás</button>
        </div>
    </section>
      
      

</section>

    
    `,



    mounted() {

        this.$nextTick(() => {
            loadTabla();
            iniciarMaterialesTabla();
            iniciarAuxiliaresTabla();
            iniciarReactivosTabla();
        });


    }



}