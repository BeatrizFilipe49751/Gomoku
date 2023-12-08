import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider, Outlet, Route } from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/App.css';

import Home from './web-ui/home';
import Login from './user/login';
import Register from './user/register';
import Navbar from './web-ui/navbar';
import Leaderboard from './user/leaderboard';
import Join_Lobby from './lobby/join_lobby';
import Create_Lobby from './lobby/create_lobby';

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
      path: '/',
      element: <HeaderLayout />,
      children: [
        {
          index: true,
          element: <Home />,
        },
        {
          path: 'users/login',
          element: <Login />,
        },
        {
          path: 'users/register',
          element: <Register />,
        },
        {
          path: 'users/leaderboard',
          element: <Leaderboard />,
        },
        {
          path: 'users/lobbies',
          element: <Join_Lobby />,
        },
        {
          path: 'users/lobby',
          element: <Create_Lobby />,
        },
      ],
    },
  ]
);

export function appRouter() {
  const root = createRoot(document.getElementById('container'));
  root.render(<RouterProvider router={router} />);
}
