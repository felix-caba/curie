document.addEventListener('DOMContentLoaded', () => {
    loadDataUbicaciones(offsetU);

    const modalUbicacion = document.getElementById("editModalUbicacion");
    const spanUbicacion = document.getElementsByClassName("close Ubicacion")[0];

    function closeModal() {
        modalUbicacion.classList.remove("active");
    }

    spanUbicacion.onclick = function() {
        closeModal();
    }

    window.onclick = function(event) {
        if (event.target == modalUbicacion) {
            closeModal();
        }
    }

    document.getElementById('ubicacionTableBody').addEventListener('click', (event) => {
        if (event.target.classList.contains('edit-buttonUbicacion')) {
            const id = event.target.getAttribute('data-id');
            openEditModalUbicacion(id);
        }
    });

    document.getElementById('crearUbicacionButton').addEventListener('click', () => {
        openCreateModalUbicacion();
    });
});

function openEditModalUbicacion(id) {
    fetch(`/api/ubicacion/${id}`)
        .then(response => response.json())
        .then(data => {

            console.log(data)

            document.getElementById('editIdUbicacion').value = data.idUbicacion;
            document.getElementById('editNombreUbicacion').value = data.nombreUbicacion;

            // Cargar las localizaciones y seleccionar la correspondiente

            console.log("nombre localizacion" + data.nombreLocalizacion)


            loadLocalizaciones(data.nombreLocalizacion);

            document.getElementById('editFormUbicacion').setAttribute('data-mode', 'edit');

            const modalUbicacion = document.getElementById("editModalUbicacion");
            modalUbicacion.classList.add("active");
        })
        .catch(error => console.error('Error:', error));
}

function openCreateModalUbicacion() {
    document.getElementById('editIdUbicacion').value = '';
    document.getElementById('editNombreUbicacion').value = '';

    // Cargar las localizaciones sin preseleccionar ninguna
    loadLocalizaciones('');

    document.getElementById('editFormUbicacion').setAttribute('data-mode', 'create');

    const modalUbicacion = document.getElementById("editModalUbicacion");
    modalUbicacion.classList.add("active");
}

function loadLocalizaciones(selectedLocalizacion) {
    fetch(`/api/localizacion/`)
        .then(response => response.json())
        .then(localizaciones => {
            const localizacionSelect = document.getElementById('editNombreLocalizacion');
            localizacionSelect.innerHTML = '';

            localizaciones.forEach(localizacion => {




                const option = document.createElement('option');
                option.value = localizacion.nombre;
                option.text = localizacion.nombre;

                console.log("seleccionada" + selectedLocalizacion);
                console.log("localizacion nombre" +localizacion.nombre);

                if (localizacion.nombre === selectedLocalizacion) {
                    option.selected = true;
                    console.log("entro");
                }

                localizacionSelect.appendChild(option);


            });
        })
        .catch(error => console.error('Error:', error));
}

document.getElementById('editFormUbicacion').addEventListener('submit', (event) => {
    event.preventDefault();
    const mode = document.getElementById('editFormUbicacion').getAttribute('data-mode');
    const id = document.getElementById('editIdUbicacion').value;

    const data = {
        nombreUbicacion: document.getElementById('editNombreUbicacion').value,
        nombreLocalizacion: document.getElementById('editNombreLocalizacion').value
    };

    const method = mode === 'edit' ? 'PUT' : 'POST';
    const url = mode === 'edit' ? `/api/ubicacion/${id}` : '/api/ubicacion/new';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(data => {
            const modalUbicacion = document.getElementById("editModalUbicacion");
            modalUbicacion.classList.remove("active");
            loadDataUbicaciones(offsetU);
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('ubicacionTableBody').addEventListener('click', (event) => {
    if (event.target.classList.contains('delete-buttonUbicacion')) {
        const id = event.target.getAttribute('data-id');
        deleteUbicacion(id);
    }
});

function deleteUbicacion(id) {
    fetch(`/api/ubicacion/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                loadDataUbicaciones(offsetU);
            } else {
                console.error('Error deleting ubicacion:', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
}
