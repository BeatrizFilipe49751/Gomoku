import React from "react";
import Confetti from "./utils/confetti";

interface GomokuBoardProps {
    game: GameInfo,
    playFunction: (row: number, col: number) => Promise<void>,
    playersInfo: PlayersInfo
}

const GomokuBoard: React.FC<GomokuBoardProps> = ({ game, playFunction, playersInfo }) => {
    const boardSize = game.board.boardSize
    const currentUserTurn = game.currentTurn === playersInfo.playerColor ?
        playersInfo.playerUsername :
        playersInfo.opponentUsername


    let drawBoard = () => {
        const squares = [];
        const circles = [];

        for (let row = 0; row < boardSize; row++) {
            for (let col = 0; col < boardSize; col++) {
                if (row < boardSize - 1 && col < boardSize - 1) {
                    squares.push(
                        <div key={`square-${row}-${col}`} className="game-square"></div>
                    )
                }
                const piece =
                    game.board.pieces.find(p => p.position.row === row && p.position.col === col)
                if (piece !== undefined) {
                    if (piece.color === "white") {
                        circles.push(
                            <div key={`circle-${row}-${col}`} className="game-circle white"></div>
                        )
                    } else {
                        circles.push(
                            <div key={`circle-${row}-${col}`} className="game-circle black"></div>
                        )
                    }
                } else {
                    circles.push(
                        <div
                            key={`circle-${row}-${col}`}
                            className="game-circle"
                            onClick={
                                async () => {
                                    if (game.currentTurn === playersInfo.playerColor) {
                                        await playFunction(row, col)
                                    }
                                }
                            }
                        ></div>
                    );
                }
            }
        }

        const boardSquareStyle = {
            display: 'grid',
            gridTemplateColumns: `repeat(${boardSize - 1}, 30px)`,
            gridTemplateRows: `repeat(${boardSize - 1}, 30px)`,
            boxShadow: '5px 5px 5px rgba(0, 0, 0, 1)',
            gap: '0',
        }
        const boardCircleStyle = {
            display: 'grid',
            gridTemplateColumns: `repeat(${boardSize}, 30px)`,
            gridTemplateRows: `repeat(${boardSize}, 30px)`,
            gap: '0',
        }

        let endContent: React.JSX.Element;
        if (game.state === "Finished") {
            endContent = game.currentTurn === playersInfo.playerColor ?
                (<div className="absolute-win">
                    <h1 className="h1-winner">Winner: {currentUserTurn}</h1>
                    <Confetti></Confetti>
                </div>) :
                (<div className="absolute-win">
                    <h1 className="h1-winner">Winner: {currentUserTurn}</h1>
                </div>)
        } else if (game.state === "Draw") {
            endContent = (
                <div className="absolute-win">
                    <h1 className="h1-winner">Draw...</h1>
                </div>
            )
        } else endContent = <></>

        return (
            <div>
                {endContent}
                <div style={boardSquareStyle} >
                    {squares}
                    <div style={boardCircleStyle} className="board-pos">
                        {circles}
                    </div>
                </div>
                <div className="down-info-container left">
                    <p className="down-info">Your Color:<span className={"demo-circle " + playersInfo.playerColor}></span></p>
                </div>
                <div className="down-info-container right">
                    <p className="down-info">Turn: <span className="current-turn">{currentUserTurn}</span></p>
                </div>
            </div>
        );
    };

    return drawBoard();
};

export default GomokuBoard;