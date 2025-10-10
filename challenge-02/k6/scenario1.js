import http from 'k6/http';
import { sleep } from 'k6';
export const options = {
    vus: 5,
    duration: '360s',

};
export default function () {
    http.get('http://localhost:8081/');
    sleep(1);
    http.get('http://localhost:8082/');
    sleep(1);
    http.get('http://localhost:8083/');
}