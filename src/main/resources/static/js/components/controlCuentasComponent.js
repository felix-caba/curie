import {TabulatorFull as Tabulator} from 'tabulator-tables';


export default {


    template: `
    
        <section class="service-sectionFather"> 
        
        
        <div id="user-table"></div>
        
      
        

        </section>
    
    
     `,

    mounted() {

            var table = new Tabulator("#user-table", {
                ajaxURL: "/api/usuarios/allExceptCurrent", // URL de la API para cargar datos
                layout: "fitColumns",
                columns: [
                    { title: "ID", field: "id", editor: "input", visible: false },
                    { title: "Username", field: "username", editor: "input" },
                    { title: "Email", field: "email", editor: "input" },
                    { title: "Password", field: "password", editor: "input", formatter: "plaintext", visible: false },

                    { title: "Admin", field: "admin", formatter: "tickCross",

                        formatterParams: {
                            allowEmpty:false,
                            allowTruthy:true,
                        },

                        responsive: 1,

                        cellClick: function (e, cell) {
                            // Toggle the value of the cell
                            var currentValue = cell.getValue();
                            var newValue = currentValue === 1 ? 0 : 1;
                            cell.setValue(newValue);
                        }



                    },

                    { title: "Profile Picture", field: "pfp64", formatter: imageFormatter },
                    { title: "Acciones", field: "acciones", formatter: actionButtons, width: 150}
                ],
            });
            table.on("cellEdited", function(cell){

                   var data = cell.getRow().getData();
                   fetch(`/api/usuarios/${data.id}`, {
                       method: 'PUT',
                       headers: {
                           'Content-Type': 'application/json'
                       },
                       body: JSON.stringify(data)
                   }).then(response => {
                       if (!response.ok) {
                           console.error('Error actualizando el usuario');
                       }
                   });
              });


            function imageFormatter(cell, formatterParams) {
                var value = cell.getValue();
                return `<img src="data:image/png;base64,${value}" alt="Profile Picture" style="width:50px;height:50px;border-radius:50%;">`;
            }


            function actionButtons(cell, formatterParams) {

                var div = document.createElement("divAcciones");

                var deleteButton = document.createElement("button");
                deleteButton.textContent = "Eliminar";
                deleteButton.onclick = function() {
                    var row = cell.getRow();
                    var data = row.getData();
                    fetch(`/api/usuarios/${data.id}`, {
                        method: 'DELETE'
                    }).then(response => {
                        if (response.ok) {
                            row.delete();
                        } else {
                            console.error('Error eliminando el usuario');
                        }
                    });
                };

                var resetPasswordButton = document.createElement("button");
                resetPasswordButton.textContent = "Restablecer Contraseña";
                resetPasswordButton.onclick = function() {
                    var data = cell.getRow().getData();
                    fetch(`/api/usuarios/reset-password/${data.id}`, {
                        method: 'POST'
                    }).then(response => {
                        if (response.ok) {
                            alert('Correo de restablecimiento de contraseña enviado');
                        } else {
                            console.error('Error enviando el correo de restablecimiento');
                        }
                    });
                };

                div.appendChild(deleteButton);
                div.appendChild(resetPasswordButton);

                return div;





            }



    }}





