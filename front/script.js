// Direcciones de la API
const URL_API = "http://localhost:8080/empleados";

// Elementos de la página
const lista = document.getElementById("lista");
const mensaje = document.getElementById("mensaje");

// Empleados que trajo la API
let empleados = [];

// ---------------------------------------------------------------
// Mensajes en pantalla
// ---------------------------------------------------------------
function mostrarMensaje(texto, esError) {
  mensaje.textContent = texto;
  mensaje.className = esError ? "mensaje error" : "mensaje exito";
  mensaje.hidden = false;
  setTimeout(() => mensaje.hidden = true, 4000);
}

// fetch NO considera error un 400 o un 404: la promesa se cumple igual.
// Por eso se revisa response.ok. Nuestra API responde los errores como
// JSON { "error": "mensaje" }: se lee ese campo y se lanza para que lo tome catch.
function leerRespuesta(response) {
  if (!response.ok) {
    return response.json()
      .catch(() => ({ error: "Error " + response.status }))
      .then(cuerpo => {
        throw new Error(cuerpo.error);
      });
  }
  return response;
}

// ---------------------------------------------------------------
// GET empleados
// ---------------------------------------------------------------
function cargarEmpleados() {
  fetch(URL_API)
    .then(leerRespuesta)
    .then(response => response.json())
    .then(datos => {
      empleados = datos;
      mostrarEmpleados();
    })
    .catch(error => mostrarMensaje(error.message, true));
}

function mostrarEmpleados() {
  if (empleados.length === 0) {
    lista.innerHTML = "<p>No hay empleados para mostrar.</p>";
    return;
  }

  let html = "";

  empleados.forEach(e => {
    // El rol puede venir null si el empleado no tiene uno asignado
    const rol = e.rol ? e.rol.nombre : "Sin rol";

    html += `
      <article class="tarjeta">
        <h3>${e.nombreCompleto}</h3>
        <p class="rol">${rol}</p>
        <p class="legajo">Legajo ${e.legajo}</p>
        <p>CUIL: ${e.cuil}</p>
        <p>${e.email}</p>
      </article>
    `;
  });

  lista.innerHTML = html;
}

// Carga inicial
cargarEmpleados();
