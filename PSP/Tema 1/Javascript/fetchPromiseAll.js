const fetch = require('node-fetch');

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
        console.log('Resultados:', results);
    })
    .catch(error => {
        console.error('Error:', error);
    });
