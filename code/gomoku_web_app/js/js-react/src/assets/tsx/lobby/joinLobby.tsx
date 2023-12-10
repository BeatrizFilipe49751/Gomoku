import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Loading } from "../web-ui/request-ui-handler";
import { execute_request_auth, formatUrl } from "../requests/requests";
import { lobby_api_routes } from "../api-routes/api_routes";

export function JoinLobby() {
    const [loading, setLoading] = useState(true)
    const { lobbyId } = useParams()
    const navigate = useNavigate();
    useEffect(() => {
        execute_request_auth(
            formatUrl(lobby_api_routes.join_lobby.url, { lobbyId: lobbyId }),
            lobby_api_routes.join_lobby.method,
            null
        )
            .then(response => {
                navigate(`/game/${response.properties.gameId}`)
            })
            .catch(rejectedPromise => {
                alert(rejectedPromise.message)
            })
            .finally(() => { setLoading(false) }
            )
    }, []);

    return (
        loading && <Loading />
    )
}