import { createSlice, PayloadAction } from "@reduxjs/toolkit";
// import axios from "axios";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";

export interface ISchedule {
  id: number
  date: string
  studyGroupId: number
  subjectId: number
  classFormat: string
  auditorium: string
  linkForTheClass: string
  professorId: number
}

export interface IProfessor {
  id: number
  login: string
  name: string
  surname: string
  patronymic: string
  email: string
  group: string
  photo: string
  password: string
  roles: { id: number, name: string}[]
  activationStatus: string
}

export interface IHomework {
  id: number
  task: string
  studentId: number
  date: string
  grade: number
  comment: string
  filesNames: string[]
  fileIds: number[]
}

export interface IStudyMaterial {
  id: number
  studyMaterialsType: string
  materialsText: string
  subject: string
  homework: IHomework[],
  filesId: number[]
  filesNames: string[]
}

interface scheduleState {
  entities: ISchedule[] | null;
  error: string | null;
  loading: boolean;
}

const initialState: scheduleState = {
  entities: null,
  error: null,
  loading: false
};

const scheduleSlice = createSlice({
  name: "schedule",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<ISchedule[]>) => {
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
} = scheduleSlice.actions;
export default scheduleSlice.reducer;

export const getFullSchedule = () => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const response = await axios.get(`http://158.160.49.7:8080/api/study/schedule`);
      dispatch(fetchDataSuccess(response.data));

    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};

export const getScheduleById = (id: number | undefined) => (state: RootState) => {
  if (state.schedule.entities && id) {
      return state.schedule.entities.find((n) => n.id === id);
  }
};

export const getSchedulesByIds = (ids: number[] | undefined) => (state: RootState) => {
  if (state.schedule.entities && ids) {
    let array = []

    for (let i = 0; i < ids.length; i++) {
      const s = state.schedule.entities.find((n) => n.id === ids[i]);
      if (s) {
        array.push(s)
      }
    }
    return array
  }
};

export const fullSchedule = () => (state: RootState) => state.schedule.entities;
