import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
    return (
        <div>
            <div className="image-container">
                <div className="overlay-text">
                    <h1>Gomoku</h1>
                </div>
            </div>
            <div className="divider"></div>
            <div className="bg">
               <h3>Welcome!</h3>
            </div>
        </div>
    );
}

export default Home;
