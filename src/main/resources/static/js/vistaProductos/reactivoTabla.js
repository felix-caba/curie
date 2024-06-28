
function iniciarReactivosTabla() {

let offset = 0;
const limit = 15;

const isAdmin = document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true';

let isSearchingReactivo = false;

function loadData(offset) {

    const searchQueryReactivo = document.getElementById('searchInputReactivos').value;


    let urlReactivo = `/api/reactivos/paginados?offset=${offset}&limit=${limit}`;

    if (isSearchingReactivo && searchQueryReactivo) {
        urlReactivo = `/api/reactivos/search?query=${encodeURIComponent(searchQueryReactivo)}`;
    }

    fetch(urlReactivo)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('reagentTableBody');
            tableBody.innerHTML = '';
            data.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `
                   <td>${item.idProducto}</td>
                    <td>${item.nombre}</td>
                    <td>${item.cantidad}</td>
                    <td>${item.stockMinimo}</td>
                    <td>${item.formato}</td>
                    <td>${item.gradoPureza}</td>
                    <td>${item.fechaCaducidad ? item.fechaCaducidad : ''}</td>
                    <td>${item.nombreLocalizacion}</td>
                    <td>${item.nombreUbicacion}</td>
                    
                    <td> ${generateRiskCellContent(item.riesgos)} </td>`;

                   if (isAdmin) {
                       row.innerHTML += `

                  <td>
                    <div class="botonesEditDeleteTabla">
                         <button class="edit-button" id="editButtonReactivo" data-id="${item.idProducto}">Editar</button>
                         <button class="delete-button" id="deleteButtonReactivo" data-id="${item.idProducto}">Borrar</button>
                    </div>
                  </td>`;

                }

                tableBody.appendChild(row);
            });

            if (isSearchingReactivo) {
                document.getElementById('loadMoreButtonReact').style.pointerEvents = 'none';
                document.getElementById('loadPreviousButtonReact').style.pointerEvents = 'none';
                document.getElementById('crearReactivoButton').style.pointerEvents = 'none';
            } else {
                document.getElementById('loadMoreButtonReact').style.pointerEvents = data.length < limit ? 'none' : 'auto';
                document.getElementById('loadPreviousButtonReact').style.pointerEvents = offset === 0 ? 'none' : 'auto';
                document.getElementById('crearReactivoButton').style.pointerEvents = 'auto';
            }

        })
        .catch(error => console.error('Error:', error));
}

function handleSearchReactivo() {
    offset = 0; // Reset the offset when searching
    isSearchingReactivo = true;
    loadData(offset);
}

document.getElementById('searchButtonReactivos').addEventListener('click', handleSearchReactivo);
document.getElementById('searchInputReactivos').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        handleSearchReactivo();
    }
});

function loadPreviousData() {
    if (offset >= limit) {
        offset -= limit;
        loadData(offset); // Recargar datos con el nuevo offset
    }
}

document.getElementById('loadPreviousButtonReact').addEventListener('click', loadPreviousData);
document.getElementById('loadMoreButtonReact').addEventListener('click', () => {
    offset += limit;
    loadData(offset);
});

function generateRiskCellContent(riesgos) {
    if (!riesgos || riesgos.length === 0) {
        return '';
    }

    let content = '<div class="risk-container">';

    riesgos.forEach((risk, index) => {
        if (index > 0 && index % 2 === 0) {
            content += '</div><div class="risk-container">';
        }
        content += `
            <div class="risk-image">
                <img src="data:image/jpeg;base64,${risk.imagenBase64}" alt="Imagen del riesgo" title="${risk.descripcion}">
            </div>
        `;
    });

    content += '</div>';

    return content;
}

document.getElementById('loadMoreButtonReact').addEventListener('click', loadData);

document.addEventListener('DOMContentLoaded', loadData);

document.getElementById('cancelSearchButtonReactivos').addEventListener('click', cancelSearchReactivo);

function cancelSearchReactivo() {

    document.getElementById('searchInputReactivos').value = '';
    isSearchingReactivo = false; // Restablecer la búsqueda
    loadData(offset); // Cargar los resultados paginados
}

// Carga los primeros datos al cargar la página
loadData(offset);





    const modalReactivo = document.getElementById("editModalReactivo");
    const spanReactivo = document.getElementsByClassName("close Reactivo")[0];


    // Function to close the modal
    function closeModal() {
        modalReactivo.classList.remove("active");
    }


    // When the user clicks on <spanReactivo> (x), close the modalReactivo
    spanReactivo.onclick = function() {
        closeModal();
    }

    // When the user clicks anywhere outside of the modalReactivo, close it
    window.onclick = function(event) {
        if (event.target === modalReactivo) {
            closeModal();
        }
    }

    document.getElementById('reagentTableBody').addEventListener('click', (event) => {
        if (event.target.id === 'editButtonReactivo') {
            const id = event.target.getAttribute('data-id');
            openEditModalReactivo(id);
        }
    });
