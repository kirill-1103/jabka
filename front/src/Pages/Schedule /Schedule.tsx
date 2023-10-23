import { Button, Container, LinearProgress, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from "@mui/material";
import { fullSchedule, getFullSchedule } from "../../store/scheduleReducer";
import { useAppDispatch } from "../../store/store";
import { useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchAllGroups, getAllGroups } from "../../store/groupReducer";
import { fetchAllSubjects, getAllSubjects } from "../../store/subjectReducer";
import { formatDate } from "../../utils/formatDate";
import { fetchAllUsers, getAllUsers } from "../../store/userReducer";
import { getCurrentUserData } from "../../store/authReducer";
import AddIcon from '@mui/icons-material/Add';
import CreateScheduleModal from "./CreateScheduleModal";

export default function Schedule () {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const schedule = useSelector(fullSchedule())
  const groups = useSelector(getAllGroups())
  const subjects = useSelector(getAllSubjects())
  const users = useSelector(getAllUsers())
  const currentUser = useSelector(getCurrentUserData());
  const isAdmin = currentUser?.roles?.find((r) => r.name === 'ROLE_ADMIN') !== undefined


  function findGroupById(id: number) {
    const group = groups?.find((n) => n.id === id);
    if (group) {
      return group.name
    }
  }

  function findUserById(id: number) {
    const user = users?.find((n) => n.id === id);
    if (user) {
      return user.name
    }
  }

  function findSubjectsById(id: number) {
    const subject = subjects?.find((n) => n.id === id);
    if (subject) {
      return subject.name
    }
  }

  //modal
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  useEffect(() => {
    dispatch(getFullSchedule())
    dispatch(fetchAllGroups())
    dispatch(fetchAllSubjects())
    dispatch(fetchAllUsers())

  }, []);

  if (!schedule) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Typography variant="h4">Расписание</Typography>

        {!isAdmin && <Button variant="contained" color="secondary" startIcon={<AddIcon />} onClick={handleOpen}>
          Добавить расписание
        </Button>}
      </Stack>

      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Дата</TableCell>
              <TableCell>Предмет</TableCell>
              <TableCell>Группа</TableCell>
              <TableCell>Преподаватель</TableCell>
              <TableCell>Аудитория</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {schedule.map((s) => (
              <TableRow key={s.date}>
                <TableCell><Button onClick={() => navigate(`/schedule/${s.id}`, {replace: true})} sx={{textTransform: 'none'}}>{formatDate(s.date)}</Button></TableCell>
                <TableCell><Button onClick={() => navigate(`/subject/${s.subjectId}`, {replace: true})} sx={{textTransform: 'none'}}>{findSubjectsById(s.subjectId)}</Button></TableCell>
                <TableCell><Button onClick={() => navigate(`/studygroup/${s.studyGroupId}`, {replace: true})} sx={{textTransform: 'none'}}>{findGroupById(s.studyGroupId)}</Button></TableCell>
                <TableCell><Button onClick={() => navigate(`/professor/${s.professorId}`, {replace: true})} sx={{textTransform: 'none'}}>{findUserById(s.professorId)}</Button></TableCell>
                <TableCell>{s.auditorium}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <CreateScheduleModal open={open} handleClose={handleClose}/>
    </Container>
  );
};

