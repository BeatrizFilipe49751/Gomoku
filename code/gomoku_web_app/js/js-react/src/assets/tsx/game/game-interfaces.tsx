interface Piece {
    position: { row: number; col: number },
    color: string
}

interface Board {
    boardSize: number,
    pieces: Piece[]
}

interface GameInfo {
    gameId: number,
    name: string,
    playerWhite: number,
    playerBlack: number,
    currentTurn: string,
    board: Board,
    state: string,
    opening: string,
    variant: string
}

interface PlayersInfo {
    playerColor: string,
    opponentColor: string,
    playerUsername: string,
    opponentUsername: string
}