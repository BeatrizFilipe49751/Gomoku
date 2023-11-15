import React, { useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'
import { RequestInfo } from 'undici-types'

function Fetch(props: { uri: RequestInfo }) {
    const [data, setData] = useState(null)
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)
    useEffect(()=>{
        setLoading(true)
        fetch(props.uri)
            .then(result => result.json())
            .then(setData)
            .then(() => setLoading(false))
            .catch(setError)

    },[props.uri])
    if(error) return<textarea value={JSON.stringify(error, null, 2)}cols={120} rows={40}/>
    if(loading) return <textarea value="LOADING!!!!!!!!!!!!!!!" cols={120} rows={40}/>
    if(data) return <textarea value={JSON.stringify(data, null, 2)} cols={120} rows={40} />
    return null
}

export function demo() {
    const root = createRoot(document.getElementById("container"))
    setTimeout( () => {
        root.render(<Fetch uri="https://httpbin.org/delay/5" />)
    }, 2000)
    
}