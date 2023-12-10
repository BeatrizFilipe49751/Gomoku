import React, {useEffect, useState} from 'react';
import SidePanel from "./utils/sidepanel";
import {execute_request, execute_request_auth, formatUrl} from "../requests/requests";
import {game_api_routes, user_routes} from "../api-routes/api_routes";
import {getUser} from "../requests/session-handler";
import GomokuBoard from "./gomoku-board";
import {useParams} from "react-router-dom";
import {convertToGameInfo, createPlayersInfo} from "./game-conversions";

function Game() {
    const [loading, setLoading] = useState(true);
    const {gameId} = useParams()
    const [gameInfo, setGameInfo] = useState<GameInfo>(undefined)
    const [playersInfo, setPlayersInfo] = useState<PlayersInfo>(undefined)
    const userInfo = getUser()
    const userId = userInfo.userId
    const username = userInfo.username

    function updateGameInformation() {
        execute_request_auth(
            formatUrl(game_api_routes.get_game.url, { gameId: gameId }),
            game_api_routes.get_game.method,
            null
        )
            .then((response) => {
                console.log("GET GAME REQUEST")
                const newGameInfo = convertToGameInfo(response);
                setGameInfo(newGameInfo);
                const opponentId =
                    newGameInfo.playerBlack === userId ?
                        newGameInfo.playerWhite : newGameInfo.playerBlack
                execute_request(
                    formatUrl(user_routes.get_user.url, { userId: `${opponentId}` }),
                    user_routes.get_user.method,
                    null
                )
                    .then((userResponse) => {
                        const newPlayersInfo =
                            createPlayersInfo(userId, username, userResponse, newGameInfo);
                        setPlayersInfo(newPlayersInfo);
                    })
                    .catch((rejectedPromise) => {
                        alert(rejectedPromise.message);
                    })
                    .finally(() => {
                        setLoading(false);
                    });
            })
            .catch((rejectedPromise) => {
                alert(rejectedPromise.message);
            });
    }

    useEffect(() => {
        if (gameInfo != undefined && playersInfo != undefined) {
            if (gameInfo.currentTurn === playersInfo.opponentColor) {
                const intervalId = setInterval(updateGameInformation, 2000);
                return () => clearInterval(intervalId);
            }
        } else {
            updateGameInformation()
        }
    }, [])

    function play(row: number, col: number) {
        console.log(gameInfo)
        if(gameInfo != undefined) {
            if (gameInfo.state === "Active") {
                execute_request_auth(
                    formatUrl(game_api_routes.play.url, {gameId: gameId}),
                    game_api_routes.play.method,
                    {
                        row: row,
                        col: col
                    }
                )
                    .then(response => {
                        const newGameInfo = convertToGameInfo(response)
                        console.log(newGameInfo)
                        setGameInfo(newGameInfo)
                    })
                    .catch(rejectedPromise => {
                        alert(rejectedPromise.message)
                    })
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