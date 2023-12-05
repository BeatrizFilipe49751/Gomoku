import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

// Test data - to be removed!
const sampleLeaderboardData = [
  { username: 'ABC', points: 1000 },
  { username: 'Mark Lee', points: 500 },
  { username: 'Lee', points: 225 },
  { username: 'user2', points: 100 },
  { username: 'user6', points: 50 }
];

function Leaderboard() {
    const [leaderboardData, setLeaderboardData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // To be used when integration with API is ready
    /*useEffect(() => {
        const fetchLeaderboardData = async () => {
          try {
            // Replace with actual API call
            const response = await fetch('/users/leaderboard');
            const data = await response.json();
            setLeaderboardData(data);
            setLoading(false);
          } catch (error) {
            setError(error);
            setLoading(false);
          }
    };

    fetchLeaderboardData();
      }, []);*/

      // To test loading - to be removed!
      useEffect(() => {
          setLoading(true);

          setTimeout(() => {
            // Simulate successful data fetching
            setLeaderboardData(sampleLeaderboardData);
            setLoading(false);
          }, 2000);
      }, []);

      if (loading) {
        return (
            <div className="spinner-container">
                  <div className="spinner"></div>
            </div>
        );
      }
      if (error) {
        return <div>Error: {error.message}</div>;
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
                    <td className="name">{user.username}</td>
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
