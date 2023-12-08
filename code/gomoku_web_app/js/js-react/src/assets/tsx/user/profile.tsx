import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { user_routes } from '../api-routes/api_routes';
import { execute_request } from '../requests/requests';

const sampleProfile = { username: 'Username', email: 'username_123@gmail.com', points: 1000 };

function Profile() {
  const { userId } = useParams();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
      </div>
    );
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div className="card-container">
      <div className="card">
        <div className="card_background_img"></div>
        <div className="card_profile_img"></div>
        <div className="user_details">
          <h3>{sampleProfile.username}</h3>
          <p>{sampleProfile.email}</p>
        </div>
        <div className="user_details_2">
          <p>id: {userId}</p>
        </div>
        <div className="card_count">
          <div className="count">
            <div className="points">
              <h3>{sampleProfile.points}</h3>
              <p>Points</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;
