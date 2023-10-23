import { Box, Container, LinearProgress, Stack, Typography } from "@mui/material";
import { useSelector } from "react-redux";
import { getCurrentUserData } from "../../store/authReducer";

export default function Profile() {
  const currentUser = useSelector(getCurrentUserData());


  if (!currentUser) {
    return <LinearProgress />
  }
  // currentUser?.login
  // currentUser?.photo
  return (
    <Container>

      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Typography variant="h4">Профиль</Typography>
      </Stack>

      <Box>

      <Typography variant="h5">{currentUser?.name} {currentUser?.patronymic} {currentUser?.surname}</Typography>
      <Typography variant="subtitle1">Почта: {currentUser?.email}</Typography>

      <Typography variant="subtitle1">{!currentUser?.activationStatus && '(требуется подтверждение почты)'}</Typography>

      {currentUser?.grop && <Typography variant="subtitle1">{currentUser?.grop}</Typography>}


      </Box>



    </Container>

  )
}