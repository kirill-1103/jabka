import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import localStorageService from "../services/localStorage.service";

export interface IMaterial {
  id: number
  type: string
  materialsText: string
  subject: number
  homeworkIds: number[]
  filesId: number[]
  filesNames: string[]
}

interface MaterialState {
  entities: IMaterial[] | null
  error: string | null
  loading: boolean
}

const initialState: MaterialState = {
  entities: null,
  error: null,
  loading: false
};

const materialSlice = createSlice({
  name: "material",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IMaterial[]>) => {
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
} = materialSlice.actions;
export default materialSlice.reducer;

export const fetchAllMaterials = () => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const token = localStorageService.getAccessToken()
      const response = await axios.get(
        `http://158.160.49.7:8080/api/study/materials`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const getGroupById = (id: number | undefined) => (state: RootState) => {
  if (state.material.entities && id !== undefined) {
      return state.material.entities.find((n) => n.id === id);
  }
};

export const getMaterialsByIds = (ids: number[] | undefined) => (state: RootState) => {
  if (state.material.entities && ids) {
    let array = []

    for (let i = 0; i < ids.length; i++) {
      const s = state.material.entities.find((n) => n.id === ids[i]);
      if (s) {
        array.push(s)
      }
    }
    return array
  }
};

export const getAllGroups = () => (state: RootState) => state.material.entities;
