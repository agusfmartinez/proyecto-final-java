// Direcciones de la API
const URL_API = "http://localhost:8080/empleados";
const URL_ROLES = "http://localhost:8080/roles";

// Elementos de la página
const lista = document.getElementById("lista");
const mensaje = document.getElementById("mensaje");
const formulario = document.getElementById("formulario");
const tituloFormulario = document.getElementById("titulo-formulario");
const botonCancelar = document.getElementById("btn-cancelar");
const campoId = document.getElementById("id");
const campoLegajo = document.getElementById("legajo");
const campoNombre = document.getElementById("nombre");
const campoApellido = document.getElementById("apellido");
const campoCuil = document.getElementById("cuil");
const campoRol = document.getElementById("rol");
const busquedaApellido = document.getElementById("busqueda-apellido");
const filtroRol = document.getElementById("filtro-rol");

// Empleados que trajo la API (se usan para completar el formulario al editar)
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
// GET roles: completa el select del formulario y el filtro
// ---------------------------------------------------------------
function cargarRoles() {
  fetch(URL_ROLES)
    .then(leerRespuesta)
    .then(response => response.json())
    .then(roles => {
      let opcionesFormulario = `<option value="">Sin rol</option>`;
      let opcionesFiltro = `<option value="">Todos</option>`;

      // El formulario manda el id del rol; el filtro usa el nombre
      // (GET /empleados/rol/{nombre})
      roles.forEach(r => {
        opcionesFormulario += `<option value="${r.id}">${r.nombre}</option>`;
        opcionesFiltro += `<option value="${r.nombre}">${r.nombre}</option>`;
      });

      campoRol.innerHTML = opcionesFormulario;
      filtroRol.innerHTML = opcionesFiltro;
    })
    .catch(error => mostrarMensaje(error.message, true));
}

// ---------------------------------------------------------------
// GET empleados: todos, los que coinciden con el apellido buscado,
// o los del rol elegido en el filtro
// ---------------------------------------------------------------
function cargarEmpleados() {
  let url = URL_API;

  // encodeURIComponent convierte espacios y tildes para que viajen bien en la URL
  if (busquedaApellido.value.trim() !== "") {
    url = URL_API + "?apellido=" + encodeURIComponent(busquedaApellido.value.trim());
  } else if (filtroRol.value !== "") {
    url = URL_API + "/rol/" + encodeURIComponent(filtroRol.value);
  }

  fetch(url)
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
        <div class="acciones">
          <button onclick="editarEmpleado(${e.id})">Editar</button>
          <button class="peligro" onclick="eliminarEmpleado(${e.id})">Eliminar</button>
        </div>
      </article>
    `;
  });

  lista.innerHTML = html;
}

// La API no combina búsqueda y filtro en un mismo pedido:
// al usar uno, se limpia el otro para que el listado no confunda.
busquedaApellido.addEventListener("input", function () {
  filtroRol.value = "";
  cargarEmpleados();
});

filtroRol.addEventListener("change", function () {
  busquedaApellido.value = "";
  cargarEmpleados();
});

// ---------------------------------------------------------------
// POST y PUT: crear o actualizar (según el formulario tenga un id o no)
// ---------------------------------------------------------------
formulario.addEventListener("submit", function (evento) {
  evento.preventDefault();

  // El rol se envía como objeto: la API espera { "id": ... }.
  // Si no se eligió ninguno, va null (el empleado queda sin rol).
  const empleado = {
    legajo: Number(campoLegajo.value),
    nombre: campoNombre.value,
    apellido: campoApellido.value,
    cuil: campoCuil.value,
    rol: campoRol.value === "" ? null : { id: Number(campoRol.value) }
  };

  let url = URL_API;
  let metodo = "POST";

  if (campoId.value !== "") {
    url = URL_API + "/" + campoId.value;
    metodo = "PUT";
  }

  fetch(url, {
    method: metodo,
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(empleado)
  })
    .then(leerRespuesta)
    .then(() => {
      mostrarMensaje("Empleado guardado.", false);
      limpiarFormulario();
      cargarEmpleados();
    })
    .catch(error => mostrarMensaje(error.message, true));
});

// ---------------------------------------------------------------
// DELETE: eliminar empleado
// La API responde 204 (sin cuerpo): no se llama a response.json().
// ---------------------------------------------------------------
function eliminarEmpleado(id) {
  const e = empleados.find(empleado => empleado.id === id);

  if (!confirm("¿Eliminar a " + e.nombreCompleto + " (legajo " + e.legajo + ")?")) {
    return;
  }

  fetch(URL_API + "/" + id, { method: "DELETE" })
    .then(leerRespuesta)
    .then(() => {
      mostrarMensaje("Empleado eliminado.", false);
      // Si se estaba editando a este empleado, se vacía el formulario
      if (campoId.value === String(id)) {
        limpiarFormulario();
      }
      cargarEmpleados();
    })
    .catch(error => mostrarMensaje(error.message, true));
}

// ---------------------------------------------------------------
// Editar: pasa los datos del empleado al formulario
// ---------------------------------------------------------------
function editarEmpleado(id) {
  const e = empleados.find(empleado => empleado.id === id);

  campoId.value = e.id;
  campoLegajo.value = e.legajo;
  campoNombre.value = e.nombre;
  campoApellido.value = e.apellido;
  campoCuil.value = e.cuil;
  campoRol.value = e.rol ? e.rol.id : "";

  tituloFormulario.textContent = "Editando empleado " + e.legajo;
  botonCancelar.hidden = false;
  window.scrollTo({ top: 0, behavior: "smooth" });
}

function limpiarFormulario() {
  formulario.reset();
  campoId.value = "";
  tituloFormulario.textContent = "Nuevo empleado";
  botonCancelar.hidden = true;
}

botonCancelar.addEventListener("click", limpiarFormulario);

// Carga inicial
cargarRoles();
cargarEmpleados();
