import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {tryRequest} from '../utils/requests';
import {getUser} from "../requests/user_requests";

function Profile() {
  const { userId } = useParams();
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true);
  useEffect(() => {

    tryRequest({
      loadingSetter: setLoading,
      request: getUser,
      args: [userId]
    }).then(response => {
      if (response != undefined) {
        setUser(response.properties)
      }
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
