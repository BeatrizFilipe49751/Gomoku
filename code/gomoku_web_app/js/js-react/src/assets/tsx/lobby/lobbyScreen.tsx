import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import {execute_request_auth, execute_request_gen, formatUrl} from "../requests/requests";
import { game_api_routes, lobby_api_routes } from "../api-routes/api_routes";
import { Loading } from "../web-ui/request-ui-handler";

function LobbyScreen() {
  const [name, setName] = useState('');
  const [openingType, setOpeningType] = useState(1);
  const [variantType, setVariantType] = useState(1);
  const [boardSize, setBoardSize] = useState(15);
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate()

  useEffect(() => {
    execute_request_gen(
        game_api_routes.get_game_userId.url,
        game_api_routes.get_game_userId.method,
        null,
        true
    )
        .then(response => {
          setLoading(false);
          navigate(`/game/${response.gameId}`);
        })
        .catch(rejectedPromise => {
          // User not in an active Game
          rejectedPromise.then(error => console.log(error))
          execute_request_gen(
              lobby_api_routes.get_lobby_userId.url,
              lobby_api_routes.get_lobby_userId.method,
              null,
              true
          )
              .then(lobbyResponse => {
                setLoading(false)
                navigate(`/users/lobby/${lobbyResponse.properties.lobbyId}/wait`)
              })
              .catch(rejectedPromise => {
                // User not in a Lobby
                rejectedPromise.then(error => console.log(error))
                setLoading(false)
              })
        })
  }, [])

  const handleSubmit = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    const data = {
      name: name,
      opening: openingType,
      variant: variantType,
      size: boardSize
    }
    try {
      const response = await execute_request_gen(
        lobby_api_routes.create_lobby.url,
        lobby_api_routes.create_lobby.method,
        data,
          true
      )
      alert('Create lobby successful!');
      setLoading(false)
      navigate(`/users/lobby/${response.properties.lobbyId}/wait`)
    } catch (rejectedPromise) {
      const error = await rejectedPromise
      alert(error.message)
    }
    finally {
      setLoading(false)
    }
  }

  // Update the submit button state based on the conditions
  useEffect(() => {
    setIsSubmitDisabled(!name);
  }, [name]);

  if (loading) {
    return <Loading />
  }

  return (
    <div className="Auth-form-container">
      <div className="Auth-form-wrapper">
        <form className="Auth-form" onSubmit={handleSubmit}>
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Create Lobby</h3>
            <div className="form-group mt-3">
              <label>Name</label>
              <input
                type="text"
                className="form-control mt-1"
                placeholder="Enter lobby name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>
            <div className="form-group mt-3">
              <label>Opening</label>
              <div className="custom-dropdown">
                <select
                  className="form-control"
                  value={openingType}
                  onChange={(e) => setOpeningType(parseInt(e.target.value, 10))}
                >
                  <option value="1">Freestyle</option>
                </select>
              </div>
            </div>
            <div className="form-group mt-3">
              <label>Variant</label>
              <div className="custom-dropdown">
                <select
                  className="form-control"
                  value={variantType}
                  onChange={(e) => setVariantType(parseInt(e.target.value, 10))}
                >
                  <option value="1">Freestyle</option>
                </select>
              </div>
            </div>
            <div className="form-group mt-3">
              <label>Board Size</label>
              <div className="custom-dropdown">
                <select
                  className="form-control"
                  value={boardSize}
                  onChange={(e) => {
                    const newSize = parseInt(e.target.value, 10)
                    setBoardSize(newSize)
                  }}
                >
                  <option value="15">15</option>
                  <option value="19">19</option>
                </select>
              </div>
            </div>
            <div className="d-grid gap-2 mt-3">
              <button type="submit" className="btn btn-primary custom-btn" disabled={isSubmitDisabled}>
                Create
              </button>
            </div>
          </div>
        </form>
        <div className="Square">
          <h3 className="Square-title-lobby">Welcome back!</h3>
          <p className="Square-text">
            Want to join a lobby instead? Choose an available one here!
          </p>
          <Link to="/users/lobbies">
            <button className="btn btn-outline-light custom-btn-transparent">Choose Lobby</button>
          </Link>
        </div>
      </div>
    </div>
  );
}
export default LobbyScreen 
