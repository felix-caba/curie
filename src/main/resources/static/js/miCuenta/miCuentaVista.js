
import {showNotification} from "../notificationGenerator.js";

function fillUserData() {


        const userForm = document.getElementById('userForm');
        const usernameInput = document.getElementById('username');
        const emailInput = document.getElementById('email');
        const pfpImage = document.getElementById('pfp');
        const pfpUpload = document.getElementById('pfpUpload');
        const saveButton = document.getElementById('saveButton');

        // Función para obtener datos del usuario actual
        function getUserData() {
            fetch('/api/usuarios/current')

                .then(response => response.json())
                .then(data => {
                    usernameInput.value = data.username;
                    emailInput.value = data.email;
                    pfpImage.src = data.pfp64 ? `data:image/png;base64,${data.pfp64}` : 'default-profile.png';
                })
                .catch(error => console.error('Error al obtener los datos del usuario:', error));
        }


        getUserData();

    function toBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result.split(',')[1]);
            reader.onerror = error => reject(error);
        });
    }

    saveButton.addEventListener('click', async function () {
        const username = usernameInput.value;
        const email = emailInput.value;
        let pfp64 = null;

        if (pfpUpload.files.length > 0 && pfpUpload.files.length < 5000) {
            try {

                // check if the file is an image

                if (!pfpUpload.files[0].type.startsWith('image/')) {
                    showNotification('El archivo seleccionado no es una imagen', "error");
                    return;
                }

                pfp64 = await toBase64(pfpUpload.files[0]);


            } catch (error) {
                showNotification('Error al convertir la imagen a base64', "error");
                return;

            }
        }

        const userData = {
            username: username,
            email: email,
            pfp64: pfp64
        };

        fetch('/api/usuarios/updateCurrent', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        })
            .then(response => {
                if (response.ok) {
                    window.location.reload();
                    alert('Datos actualizados correctamente');
                    getUserData(); // Volver a obtener los datos para actualizar la vista
                } else {
                    alert('Error al actualizar los datos');
                }
            })
            .catch(error => console.error('Error al actualizar los datos del usuario:', error));
    });

}
export {fillUserData};
