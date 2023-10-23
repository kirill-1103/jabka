import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Dispatch } from "redux";
import { RootState } from "./store";
import axios from "axios";

export interface IAttendance {
  scheduleId: number
  status: string
  studentId: number
}

interface AttendanceState {
  entities: IAttendance | null
  error: string | null
  loading: boolean
}

const initialState: AttendanceState = {
  entities: null,
  error: null,
  loading: false
};
const attendanceSlice = createSlice({
  name: "attendance",
  initialState,
  reducers: {
    fetchDataStart: (state) => {
      state.loading = true;
      state.error = null;
    },
    fetchDataSuccess: (state, action: PayloadAction<IAttendance>) => {
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
} = attendanceSlice.actions;
export default attendanceSlice.reducer;

export const fetchAllAttendanceById = (id: number) => {
  return async (dispatch: Dispatch) => {
    try {
      dispatch(fetchDataStart());
      const response = await axios.get(`http://158.160.49.7:8080/api/study/attendance-statistics/${id}`);
      dispatch(fetchDataSuccess(response.data));
    } catch (error) {
      dispatch(fetchDataFailure("Что-то пошло не так"));
    }
  }
};


export const getAttendance = () => (state: RootState) => state.attendance.entities;
