import {Cookies} from 'react-cookie';


const cookies = new Cookies()
export function createCookie(data: any) {
    if (getUserCookie() != undefined) removeUserCookie()
    const expires = new Date();
    expires.setTime(expires.getTime() + 6 * 60 * 60 * 1000);
    cookies.set('user', data, { path: '/', expires });
}
export function getUserCookie() {
    return cookies.get('user')
}

export function removeUserCookie() {
    cookies.remove('user', { path: '/' })
}
