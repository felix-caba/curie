import {fillUserData} from "../miCuenta/miCuentaVista.js";

export default {



    template: `
     
     
    <section class="service-sectionFather"> 
    
    
    <h1>Mi Cuenta</h1>
    
    <form id="userForm">
            <label for="username">Nombre del usuario:</label>
            <input type="text" id="username" name="username" required><br><br>

            <label for="email">Email del usuario:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="pfp">Foto del usuario:</label><br>
            <img id="pfp" src="" alt="Foto de perfil" width="150"><br>
            <input type="file" id="pfpUpload" name="pfpUpload"><br><br>

            <button type="button" id="saveButton">Guardar Cambios</button>
        </form>
    
    
    </section>
     
     
     
     `,

    mounted() {

        fillUserData();

    }


}