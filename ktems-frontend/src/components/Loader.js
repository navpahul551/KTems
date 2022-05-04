import React from 'react';
import Spinner from 'react-spinkit';

export default function Loader(){
    return (
        <div style={{
            display: 'flex',
            justifyContent: 'center',
            marginTop: 200
        }}>
            <Spinner name='rotating-plane' />
        </div>
    );
}