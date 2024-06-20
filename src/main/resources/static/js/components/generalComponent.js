import Chart from 'chart.js'; // Asegúrate de importar Chart.js si no lo has hecho


export default {



    template: `
    
    <div>
    <canvas id="graficaProductos"></canvas>
    </div>
   
    `,

    mounted() {
        const canvas = document.getElementById('graficaProductos');
        const ctx = canvas.getContext('2d');

        const countProductos = parseInt(document.querySelector('meta[name="countProductos"]').getAttribute('content'));
        const countReactivos = parseInt(document.querySelector('meta[name="countReactivos"]').getAttribute('content'));
        const countProductosAuxiliares = parseInt(document.querySelector('meta[name="countProductosAuxiliares"]').getAttribute('content'));
        const countMateriales = parseInt(document.querySelector('meta[name="countMateriales"]').getAttribute('content'));


        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Total', 'Reactivos', 'Productos Auxiliares', 'Materiales'],
                datasets: [{
                    label: '# de Productos',
                    data: [
                        countProductos,
                        countReactivos,
                        countProductosAuxiliares,
                        countMateriales
                    ],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
};
