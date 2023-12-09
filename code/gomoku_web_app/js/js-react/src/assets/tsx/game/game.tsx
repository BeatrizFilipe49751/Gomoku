import React, { useState } from 'react';

interface GomokuBoardProps {
    boardSize: number;
}

interface Piece {
    position: { row: number; col: number },
    color: string
}

const GomokuBoard: React.FC<GomokuBoardProps> = ({ boardSize }) => {
    const [dummyPieces, setDummyPieces] = useState<Piece[]>([
        { position: { row: 14, col: 0 }, color: "white" },
        { position: { row: 5, col: 12 }, color: "white" },
        { position: { row: 1, col: 0 }, color: "white" },
        { position: { row: 4, col: 6 }, color: "black" },
        { position: { row: 14, col: 14 }, color: "black" }
    ]);

    function dummyPlay(row: number, col: number, color: string) {
        setDummyPieces(prevPieces => [...prevPieces,
        { position: { row: row, col: col }, color: color }
        ]);
    }

    let drawBoard = () => {
        const squares = [];
        const circles = [];

        for (let row = 0; row < 15; row++) {
            for (let col = 0; col < 15; col++) {
                if (row < boardSize - 1 && col < boardSize - 1) {
                    squares.push(
                        <div key={`square-${row}-${col}`} className="square"></div>
                    )
                }
                const piece =
                    dummyPieces.find(p => p.position.row === row && p.position.col === col)
                if (piece !== undefined) {
                    if (piece.color === "white") {
                        circles.push(
                            <div key={`circle-${row}-${col}`} className="circle white"></div>
                        )
                    } else {
                        circles.push(
                            <div key={`circle-${row}-${col}`} className="circle black"></div>
                        )
                    }
                } else {
                    circles.push(
                        <div key={`circle-${row}-${col}`} className="circle" onClick={() => dummyPlay(row, col, "black")}></div>
                    );
                }
            }
        }

        const boardSquareStyle = {
            display: 'grid',
            gridTemplateColumns: `repeat(${boardSize - 1}, 30px)`,
            gridTemplateRows: `repeat(${boardSize - 1}, 30px)`,
            boxShadow: '5px 5px 5px rgba(0, 0, 0, 0.5)',
            gap: '0',
        }
        const boardCircleStyle = {
            display: 'grid',
            gridTemplateColumns: `repeat(${boardSize}, 30px)`,
            gridTemplateRows: `repeat(${boardSize}, 30px)`,
            gap: '0',
        }

        return (
            <div>
                <div style={boardSquareStyle}>
                    {squares}
                    <div style={boardCircleStyle} className="board-pos">
                        {circles}
                    </div>
                </div>
            </div>
        );
    };

    return drawBoard();
};

function Game() {
    // get gameId from route params
    //const gameInfo = getGameInfo(gameId)
    //const boardSize = gameInfo.boardSize
    const dummyBoardSize = 15
    return (
        <div className="body-game">
            <GomokuBoard boardSize={dummyBoardSize} />
        </div>
    );
}

export default Game;