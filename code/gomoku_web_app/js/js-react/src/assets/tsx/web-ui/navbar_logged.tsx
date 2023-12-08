import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap-icons/font/bootstrap-icons.css';

const user = {
  userId: 6,
  username: 'Username'
};

const NavbarLogged = () => {
  const navigate = useNavigate();

  return (
    <div className="sidenav">
      <div className="user-profile">
        <Link to={`/users/profile/${user.userId}`} className="profile-link">
          <div className="profile-pic"></div>
          <h3>{user.username}</h3>
        </Link>
      </div>
      <Link to="/" className="nav-link" onClick={() => navigate('/')}>
        <i className="bi-house-door"></i> Home
      </Link>
      <Link
        to="/users/logout"
        className="nav-link"
        onClick={() => navigate('/users/logout')}
      >
        <i className="bi-door-closed"></i> Logout
      </Link>
      <Link
        to="/users/leaderboard"
        className="nav-link"
        onClick={() => navigate('/users/leaderboard')}
      >
        <i className="bi-trophy"></i> Leaderboard
      </Link>
      <Link to="/users/lobbies" className="nav-link" onClick={() => navigate('/users/lobbies')}>
        <i className="bi-controller"></i> Join Lobby
      </Link>
      <Link to="/users/lobby" className="nav-link" onClick={() => navigate('/users/lobby')}>
        <i className="bi-plus-circle"></i> New Lobby
      </Link>
    </div>
  );
};

export default NavbarLogged;
