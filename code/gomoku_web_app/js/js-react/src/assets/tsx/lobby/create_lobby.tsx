import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Create_Lobby() {
  const [name, setName] = useState('');
  const [openingType, setOpeningType] = useState(1);
  const [variantType, setVariantType] = useState(1);
  const [boardSize, setBoardSize] = useState(15);
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  const navigate = useNavigate();

  // Event handler for form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // TODO - Call authentication API
      // Simulating successful login

      alert('Create lobby successful!');
      //navigate('/');
    } catch (error) {
      // TODO - Handle authentication errors
      alert('Create lobby failed. Please try again.');
    }
  };

  // Update the submit button state based on the conditions
  useEffect(() => {
    setIsSubmitDisabled(!name);
  }, [name]);

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
                  <option value="16">16</option>
                  <option value="17">17</option>
                  <option value="18">18</option>
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
