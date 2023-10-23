import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import { Dispatch } from "redux";
import { RootState } from "./store";
import localStorageService from "../services/localStorage.service";

export interface IAuth {
  id: number;
  email: string;
  username: string;
  name: string;
  login: string;
  surname: string;
  patronymic?: string;
  photo?: string;
  activationStatus?: null | string;
  group: string
  roles?: {id: number, name: string}[]
}
interface authState {
  entities: IAuth | null;
  isAuthenticated: boolean;
  error: string | null;
}

const initialState: authState = {
  isAuthenticated: false,
  entities: null,
  error: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    fetchDataSuccess: (state, action: PayloadAction<IAuth>) => {
      state.isAuthenticated = true;
      state.entities = action.payload;
      state.error = null;
    },
    fetchDataFailure: (state, action: PayloadAction<string>) => {
      state.isAuthenticated = false;
      state.entities = null;
      state.error = action.payload;
    },
    logout: () => {
      return initialState
    }
  },
});

export const {
  fetchDataSuccess,
  fetchDataFailure,
  logout,
} = authSlice.actions;
export default authSlice.reducer;

export const login = (
  login: string,
  password: string,
) => {
  return async (dispatch: Dispatch) => {
    try {
      const response = await axios.post('http://158.160.49.7:8080/api/user/auth/signin', {
        login: login,
        password: password,
      }, {
        headers: {
          'Content-Type': 'application/json',
        }
      })

      dispatch(fetchDataSuccess(response.data.user));
      localStorageService.setTokens({ idToken: response.data.accessToken, localId:response.data.user.id});

    } catch (error) {
      dispatch(fetchDataFailure("Неверный логин или пароль"));
    }
  };
};

export const logOut = () => (dispatch: Dispatch) => {
  localStorageService.removeAuthData();
  dispatch(logout());
};

export const register = (
  login: string,
  password: string,
  email: string,
  name: string,
  surname: string,
  patronymic?: string
) => {
  return async (dispatch: Dispatch) => {
    try {
      const data = patronymic ? {
         login,
         password,
         email,
         name,
         surname,
         patronymic
      } :
      {
         login,
         password,
         email,
         name,
         surname,
      }
       await axios.post('http://158.160.49.7:8080/api/user/auth/signup', {...data}, {
        headers: {
          'Content-Type': 'application/json',
        }})

    } catch (error) {
      dispatch(fetchDataFailure("Неверный логин или пароль"));
    }
  };
};

export const getCurrentUserWithToken = (id: string) => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken()
      const response = await axios.get(`http://158.160.49.7:8080/api/user/${id}`, {headers: {'Authorization': `Bearer ${token}`}})
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      localStorageService.removeAuthData();
      dispatch(logout());
    }
  };
};



export const isAuthenticated = () => (state: RootState) => state.auth.isAuthenticated;
export const getAuthErrors = () => (state: RootState) => state.auth.error;
export const getCurrentUserData = () => (state: RootState) => state.auth.entities;
export const getCurrentUserId = () => (state: RootState) => state.auth.entities?.id;
