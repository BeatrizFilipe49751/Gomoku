import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Register() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [verifyPassword, verifySetPassword] = useState('');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [error, setError] = useState(null);

    // Update the submit button state based on the conditions
      useEffect(() => {
        setIsSubmitDisabled(!username || !email || !password || !verifyPassword);
      }, [username, email, password, verifyPassword]);

  // Event handler for form submission
    const handleSubmit = async (e) => {
      e.preventDefault();

      if (password !== verifyPassword) {
        alert('Passwords do not match');
        return;
      }

      const data = {
        username: username,
        email: email,
        password: password,
      };

      try {
        //setLoading(true);
        const response = await fetch('http://localhost:8080/api/users', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: { 'Content-Type': 'application/json' },
        });

        /*if (!response.ok) {
          throw new Error('Network response was not ok');
        }*/

        const responseData = await response.json();
        // Handle the response data if needed
        console.log(responseData);

        alert('Registration successful! YEY! Proceed to login.');
        navigate('/users/login');
      } catch (error) {
        setError(error);
        setLoading(false);
      } finally {
        setLoading(false);
      }
    };

    /*if (loading) {
      return (
        <div className="spinner-container">
          <div className="spinner"></div>
        </div>
      );
    }*/

    if (error) {
      return <div>Error: {error.message}</div>;
    }

  return (
    <div className="Auth-form-container">
    <div className="Auth-form-wrapper">
      <form className="Auth-form" onSubmit={handleSubmit}>
        <div className="Auth-form-content">
          <h3 className="Auth-form-title">Create Account</h3>
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
                      <label>Username</label>
                      <input
                        type="text"
                        className="form-control mt-1"
                        placeholder="Enter username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
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
              Sign Up
            </button>
          </div>
        </div>
      </form>
          <div className="Square-register">
                          <h3 className="Square-title">Welcome!</h3>
                          <p className="Square-text">
                            Already a member? Sign in here!
                          </p>
                          <Link to="/users/login">
                            <button className="btn btn-outline-light custom-btn-transparent">Sign In</button>
                          </Link>
                        </div>
                      </div>
                    </div>
  );
}

export default Register;
