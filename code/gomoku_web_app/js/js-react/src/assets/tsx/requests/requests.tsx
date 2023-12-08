export function formatUrl(template: string, replacements: Record<string, string>): string {
    return template.replace(/\{(\w+)}/g, (match, key) => replacements[key] || match)
}

export async function execute_request(requestInfo: RequestInfo, method: string, data: any): Promise<any> {
    const response: Response = await fetch(requestInfo, {
        method: method,
        body: JSON.stringify(data),
        headers: { 'Content-Type': 'application/json' },
    });
    return handleResponse(response);
}
export function handleResponse(response: Response): Promise<any> {
    let status: number = response.status
    console.log(status)
    switch (status) {
        case 400:
        case 404:
        case 401:
        case 500:
            return handleError(response);
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
        const errorMessage: Error = { message: "Invalid Request: " + jsonError.message }
        return errorMessage
    }))
}