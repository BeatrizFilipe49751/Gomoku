import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { user_routes } from '../api-routes/api_routes';
import {execute_request, execute_request_auth} from '../requests/requests';
import {createCookie, removeToken} from "../requests/session-handler";
import {Loading} from "../web-ui/request-ui-handler";


function Logout() {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const logout = async () => {
        try {
            setLoading(true)
            await execute_request_auth(
                user_routes.logout.url,
                user_routes.logout.method,
                null

            )
            //remove the cookie
            removeToken()
            alert('Logout successful!')
            navigate('/')
        } catch (rejectedPromise) {
            const error = await rejectedPromise
            alert(error.message)
        } finally {
            setLoading(false)
        }
    };

    if(loading) {
        return <Loading/>
    } else {
        logout().then(() => {})
    }
}

export default Logout