import { Button, Container, LinearProgress, List, ListItem, Stack, Typography } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useAppDispatch } from "../../store/store";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { fetchAllSubjects, getAllSubjects } from "../../store/subjectReducer";
import { useEffect } from "react";

export function Subject() {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const subjects = useSelector(getAllSubjects())

  useEffect(() => {
    dispatch(fetchAllSubjects())
  }, []);

  if (!subjects) {
    return <LinearProgress />
  }

  return (
    <Container>
    <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Typography variant="h4">Все предметы</Typography>

      <Button variant="contained" color="secondary" startIcon={<AddIcon />}  >
        Добавить предмет
      </Button>
    </Stack>

    <List>
      {subjects.map((s) => <ListItem key={s.name}><Button onClick={() => navigate(`/subject/${s.id}`, {replace: true})} sx={{textTransform: 'none'}}>{s.name}</Button></ListItem>)}
    </List>
    </Container>

  )
}