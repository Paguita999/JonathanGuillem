import fetch from 'node-fetch';

function fetchUrl(url) {
    return fetch(url)
        .then(response => response.json());
}

const urls = [
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=1&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=2&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=3&pageSize=4',
    'https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=4&pageSize=4',
];

Promise.all(urls.map(fetchUrl))
    .then(results => {
        console.log('ElJust Eat Pizzeria. Menú.');
        console.log('='.repeat(59));
        let records = results.map(result => result.records).flat();
        for (let record of records) {
            // Nombre alineado a la izquierda (25 caracteres), precio a la derecha
            console.log(
                record.nom.padEnd(25, ' ') +
                record.preu.toFixed(2).padStart(7, ' ') +
                '€'
            );
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
