import React from 'react';
import {createRoot} from 'react-dom/client';
import {createBrowserRouter, Outlet, RouteObject, RouterProvider} from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.min.css';
import '../css/App.css';
import '../css/Game.css'

import Home from './web-ui/home';
import Login from './user/login';
import Register from './user/register';
import Navbar from './web-ui/navbar';
import Leaderboard from './user/leaderboard';
import Get_lobbies from './lobby/lobby_operations/get_lobbies';
import LobbyScreen from "./lobby/lobbyScreen";
import Profile from "./user/profile";
import Game from "./game/game"
import Waiting_Opponent from "./lobby/lobby_operations/waiting_opponent";
import Logout from "./user/logout";
import {JoinLobby} from "./lobby/lobby_operations/joinLobby";
import {QuitLobby} from "./lobby/lobby_operations/quit_lobby";


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
    path: 'users/logout',
    element: <Logout />,
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
    element: <LobbyScreen />,
  },
  {
    path: 'users/lobbies',
    element: <Get_lobbies />,
  },
  {
    path: "users/lobby/:lobbyId",
    element: <JoinLobby />
  },
  {
    path: "users/lobby/quit/:lobbyId",
    element: <QuitLobby />
  },
  {
    path: 'users/lobby/:lobbyId/wait',
    element: <Waiting_Opponent />,
  }
]

const game_routes: RouteObject[] = [
  {
    path: 'game/:gameId',
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
