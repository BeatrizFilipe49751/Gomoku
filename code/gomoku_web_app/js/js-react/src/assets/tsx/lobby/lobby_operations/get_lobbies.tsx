import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import {Loading} from "../../web-ui/request-ui-handler";
import {tryRequest} from "../../utils/requests";
import {next, pagingHidden, prev} from '../../utils/paging';
import {getLobbies} from "../../requests/lobby_requests";

function Get_lobbies() {
  const [lobbiesData, setLobbiesData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [skip, setSkip] = useState(0)
  const [totalListSize, setTotalListSize] = useState(5);
  useEffect(() => {

    const fetchLobbies = async() => {
        const leaderBoard = await tryRequest({
            loadingSetter: setLoading,
            request: getLobbies,
            args : [skip]
        })

        if(leaderBoard != undefined) {
            setTotalListSize(leaderBoard.totalListSize)
            setLobbiesData(leaderBoard.list)
        }
    }
    fetchLobbies()
  }, [skip]);

  const getOpeningString = (opening: number) => {
    switch (opening) {
        case 1:
            return 'freestyle';
        case 2:
            return 'pro';
        case 3:
            return 'long_pro';
        case 4:
            return 'swap';
    }
  };

  const getVariantString = (variant: number) => {
    return variant === 1 ? 'freestyle' : 'swap';
  };

  if (loading) {
    return <Loading />
  }

  const lobbiesTitle = lobbiesData.length > 0 ?
    "Available Lobbies" : "No Available Lobbies"

  return (
    <div className="lobbies-container">
      <div className="title-lobbies">{lobbiesTitle}</div>
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
        <button hidden={pagingHidden(lobbiesData)} className="btn btn-primary btn-paging" onClick={() => {
          prev({
            skip: skip,
            setSkip: setSkip,
            setLoading: setLoading
          })
        }}>Previous Page</button>
        <button hidden={pagingHidden(lobbiesData)} className="btn btn-primary btn-paging" onClick={() =>
          next({
            skip: skip,
            setSkip: setSkip,
            setLoading: setLoading,
            totalListSize: totalListSize
          })
        }>Next Page</button>
      </div>
    </div>

  );
}

export default Get_lobbies;