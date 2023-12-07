import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap-icons/font/bootstrap-icons.css';

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <div className="sidenav">
      <Link to='/' className="nav-link" onClick={() => navigate('/')}>
        <i className="bi-house-door"></i> Home
      </Link>
      <Link to='/users/login' className="nav-link" onClick={() => navigate('/users/login')}>
        <i className="bi-box-arrow-in-right"></i> Sign In
      </Link>
      <Link to='/users/register' className="nav-link" onClick={() => navigate('/users/register')}>
        <i className="bi-person-plus"></i> Sign Up
      </Link>
      <Link to='/users/leaderboard' className="nav-link" onClick={() => navigate('/users/leaderboard')}>
        <i className="bi-trophy"></i> Leaderboard
      </Link>
      <Link to='/users/lobbies' className="nav-link" onClick={() => navigate('/users/lobbies')}>
        <i className="bi-controller"></i> Join Lobby
      </Link>
      <Link to='/users/lobby' className="nav-link" onClick={() => navigate('/users/lobby')}>
        <i className="bi-plus-circle"></i> New Lobby
      </Link>
    </div>
  );
};

export default Navbar;
