import {Cookies} from 'react-cookie';

export function createSession(token: string) {
    sessionStorage.setItem('userToken',token)
}

export function removeSession() {
    sessionStorage.removeItem('userToken')
}
export function getTokenFromSession() {
    return sessionStorage.getItem('userToken')
}

const cookies = new Cookies()
export function createCookie(token: string) {
    if(getAuthToken() != undefined) removeToken()
    const expires = new Date();
    expires.setTime(expires.getTime() + 6 * 60 * 60 * 1000);
    cookies.set('authToken', token, { path: '/', expires });
}

export function getAuthToken() {
    return cookies.get('authToken')
}

export function removeToken() {
    cookies.remove('authToken', { path: '/'})
}
