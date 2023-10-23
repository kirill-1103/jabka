import { createSlice, PayloadAction } from "@reduxjs/toolkit";
// import axios from "axios";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import localStorageService from "../services/localStorage.service";
import { IAuth } from "./authReducer";

export interface IApplication {
  id?: number;
  leaderName: string;
  requestStatus?: string;
  subunitName: string;
  currentPosition: string;
  workExperience: string;
  personalAchievements: string;
  motivationMessage: string;
  user: IAuth;
}

interface applicationsState {
  entities: IApplication[] | null;
  error: string | null;
  loading: boolean;
}

const initialState: applicationsState = {
  entities: null,
  error: null,
  loading: false,
};

const applicationsSlice = createSlice({
  name: "applications",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IApplication[]>) => {
      state.entities = action.payload;
      state.error = null;
      state.loading = false;
    },
    fetchDataEnd: (state) => {
      state.loading = false;
      state.error = null;
    },
    fetchDataFailure: (state, action: PayloadAction<string>) => {
      state.error = action.payload;
      state.loading = false;
    },
  },
});

export const {
  fetchDataStart,
  fetchDataSuccess,
  fetchDataFailure,
  fetchDataEnd,
} = applicationsSlice.actions;
export default applicationsSlice.reducer;

export const getAllUserApplications = () => {
  return async (dispatch: Dispatch) => {
    try {
      const token = localStorageService.getAccessToken();

      dispatch(fetchDataStart());
      const response = await axios.get(
        `http://158.160.49.7:8080/api/user/request`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  };
};

export const getUserApplications = () => (state: RootState) =>
  state.applications.entities;

  export const getApplicationById = (id: number | null) => (state: RootState) => {
    if (state.applications.entities && id) {
      return state.applications.entities.find((n) => n.user.id === id);
    }
  };

export const getApplicationsError = () => (state: RootState) =>
  state.applications.error;

