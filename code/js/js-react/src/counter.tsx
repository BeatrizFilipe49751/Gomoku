import React, { useEffect, useState } from 'react'
import { createRoot } from 'react-dom/client'

function useCounter(initial: number): {observed: number, inc: () => void, dec: () => void} {
    const [counter, setCounter] = useState(initial)
    function inc() {
        setCounter(c => c + 1)
    }

    function dec() {
        setCounter(c => c - 1)
    }
    return {
        observed: counter,
        inc: inc, 
        dec: dec
    }
}

function Counter(props : {counter : {observed: number, inc: () => void, dec: () => void}}) {
    return (
        <div>
            <button onClick={props.counter.inc}> + </button>
            <h2>{props.counter.observed}</h2>
            <button onClick={props.counter.dec}> - </button>
            <br></br>
        </div>
    )
}

function Counters(props: {initial: number}) {
    let counter = useCounter(props.initial)
    return (
        <div>
            <h1>Counters</h1>
            <Counter counter={counter}></Counter>
            <Counter counter={counter}></Counter>
        </div>
    )
}

export function main() {
    const root = createRoot(document.getElementById("container"))
    root.render(<Counters initial={0}/>)
}