import React from "react";
import {useState} from "react";

interface SidePanelProps {
    size: number,
    gameInfo: GameInfo
    playersInfo: PlayersInfo
    quitGameFunction: () => Promise<void>
}

const SidePanel: React.FC<SidePanelProps> = (
    { size, gameInfo, playersInfo, quitGameFunction}
) => {
    const [sidePanelWidth, setSidePanelWidth] = useState(0);
    const [sidePanelHeight, setSidePanelHeight] = useState(0);

    const openNav = () => {
        setSidePanelWidth(size);
        setSidePanelHeight(size);
    };

    const closeNav = () => {
        setSidePanelWidth(0);
        setSidePanelHeight(0);
    };

    let goodMessage: string;
    switch (gameInfo.state) {
        case "Active": {
            goodMessage = "Good Luck!"
            break;
        }
        case "Finished": {
            goodMessage = gameInfo.currentTurn === playersInfo.playerColor ?
                "Good Job! You have new points!" : "Good Try!"
            break;
        }
        case "Draw": {
            goodMessage = "Skill Issue"
            break;
        }
        default: goodMessage = "???"
    }

    return (
        <div>
            <div
                className="sidepanel"
                style={{ width: sidePanelWidth + 'px', height: sidePanelHeight + 'px', right: 0, zIndex: 10000 }}
            >
                <a className="closebtn" onClick={closeNav}>×</a>
                <p className="topic">OPENING </p>
                <p>{gameInfo.opening}</p>
                <p className="topic">VARIANT </p>
                <p>{gameInfo.variant}</p>
                <p className="topic">PLAYERS</p>
                <p>{playersInfo.playerUsername}</p>
                <p>{playersInfo.opponentUsername}</p>
                <p className="help">{goodMessage}</p>
                <a
                    className="quit"
                    onClick={async () => {await quitGameFunction()}}
                >
                    Quit Game
                </a>
                <a href="https://en.wikipedia.org/wiki/Gomoku" target="_blank" rel="noopener noreferrer" className="help">
                    <i className="bi-book"></i>
                </a>
            </div>
            <div className="openbtn" onClick={openNav}>☰ </div>
        </div>
    );
};

export default SidePanel;