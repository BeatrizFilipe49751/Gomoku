

const api_route = "http://localhost:8080/api"
export const user_routes = {
    create_user: {
        method: "POST",
        url: api_route + "/users"
    },
    get_user: {
        method: "GET",
        url: api_route + "/users/{userId}"
    },
    login: {
        method: "POST",
        url: api_route + "/users/login"
    },
    logout: {
        method: "POST",
        url: api_route + "/users/logout"
    },
    get_leaderboard: {
        method: "GET",
        url: api_route + "/users/leaderboard"
    }
}

export const lobby_api_routes = {
    create_lobby: {
        method: "POST",
        url: api_route + "/users/lobby"
    },
    get_lobby: {
        method: "GET",
        url: api_route + "/users/lobby/{lobbyId}"
    },
    get_lobby_userId: {
        method: "GET",
        url: api_route + "/users/lobby/get"
    },
    join_lobby: {
        method: "PUT",
        url: api_route + "/users/lobby/{lobbyId}"
    },
    get_available_lobbies: {
        method: "GET",
        url: api_route + "/users/lobbies"
    },
    check_full_lobby: {
        method: "GET",
        url: api_route + "/users/lobby/{lobbyId}/full"
    },
    quit_lobby: {
        method: "DELETE",
        url: api_route + "/users/lobby/{lobbyId}"
    }
}

export const game_api_routes = {
    get_game: {
        method: "GET",
        url: api_route + "/users/game/{gameId}"
    },
    play: {
        method: "PUT",
        url: api_route + "/users/game/play/{gameId}"
    },
    get_game_userId: {
        method: "GET",
        url: api_route + "/users/game"
    },

}