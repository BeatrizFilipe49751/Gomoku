import React, { useState, useEffect } from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {execute_request_auth, formatUrl} from "../requests/requests";
import {lobby_api_routes} from "../api-routes/api_routes";

function Waiting_Opponent() {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { lobbyId  } = useParams()
  const checkGameStatus = async () => {
    try {
      const response = await execute_request_auth(
          formatUrl(
              lobby_api_routes.check_full_lobby.url,
              {lobbyId: lobbyId}
          ),
          lobby_api_routes.check_full_lobby.method,
          null
      )
      if(response.gameId != null) {
        setLoading(false)
        navigate(`/game/${response.gameId}`)
      }
    } catch (rejectedPromise) {
      const error = await rejectedPromise
      alert(error.message)
    }
  };

  useEffect(() => {
    const intervalId = setInterval(checkGameStatus, 5000);
    return () => clearInterval(intervalId);
  }, [])

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
        <div className="loading-text">
          Waiting for opponent...
          Don't want to wait?
          <div className="btn-place">
            <Link to="/">
              <button className="btn btn-primary custom-btn">Go back</button>
            </Link>
          </div>
        </div>
      </div>
    );
  }

}

export default Waiting_Opponent;