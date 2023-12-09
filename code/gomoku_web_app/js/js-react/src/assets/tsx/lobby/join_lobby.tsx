import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import {Loading} from "../web-ui/request-ui-handler";
import {execute_request, execute_request_auth} from "../requests/requests";
import {lobby_api_routes, user_routes} from "../api-routes/api_routes";

// Test data - to be removed!
const sampleLobbiesData = [
  { lobbyId: 2, name: 'Lobby Test', opening: 1, variant: 1, boardSize: 15, p1: 1 },
    { lobbyId: 3, name: 'Lobby Test 2', opening: 2, variant: 2, boardSize: 15, p1: 1 },
    { lobbyId: 4, name: 'Lobby Test 3', opening: 3, variant: 1, boardSize: 15, p1: 1 },
    { lobbyId: 6, name: 'Lobby Test 4', opening: 4, variant: 2, boardSize: 15, p1: 1 },
    { lobbyId: 6, name: 'Lobby Test 4', opening: 4, variant: 2, boardSize: 15, p1: 1 },
];

function Join_Lobby() {
  const [lobbiesData, setLobbiesData] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchLobbiesData = async () => {
      try {
        // Replace with actual API call
        setLoading(true);

        const reponse = await execute_request_auth(
            lobby_api_routes.get_available_lobbies.url,
            lobby_api_routes.get_available_lobbies.method,
            null
        )
        setLoading(false);
      } catch (error) {
        setLoading(false);
      }
    };

    fetchLobbiesData();
  }, []);

  const getOpeningString = (opening) => {
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
    return <Loading/>
  }

  return (
    <div className="lobbies-container">
      <div className="title-lobbies">Available Lobbies</div>
      <ol>
        {sampleLobbiesData.map((lobby) => (
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

export default Join_Lobby;