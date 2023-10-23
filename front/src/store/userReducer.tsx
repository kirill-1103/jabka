import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import { IAuth } from "./authReducer";
import localStorageService from "../services/localStorage.service";

interface UserState {
  entities: IAuth[] | null
  error: string | null
  loading: boolean
}

const initialState: UserState = {
  entities: null,
  error: null,
  loading: false
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IAuth[]>) => {
      state.entities = action.payload;
      state.error = null;
    },
    fetchDataFailure: (state, action: PayloadAction<string>) => {
      state.entities = null;
      state.error = action.payload;
    }
  },
});

export const {
  fetchDataStart,
  fetchDataSuccess,
  fetchDataFailure,
} = userSlice.actions;
export default userSlice.reducer;

export const fetchAllUsers = () => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const token = localStorageService.getAccessToken()
      const response = await axios.get(
        `http://158.160.49.7:8080/api/user`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const getUserById = (id: number | undefined) => (state: RootState) => {
  if (state.user.entities && id !== undefined) {
      return state.user.entities.find((n) => n.id === id);
  }
};

export const getAllProfessors = () => (state: RootState) => {
  if (state.user.entities) {
    let array = []

    for (let i = 0; i < state.user.entities.length; i++) {
      if (state.user.entities[i].roles?.find((r) => r.name === 'ROLE_TEACHER')) {
        array.push(state.user.entities[i])
      }
    }
    return array
  }
};

export const getUsersByIds = (ids: number[] | undefined) => (state: RootState) => {
  if (state.user.entities && ids) {
    let array = []

    for (let i = 0; i < ids.length; i++) {
      const s = state.user.entities.find((n) => n.id === ids[i]);
      if (s) {
        array.push(s)
      }
    }
    return array
  }
};

export const getAllUsers = () => (state: RootState) => state.user.entities;
