import {Cookies} from 'react-cookie';


const cookies = new Cookies()
export function createCookie(data: any) {
    console.log()
    if (getAuthToken() != undefined) removeUserCookie()
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

export function getUserCookie() {
    return cookies.get('user')
}

export function removeUserCookie() {
    cookies.remove('user', { path: '/' })
}
