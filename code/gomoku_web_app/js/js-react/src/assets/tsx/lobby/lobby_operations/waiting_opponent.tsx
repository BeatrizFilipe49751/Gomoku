import React, {useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {tryRequest} from "../../utils/requests";
import {check_full_lobby} from '../../requests/lobby_requests';

function Waiting_Opponent() {
  const [waiting, setWaiting] = useState(true);
  const navigate = useNavigate();
  const { lobbyId } = useParams()
  const checkGameStatus = async () => {
    const game = await tryRequest({
      request: check_full_lobby,
      args: [lobbyId]
    })

    if(game != undefined && game.gameId != null) {
      setWaiting(false)
      navigate(`/game/${game.gameId}`)
    }
  };

  useEffect(() => {
    let iterations = 0;
    const maxIterations = 12 * 10;
  
    const intervalId = setInterval(() => {
      checkGameStatus();
      iterations++;
      if (iterations >= maxIterations) {
        clearInterval(intervalId); // Stop the interval when the maximum is reached
        alert('No player has joined, try again later');
        navigate(`/users/lobby/quit/${lobbyId}`)
      }
    }, 5000);
  
    return () => clearInterval(intervalId);
  }, [])

  if (waiting) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
        <div className="loading-text">
          Waiting for opponent...
          Don't want to wait?
          <div className="btn-placement">
            <div className="btn-place">
              <Link to="/">
                <button className="btn btn-primary custom-btn">Go back</button>
              </Link>
            </div>
            <div className="btn-place">
              <Link to={`/users/lobby/quit/${lobbyId}`}>
                <button className="btn btn-primary custom-btn">Quit</button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    );
  }

}

export default Waiting_Opponent;