function iniciarMaterialesTabla(){



let offsetM = 0;
const limitM = 15;
const isAdmin = document.querySelector('meta[name="isAdmin"]').getAttribute('content') === 'true';

let isSearchingMaterial = false;

function loadDataMateriales(offsetM) {

    const searchQueryMaterial = document.getElementById('searchInputMateriales').value;

    let urlMaterial = `/api/materiales/paginados?offsetM=${offsetM}&limitM=${limitM}`;

    if (isSearchingMaterial && searchQueryMaterial) {
        urlMaterial = `/api/materiales/search?query=${encodeURIComponent(searchQueryMaterial)}`;
    }


    fetch(urlMaterial)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('materialTableBody');
            tableBody.innerHTML = '';
            data.forEach(item => {
                const row = document.createElement('tr');
                row.innerHTML = `

                    <td>${item.idProducto}</td>
                    <td>${item.nombre}</td>
                    <td>${item.cantidad}</td>
                    <td>${item.stockMinimo}</td>
                    
                    <td>${item.subcategoria}</td>
                    <td>${item.numero_serie}</td>
                    <td>${item.descripcion}</td>
                    
                    <td>${item.fecha_compra ? item.fecha_compra : ''}</td>
                    <td>${item.fechaCaducidad ? item.fechaCaducidad : ''}</td>
                    
                    <td>${item.nombreLocalizacion}</td>
                    <td>${item.nombreUbicacion}</td>`;
             
                      if (isAdmin) {
                          row.innerHTML += `

                    <td>
                        <div class="botonesEditDeleteTabla">
                                <img class="botonAccionTabla" id="editButtonMaterial" src="/svg/edit.svg" alt="Editar" data-id="${item.idProducto}">
                                <img class="botonAccionTabla" id="deleteButtonMaterial" src="/svg/delete.svg" alt="Borrar" data-id="${item.idProducto}">
                            </div>
                    </td>`;

                      }
                          tableBody.appendChild(row);
            });


            if (isSearchingMaterial) {
                document.getElementById('loadMoreButtonMat').style.pointerEvents = 'none';
                document.getElementById('loadPreviousButtonMat').style.pointerEvents = 'none';
                document.getElementById('crearMaterialButton').style.pointerEvents = 'none';
            } else {
                document.getElementById('loadMoreButtonMat').style.pointerEvents = data.length < limitM ? 'none' : 'auto';
                document.getElementById('loadPreviousButtonMat').style.pointerEvents = offsetM === 0 ? 'none' : 'auto';
                document.getElementById('crearMaterialButton').style.pointerEvents = 'auto';
            }


        })
        .catch(error => console.error('Error:', error));
}

function handleSearchMaterial() {
    offsetM = 0; // Reset the offset when searching
    isSearchingMaterial = true;
    loadDataMateriales(offsetM);
}

function loadPreviousData() {
    if (offsetM >= limitM) {
        offsetM -= limitM;
        loadDataMateriales(offsetM); // Recargar datos con el nuevo offsetM
    }
}

document.getElementById('searchButtonMateriales').addEventListener('click', handleSearchMaterial);
document.getElementById('searchInputMateriales').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
        handleSearchMaterial();
    }
});

document.getElementById('loadPreviousButtonMat').addEventListener('click', loadPreviousData);
document.getElementById('loadMoreButtonMat').addEventListener('click', () => {
    offsetM += limitM;
    loadDataMateriales(offsetM);
});



document.addEventListener('DOMContentLoaded', loadDataMateriales);

document.getElementById('cancelSearchButtonMateriales').addEventListener('click', cancelSearchMaterial);

function cancelSearchMaterial() {

    document.getElementById('searchButtonMateriales').value = '';
    isSearchingMaterial = false; // Restablecer la búsqueda
    loadData(offsetM); // Cargar los resultados paginados
}


// Carga los primeros datos al cargar la página
loadDataMateriales(offsetM);




const modalMaterial = document.getElementById("editModalMaterial");
const spanMaterial = document.getElementsByClassName("close Material")[0];

function closeModal() {
    modalMaterial.classList.remove("active");
}


        // When the user clicks on <spanMaterial> (x), close the modalMaterial
spanMaterial.onclick = function() {
    closeModal();
}

        // When the user clicks anywhere outside of the modalMaterial, close it
 window.onclick = function(event) {
            if (event.target === modalMaterial) {
                closeModal();
            }
        }

        document.getElementById('materialTableBody').addEventListener('click', (event) => {

            if (event.target.id === 'editButtonMaterial') {
                const id = event.target.getAttribute('data-id');
                openEditModalMaterial(id);
            }

        });

