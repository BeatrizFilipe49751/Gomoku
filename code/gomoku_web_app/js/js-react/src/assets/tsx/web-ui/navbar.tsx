import React from 'react';
import { Link, NavigateFunction, useNavigate } from 'react-router-dom';
import 'bootstrap-icons/font/bootstrap-icons.css';
import { getAuthToken, getUser } from "../requests/session-handler";

function Navbar() {
    const navigate = useNavigate()
    let token = getAuthToken()
    if (token === undefined) {
        return (
            <NavbarDefault navigate={navigate} />
        );
    } else {
        return (
            <NavbarLogged navigate={navigate} />
        );
    }
}

function NavbarDefault({ navigate }: { navigate: NavigateFunction }) {
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
        </div>
    )
}

function NavbarLogged({ navigate }: { navigate: NavigateFunction }) {
    const user = getUser()

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
                <i className="bi-plus-circle"></i> Lobby
            </Link>
        </div>
    )
}

export default Navbar;
