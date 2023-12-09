import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { user_routes } from '../api-routes/api_routes';
import {execute_request_get, execute_request_post, formatUrl} from '../requests/requests';

const sampleProfile = { username: 'Username', email: 'username_123@gmail.com', points: 1000 };

function Profile() {
  const { userId } = useParams();
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(undefined);
  useEffect(() => {
    setLoading(true)
    execute_request_get(
        formatUrl(user_routes.get_user.url, {userId: userId}),
        user_routes.get_user.method
    ).then(response => {
      console.log(response)
      setUser(response.properties)
    }).catch(error => {
      alert(error.message);
    }).finally(() => {
      setLoading(false)
    })

  }, []);

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
      </div>
    );
  }
    return (
      user &&
        <div className="card-container">
          <div className="card">
            <div className="card_background_img"></div>
            <div className="card_profile_img"></div>
            <div className="user_details">
              <h3>{user.username}</h3>
              <p>{user.email}</p>
            </div>
            <div className="user_details_2">
              <p>id: {userId}</p>
            </div>
          </div>
        </div>
    );
}

/*
 <div className="card_count">
              <div className="count">
                <div className="points">
                  <h3>{sampleProfile.points}</h3>
                  <p>Points</p>
                </div>
              </div>
            </div>
 */
export default Profile;
