import { Breadcrumbs, Button, Chip, Container, LinearProgress, Link, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { useAppDispatch } from "../../store/store";
import { useLocation, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { fetchAllUsers, getUserById } from "../../store/userReducer";
import { useEffect } from "react";
import { fetchAllAttendanceById, getAttendance } from "../../store/attendanceReducer";
import { getFullSchedule, getScheduleById } from "../../store/scheduleReducer";
import { formatDate } from "../../utils/formatDate";

export function StudentItem() {
  const dispatch = useAppDispatch()
  const location = useLocation()
  const navigate = useNavigate()

  const studentId = Number(location.pathname.split("/")[2])
  const student = useSelector(getUserById(studentId))
  const attendance = useSelector(getAttendance())
  const schedule = useSelector(getScheduleById(attendance?.scheduleId))

  useEffect(() => {
    dispatch(fetchAllUsers())
    dispatch(fetchAllAttendanceById(studentId))
    dispatch(getFullSchedule())

  }, []);

  if (!student || !schedule || !attendance) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Breadcrumbs aria-label="breadcrumb">
          <Link underline="hover" color="inherit" href="/student">
            Студенты
          </Link>
          <Typography color="text.primary">{student.name}</Typography>
      </Breadcrumbs>
      </Stack>

      <Typography color="text.primary">Группа {student.group}</Typography>

      <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Занятие</TableCell>
                <TableCell>Посещение</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
                <TableRow>
                  <TableCell><Button onClick={() => navigate(`/schedule/${schedule.id}`, {replace: true})} sx={{textTransform: 'none'}}>{formatDate(schedule.date)}</Button></TableCell>
                  <TableCell><Chip sx={{margin: '10px'}} label={attendance.status === "ATTENDED" ? 'посетил' : 'пропустил'} color={attendance.status === "ATTENDED" ? 'success' : 'error'}/></TableCell>
                </TableRow>
            </TableBody>
          </Table>
        </TableContainer>
    </Container>
  )
}