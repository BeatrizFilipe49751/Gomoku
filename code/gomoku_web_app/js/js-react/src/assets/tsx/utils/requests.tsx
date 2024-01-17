export function formatUrl(template: string, replacements: Record<string, string>): string {
    return template.replace(/\{(\w+)}/g, (match, key) => replacements[key] || match)
}

export async function tryRequest(
    {
        loadingSetter,
        request,
        args
    }: {
    loadingSetter?: (newState: boolean) => void,
    request: (...args: any[]) => Promise<any>,
    args: any[]
}, error_message: boolean = true): Promise<any>{
    try {
        loadingSetter?.(true)
        return await request(...args)
    } catch(rejectedPromise) {
        const error = await rejectedPromise
        if(error_message)
            alert(error.message)
    } finally {
        loadingSetter?.(false)
    }
}

export async function execute_request(requestInfo: RequestInfo, method: string, data: any) {
    let headers = {
        'Content-Type': 'application/json'
    }

    const requestOptions = {
        method: method,
        headers: headers,
        body: undefined
    }

    if (data !== null) {
        requestOptions.body = JSON.stringify(data);
    }
    const response: Response = await fetch(requestInfo, requestOptions);
    return handleResponse(response);
}

export async function handleResponse(response: Response): Promise<any> {
    let status: number = response.status
    switch (status) {
        case 400:
        case 404:
        case 401:
        case 500:
            return await handleError(response);
        case 200:
        case 201:
            return response.json();
    }
}


type Error = {
    message: string,
}

function handleError(response: Response): Promise<Error> {
    return Promise.reject(response.text().then(error => {
        const jsonError = JSON.parse(error)
        const errorMessage: Error = { message: jsonError.message }
        return errorMessage
    }))
}