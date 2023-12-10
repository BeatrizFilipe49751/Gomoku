import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {execute_request_auth, formatUrl} from "../requests/requests";
import {lobby_api_routes} from "../api-routes/api_routes";
import {Loading} from "../web-ui/request-ui-handler";


export function QuitLobby() {
    const [quitting,setQuitting] = useState(true)
    const { lobbyId } = useParams()
    const navigate = useNavigate();
    useEffect(() => {
        execute_request_auth(
            formatUrl(lobby_api_routes.quit_lobby.url, { lobbyId: lobbyId }),
            lobby_api_routes.quit_lobby.method,
            null
        )
            .then(response => {
                setQuitting(false)
                navigate(`/`)
            })
            .catch(rejectedPromise => {
                alert(rejectedPromise.message)
            })
            .finally(() => { setQuitting(false) }
            )
    }, []);

    return (
        quitting && <Loading />
    )
}