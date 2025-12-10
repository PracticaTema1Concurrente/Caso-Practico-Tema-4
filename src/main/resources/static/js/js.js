document.addEventListener('DOMContentLoaded', () => {
    // Usamos la variable USUARIO_ACTUAL que definimos en el HTML
    console.log(`🚀 Conectando SSE para: ${USUARIO_ACTUAL}`);
    iniciarSSE();
});

function iniciarSSE() {
    const eventSource = new EventSource(`/sse/notificaciones/${USUARIO_ACTUAL}`);

    eventSource.onmessage = (event) => {
        const notificacion = JSON.parse(event.data);
        renderizarTarjeta(notificacion);
    };

    eventSource.onerror = (err) => {
        console.error("⚠️ Error conexión SSE", err);
    };
}

function renderizarTarjeta(n) {
    const container = document.getElementById('notificaciones-container');

    // Crear la tarjeta (DIV, no TR)
    const card = document.createElement('div');
    const tipoClass = `type-${n.tipo.toLowerCase()}`;
    const bgClass = `bg-${n.tipo.toLowerCase()}`;
    const estadoClass = n.leido ? 'leido' : '';

    card.className = `card ${tipoClass} ${estadoClass} slide-in`;
    card.id = `card-${n.id}`;

    // HTML moderno con comillas invertidas
    card.innerHTML = `
        <div class="badge ${bgClass}">${n.tipo}</div>
        <div class="card-content">
            <span class="card-date">${new Date(n.fecha).toLocaleString()}</span>
            <span class="card-msg">${n.mensaje}</span>
        </div>
        <div class="actions">
            ${!n.leido ? `<button class="btn-read" onclick="marcarLeido('${n.id}')"><i class="fas fa-check"></i></button>` : ''}
            <button class="btn-delete" onclick="eliminar('${n.id}')"><i class="fas fa-trash"></i></button>
        </div>
    `;

    container.prepend(card);
}

// Funciones para los botones
const marcarLeido = async (id) => {
    await fetch(`/api/notificaciones/${id}/leer`, { method: 'PATCH' });
    const card = document.getElementById(`card-${id}`);
    if (card) {
        card.classList.add('leido');
        const btn = card.querySelector('.btn-read');
        if(btn) btn.remove();
    }
};

const eliminar = async (id) => {
    if(!confirm("¿Borrar?")) return;
    await fetch(`/api/notificaciones/${id}`, { method: 'DELETE' });
    const card = document.getElementById(`card-${id}`);
    if (card) card.remove();
};

// Hacerlas públicas para que el HTML las vea
window.marcarLeido = marcarLeido;
window.eliminar = eliminar;