if (isAdmin) {
    document.getElementById('crearMaterialButton').addEventListener('click', () => {
        openCreateModalMaterial();
    });

}




    function openEditModalMaterial(id) {
        fetch(`/api/materiales/${id}`)
            .then(response => response.json())
            .then(data => {

                document.getElementById('editIdMaterial').value = data.idProducto;
                document.getElementById('editNombreMaterial').value = data.nombre;
                document.getElementById('editCantidadMaterial').value = data.cantidad;
                document.getElementById('editStockMinimoMaterial').value = data.stockMinimo;


                document.getElementById('editSubcategoriaMaterial').value = data.subcategoria;
                document.getElementById('editNumeroDeSerieMaterial').value = data.numero_serie;
                document.getElementById('editDescripcionMaterial').value = data.descripcion;

                document.getElementById('editFechaCompraMaterial').value = data.fecha_compra ? data.fecha_compra : '';

                document.getElementById('editFechaCaducidadMaterial').value = data.fechaCaducidad ? data.fechaCaducidad : '';




                loadLocalizationsMaterial(data.nombreLocalizacion, data.nombreUbicacion);

                // Set the form mode to edit
                document.getElementById('editFormMaterial').setAttribute('data-mode', 'edit');

                const modalMaterial = document.getElementById("editModalMaterial");
                modalMaterial.classList.add("active");
            })
            .catch(error => console.error('Error:', error));
    }

    function openCreateModalMaterial() {
        // Clear the form fields
        document.getElementById('editIdMaterial').value = '';
        document.getElementById('editNombreMaterial').value = '';
        document.getElementById('editCantidadMaterial').value = '';
        document.getElementById('editStockMinimoMaterial').value = '';

        document.getElementById('editSubcategoriaMaterial').value = '';

        document.getElementById('editNumeroDeSerieMaterial').value = '';
        document.getElementById('editDescripcionMaterial').value = '';
        document.getElementById('editFechaCompraMaterial').value = '';
        document.getElementById('editFechaCaducidadMaterial').value = '';


        // Load localizations and risks for the new reactivo
        loadLocalizationsMaterial('Laboratorio 1', '');

        // Set the form mode to create
        document.getElementById('editFormMaterial').setAttribute('data-mode', 'create');

        const modalMaterial = document.getElementById("editModalMaterial");
        modalMaterial.classList.add("active");

    }

    function loadLocalizationsMaterial(selectedLocalizacion, selectedUbicacion) {
        console.log(selectedLocalizacion)
        console.log(selectedUbicacion)
        fetch(`/api/localizacion/`)
            .then(response => response.json())
            .then(localizaciones => {
                const localizacionSelect = document.getElementById('editLocalizacionMaterial');
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
                loadUbicacionesMaterial(selectedLocalizacion, selectedUbicacion);
            })
            .catch(error => console.error('Error:', error));
    }

    function loadUbicacionesMaterial(localizacion, selectedUbicacion) {
        fetch(`/api/ubicacion/getFromNombre/${localizacion}`)
            .then(response => response.json())
            .then(ubicaciones => {
                const ubicacionSelect = document.getElementById('editUbicacionMaterial');
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

    document.getElementById('editFormMaterial').addEventListener('submit', (event) => {
        event.preventDefault();
        const mode = document.getElementById('editFormMaterial').getAttribute('data-mode');
        const id = document.getElementById('editIdMaterial').value;

        const editFechaCompraMaterialValue = document.getElementById('editFechaCompraMaterial').value;
        const fecha_compraMaterial = editFechaCompraMaterialValue ? new Date(editFechaCompraMaterialValue) : null;
        const fecha_compraFormateadaMaterial = fecha_compraMaterial ? fecha_compraMaterial.toISOString().split('T')[0] : null;

        const editFechaCaducidadMaterialValue = document.getElementById('editFechaCaducidadMaterial').value;
        const fechaCaducidadMaterial = editFechaCaducidadMaterialValue ? new Date(editFechaCaducidadMaterialValue) : null;
        const fechaFormateadaMaterial = fechaCaducidadMaterial ? fechaCaducidadMaterial.toISOString().split('T')[0] : null;


        const data = {

            nombre: document.getElementById('editNombreMaterial').value,
            cantidad: document.getElementById('editCantidadMaterial').value,
            stockMinimo: document.getElementById('editStockMinimoMaterial').value,


            subcategoria: document.getElementById('editSubcategoriaMaterial').value,
            numero_serie: document.getElementById('editNumeroDeSerieMaterial').value,
            descripcion: document.getElementById('editDescripcionMaterial').value,

            fecha_compra: fecha_compraFormateadaMaterial,
            fechaCaducidad: fechaFormateadaMaterial,


            nombreLocalizacion: document.getElementById('editLocalizacionMaterial').value,
            nombreUbicacion: document.getElementById('editUbicacionMaterial').value

        };

        const method = mode === 'edit' ? 'PUT' : 'POST';
        const url = mode === 'edit' ? `/api/materiales/${id}` : '/api/materiales/new';

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
                loadDataMateriales(offsetM);
            })
            .catch(error => console.error('Error:', error));
    });

    document.getElementById('materialTableBody').addEventListener('click', (event) => {
        if (event.target.id === 'deleteButtonMaterial') {
            const id = event.target.getAttribute('data-id');
            deleteMaterial(id);
        }
    });

    function deleteMaterial(id) {
        fetch(`/api/materiales/${id}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    loadDataMateriales(offsetM);
                } else {
                    console.error('Error deleting auxiliar:', response.statusText);
                }
            })
            .catch(error => console.error('Error:', error));
    }

    document.getElementById('editLocalizacionMaterial').addEventListener('change', (event) => {
        const selectedLocalizacion = event.target.value;
        const selectedUbicacion = document.getElementById('editUbicacionMaterial').value;
        loadUbicacionesMaterial(selectedLocalizacion, selectedUbicacion);
    });








}

export {iniciarMaterialesTabla};







