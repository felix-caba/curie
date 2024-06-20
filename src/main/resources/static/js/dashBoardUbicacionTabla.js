let offsetU = 0;
const limitU = 15;

let isSearchingUbicacion = false;

function loadDataUbicaciones(offsetU) {

    const searchQueryUbicacion = document.getElementById('searchInputUbicaciones').value;

    let urlUb = `/api/ubicacion/paginados?offsetU=${offsetU}&limitU=${limitU}`;

    if (isSearchingUbicacion && searchQueryUbicacion) {
        urlUb = `/api/ubicacion/search?query=${encodeURIComponent(searchQueryUbicacion)}`;
    }

    fetch(urlUb)
        .then(response => response.json())
        .then(data => {

            console.log(data);

            const tableBody = document.getElementById('ubicacionTableBody');
            tableBody.innerHTML = '';
            data.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `



                    <td>${item.idUbicacion}</td>
                    <td>${item.nombreUbicacion}</td>
                    <td>${item.nombreLocalizacion}</td>
                  
   
           
                   <td >
                   
                        <button class="edit-buttonUbicacion" data-id="${item.idUbicacion}">Editar</button>
                        <button class="delete-buttonUbicacion" data-id="${item.idUbicacion}">Borrar</button>
                        
                    </td>
                    
                `;
                tableBody.appendChild(row);
            });


            if (isSearchingUbicacion) {
                document.getElementById('loadMoreButtonUbicacion').style.pointerEvents = 'none';
                document.getElementById('loadPreviousButtonUbicacion').style.pointerEvents = 'none';
                document.getElementById('crearUbicacionButton').style.pointerEvents = 'none';
            } else {
                document.getElementById('loadMoreButtonUbicacion').style.pointerEvents = data.length < limit ? 'none' : 'auto';
                document.getElementById('loadPreviousButtonUbicacion').style.pointerEvents = offsetU === 0 ? 'none' : 'auto';
                document.getElementById('crearUbicacionButton').style.pointerEvents = 'auto';
            }

        })
        .catch(error => console.error('Error:', error));
}

function handleSearchUbicacion() {
    offsetU = 0; // Reset the offset when searching
    isSearchingUbicacion = true;
    loadDataUbicaciones(offsetU);
}

function loadPreviousData() {
    if (offsetU >= limitU) {
        offsetU -= limitU;
        loadDataUbicaciones(offsetU); // Recargar datos con el nuevo offsetA
    }
}



document.getElementById('searchButtonUbicaciones').addEventListener('click', handleSearchUbicacion);
document.getElementById('searchInputUbicaciones').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        handleSearchUbicacion();
    }
});

document.getElementById('loadPreviousButtonUbicacion').addEventListener('click', loadPreviousData);
document.getElementById('loadMoreButtonUbicacion').addEventListener('click', () => {
    offsetU += limitU;
    loadDataUbicaciones(offsetU);
});



document.getElementById('cancelSearchButtonUbicaciones').addEventListener('click', cancelSearchUbicacion);

document.getElementById('loadMoreButtonUbicacion').addEventListener('click', loadDataUbicaciones);

document.addEventListener('DOMContentLoaded', loadDataUbicaciones);

function cancelSearchUbicacion() {

    document.getElementById('searchButtonUbicaciones').value = '';
    isSearchingUbicacion = false; // Restablecer la búsqueda
    loadDataUbicaciones(offsetU); // Cargar los resultados paginados
}

// Carga los primeros datos al cargar la página
loadDataUbicaciones(offsetU);








