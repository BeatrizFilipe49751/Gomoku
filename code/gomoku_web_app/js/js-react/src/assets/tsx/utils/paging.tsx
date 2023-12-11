export function next(
    {skip, setSkip, setLoading, totalListSize}: {
        skip: number,
        setSkip: (nextSkip: number) => void,
        setLoading: (loading: boolean) => void,
        totalListSize: number
    }
) {
    let nextSkip = skip + 5
    if(nextSkip > totalListSize) nextSkip = totalListSize
    if(nextSkip < totalListSize){
        setLoading(true);
        setSkip(nextSkip)
    }
}

export function prev(
    {skip, setSkip, setLoading}: {
        skip: number,
        setSkip: (nextSkip: (prevSkip: number) => number) => void,
        setLoading: (loading: boolean) => void
    }
) {
    if(skip >= 5) {
        setLoading(true);
        setSkip(nextSkip => nextSkip - 5)
    }
}
