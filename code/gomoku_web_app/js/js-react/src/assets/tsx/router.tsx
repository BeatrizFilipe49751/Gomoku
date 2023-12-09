import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider, Outlet, RouteObject } from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/App.css';
import '../css/Game.css'

import Home from './web-ui/home';
import Login from './user/login';
import Register from './user/register';
import Navbar from './web-ui/navbar';
import Leaderboard from './user/leaderboard';
import Join_Lobby from './lobby/join_lobby';
import Create_Lobby from "./lobby/create_lobby";
import Profile from "./user/profile";
import NavbarLogged from "./web-ui/navbar_logged";
import Game from "./game/game"
import HomeLogged from "./web-ui/home_logged";
import Waiting_Opponent from "./lobby/waiting_opponent";

const HeaderLayout = () => (
  <>
    <header>
      <Navbar />
    </header>
    <Outlet />
  </>
);

const home: RouteObject = {
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
  {
    path: 'users/profile/:userId',
    element: <Profile />,
  }
]

const lobby_routes: RouteObject[] = [
  {
    path: 'users/lobby',
    element: <Create_Lobby />,
  },
  {
    path: 'users/lobbies',
    element: <Join_Lobby />,
  },
  {
    path: 'users/lobby/:lobbyId/wait',
    element: <Waiting_Opponent />,
  }
]

const game_routes: RouteObject[] = [
  {
    path: 'game',
    element: <Game />
  }
]

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <HeaderLayout />,
      children: [
        home,
        ...user_routes,
        ...lobby_routes,
        ...game_routes
      ],
    },
  ]
);


export function appRouter() {
  const root = createRoot(document.getElementById('container'));
  root.render(<RouterProvider router={router} />);
}
