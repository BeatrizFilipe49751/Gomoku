import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import {createBrowserRouter, RouterProvider, Outlet, RouteObject} from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/App.css';

import Home from './web-ui/home';
import Login from './user/login';
import Register from './user/register';
import Navbar from './web-ui/navbar';
import Leaderboard from './user/leaderboard';
import Join_Lobby from './lobby/join_lobby';

const HeaderLayout = () => (
  <>
    <header>
      <Navbar />
    </header>
    <Outlet />
  </>
);

const home : RouteObject =  {
      index: true,
      element: <Home />,
}

const user_routes: RouteObject[] = [
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
]


const lobby_routes: RouteObject[] = [
  {
    path: 'users/lobbies',
    element: <Join_Lobby />,
  },
]


const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <HeaderLayout />,
      children: [
          home, ...user_routes, ...lobby_routes
      ],
    },
  ]
);

export function appRouter() {
  const root = createRoot(document.getElementById('container'));
  root.render(<RouterProvider router={router} />);
}
