import { getAuthToken } from "./session-handler";

export function formatUrl(template: string, replacements: Record<string, string>): string {
    return template.replace(/\{(\w+)}/g, (match, key) => replacements[key] || match)
}

export async function execute_request(requestInfo: RequestInfo, method: string, data: any): Promise<any> {
    const requestOptions = {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: undefined

    };
    if (data !== null) {
        requestOptions.body = JSON.stringify(data);
    }
    const response = await fetch(requestInfo, requestOptions);
    return handleResponse(response);
}

export async function execute_request_gen(requestInfo: RequestInfo, method: string, data: any, auth: boolean) {
    let headers = {
        'Content-Type': 'application/json'
    }
    if(auth) {
        const token = getAuthToken()
        headers['Authorization'] = `Bearer ${token}`
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

export async function execute_request_auth(requestInfo: RequestInfo, method: string, data: any): Promise<any> {
    const token = getAuthToken()
    const requestOptions = {
        method: method,
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        body: undefined
    };
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