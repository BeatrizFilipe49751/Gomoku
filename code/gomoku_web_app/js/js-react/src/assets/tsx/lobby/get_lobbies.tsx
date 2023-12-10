import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Loading } from "../web-ui/request-ui-handler";
import { execute_request_auth } from "../requests/requests";
import { lobby_api_routes } from "../api-routes/api_routes";

function Get_lobbies() {
  const [lobbiesData, setLobbiesData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    execute_request_auth(
      lobby_api_routes.get_available_lobbies.url,
      lobby_api_routes.get_available_lobbies.method,
      null
    )
      .then(response => setLobbiesData(response))
      .catch(error => alert(error.message))
      .finally(() => setLoading(false))
  }, []);

  const getOpeningString = (opening: number) => {
    if (opening === 1) {
      return 'freestyle';
    } else if (opening === 2) {
      return 'pro';
    } else if (opening === 3) {
      return 'long_pro';
    } else if (opening === 4) {
      return 'swap';
    }
  };

  const getVariantString = (variant: number) => {
    return variant === 1 ? 'freestyle' : 'swap';
  };

  if (loading) {
    return <Loading />
  }

  return (
    <div className="lobbies-container">
      <div className="title-lobbies">Available Lobbies</div>
      <ol>
        {lobbiesData.map((lobby) => (
          <Link
            key={lobby.lobbyId}
            to={`/users/lobby/${lobby.lobbyId}`}
            style={{ textDecoration: 'none', color: 'inherit' }}
          >
            <li key={lobby.lobbyId}>
              <div className="title">{lobby.name}</div>
              <div className="descr">
                <span>{`Player1: ${lobby.p1}`}</span>
                <span>{`Id: ${lobby.lobbyId}`}</span>
                <span>{`Opening: ${getOpeningString(lobby.opening)}`}</span>
                <span>{`Variant: ${getVariantString(lobby.variant)}`}</span>
                <span>{`Board Size: ${lobby.boardSize}`}</span>
              </div>
            </li>
          </Link>
        ))}
      </ol>
    </div>
  );
}

export default Get_lobbies;