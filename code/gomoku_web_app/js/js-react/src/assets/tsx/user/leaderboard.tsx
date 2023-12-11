import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {tryRequest} from "../utils/requests";
import {getLeaderBoard} from "../requests/user_requests";
import {next, prev} from '../utils/paging';

function Leaderboard() {
  const [leaderboardData, setLeaderboardData] = useState([]);
  const [totalListSize, setTotalListSize] = useState(5);
  const [loading, setLoading] = useState(true);
  const [skip, setSkip] = useState(0)

  useEffect(() => {
    tryRequest({
      loadingSetter: setLoading,
      request: getLeaderBoard,
      args: [skip]
    })
      .then(response => {
        if(response != undefined) {
          setTotalListSize(response.totalListSize)
          setLeaderboardData(response.list);
        }
      })
  }, [skip]);

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
        <div className="btn-place">
          <button className="btn btn-primary btn-paging" onClick={() =>
            prev({
              skip: skip,
              setSkip: setSkip,
              setLoading: setLoading
            })
          }>Previous Page</button>
          <button className="btn btn-primary btn-paging" onClick={() =>
            next({
              skip: skip,
              setSkip: setSkip,
              setLoading: setLoading,
              totalListSize: totalListSize
            })
          }>Next Page</button>
        </div>
      </div>

    </div>
  );
}

export default Leaderboard;
