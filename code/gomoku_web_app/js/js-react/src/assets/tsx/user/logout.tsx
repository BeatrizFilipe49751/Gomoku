import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { user_routes } from '../api-routes/api_routes';
import {execute_request_gen, handleResponse} from '../requests/requests';
import { removeToken } from "../requests/session-handler";
import { Loading } from "../web-ui/request-ui-handler";


function Logout() {
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        execute_request_gen(
            user_routes.logout.url,
            user_routes.logout.method,
            null, true)
            .then(() => {
                removeToken()
                alert('Logout successful!')
                navigate('/')
            })
            .catch(error => alert(error.message))
            .finally( () => {setLoading(false)}
        )
    }, []);

    if (loading) {
        return <Loading />
    }
}

export default Logout