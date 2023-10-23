import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import localStorageService from "../services/localStorage.service";
import { IAuth } from "./authReducer";

export interface IApplication {
  id?: number,
  leaderName: string,
  requestStatus?: string;
  subunitName: string,
  currentPosition: string,
  workExperience: string,
  personalAchievements: string,
  motivationMessage: string,
  user: IAuth
}

interface applicationState {
  entities: IApplication | null;
  error: string | null;
  loading: boolean;
}

const initialState: applicationState = {
  entities: null,
  error: null,
  loading: false
};

const applicationSlice = createSlice({
  name: "application",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IApplication>) => {
      state.entities = action.payload;
      state.error = null;
      state.loading = false
    },
    fetchDataEnd: (state) => {
      state.loading = false;
    },
    fetchDataFailure: (state, action: PayloadAction<string>) => {
      state.error = action.payload;
      state.loading = false
    }
  },
});

export const {
  fetchDataStart,
  fetchDataSuccess,
  fetchDataFailure,
  fetchDataEnd,
} = applicationSlice.actions;
export default applicationSlice.reducer;

export const createApplication = (data: IApplication) => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken()
      dispatch(fetchDataStart());
      await axios.post(`http://158.160.49.7:8080/api/user/request`,data,
      {headers: {'Authorization': `Bearer ${token}`}});
      // dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const deleteApplicationById = (id: number) => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken()
      dispatch(fetchDataStart());
      await axios.delete(`http://158.160.49.7:8080/api/user/request/${id}`, {headers: {'Authorization': `Bearer ${token}`}} );
      // dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const updateApplication = (data: IApplication) => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken()
      dispatch(fetchDataStart());
      const response = await axios.put(`http://158.160.49.7:8080/api/user/request`,data,
      {headers: {'Authorization': `Bearer ${token}`}});
      dispatch(fetchDataSuccess(response.data));

    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};


export const getUserApplicationById = (id: number) => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken()

      dispatch(fetchDataStart());
      const response = await axios.get(`http://158.160.49.7:8080/api/user/request/user/${id}`,
       {headers: {'Authorization': `Bearer ${token}`}});
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataEnd());
    }
  }
};

export const getUserApplication = () => (state: RootState) => state.application.entities;
export const getApplicationError = () => (state: RootState) => state.application.error;
export const getApplicationLoading = () => (state: RootState) => state.application.loading;
