import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {tryRequest} from "../../utils/requests";
import {Loading} from "../../web-ui/request-ui-handler";
import {quitLobby} from "../../requests/lobby_requests";


export function QuitLobby() {
    const [quitting, setQuitting] = useState(true)
    const { lobbyId } = useParams()
    const navigate = useNavigate();
    useEffect(() => {
        tryRequest({
            loadingSetter: setQuitting,
            request: quitLobby,
            args: [lobbyId]
        }).then(resp => {
            if(resp != undefined) {
                navigate(`/`)
            }
        })
        
    }, []);

    return (
        quitting && <Loading />
    )
}