import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import { useDispatch } from 'react-redux';
import authReducer from './authReducer';
import newsReducer from './newsReducer';
import applicationReducer from './applicationReducer';
import applicationsReducer from './applicationsReducer';
import scheduleReducer from './scheduleReducer';
import groupReducer from './groupReducer';
import subjectReducer from './subjectReducer';
import userReducer from './userReducer';
import attendanceReducer from './attendanceReducer';
import materialReducer from './materialReducer';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    news: newsReducer,
    application: applicationReducer,
    applications: applicationsReducer,
    schedule: scheduleReducer,
    group: groupReducer,
    subject: subjectReducer,
    user: userReducer,
    attendance: attendanceReducer,
    material: materialReducer
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<ReturnType, RootState, unknown, Action<string>>;
export type AppDispatch = typeof store.dispatch;
export const useAppDispatch: () => AppDispatch = useDispatch;
