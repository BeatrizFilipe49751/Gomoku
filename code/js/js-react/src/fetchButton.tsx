import React, { useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'
import { RequestInfo } from 'undici-types'

function FetchForm(){
    const [url, setUrl] = useState("")
	const [fetchResult, setFetchResult] = useState("")

	function onChangeHandler(event) {
		console.log("onChangeHandler")
		setUrl(event.target.value)
	}

    function onClickHandler() {
        console.log("onClickHandler")		
		let cancel = false
		setFetchResult('LOADING!!!')
		fetch(url)
			.then((result) => result.json())
			.then((result) => {
				if (cancel) return
				setFetchResult(JSON.stringify(result))
			})
			.catch((error) => setFetchResult(JSON.stringify(error)))
		return () => {
			cancel = true;
		}
    }

    return (
        <div>
			<b>URL </b>
            <input onChange={onChangeHandler} type="text" value={url} />
            <button onClick={onClickHandler}>FETCH</button>
			<br></br>
			<br></br>
			<textarea cols={50} rows={30} value={fetchResult}/>
        </div>
    )
}

export function demo(){
    const root = createRoot(document.getElementById("container"))
    root.render(<FetchForm/>)
}