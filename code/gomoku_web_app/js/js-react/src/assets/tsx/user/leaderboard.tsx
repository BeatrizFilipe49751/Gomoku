import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {execute_request} from "../requests/requests";
import {user_routes} from "../api-routes/api_routes";

function Leaderboard() {
  const [leaderboardData, setLeaderboardData] = useState([]);
  const [totalListSize, setTotalListSize] = useState(5);
  const [loading, setLoading] = useState(true);
  const [skip, setSkip] = useState(0)
  useEffect(() => {
    console.log(skip)
    const url = user_routes.get_leaderboard.url + `?limit:5&skip=${skip}`
    execute_request(
        url,
        user_routes.get_leaderboard.method,
        null
    ).then(response => {
      setTotalListSize(response.totalListSize)
      setLeaderboardData(response.list);
    }).catch(rejectedPromise => {
      alert(rejectedPromise.message);
    }).finally(() => {
      setLoading(false);
    });
  }, [skip]);

  const nextPage = () => {
    let nextSkip = skip + 5
    if(nextSkip > totalListSize) nextSkip = totalListSize
    if(nextSkip < totalListSize){
      setLoading(true);
      setSkip(nextSkip)
    }
  }

  const prevPage = () => {
    if(skip >= 5) {
      setLoading(true);
      setSkip(prevState => prevState - 5)
    }
  }


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
          <button className="btn btn-primary btn-paging" onClick={prevPage}>Previous Page</button>
          <button className="btn btn-primary btn-paging" onClick={nextPage}>Next Page</button>
        </div>
      </div>

    </div>
  );
}

export default Leaderboard;
