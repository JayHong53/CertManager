import { useAuthContext } from "./useAuthContext";

export const useLogout = () => {
    const { dispatch } = useAuthContext();

    const logout = () => {
        // Remove the user from localStorage
        localStorage.removeItem('user');
  
        // Dispatch the logout action to the reducer
        dispatch({type: 'LOGOUT'});
    }

    return { logout }; 
}