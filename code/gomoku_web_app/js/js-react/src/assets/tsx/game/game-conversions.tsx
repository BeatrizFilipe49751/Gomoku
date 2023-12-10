function convertToPieceColor(color: string) {
    return color === "b" ? "black" : "white";
}

function convertToOpening(opening: number) {
    let op: string;
    switch(opening) {
        case 1: {
            op = "Freestyle";
            break;
        }
        case 2: {
            op = "Pro";
            break;
        }
        case 3: {
            op = "Long-Pro";
            break;
        }
        case 4: {
            op = "swap";
            break;
        }
        default:
            op = "Freestyle"
    }
    return op;
}

function convertToVariant(variant: number) {
    let va: string;
    switch(variant) {
        case 1: {
            va = "Freestyle";
            break;
        }
        case 2: {
            va = "Swap";
            break;
        }
        default:
            va = "Freestyle"
    }
    return va;
}

function convertToGameState(gameState: string) {
    let state: string;
    switch (gameState) {
        case 'A': {
            state = "Active";
            break;
        }
        case 'F': {
            state = "Finished";
            break;
        }
        case 'D': {
            state = "Draw";
            break;
        }
        case 'C': {
            state = "Cancelled";
            break;
        }
        default:
            state = "Unknown";
    }
    return state;
}

function deserializePiece(piece: string): Piece {
    const params = piece.split(":");
    const row = parseInt(params[0], 10);
    const col = parseInt(params[1], 10);
    const color: string = convertToPieceColor(params[2])
    return {position: {col: row, row: col}, color: color}
}

export function convertToGameInfo(response: any): GameInfo {
    const pieceStrings: string[] = response.properties.pieces.replace(/\s/g, "").split(",").slice(1);
    const pieces: Piece[] = pieceStrings.map((piece) => deserializePiece(piece));
    return {
        board: {
            boardSize: response.properties.boardSize,
            pieces: pieces
        },
        currentTurn: convertToPieceColor(response.properties.currentTurn),
        gameId: response.properties.gameId,
        name: response.properties.name,
        opening: convertToOpening(response.properties.opening),
        playerBlack: response.properties.playerBlack,
        playerWhite: response.properties.playerWhite,
        state: convertToGameState(response.properties.state),
        variant: convertToVariant(response.properties.variant)
    }

}

export function createPlayersInfo(userId: number, username: string, response: any, gameInfo: GameInfo): PlayersInfo {
    return userId === gameInfo.playerBlack ?
        {
            playerColor: "black",
            opponentColor: "white",
            playerUsername: username,
            opponentUsername: response.properties.username
        } :
        {
            playerColor: "white",
            opponentColor: "black",
            playerUsername: username,
            opponentUsername: response.properties.username
        }
}