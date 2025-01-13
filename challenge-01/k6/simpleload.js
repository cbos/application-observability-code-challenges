import http from 'k6/http';
import { sleep } from 'k6';
export const options = {
    vus: 2,
    duration: '120s',

};

export default function () {
    const rnd = getRandomInt(10);
    http.get('http://localhost:8080/spring-app/challenge/' + rnd);
    sleep(1);
}

function getRandomInt(max) {
    return 10 + Math.floor(Math.random() * max);
}