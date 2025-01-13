import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        chain: {
            executor: 'ramping-vus',
            startVUs: 1,
            stages: [
                { duration: '20s', target: 1 }, // start with just 1 user
                { duration: '15s', target: 20 }, // Ramp up to 20 users
                { duration: '300s', target: 20 }, // Keep sending with 20 users
                { duration: '20s', target: 1 } // Calm down
            ],
            gracefulRampDown: '0s',
        },
    },
};


export default function () {
    const rnd = getRandomInt(10);
    http.get('http://localhost:8080/spring-app/challenge/' + rnd);
    sleep(1);
}

function getRandomInt(max) {
    return 10 + Math.floor(Math.random() * max);
}