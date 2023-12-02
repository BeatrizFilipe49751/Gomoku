import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [verifyPassword, verifySetPassword] = useState('');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  // Event handler for form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (password !== verifyPassword) {
      alert('Passwords do not match');
      return;
    }

    try {
      // TODO - Call authentication API
      // Simulating successful login
      alert('Login successful!');
    } catch (error) {
      // TODO - Handle authentication errors
      alert('Login failed. Please try again.');
    }
  };

  // Update the submit button state based on the conditions
    useEffect(() => {
      setIsSubmitDisabled(!email || !password || !verifyPassword);
    }, [email, password, verifyPassword]);

  return (
    <div className="Auth-form-container">
      <form className="Auth-form" onSubmit={handleSubmit}>
        <div className="Auth-form-content">
          <h3 className="Auth-form-title">Login</h3>
          <div className="form-group mt-3">
            <label>Email address</label>
            <input
              type="email"
              className="form-control mt-1"
              placeholder="Enter email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <div className="form-group mt-3">
            <label>Password</label>
            <input
              type="password"
              className="form-control mt-1"
              placeholder="Enter password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
            <div className="form-group mt-3">
                <label>Verify Password</label>
                <input
                type="password"
                className="form-control mt-1"
                placeholder="Verify password"
                value={verifyPassword}
                onChange={(e) => verifySetPassword(e.target.value)}
                />
            </div>
          <div className="d-grid gap-2 mt-3">
            <button type="submit" className="btn btn-primary custom-btn" disabled={isSubmitDisabled}>
              Submit
            </button>
          </div>
          <p className="not-registered text-right mt-2">
            Not a member? <Link to="/users/register" style={{ color: '#009b77' }}>Sign up here!</Link>
          </p>
        </div>
      </form>
    </div>
  );
}

export default Login;
