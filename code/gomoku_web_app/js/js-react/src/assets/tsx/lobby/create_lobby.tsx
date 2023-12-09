import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { execute_request_auth } from "../requests/requests";
import { lobby_api_routes } from "../api-routes/api_routes";
import { Loading } from "../web-ui/request-ui-handler";

function Create_Lobby() {
  const [name, setName] = useState('');
  const [openingType, setOpeningType] = useState(1);
  const [variantType, setVariantType] = useState(1);
  const [boardSize, setBoardSize] = useState(15);
  const [waitingForOpponent, setWaitingForOpponent] = useState(false)
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  const navigate = useNavigate();

  // Event handler for form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    const data = {
      name: name,
      opening: openingType,
      variant: variantType,
      size: boardSize
    }
    try {
      await execute_request_auth(
        lobby_api_routes.create_lobby.url,
        lobby_api_routes.create_lobby.method,
        data
      )
      alert('Create lobby successful!');
      setWaitingForOpponent(true)

      /**
       *  TODO
       *  - do polling
       *  - quit waiting
       *  - navigate to game when other user joins
       */

    } catch (rejectedPromise) {
      const error = await rejectedPromise
      alert(error.message)
    }
  };

  // Update the submit button state based on the conditions
  useEffect(() => {
    setIsSubmitDisabled(!name);
  }, [name]);

  if (waitingForOpponent) {
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
                  <option value="2">Pro</option>
                  <option value="3">Long_Pro</option>
                  <option value="4">Swap</option>
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
                  <option value="2">Swap</option>
                </select>
              </div>
            </div>
            <div className="form-group mt-3">
              <label>Board Size</label>
              <div className="custom-dropdown">
                <select
                  className="form-control"
                  value={boardSize}
                  onChange={(e) => setBoardSize(parseInt(e.target.value, 10))}
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

export default Create_Lobby;
