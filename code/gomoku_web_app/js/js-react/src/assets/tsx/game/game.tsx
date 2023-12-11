import React, {useEffect, useState} from 'react';
import SidePanel from "./utils/sidepanel";
import {tryRequest} from "../utils/requests";
import GomokuBoard from "./gomoku-board";
import {useNavigate, useParams} from "react-router-dom";
import {convertToGameInfo, createPlayersInfo} from "./game-conversions";
import {getGame, play, quitGame} from "../requests/game_requests";
import {getUser} from "../requests/user_requests";
import {getUserCookie} from "../utils/session-handler"

function Game() {
    const [loading, setLoading] = useState(true);
    const { gameId } = useParams()
    const [gameInfo, setGameInfo] = useState<GameInfo>(undefined)
    const [playersInfo, setPlayersInfo] = useState<PlayersInfo>(undefined)
    const userInfo = getUserCookie()
    const userId = userInfo.userId
    const username = userInfo.username

    const navigate = useNavigate()

    const updateGameInformation = async () => {
        const game = await tryRequest({
            request: getGame,
            args: [gameId]
        })
        console.log("GET GAME REQUEST")
        if (game != undefined) {
            const newGameInfo = convertToGameInfo(game);
            if (newGameInfo.state === "Cancelled") {
                alert(`Game was ${newGameInfo.state}`)
                navigate("/") // Navigate to Home
            }
            setGameInfo(newGameInfo);
            const opponentId =
                newGameInfo.playerBlack === userId ?
                    newGameInfo.playerWhite : newGameInfo.playerBlack

            const user = await tryRequest({
                request: getUser,
                args: [`${opponentId}`]
            })
            console.log("GET USER REQUEST")
            if (user != undefined) {
                const newPlayersInfo =
                    createPlayersInfo(userId, username, user, newGameInfo);
                setPlayersInfo(newPlayersInfo);
            }
        }
    }


    const checkGameInformation = async () => {
        if (gameInfo === undefined && playersInfo === undefined) {
            await updateGameInformation()
            setLoading(false)
        }

        else if (gameInfo.currentTurn === playersInfo.opponentColor)
            await updateGameInformation()
    }

    useEffect(() => {
        const intervalId = setInterval(checkGameInformation, 2000);
        return () => clearInterval(intervalId);
    }, [])

    async function playPiece(
        row: number,
        col: number
    ) {
        if (gameInfo != undefined) {
            if (gameInfo.state === "Active") {
                const newGame = await tryRequest({
                    request: play,
                    args: [gameId, row, col]
                })
                console.log("PLAY REQUEST")
                if (newGame != undefined) {
                    const newGameInfo = convertToGameInfo(newGame)
                    setGameInfo(newGameInfo)
                }
            }
        }
    }


    async function quit() {
        if (gameInfo != undefined) {
            const quit = tryRequest({
                loadingSetter: setLoading,
                request: quitGame,
                args: [gameId]
            })
            if (quit != undefined) {
                navigate("/") // Navigate to Home
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
        const containerSize = gameInfo.board.boardSize * 40
        return (
            <div className="game-body">
                <div className="sidemenu" style={{ width: containerSize, height: containerSize }}>
                    <SidePanel
                        size={containerSize}
                        gameInfo={gameInfo}
                        playersInfo={playersInfo}
                        quitGameFunction={quit}
                    />
                </div>
                <div className="game-container" style={{ width: containerSize, height: containerSize }}>
                    <h1 className="game-title">{gameInfo.name}</h1>
                    <GomokuBoard game={gameInfo} playFunction={playPiece} playersInfo={playersInfo} />
                </div>
            </div>
        );
    }
}

export default Game;