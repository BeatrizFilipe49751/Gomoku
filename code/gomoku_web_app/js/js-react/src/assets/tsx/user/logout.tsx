import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {tryRequest} from '../utils/requests';
import {Loading} from "../web-ui/request-ui-handler";
import {logout} from "../requests/user_requests";


function Logout() {
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        tryRequest(
            {
            loadingSetter: setLoading,
            request: logout,
            args: []
        }).then( () => {
            navigate('/')
        })

    }, []);

    if (loading) {
        return <Loading />
    }
}

export default Logout