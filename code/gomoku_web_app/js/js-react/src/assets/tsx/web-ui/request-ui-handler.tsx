import React from 'react';


export function ViewError({ error }: { error: any }) {
    return <span className="broken-component">{error.message}</span>
}

export function Loading() {
    return (
        <div className="spinner-container">
            <div className="spinner"></div>
        </div>
    );
}
