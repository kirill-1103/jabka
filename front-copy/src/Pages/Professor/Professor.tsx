import { Avatar, Box, Button, Card, Container, LinearProgress, Stack, Typography } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { fetchAllUsers, getAllProfessors } from "../../store/userReducer";
import { useEffect } from "react";
import { useAppDispatch } from "../../store/store";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export function Professor() {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const professors = useSelector(getAllProfessors())

  useEffect(() => {
    dispatch(fetchAllUsers())
  }, []);

  if (!professors) {
    return <LinearProgress />
  }

  return (
    <Container>
    <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Typography variant="h4">Наши преподаватели</Typography>

      <Button variant="contained" color="secondary" startIcon={<AddIcon />}  >
        Добавить преподавателя
      </Button>
    </Stack>

    <Box display='flex' py={2} columnGap={2}>
      {professors?.length !== 0 && professors.map((p) => <Card>
          <Box display="flex" flexDirection="column" alignItems="center" m={3}>
            <Avatar/>
            <Button onClick={() => navigate(`/professor/${p.id}`, {replace: true})} variant="text" sx={{textTransform: 'none', fontSize: '16px', marginTop: '10px'}}>{p.name}</Button>
          </Box>
        </Card>)}
    </Box>
    </Container>

  )
}