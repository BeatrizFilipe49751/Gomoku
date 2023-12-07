import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

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
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  /*useEffect(() => {
    const fetchLobbiesData = async () => {
      try {
        // Replace with actual API call
        setLoading(true);
        const response: Response = await fetch('http://localhost:8080/api/users/lobbies', {
          method: 'GET',
          headers: { 'Content-Type': 'application/json' },
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setLobbiesData(data);
        setLoading(false);
      } catch (error) {
        setError(error);
        setLoading(false);
      }
    };

    fetchLobbiesData();
  }, []);*/

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

    const getVariantString = (variant) => {
      return variant === 1 ? 'freestyle' : 'swap';
    };

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
      </div>
    );
  }
  if (error) {
    return <div>Error: {error.message}</div>;
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