if (isAdmin) {
    document.getElementById('crearReactivoButton').addEventListener('click', () => {
        openCreateModalReactivo();
    });
}

    function openEditModalReactivo(id) {
        fetch(`/api/reactivos/${id}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('editIdReactivo').value = data.idProducto;
                document.getElementById('editNombreReactivo').value = data.nombre;
                document.getElementById('editCantidadReactivo').value = data.cantidad;
                document.getElementById('editStockMinimoReactivo').value = data.stockMinimo;
                document.getElementById('editFormatoReactivo').value = data.formato;
                document.getElementById('editGradoPurezaReactivo').value = data.gradoPureza;


                document.getElementById('editFechaCaducidadReactivo').value = data.fechaCaducidad ? data.fechaCaducidad : '';


                loadLocalizationsReactivo(data.nombreLocalizacion, data.nombreUbicacion);
                loadRisks(data.riesgos);


                // Set the form mode to edit
                document.getElementById('editFormReactivo').setAttribute('data-mode', 'edit');


                const modalReactivo = document.getElementById("editModalReactivo");
                modalReactivo.classList.add("active");



            })
            .catch(error => console.error('Error:', error));
    }
    function openCreateModalReactivo() {
        // Clear the form fields
        document.getElementById('editIdReactivo').value = '';
        document.getElementById('editNombreReactivo').value = '';
        document.getElementById('editCantidadReactivo').value = '';
        document.getElementById('editStockMinimoReactivo').value = '';
        document.getElementById('editFormatoReactivo').value = '';
        document.getElementById('editGradoPurezaReactivo').value = '';
        document.getElementById('editFechaCaducidadReactivo').value = '';
        document.getElementById('editRiesgos').innerHTML = '';

        // Load localizations and risks for the new reactivo
        loadLocalizationsReactivo('Laboratorio 1', '');
        loadRisks([]);

        // Set the form mode to create
        document.getElementById('editFormReactivo').setAttribute('data-mode', 'create');

        const modalReactivo = document.getElementById("editModalReactivo");

        modalReactivo.classList.add("active");

    }
    function loadRisks(riesgos) {
        const riesgosContainer = document.getElementById('editRiesgos');
        riesgosContainer.innerHTML = '';

        fetch('/api/riesgos')
            .then(response => response.json())
            .then(allRiesgos => {
                allRiesgos.forEach(risk => {
                    const riesgoContainer = document.createElement('div');
                    riesgoContainer.classList.add('riesgo-container');

                    const checkbox = document.createElement('input');
                    checkbox.type = 'checkbox';
                    checkbox.name = 'riesgo';
                    checkbox.value = risk.idRiesgo;

                    const isChecked = riesgos.some(selected => selected.idRiesgo === risk.idRiesgo);
                    checkbox.checked = isChecked;

                    riesgoContainer.appendChild(checkbox);

                    const label = document.createElement('label');
                    label.textContent = risk.descripcion;
                    riesgoContainer.appendChild(label);

                    if (risk.imagen) {
                        const imagen = document.createElement('img');
                        imagen.src = 'data:image/jpeg;base64,' + risk.imagen;
                        imagen.alt = 'Imagen del riesgo';
                        imagen.title = risk.descripcion;
                        riesgoContainer.appendChild(imagen);
                    }

                    riesgosContainer.appendChild(riesgoContainer);
                    riesgosContainer.appendChild(document.createElement('br'));
                });
            })
            .catch(error => console.error('Error:', error));
    }
    function loadLocalizationsReactivo(selectedLocalizacion, selectedUbicacion) {


        fetch(`/api/localizacion/`)
            .then(response => response.json())
            .then(localizaciones => {


                const localizacionSelect = document.getElementById('editLocalizacionReactivo');
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



                loadUbicacionesReactivo(selectedLocalizacion, selectedUbicacion);
            })
            .catch(error => console.error('Error:', error));
    }

    function loadUbicacionesReactivo(localizacion, selectedUbicacion) {
        fetch(`/api/ubicacion/getFromNombre/${localizacion}`)
            .then(response => response.json())
            .then(ubicaciones => {



                const ubicacionSelect = document.getElementById('editUbicacionReactivo');
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

    document.getElementById('editFormReactivo').addEventListener('submit', (event) => {
        event.preventDefault();
        const mode = document.getElementById('editFormReactivo').getAttribute('data-mode');
        const id = document.getElementById('editIdReactivo').value;

        const selectedRiesgos = Array.from(document.querySelectorAll('input[name=riesgo]:checked')).map(checkbox => {
            return {
                idRiesgo: parseInt(checkbox.value),
                descripcion: checkbox.nextSibling.textContent,
                imagenBase64: checkbox.nextSibling.nextSibling ? checkbox.nextSibling.nextSibling.src : ''
            };
        });

        const fechaCaducidad = new Date(document.getElementById('editFechaCaducidadReactivo').value);
        const fechaFormateada = fechaCaducidad.toISOString().split('T')[0];

        const data = {
            nombre: document.getElementById('editNombreReactivo').value,
            cantidad: document.getElementById('editCantidadReactivo').value,
            stockMinimo: document.getElementById('editStockMinimoReactivo').value,
            formato: document.getElementById('editFormatoReactivo').value,
            gradoPureza: document.getElementById('editGradoPurezaReactivo').value,
            fechaCaducidad: fechaFormateada,
            nombreLocalizacion: document.getElementById('editLocalizacionReactivo').value,
            nombreUbicacion: document.getElementById('editUbicacionReactivo').value,
            riesgos: selectedRiesgos

        };

        const method = mode === 'edit' ? 'PUT' : 'POST';
        const url = mode === 'edit' ? `/api/reactivos/${id}` : '/api/reactivos/new';

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
                loadData(offset);
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('reagentTableBody').addEventListener('click', (event) => {
        if (event.target.id === 'deleteButtonReactivo') {
            const id = event.target.getAttribute('data-id');
            deleteReactivo(id);
        }
    });

    function deleteReactivo(id) {
        fetch(`/api/reactivos/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    loadData(offset);
                } else {
                    console.error('Error deleting reactivo:', response.statusText);
                }
            })
            .catch(error => console.error('Error:', error));
    }

    document.getElementById('editLocalizacionReactivo').addEventListener('change', (event) => {
        const selectedLocalizacion = event.target.value;
        const selectedUbicacion = document.getElementById('editUbicacionReactivo').value;
        loadUbicacionesReactivo(selectedLocalizacion, selectedUbicacion);
    });


}

export {iniciarReactivosTabla};







