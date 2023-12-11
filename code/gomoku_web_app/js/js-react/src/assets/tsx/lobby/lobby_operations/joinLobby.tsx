import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import {Loading} from "../../web-ui/request-ui-handler";
import {tryRequest} from "../../utils/requests";
import {joinLobby} from '../../requests/lobby_requests';

export function JoinLobby() {
    const [loading, setLoading] = useState(true)
    const { lobbyId } = useParams()
    const navigate = useNavigate();
    useEffect(() => {
        tryRequest({
            loadingSetter: setLoading,
            request: joinLobby,
            args: [lobbyId]
        }).then(resp => {
            if (resp != undefined) {
                navigate(`/game/${resp.properties.gameId}`)
            } else {
                navigate("/")
            }
        })
    }, []);

    return (
        loading && <Loading />
    )
}