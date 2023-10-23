import { Avatar, Box, Breadcrumbs, Button, Card, Container, LinearProgress, Link, Stack, Typography } from "@mui/material";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { fetchAllGroups, getGroupById } from "../../store/groupReducer";
import { fetchAllUsers, getUsersByIds } from "../../store/userReducer";
import { useEffect } from "react";
import { useAppDispatch } from "../../store/store";

export function StudygroupItem() {
  const dispatch = useAppDispatch()
  const location = useLocation()
  const navigate = useNavigate()
  const groupId = Number(location.pathname.split("/")[2])
  const gropupById = useSelector(getGroupById(groupId))
  const students = useSelector(getUsersByIds(gropupById?.studentsIds))

  useEffect(() => {
    dispatch(fetchAllGroups())
    dispatch(fetchAllUsers())
  }, []);

  if (!gropupById || !students) {
    return <LinearProgress />
  }

  return (
    <Container>
    <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
    <Breadcrumbs aria-label="breadcrumb">
        <Link underline="hover" color="inherit" href="/studygroup">
          Учебные группы
        </Link>
        <Typography color="text.primary">{gropupById.name}</Typography>
    </Breadcrumbs>
    </Stack>

    <Box display='flex' py={2} columnGap={2}>
        {students?.length !== 0 && students.map((s) => <Card key={s.id}>
          <Box display="flex" flexDirection="column" alignItems="center" m={3}>
            <Avatar/>
            <Button onClick={() => navigate(`/student/${s.id}`, {replace: true})} variant="text" sx={{textTransform: 'none', fontSize: '16px', marginTop: '10px'}}>{s.name}</Button>
          </Box>
        </Card>)}
      </Box>
    </Container>
  )
}