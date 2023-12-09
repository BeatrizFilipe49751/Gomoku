import React from 'react';
import { Link } from 'react-router-dom';

function HomeLogged() {
  return (
    <div>
      <div className="wave-container">
        <h1 className="wave-text">Gomoku</h1>
      </div>
      <div className="home-container">
        <div className="home-text-title">
          <b>Welcome back!</b>
        </div>
        <div className="home-text">
          🎮 <b>Play with other Members:</b> As a member, enjoy the privilege of diving into Gomoku matches with other players. Experience the thrill of strategic gameplay in real-time.
          <br /><br />
          🏆 <b>Track Your Progress:</b> Check your position on the leaderboard, and strive for the top spot. Your journey to Gomoku mastery continues!
        </div>
        <div className="home-text-end">
          <b>Ready to dive in?</b> Let the games begin!
        </div>
        <div className="circles-container">
          <div className="circle">
            <div className="circle-content">
              Meet the Team
            </div>
            <div className="team-members">
              <div className="team-member-title">
                Our Team:
              </div>
              <div className="team-member">
                Beatriz Filipe
                A49751@alunos.isel.pt
              </div>
              <div className="team-member">
                Alexandre Severino
                A49457@alunos.isel.pt
              </div>
              <div className="team-member-last">
                Diogo Carichas
                A49473@alunos.isel.pt
              </div>
            </div>
          </div>
          <div className="circle">
            <div className="circle-content">
              About the game
            </div>
            <div className="team-members">
              <div className="about-game-line">
                Gomoku is a classic strategy board game
              </div>
              <div className="about-game-line-first">
                that originated in China.
              </div>
              <div className="about-game-line">
                <b>The objective</b> is simple:
              </div>
              <div className="about-game-line">
                be the first to connect five of your pieces
              </div>
              <div className="about-game-line-last">
                in a row horizontally, vertically, or diagonally.
              </div>
              <div className="about-game-line">
                <b>Version: 1.0.0</b>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomeLogged;
