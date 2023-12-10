import {Cookies} from 'react-cookie';


const cookies = new Cookies()
export function createCookie(data: any) {
    console.log()
    if (getAuthToken() != undefined) removeToken()
    const expires = new Date();
    expires.setTime(expires.getTime() + 6 * 60 * 60 * 1000);
    cookies.set('user', data, { path: '/', expires });
}

export function getAuthToken() {
    const user = cookies.get('user')
    if(user === undefined) {
        return undefined
    } else return user.token
}

export function getUser() {
    return cookies.get('user')
}

export function removeToken() {
    cookies.remove('user', { path: '/' })
}
