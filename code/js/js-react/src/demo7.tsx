import React, { useState } from 'react'
import { createRoot } from 'react-dom/client'

function InputForm(){
    console.log("Input Form")
    const [text, setText] = useState("")

    function onChangeHandler(event){
        console.log("onChangeHandler")
        setText(event.target.value)
    }
	
	function onChangeHandler1(event){
        console.log("onChangeHandler1")
		setText1("")
        setText(event.target.value)
    }

    function onClickHandler(){
        console.log("onClickHandler")
		setText1(text)
        console.log(text)
    }

    console.log("Input Form")
    const [text1, setText1] = useState("")

    return (
        <div>
            <input onChange={onChangeHandler} type="text" value={text} />
            <button onClick={onClickHandler}>Click</button>
            <input onChange={onChangeHandler1} type="text" value={text1} />
        </div>
    )
}

export function demo(){
    const root = createRoot(document.getElementById("container"))
    root.render(<InputForm />)
}