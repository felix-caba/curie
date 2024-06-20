import {impresion} from "../impresion/impresor.js";


export default {

    template: `
        
        <section class="service-sectionFather">
  <h1>Selecciona el elemento a imprimir</h1>

  <div class="impresionesContenedor">

    <div class="impresionesBotones">

      <h2 id="impresionTituloTodos">Imprimir Total</h2>

      <div class="impresionTotal">
        <button id="imprimirReactivoTodos">Imprimir Reactivos</button>
        <button id="imprimirAuxiliarTodos">Imprimir Auxiliares</button>
        <button id="imprimirMaterialTodos">Imprimir Materiales</button>
      </div>

      <h2 id="impresionTituloCantidad">Imprimir por Cantidad</h2>

      <div class="auxiliarDeCantidad">
        <label for="cantidadInput">Introduce una cantidad desde la que quieres realizar la impresión</label>
        <input type="number" id="cantidadInput" placeholder="Cantidad">
      </div>

      <div class="impresionPorCantidad">
        <button id="imprimirReactivoCantidad">Imprimir Reactivos</button>
        <button id="imprimirAuxiliarCantidad">Imprimir Auxiliares</button>
        <button id="imprimirMaterialCantidad">Imprimir Materiales</button>
      </div>

      <h2 id="impresionTituloCaducidad">Imprimir por Fecha</h2>

      <div class="impresionPorFechaDeCaducidad">

        <label for="fechaCaducidadInput">Introduce desde la fecha que quieres realizar la impresión</label>
        <input type="date" id="fechaCaducidadInput">
        <button id="imprimirReactivoCaducidad">Imprimir Reactivo</button>

      </div>

    </div>

  </div>


</section>
    
    `,

    mounted() {

        impresion();

    }



}