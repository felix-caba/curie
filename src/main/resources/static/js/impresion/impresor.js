import 'jspdf-autotable';
import autoTable from "jspdf-autotable";


function impresion (){


    document.getElementById('imprimirAuxiliarCantidad').addEventListener('click', function() {
        const cantidad = document.getElementById('cantidadInput').value;
        imprimirAuxiliaresCantidad(cantidad);
    });
    document.getElementById('imprimirAuxiliarTodos').addEventListener('click', function() {
        imprimirAuxiliaresTodos();
    });

    function imprimirAuxiliaresCantidad(cantidad) {
        fetch(`/api/auxiliares/cantidadMenor/${cantidad}`)
            .then(response => response.json())
            .then(data => generarPDF(data, 'cantidad'));
    }
    function imprimirAuxiliaresTodos() {
        fetch(`/api/auxiliares/all`)
            .then(response => response.json())
            .then(data => generarPDF(data, ''));
    }



    document.getElementById('imprimirMaterialCantidad').addEventListener('click', function() {
        const cantidad = document.getElementById('cantidadInput').value;
        imprimirMaterialesCantidad(cantidad);
    });
    document.getElementById('imprimirMaterialTodos').addEventListener('click', function() {
        imprimirMaterialesTodos();
    });

    function imprimirMaterialesCantidad(cantidad) {
        fetch(`/api/materiales/cantidadMenor/${cantidad}`)
            .then(response => response.json())
            .then(data => generarPDF(data, 'cantidad'));
    }
    function imprimirMaterialesTodos() {
        fetch(`/api/materiales/all`)
            .then(response => response.json())
            .then(data => generarPDF(data, ''));
    }



    document.getElementById('imprimirReactivoCantidad').addEventListener('click', function() {
        const cantidad = document.getElementById('cantidadInput').value;
        imprimirReactivosPorCantidad(cantidad);
    });
    document.getElementById('imprimirReactivoCaducidad').addEventListener('click', function() {
        const fechaCaducidad = document.getElementById('fechaCaducidadInput').value;
        imprimirReactivosPorFecha(fechaCaducidad);
    });
    document.getElementById('imprimirReactivoTodos').addEventListener('click', function() {
        imprimirReactivosTodos();
    });

    function imprimirReactivosPorCantidad(cantidad) {
        fetch(`/api/reactivos/cantidadMenor/${cantidad}`)
            .then(response => response.json())
            .then(data => generarPDF(data, 'cantidad'));
    }
    function imprimirReactivosPorFecha(fechaCaducidad) {
        fetch(`/api/reactivos/fechaCaducidadMenor/${fechaCaducidad}`)
            .then(response => response.json())
            .then(data => generarPDF(data, 'fechaCaducidad'));
    }
    function imprimirReactivosTodos() {
        fetch(`/api/reactivos/all`)
            .then(response => response.json())
            .then(data => generarPDF(data, ''));
    }


    function generarPDF(data, columna) {

        const { jsPDF } = window.jspdf;

        const doc = new jsPDF();


        const headers = ["ID", "NOMBRE", "LOCALIZACION", "UBICACION", columna.toUpperCase()];
        const rows = data.map(item => [item.idProducto, item.nombre, item.nombreLocalizacion, item.nombreUbicacion, item[columna]]);


        autoTable(doc, {
            head: [headers],
            body: rows,
        });


        doc.save("productos.pdf");

    }


}


export {impresion};


