import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [verifyPassword, verifySetPassword] = useState('');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

  const navigate = useNavigate();

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
      navigate('/');
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
    <div className="Auth-form-wrapper">
      <form className="Auth-form" onSubmit={handleSubmit}>
        <div className="Auth-form-content">
          <h3 className="Auth-form-title">Sign In</h3>
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
              Sign In
            </button>
          </div>
        </div>
      </form>
      <div className="Square">
                <h3 className="Square-title">Welcome!</h3>
                <p className="Square-text">
                  Not a member? Sign up now!
                </p>
                <Link to="/users/register">
                  <button className="btn btn-outline-light custom-btn-transparent">Sign Up</button>
                </Link>
              </div>
            </div>
          </div>
  );
}

export default Login;
