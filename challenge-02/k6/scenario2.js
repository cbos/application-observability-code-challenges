import { randomSeed } from 'k6';

import http from 'k6/http';
import { sleep } from 'k6';
export const options = {
    vus: 20,
    duration: '360s',
};
export default function () {

    const rnd = getRandomInt(10);

    http.get('http://localhost:8081/random/' + rnd);
    http.get('http://localhost:8082/random/' + rnd);
    http.get('http://localhost:8083/random/' + rnd);
    sleep(1);
}

function getRandomInt(max) {
    return 1 + Math.floor(Math.random() * max);
}