import { Button, Container, LinearProgress, List, ListItem, Stack, Typography } from "@mui/material";
import AddIcon from '@mui/icons-material/Add';
import { useEffect } from "react";
import { useAppDispatch } from "../../store/store";
import { useNavigate } from "react-router-dom";
import { fetchAllGroups, getAllGroups } from "../../store/groupReducer";
import { useSelector } from "react-redux";

export function Studygroup() {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const groups = useSelector(getAllGroups())

  useEffect(() => {
    dispatch(fetchAllGroups())
  }, []);

  if (!groups) {
    return <LinearProgress />
  }

  return (
    <Container>
    <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Typography variant="h4">Все учебные группы</Typography>

      <Button variant="contained" color="secondary" startIcon={<AddIcon />}  >
        Добавить группу
      </Button>
    </Stack>

    <List>
      {groups.map((g) => <ListItem key={g.name}><Button onClick={() => navigate(`/studygroup/${g.id}`, {replace: true})} sx={{textTransform: 'none'}}>{g.name}</Button></ListItem>)}
    </List>
    </Container>

  )
}