import React, { useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'


function Component1() {
    let [loggedIn, setLoggedIn] = useState(false)

    function changeState() {
        setLoggedIn(!loggedIn)
    }
    return (
        <div>
            <h1>Component 1</h1>
            <button onClick={changeState}>
                {loggedIn ? "Logout" : "Login"}
            </button>
            <Component2 loggedIn={loggedIn}></Component2>
        </div>
    )
}

function Component2(props: {loggedIn: Boolean}) {
    return (
        <div>
            <h1>Component 2</h1>
            <Component3 loggedIn={props.loggedIn}></Component3>     
        </div>
    )
}

function Component3(props: {loggedIn: Boolean}) {
    return (
        <div>
            <h1>Component 3</h1>
            <Component4 loggedIn={props.loggedIn}></Component4>
        </div>
             
    )
}

function Component4(props: {loggedIn: Boolean}): React.JSX.Element {
    return (
        <div>
            <h1>Component 4</h1>
            <h1>{props.loggedIn ? "Logged In" : "Logged Out"}</h1>
        </div>
    )
}

export function main() {
    const root = createRoot(document.getElementById("container"))
    root.render(<Component1/>)
}

