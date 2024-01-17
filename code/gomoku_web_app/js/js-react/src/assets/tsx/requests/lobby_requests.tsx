import {lobby_api_routes} from "../api-routes/api_routes";
import {execute_request, formatUrl} from "../utils/requests";

export async function joinLobby(lobbyId: string) {
    return await execute_request(
        formatUrl(lobby_api_routes.join_lobby.url, { lobbyId: lobbyId }),
        lobby_api_routes.join_lobby.method,
        null
    )
}

export async function createLobby(
    name: string,
    opening: number,
    variant: number,
    size: number
) {
    const data = { name, opening, variant, size }
    const response = await execute_request(
        lobby_api_routes.create_lobby.url,
        lobby_api_routes.create_lobby.method,
        data
    )
    alert('Create lobby successful!');
    return response
}

export async function getLobbies(skip : number) {
    const url = lobby_api_routes.get_available_lobbies.url + `?limit=${5}&skip=${skip}`
    return await execute_request(
        url,
        lobby_api_routes.get_available_lobbies.method,
        null
    )
}

export async function check_full_lobby(lobbyId: string) {
    return await execute_request(
        formatUrl(
            lobby_api_routes.check_full_lobby.url,
            { lobbyId: lobbyId }
        ),
        lobby_api_routes.check_full_lobby.method,
        null
    )
}

export async function getActiveLobby() {
    return await execute_request(
        lobby_api_routes.get_lobby_userId.url,
        lobby_api_routes.get_lobby_userId.method,
        null
    )
}

export async function quitLobby(lobbyId: string) {
    return await execute_request(
        formatUrl(lobby_api_routes.quit_lobby.url, { lobbyId: lobbyId }),
        lobby_api_routes.quit_lobby.method,
        null
    )
}