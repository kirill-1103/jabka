import { Box, Breadcrumbs, Container, Divider, LinearProgress, Link, Stack, Typography } from "@mui/material";
import { useAppDispatch } from "../../store/store";
import { useLocation } from "react-router-dom";
import { useEffect } from "react";
import { fetchAllUsers, getUserById } from "../../store/userReducer";
import { useSelector } from "react-redux";

const postBody = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Vel ea qui similique ullam fugiat beatae quisquam quia eum libero porro deleniti, mollitia facilis, quibusdam, culpa incidunt nobis! Fuga, ab ut!"


export function ProfessorItem() {
  const dispatch = useAppDispatch()
  const location = useLocation()
  const userId = Number(location.pathname.split("/")[2])
  const user = useSelector(getUserById(userId))

  useEffect(() => {
    dispatch(fetchAllUsers())
  }, []);

  if (!user) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Breadcrumbs aria-label="breadcrumb">
          <Link underline="hover" color="inherit" href="/professor">
            Преподаватели
          </Link>
          <Typography color="text.primary">{user.name}</Typography>
      </Breadcrumbs>
      </Stack>

      <Typography>Почта для связи: {user.email}</Typography>

      <Divider />

      <Box my={2}>
        <Typography my={2} variant="h5">Информация о преподавателе</Typography>

        <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody}</Typography>
        <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody.repeat(2)}</Typography>
        <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody.repeat(3)}</Typography>
      </Box>

    </Container>

  )
}