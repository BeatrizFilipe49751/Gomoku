import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider, Outlet, Route } from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';

import Home from './home';
import Login from './login';
import Register from './register';
import Navbar from './navbar';
import Leaderboard from './leaderboard';

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
      ],
    },
  ]
);

export function appRouter() {
  const root = createRoot(document.getElementById('container'));
  root.render(<RouterProvider router={router} />);
}
