import { Loading } from "../web-ui/request-ui-handler";
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Waiting_Opponent() {
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  /*const handleSubmit = async (e: { preventDefault: () => void; }) => {
     e.preventDefault();

     try {
           setLoading(true);
           const response: any = await execute_request(
             user_routes.create_user.url,
             user_routes.create_user.method,
             data
           )

           navigate('/game');
           } catch (rejectedPromise: any) {
                 const error = await rejectedPromise
                 alert(error.message)
               } finally {
                 setLoading(false);
               }
  }*/

  if (loading) {
    return (
      <div className="spinner-container">
        <div className="spinner"></div>
        <div className="loading-text">
          Waiting for opponent...
          Don't want to wait?
          <div className="btn-place">
            <Link to="/">
              <button className="btn btn-primary custom-btn">Go back</button>
            </Link>
          </div>
        </div>
      </div>
    );
  }

}

export default Waiting_Opponent;