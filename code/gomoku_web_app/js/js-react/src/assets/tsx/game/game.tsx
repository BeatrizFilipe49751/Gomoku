import React, {useEffect, useState} from 'react';
import SidePanel from "./utils/sidepanel";
import {execute_request, execute_request_auth, execute_request_gen, formatUrl} from "../requests/requests";
import {game_api_routes, user_routes} from "../api-routes/api_routes";
import {getUser} from "../requests/session-handler";
import GomokuBoard from "./gomoku-board";
import {useNavigate, useParams} from "react-router-dom";
import {convertToGameInfo, createPlayersInfo} from "./game-conversions";

function Game() {
    const [loading, setLoading] = useState(true);
    const {gameId} = useParams()
    const [gameInfo, setGameInfo] = useState<GameInfo>(undefined)
    const [playersInfo, setPlayersInfo] = useState<PlayersInfo>(undefined)
    const userInfo = getUser()
    const userId = userInfo.userId
    const username = userInfo.username

    const navigate = useNavigate()

    const updateGameInformation = async () => {
        try {
            const response = await execute_request_gen(
                formatUrl(game_api_routes.get_game.url, {gameId: gameId}),
                game_api_routes.get_game.method,
                null,
                true
            )
            console.log("GET GAME REQUEST")
            const newGameInfo = convertToGameInfo(response);
            if (newGameInfo.state === "Cancelled") {
                alert(`Game was ${newGameInfo.state}`)
                navigate("/") // Navigate to Home
            }
            setGameInfo(newGameInfo);
            const opponentId =
                newGameInfo.playerBlack === userId ?
                    newGameInfo.playerWhite : newGameInfo.playerBlack
            try {
                const userResponse = await execute_request_gen(
                    formatUrl(user_routes.get_user.url, {userId: `${opponentId}`}),
                    user_routes.get_user.method,
                    null,
                    false
                )
                console.log("GET USER REQUEST")
                const newPlayersInfo =
                    createPlayersInfo(userId, username, userResponse, newGameInfo);
                setPlayersInfo(newPlayersInfo);
            } catch (rejectedPromise) {
                const error = await rejectedPromise
                alert(error.message)
            }
        } catch (rejectedPromise) {
            const error = await rejectedPromise
            alert(error.message)
        } finally {
            setLoading(false);
        }
    }

    const checkGameInformation = async () => {
        if (gameInfo === undefined && playersInfo === undefined)
            await updateGameInformation()
        else if (gameInfo.currentTurn === playersInfo.opponentColor)
                await updateGameInformation()
    }

    useEffect(() => {
        const intervalId = setInterval(checkGameInformation, 2000);
        return () => clearInterval(intervalId);
    }, [])

    async function play (row: number, col: number) {
        if(gameInfo != undefined) {
            if (gameInfo.state === "Active") {
                try {
                    const response = await execute_request_gen(
                        formatUrl(game_api_routes.play.url, {gameId: gameId}),
                        game_api_routes.play.method,
                        {
                            row: row,
                            col: col
                        },
                        true
                    )
                    console.log("PLAY REQUEST")
                    const newGameInfo = convertToGameInfo(response)
                    setGameInfo(newGameInfo)
                } catch (rejectedPromise) {
                    const error = await rejectedPromise
                    alert(error.message)
                }
            }
        }
    }

    async function quitGame () {
        if(gameInfo != undefined) {
            try {
                setLoading(true)
                await execute_request_auth(
                    formatUrl(game_api_routes.quit_game.url, {gameId: gameId}),
                    game_api_routes.quit_game.method,
                    null
                )
                console.log("QUIT REQUEST")
                setLoading(false)
                navigate("/") // Navigate to Home
            } catch (rejectedPromise) {
                const error = await rejectedPromise
                alert(error.message)
            }
        }
    }

    if (loading) {
        return (
            <div className="spinner-container">
                <div className="spinner"></div>
            </div>
        );
    }

    if (gameInfo != undefined && playersInfo != undefined) {
        const containerSize = gameInfo.board.boardSize*40
        return (
            <div className="game-body">
                <div className="sidemenu" style={{width: containerSize, height: containerSize}}>
                    <SidePanel
                        size={containerSize}
                        gameInfo={gameInfo}
                        playersInfo={playersInfo}
                        quitGameFunction={quitGame}
                    />
                </div>
                <div className="game-container" style={{width: containerSize, height: containerSize}}>
                    <h1 className="game-title">{gameInfo.name}</h1>
                    <GomokuBoard game={gameInfo} playFunction={play} playersInfo={playersInfo}/>
                </div>
            </div>
        );
    }
}

export default Game;