import React from 'react';
import {Link} from 'react-router-dom';


export function Loading() {
    return (
        <div className="spinner-container">
            <div className="spinner"></div>
        </div>
    );
}

export function Waiting_Opponent() {
    <div className="spinner-container">
    <div className="spinner"></div>
    <div className="loading-text">
      Waiting for opponent...
      Don't want to wait?
      <div className="btn-placement">
        <div className="btn-place">
          <Link to="/">
            <button className="btn btn-primary custom-btn">Go back</button>
          </Link>
        </div>
        <div className="btn-place">
          <Link to="/">
            <button className="btn btn-primary custom-btn">Quit</button>
          </Link>
        </div>
      </div>
    </div>
  </div>
}
