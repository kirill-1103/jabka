import { Avatar, Box, Breadcrumbs, Button, Card, Container, LinearProgress, Link, Stack, Typography } from "@mui/material";
import { getFullSchedule, getScheduleById } from "../../store/scheduleReducer";
import { useAppDispatch } from "../../store/store";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { fetchAllGroups, getGroupById } from "../../store/groupReducer";
import { fetchAllSubjects, getSubjectById } from "../../store/subjectReducer";
import { fetchAllUsers, getUserById, getUsersByIds } from "../../store/userReducer";
import { formatDate } from "../../utils/formatDate";

export default function ScheduleItem () {
  const dispatch = useAppDispatch()
  const location = useLocation()
  const navigate = useNavigate()

  const postId = Number(location.pathname.split("/")[2])
  const scheduleById = useSelector(getScheduleById(postId))
  const gropupById = useSelector(getGroupById(scheduleById?.studyGroupId))
  const subjectById = useSelector(getSubjectById(scheduleById?.subjectId))
  const user = useSelector(getUserById(scheduleById?.professorId))
  const students = useSelector(getUsersByIds(gropupById?.studentsIds))

  useEffect(() => {
    dispatch(getFullSchedule())
    dispatch(fetchAllGroups())
    dispatch(fetchAllSubjects())
    dispatch(fetchAllUsers())
  }, []);

  if (!scheduleById  || !subjectById || !gropupById || !students) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Breadcrumbs aria-label="breadcrumb">
          <Link underline="hover" color="inherit" href="/schedule">
            Расписание
          </Link>
          <Typography color="text.primary">{formatDate(scheduleById.date)}</Typography>
      </Breadcrumbs>
      </Stack>

      <Box display='flex' justifyContent='space-between'>
        <Box>
          <Button variant="text" sx={{textTransform: 'none', fontSize: '32px'}} onClick={() => navigate(`/subject/${subjectById.id}`, {replace: true})}>{subjectById.name}</Button>
          {scheduleById.classFormat === 'DISTANT' ?
          <Box m={1}>
            <Link href={scheduleById.linkForTheClass} target="_blank">Виртуальная аудитория</Link>
          </Box>
          :
          <Typography variant="h6">Аудитория: {scheduleById.auditorium}</Typography>
          }
        </Box>

        <Box>
          <Card sx={{display: "flex", justifyContent: 'space-between'}}>
            <Box display="flex" flexDirection="column" alignItems="center" m={3}>
              <Avatar/>
              <Button variant="text" sx={{textTransform: 'none', fontSize: '16px'}} onClick={() => navigate(`/professor/${scheduleById.professorId}`, {replace: true})}>Преподаватель {user?.name}</Button>
              <Typography variant="subtitle1">{user?.email}</Typography>
            </Box>
          </Card>
        </Box>
      </Box>

      <Box my={2}>
      <Button onClick={() => navigate(`/studygroup/${gropupById.id}`, {replace: true})} variant="text" sx={{textTransform: 'none', fontSize: '24px'}}>Группа {gropupById.name}</Button>

      <Box display='flex' py={2} columnGap={2}>
        {students?.length !== 0 && students.map((s) => <Card>
          <Box display="flex" flexDirection="column" alignItems="center" m={3}>
            <Avatar/>
            <Button onClick={() => navigate(`/student/${s.id}`, {replace: true})} variant="text" sx={{textTransform: 'none', fontSize: '16px', marginTop: '10px'}}>{s.name}</Button>
          </Box>
        </Card>)}
      </Box>

      </Box>
    </Container>
  );
};

