import React, { useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'
import { RequestInfo } from 'undici-types'

function Fetch(props: { uri: RequestInfo }): React.JSX.Element {

    const [data, setData] = useState(null)
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)

    function onFetchClickHandler() {
        
    }
    
    useEffect(() => {
        let cancel = false
        setLoading(true)
        fetch(props.uri)
            .then(result => result.json())
            .then(result => {
                if(cancel) return 
                setData(result)
            })
            .then(() => setLoading(false))
            .catch(setError)
        return () => {cancel = true}
    },[props.uri])
    return (
        <div>
            {loading && 
                <textarea value="LOADING!!!!!!!!!!!!!!!" cols={120} rows={40}/>
            }
            {data &&
                <textarea value={JSON.stringify(data, null, 2)} cols={120} rows={40} />
            }
            {error &&
                <textarea value={JSON.stringify(error, null, 2)}cols={120} rows={40}/>
            }
        </div>
    )
}



export function demo() {
    const root = createRoot(document.getElementById("container"))
    root.render(<Fetch uri="https://httpbin.org/delay/5" />)
    setTimeout( () => {
        root.render(<Fetch uri="https://httpbin.org/delay/1" />)
    }, 2000)

}