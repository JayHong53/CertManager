import { useState } from 'react';
import { useAuthContext } from './useAuthContext';

export const useSignup = () => {
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const { dispatch } = useAuthContext();

    const signup = async (email, password) => {
        setLoading(true);
        setError(null);

        // const response = await fetch('/api/signup', {
        //     method: 'POST',
        //     headers: {'Content-Type': 'application/json'},
        //     body: JSON.stringify({email, password})
        // });

        // mock response
        const response = {
            status: 200,
            statusText: 'OK',
            json: () => Promise.resolve({
                message: 'User created successfully',
                user: {
                    email: 'example@example.com',
                    firstName: 'Jiwoong',
                    lastName: 'Hong'
                }
            })
        };

        const json = await response.json();

        if (!response.ok) {
            setLoading(false);
            setError(json.error);
        }

        if (response.ok) {
            localStorage.setItem('user', JSON.stringify(json));
            dispatch({type: 'LOGIN', payload: json});

            setLoading(false);
        }
    }; 

    return { signup, error, loading };
}
