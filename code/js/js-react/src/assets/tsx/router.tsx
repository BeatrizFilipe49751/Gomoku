import React, {useState} from 'react'
import { createRoot } from 'react-dom/client'
import {
    createBrowserRouter,
    RouterProvider,
    Link,
    useParams,
    useNavigate
} from "react-router-dom";

const router = createBrowserRouter(
    [
        {
            path : "/",
            element : < Home />
        },
        {
            path : "/users/login",
            element : < Login />
        },
        {
            path : "/users/register",
            element : < Register />
        },
        {
            path : "navigate",
            element : < Navigate />
        }
    ]
)

function Home(){
    return (
        <div>
            <h1>"Home"</h1>
            <p><Link to="/users/login">Login</Link></p>
            <p><Link to="/users/register">Register</Link> </p>
            <p><Link to="/navigate">Navigate</Link> </p>
        </div>

    )
}

function Login(){

    return (
        <div>
            <h1>Login</h1>
            <p><Link to="/">Home</Link> </p>
        </div>

    )
}

function Register(){

    return (
        <div>
            <h1>Register</h1>
            <p><Link to="/">Home</Link> </p>
        </div>

    )
}

function Navigate(){

    const [text,setText] = useState("")
    const navigate = useNavigate()

    function onChangeHandler(event){
        setText(event.target.value)
    }
    return (
        <div>
            <h1>Navigate</h1>
            <input onChange={onChangeHandler} />
            <button onClick={()=>navigate(text)} />
        </div>

    )
}



export function appRouter(){
    const root = createRoot(document.getElementById("container"))
    root.render(<RouterProvider router={router} />)
}