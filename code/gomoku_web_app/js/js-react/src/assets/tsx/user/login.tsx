import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {tryRequest} from '../utils/requests';
import {createCookie, getUserCookie, removeUserCookie} from "../utils/session-handler";
import {login} from "../requests/user_requests";

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    const responseData = await tryRequest({
      loadingSetter: setLoading,
      request: login,
      args: [email, password]
    })

    if (responseData != undefined) {
      console.log(`response data ${responseData}`)
      const user = getUserCookie()
      if (user !== undefined) {
        removeUserCookie()
      }
      createCookie(responseData)
      navigate('/');
    }
  };

  // Update the submit button state based on the conditions
  useEffect(() => {
    setIsSubmitDisabled(!email || !password);
  }, [email, password]);


  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
      </div>
    );
  }

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
