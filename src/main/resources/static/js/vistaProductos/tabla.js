function loadTabla() {

    let tabs = document.querySelectorAll('.tab-link');
    let sections = document.querySelectorAll('.service-section');


    tabs.forEach(tab => {
        tab.addEventListener('click', function (event) {
            event.preventDefault();
            console.log('click');
            const targetSectionId = this.getAttribute('data-section');

            // Remove active class from all tabs
            tabs.forEach(tab => tab.classList.remove('active'));

            // Add active class to the clicked tab
            this.classList.add('active');

            // Hide all sections
            sections.forEach(section => section.classList.remove('active'));

            // Show the corresponding section
            document.getElementById(targetSectionId).classList.add('active');
        });
    });
}

export { loadTabla };


