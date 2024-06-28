
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


    console.log('offsetA', offsetA);
    fetch(urlAuxiliar)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('auxiliarTableBody');
            tableBody.innerHTML = '';
            data.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `
                  <td>${item.idProducto}</td>
                  <td>${item.nombre}</td>
                  <td>${item.cantidad}</td>
                   <td>${item.stockMinimo}</td>
                   <td>${item.formato}</td>
                  <td>${item.nombreLocalizacion}</td>
                   <td>${item.nombreUbicacion}</td>`;

                   if (isAdmin) {
                    row.innerHTML += `

                  <td>
                      <div class="botonesEditDeleteTabla">
                          <button class="edit-button" id="editButtonAuxiliar" data-id="${item.idProducto}">Editar</button>
                          <button class="delete-button" id="deleteButtonAuxiliar" data-id="${item.idProducto}">Borrar</button>
                    </div>
                  </td>`;



                }

                tableBody.appendChild(row);
            });


            if (isSearchingAuxiliares) {
                document.getElementById('loadMoreButtonAux').style.pointerEvents = 'none';
                document.getElementById('loadPreviousButtonAux').style.pointerEvents = 'none';
                document.getElementById('crearAuxiliarButton').style.pointerEvents = 'none';
            } else {
                document.getElementById('loadMoreButtonAux').style.pointerEvents = data.length < limitA ? 'none' : 'auto';
                document.getElementById('loadPreviousButtonAux').style.pointerEvents = offsetA === 0 ? 'none' : 'auto';
                document.getElementById('crearAuxiliarButton').style.pointerEvents = 'auto';
            }


        })
        .catch(error => console.error('Error:', error));
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

document.getElementById('loadMoreButtonAux').addEventListener('click', loadDataAuxiliares);

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
                console.log('Editando auxiliar')
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






