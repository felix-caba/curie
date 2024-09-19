function showNotification(message, type = 'error') {
    const container = document.getElementById('notification-container');
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.innerText = message;

    container.appendChild(notification);

    // Forzamos un reflow antes de añadir la clase 'show'
    notification.offsetHeight;

    // Añadimos la clase 'show' para iniciar la animación de entrada
    notification.classList.add('show');

    setTimeout(() => {
        notification.classList.remove('show'); // Esto disparará la animación de salida

        // Esperamos a que termine la animación de salida antes de eliminar el elemento
        notification.addEventListener('transitionend', () => {
            notification.remove();
        }, { once: true });
    }, 5000);
}

export { showNotification };