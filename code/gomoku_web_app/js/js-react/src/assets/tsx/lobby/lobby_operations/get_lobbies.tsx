import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Loading } from "../../web-ui/request-ui-handler";
import {execute_request_auth, execute_request_gen} from "../../requests/requests";
import { lobby_api_routes } from "../../api-routes/api_routes";

function Get_lobbies() {
  const [lobbiesData, setLobbiesData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [skip, setSkip] = useState(0)
  const [totalListSize, setTotalListSize] = useState(5);
  useEffect(() => {

    const url = lobby_api_routes.get_available_lobbies.url + `?limit=${5}&skip=${skip}`

    execute_request_gen(
      url,
      lobby_api_routes.get_available_lobbies.method,
      null,
        true
    )
      .then(response => {
        console.log(response)
        setTotalListSize(response.totalListSize)
        setLobbiesData(response.list)
      })
      .catch(error => alert(error.message))
      .finally(() => setLoading(false))
  }, [skip]);

  const getOpeningString = (opening: number) => {
    if (opening === 1) {
      return 'freestyle';
    } else if (opening === 2) {
      return 'pro';
    } else if (opening === 3) {
      return 'long_pro';
    } else if (opening === 4) {
      return 'swap';
    }
  };

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

  const getVariantString = (variant: number) => {
    return variant === 1 ? 'freestyle' : 'swap';
  };

  if (loading) {
    return <Loading />
  }

  return (
    <div className="lobbies-container">
      <div className="title-lobbies">Available Lobbies</div>
      <ol>
        {lobbiesData.map((lobby) => (
          <Link
            key={lobby.lobbyId}
            to={`/users/lobby/${lobby.lobbyId}`}
            style={{ textDecoration: 'none', color: 'inherit' }}
          >
            <li key={lobby.lobbyId}>
              <div className="title">{lobby.name}</div>
              <div className="descr">
                <span>{`Player1: ${lobby.p1}`}</span>
                <span>{`Id: ${lobby.lobbyId}`}</span>
                <span>{`Opening: ${getOpeningString(lobby.opening)}`}</span>
                <span>{`Variant: ${getVariantString(lobby.variant)}`}</span>
                <span>{`Board Size: ${lobby.boardSize}`}</span>
              </div>
            </li>
          </Link>
        ))}
      </ol>
      <div className="btn-lobbies-div">
        <button className="btn btn-primary btn-paging" onClick={prevPage}>Previous Page</button>
        <button className="btn btn-primary btn-paging" onClick={nextPage}>Next Page</button>
      </div>
    </div>

  );
}

export default Get_lobbies;