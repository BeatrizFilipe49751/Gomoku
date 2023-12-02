import React, {useState} from 'react'
import { createRoot } from 'react-dom/client'
import {
    createBrowserRouter,
    RouterProvider,
    Link,
    useParams,
    useNavigate,
    Outlet
} from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';
import "./App.css"

import Home from './home';
import Login from './login';
import Register from './register';
import Navbar from './navbar';

const HeaderLayout = () => (
  <>
    <header>
      <Navbar />
    </header>
      <Outlet />
  </>
);

const router = createBrowserRouter(
    [
    {
        element: <HeaderLayout />,
        children: [
        {
            path : "/",
            element : < Home />
        },
        {
            path : "/users/login",
            element : < Login />
        },
        {
            path : "/users/register",
            element : < Register />
        }
    ],
},
]);


/*function Navigate(){
    return (
        <Navbar bg="dark" variant="dark">
            <Nav className="mr-auto">
                <Nav.Link as={Link} to="/users/login">Login</Nav.Link>
                <Nav.Link as={Link} to="/users/register">Register</Nav.Link>
            </Nav>
        </Navbar>
        )
}*/

export function appRouter(){
    const root = createRoot(document.getElementById("container"))
    root.render(<RouterProvider router={router} />)
}