import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import localStorageService from "../services/localStorage.service";

export interface IGroup {
  id: number
  name: string
  studentsIds: number[]
}

interface GroupState {
  entities: IGroup[] | null
  error: string | null
  loading: boolean
}

const initialState: GroupState = {
  entities: null,
  error: null,
  loading: false
};

const groupSlice = createSlice({
  name: "group",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IGroup[]>) => {
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
} = groupSlice.actions;
export default groupSlice.reducer;

export const fetchAllGroups = () => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const token = localStorageService.getAccessToken()
      const response = await axios.get(
        `http://158.160.49.7:8080/api/study/group`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const getGroupById = (id: number | undefined) => (state: RootState) => {
  if (state.group.entities && id !== undefined) {
      return state.group.entities.find((n) => n.id === id);
  }
};

export const getStudyGroupsByIds = (ids: number[] | undefined) => (state: RootState) => {
  if (state.group.entities && ids) {
    let array = []

    for (let i = 0; i < ids.length; i++) {
      const s = state.group.entities.find((n) => n.id === ids[i]);
      if (s) {
        array.push(s)
      }
    }
    return array
  }
};

export const getAllGroups = () => (state: RootState) => state.group.entities;
