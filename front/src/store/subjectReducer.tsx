import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";
import localStorageService from "../services/localStorage.service.ts";

export interface ISubject {
  id: number
  name: string
  studyGroupsIds: number[]
  creatorId: number,
  editorsIds: number[]
  scheduleIds: number[]
  studyMaterialsIds: number[]
}
interface SubjectState {
  entities: ISubject[] | null
  error: string | null
  loading: boolean
}

const initialState: SubjectState = {
  entities: null,
  error: null,
  loading: false
};
const subjectSlice = createSlice({
  name: "subject",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<ISubject[]>) => {
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
} = subjectSlice.actions;
export default subjectSlice.reducer;

export const fetchAllSubjects = () => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const response = await axios.get(`http://158.160.49.7:8080/api/study/subject`,
          { headers: { Authorization: `Bearer `+localStorageService.getAccessToken() } })
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const getSubjectById = (id: number | undefined) => (state: RootState) => {
  if (state.subject.entities && id !== undefined) {
      return state.subject.entities.find((n) => n.id === id);
  }
};

export const getAllSubjects = () => (state: RootState) => state.subject.entities;
