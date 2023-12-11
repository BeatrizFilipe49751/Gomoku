import {execute_request, formatUrl} from "../utils/requests";
import {user_routes} from "../api-routes/api_routes";
import {removeUserCookie} from "../utils/session-handler";

export async function getLeaderBoard(
    skip: number
) {
    const url = user_routes.get_leaderboard.url + `?limit:5&skip=${skip}`
    return await execute_request(
        url,
        user_routes.get_leaderboard.method,
        null,
        false
    )
}


export async function getUser(userId: string) {
    return await execute_request(
        formatUrl(user_routes.get_user.url, { userId: userId }),
        user_routes.get_user.method,
        null,
        false
    )
}

export async function register(email: string, username: string, password: string) {
    const data = { email, username, password }
    const resp = await execute_request(
        user_routes.create_user.url,
        user_routes.create_user.method,
        data,
        false
    )
    alert('Registration successful! Proceed to login.');
    return resp
}

export async function login(email: string, password: string) {
    const data = {
        email,
        password
    }
    const response: any = await execute_request(
        user_routes.login.url,
        user_routes.login.method,
        data,
        true
    )
    alert('Login successful!')
    return {
        userId: response.properties.user.userId,
        username: response.properties.user.username,
        token: response.properties.token
    }
}

export async function logout() {
    const resp = await execute_request(
        user_routes.logout.url,
        user_routes.logout.method,
        null, true)
    removeUserCookie()
    alert('Logout successful!')
    return resp
}
