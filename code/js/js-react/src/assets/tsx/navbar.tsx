import React from 'react'
import { Link } from 'react-router-dom'

const Navbar = () => {
    return (
        <div className="sidenav">
          <Link to='/' className="nav-link">Home</Link>
          <Link to='/users/login' className="nav-link">Login</Link>
          <Link to='/users/register' className="nav-link">Register</Link>
        </div>
    );
};

export default Navbar;