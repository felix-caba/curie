$(document).ready(function() {
    console.log("Document is ready");


    $('#registerForm').on('submit', function(event) {
        event.preventDefault(); // Previene el envío por defecto del formulario
        console.log("Form submit event captured");

        let form = $(this);
        let formData = form.serialize(); // Serializa los datos del formulario

        console.log("Form data serialized: ", formData);

        $.ajax({
            type: 'POST', // Usamos 'POST' directamente para asegurar que se use el método correcto
            url: '/register', // Usamos la URL directamente para asegurar que se use la ruta correcta
            data: formData,
            success: function(response) {
                console.log("Success callback executed");
                console.log("Response: ", response);

                // Maneja el éxito del registro, por ejemplo, redirigiendo a otra página
                window.location.href = "/index"; // Cambia a la URL de éxito
            },
            error: function(xhr) {
                console.log("Error callback executed");
                console.log("XHR status: ", xhr.status);
                console.log("XHR response: ", xhr.responseText);

                if (xhr.status === 409) { // Conflicto: nombre de usuario ya existe
                    $('#errorMessage').text("El nombre de usuario ya existe").show();
                } else {
                    $('#errorMessage').text("Ocurrió un error durante el registro").show();
                }
            }
        });
    });




});
