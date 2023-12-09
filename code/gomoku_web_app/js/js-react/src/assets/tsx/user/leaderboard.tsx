import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {execute_request} from "../requests/requests";
import {user_routes} from "../api-routes/api_routes";

function Leaderboard() {
  const [leaderboardData, setLeaderboardData] = useState([]);
  const [loading, setLoading] = useState(true);

  // To be used when integration with API is ready
  useEffect(() => {
    // Replace with actual API call
    execute_request(
        user_routes.get_leaderboard.url,
        user_routes.get_leaderboard.method,
        null
    ).then(response => {
      setLeaderboardData(response);
    }).catch(rejectedPromise => {
      alert(rejectedPromise.message);
    }).finally(() => {
      setLoading(false);
    });
  }, []);

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="body-leaderboard">
      <div className="leaderboard-container">
        <h1 className="body-header">Leaderboard</h1>
        <div className="leaderboard">
          <table>
            <thead>
              <tr>
                <th>Position</th>
                <th>Username</th>
                <th>Points</th>
              </tr>
            </thead>
            <tbody>
              {leaderboardData.map((user, index) => (
                <tr key={index}>
                  <td className="number">{index + 1}</td>
                  <td className="name">
                    <Link
                      style={{ textDecoration: 'none', color: 'inherit' }}
                      to={`/users/profile/${user.userId}`}>{user.username}
                    </Link>
                  </td>
                  <td className="points">{user.points}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default Leaderboard;
