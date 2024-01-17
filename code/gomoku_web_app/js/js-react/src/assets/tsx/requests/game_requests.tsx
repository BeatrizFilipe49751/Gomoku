import {game_api_routes} from "../api-routes/api_routes";
import {execute_request, formatUrl} from "../utils/requests";

export async function getUserActiveGame() {
    return await execute_request(
        game_api_routes.get_game_userId.url,
        game_api_routes.get_game_userId.method,
        null
    )
}

export async function play(gameId: string, row: number, col: number) {
    return await execute_request(
        formatUrl(game_api_routes.play.url, {gameId: gameId}),
        game_api_routes.play.method,
        {
            row: row,
            col: col
        }
    )
}

export async function quitGame (gameId: string) {
    return await execute_request(
        formatUrl(game_api_routes.quit_game.url, {gameId: gameId}),
        game_api_routes.quit_game.method,
        null
    )
}
export async function getGame(gameId: string) {
    return await execute_request(
        formatUrl(game_api_routes.get_game.url, {gameId: gameId}),
        game_api_routes.get_game.method,
        null
    )
}