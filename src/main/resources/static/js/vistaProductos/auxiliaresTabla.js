
function iniciarAuxiliaresTabla(){


let offsetA = 0;
const limitA = 15;
const isAdmin = document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true';





let isSearchingAuxiliares = false;

    function loadDataAuxiliares(offsetA) {
        const searchQueryAuxiliar = document.getElementById('searchInputAuxiliares').value;
        let urlAuxiliar = `/api/auxiliares/paginados?offsetA=${offsetA}&limitA=${limitA}`;

        if (isSearchingAuxiliares && searchQueryAuxiliar) {
            urlAuxiliar = `/api/auxiliares/search?query=${encodeURIComponent(searchQueryAuxiliar)}`;
        }

        const tableBody = document.getElementById('auxiliarTableBody');

        // No borres el contenido existente inmediatamente
        // tableBody.innerHTML = '<tr><td colspan="8">Cargando...</td></tr>';

        fetch(urlAuxiliar)
            .then(response => response.json())
            .then(data => {
                const fragment = document.createDocumentFragment();

                data.forEach(item => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                    <td>${item.idProducto}</td>
                    <td>${item.nombre}</td>
                    <td>${item.cantidad}</td>
                    <td>${item.stockMinimo}</td>
                    <td>${item.formato}</td>
                    <td>${item.nombreLocalizacion}</td>
                    <td>${item.nombreUbicacion}</td>
                `;

                    if (isAdmin) {
                        row.innerHTML += `
                        <td>
                            <div class="botonesEditDeleteTabla">
                                <img class="botonAccionTabla" id="editButtonAuxiliar" src="/svg/edit.svg" alt="Editar" data-id="${item.idProducto}">
                                <img class="botonAccionTabla" id="deleteButtonAuxiliar" src="/svg/delete.svg" alt="Borrar" data-id="${item.idProducto}">
                            </div>
                        </td>
                    `;
                    }

                    fragment.appendChild(row);
                });

                // Usa setTimeout para retrasar la actualización del DOM
                setTimeout(() => {
                    tableBody.innerHTML = '';
                    tableBody.appendChild(fragment);
                    updatePaginationButtons(data.length);
                }, 50);
            })
            .catch(error => {
                console.error('Error:', error);
                tableBody.innerHTML = '<tr><td colspan="8">Error al cargar los datos. Por favor, intente de nuevo.</td></tr>';
            });
    }

    function updatePaginationButtons(dataLength) {
        const loadMoreButton = document.getElementById('loadMoreButtonAux');
        const loadPreviousButton = document.getElementById('loadPreviousButtonAux');
        const crearAuxiliarButton = document.getElementById('crearAuxiliarButton');

        if (isSearchingAuxiliares) {
            loadMoreButton.style.pointerEvents = 'none';
            loadPreviousButton.style.pointerEvents = 'none';
            crearAuxiliarButton.style.pointerEvents = 'none';
        } else {
            loadMoreButton.style.pointerEvents = dataLength < limitA ? 'none' : 'auto';
            loadPreviousButton.style.pointerEvents = offsetA === 0 ? 'none' : 'auto';
            crearAuxiliarButton.style.pointerEvents = 'auto';
        }
    }

function handleSearchAuxiliar() {
    offsetA = 0; // Reset the offset when searching
    isSearchingAuxiliares = true;
    loadDataAuxiliares(offsetA);
}

function loadPreviousData() {
    if (offsetA >= limitA) {

        offsetA -= limitA;
        loadDataAuxiliares(offsetA); // Recargar datos con el nuevo offsetA
    }
}

document.getElementById('searchButtonAuxiliares').addEventListener('click', handleSearchAuxiliar);
document.getElementById('searchInputAuxiliares').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        handleSearchAuxiliar();
    }
});

document.getElementById('loadPreviousButtonAux').addEventListener('click', loadPreviousData);




document.getElementById('loadMoreButtonAux').addEventListener('click', () => {
    offsetA += limitA;
    loadDataAuxiliares(offsetA);
});



document.getElementById('cancelSearchButtonAuxiliares').addEventListener('click', cancelSearchAuxiliar);

// XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD


document.addEventListener('DOMContentLoaded', loadDataAuxiliares);

function cancelSearchAuxiliar() {

    document.getElementById('searchButtonAuxiliares').value = '';
    isSearchingAuxiliares = false; // Restablecer la búsqueda
    loadDataAuxiliares(offsetA); // Cargar los resultados paginados
}

// Carga los primeros datos al cargar la página
loadDataAuxiliares(offsetA);


const modalAuxiliar = document.getElementById("editModalAuxiliar");



const spanAuxiliar = document.getElementsByClassName("close Auxiliar")[0];

function closeModal() {
            modalAuxiliar.classList.remove("active");
        }

spanAuxiliar.onclick = function() {
            closeModal();
        }

window.onclick = function(event) {
            if (event.target === modalAuxiliar) {
                closeModal();
            }
        }




document.getElementById('auxiliarTableBody').addEventListener('click', (event) => {
            if (event.target.id === 'editButtonAuxiliar') {
                const id = event.target.getAttribute('data-id');
                openEditModalAuxiliar(id);
            }
        });




if (isAdmin) {
    document.getElementById('crearAuxiliarButton').addEventListener('click', () => {
        openCreateModalAuxiliar();
    });
}



function openEditModalAuxiliar(id) {
        fetch(`/api/auxiliares/${id}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('editIdAuxiliar').value = data.idProducto;
                document.getElementById('editNombreAuxiliar').value = data.nombre;
                document.getElementById('editCantidadAuxiliar').value = data.cantidad;
                document.getElementById('editStockMinimoAuxiliar').value = data.stockMinimo;
                document.getElementById('editFormatoAuxiliar').value = data.formato;

                loadLocalizationsAuxiliar(data.nombreLocalizacion, data.nombreUbicacion);

                // Set the form mode to edit
                document.getElementById('editFormAuxiliar').setAttribute('data-mode', 'edit');

                const modalAuxiliar = document.getElementById("editModalAuxiliar");
                modalAuxiliar.classList.add("active");

            })
            .catch(error => console.error('Error:', error));
    }

function openCreateModalAuxiliar() {

        document.getElementById('editIdAuxiliar').value = '';
        document.getElementById('editNombreAuxiliar').value = '';
        document.getElementById('editCantidadAuxiliar').value = '';
        document.getElementById('editStockMinimoAuxiliar').value = '';
        document.getElementById('editFormatoAuxiliar').value = '';


        loadLocalizationsAuxiliar('Laboratorio 1', '');


        document.getElementById('editFormAuxiliar').setAttribute('data-mode', 'create');

        const modalAuxiliar = document.getElementById("editModalAuxiliar");
        modalAuxiliar.classList.add("active");
    }

function loadLocalizationsAuxiliar(selectedLocalizacion, selectedUbicacion) {
        console.log(selectedLocalizacion)
        console.log(selectedUbicacion)
        fetch(`/api/localizacion/`)
            .then(response => response.json())
            .then(localizaciones => {
                const localizacionSelect = document.getElementById('editLocalizacionAuxiliar');
                localizacionSelect.innerHTML = '';
                localizaciones.forEach(localizacion => {
                    const option = document.createElement('option');
                    option.value = localizacion.nombre;
                    option.text = localizacion.nombre;

                    if (localizacion.nombre === selectedLocalizacion) {
                        option.selected = true;
                    }
                    localizacionSelect.appendChild(option);
                });
                loadUbicacionesAuxiliar(selectedLocalizacion, selectedUbicacion);
            })
            .catch(error => console.error('Error:', error));
    }

function loadUbicacionesAuxiliar(localizacion, selectedUbicacion) {
        fetch(`/api/ubicacion/getFromNombre/${localizacion}`)
            .then(response => response.json())
            .then(ubicaciones => {
                const ubicacionSelect = document.getElementById('editUbicacionAuxiliar');
                ubicacionSelect.innerHTML = '';
                ubicaciones.forEach(ubicacion => {
                    const option = document.createElement('option');
                    option.value = ubicacion.nombre;
                    option.text = ubicacion.nombre;
                    if (ubicacion.nombre === selectedUbicacion) {
                        option.selected = true;
                    }
                    ubicacionSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error:', error));
    }

document.getElementById('editFormAuxiliar').addEventListener('submit', (event) => {
        event.preventDefault();
        const mode = document.getElementById('editFormAuxiliar').getAttribute('data-mode');
        const id = document.getElementById('editIdAuxiliar').value;


        const data = {
            nombre: document.getElementById('editNombreAuxiliar').value,
            cantidad: document.getElementById('editCantidadAuxiliar').value,
            stockMinimo: document.getElementById('editStockMinimoAuxiliar').value,
            formato: document.getElementById('editFormatoAuxiliar').value,

            nombreLocalizacion: document.getElementById('editLocalizacionAuxiliar').value,
            nombreUbicacion: document.getElementById('editUbicacionAuxiliar').value

        };

        const method = mode === 'edit' ? 'PUT' : 'POST';
        const url = mode === 'edit' ? `/api/auxiliares/${id}` : '/api/auxiliares/new';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(data => {
                closeModal();
                loadDataAuxiliares(offsetA);
            })
            .catch(error => console.error('Error:', error));
    });

document.getElementById('auxiliarTableBody').addEventListener('click', (event) => {
        if (event.target.id === 'deleteButtonAuxiliar') {
            const id = event.target.getAttribute('data-id');
            deleteAuxiliar(id);
        }
    });

function deleteAuxiliar(id) {
        fetch(`/api/auxiliares/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    loadDataAuxiliares(offsetA);
                } else {
                    console.error('Error deleting auxiliar:', response.statusText);
                }
            })
            .catch(error => console.error('Error:', error));
    }

document.getElementById('editLocalizacionAuxiliar').addEventListener('change', (event) => {
        const selectedLocalizacion = event.target.value;
        const selectedUbicacion = document.getElementById('editUbicacionAuxiliar').value;
        loadUbicacionesAuxiliar(selectedLocalizacion, selectedUbicacion);
    });


}







export {iniciarAuxiliaresTabla};






