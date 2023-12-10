import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { user_routes } from '../api-routes/api_routes';
import {execute_request, execute_request_gen, formatUrl} from '../requests/requests';

function Profile() {
  const { userId } = useParams();
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    execute_request_gen(
      formatUrl(user_routes.get_user.url, { userId: userId }),
      user_routes.get_user.method,
      null,
        false
    )
      .then(response => setUser(response.properties))
      .catch(rejectedPromise => {
        rejectedPromise.then((error: { message: any; }) => {
          alert(error.message)
        })
      })
      .finally(() => setLoading(false))
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
          <p>id: {user.userId}</p>
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
