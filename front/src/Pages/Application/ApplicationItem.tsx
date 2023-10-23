import { Box, Button, Card, CardActions, CardContent, Chip, Container, LinearProgress, Stack, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import AddIcon from '@mui/icons-material/Add';
import localStorageService from "../../services/localStorage.service";
import { useAppDispatch } from "../../store/store";
import { getUserApplicationById, getUserApplication, getApplicationError, IApplication } from "../../store/applicationReducer";
import { useSelector } from "react-redux";
import CreateApplicationModal from "./CreateApplicationModal";
import { getCurrentUserData } from "../../store/authReducer";
import EditApplicationModal from "./EditApplicationModal";
import { toast } from "react-toastify";
import { getApplicationById } from "../../store/applicationsReducer";

const statuses = [
  {name: 'ON_MODERATION', label: 'На модерации'},
  {name: 'DENIED',  label: 'Отклонено'},
  {name: 'APPROVED', label: 'Принято'},
  {name: 'IN_RESERVE', label: 'В резерве'},
]

export default function Application() {
    //modal
    const [openCreate, setOpenCreate] = useState(false);
    const handleOpenCreate = () => setOpenCreate(true);
    const handleCloseCreate = () => setOpenCreate(false);
    const [openEdit, setOpenEdit] = useState(false);
    const handleOpenEdit = () => setOpenEdit(true);
    const handleCloseEdit = () => setOpenEdit(false);

  const userId = localStorageService.getUserId()
  const dispatch = useAppDispatch()
  const currentUser = useSelector(getCurrentUserData());
  const reqError = useSelector(getApplicationError())

  const application = useSelector(getApplicationById(Number(userId)))

  useEffect(() => {
    if (reqError) {
      toast.error(reqError, {
        position: 'top-right',
        autoClose: 3000, // Закрыть уведомление через 3 секунды (по желанию)
      });
    }
  }, [reqError]);

  // useEffect(() => {
  //   dispatch(getUserApplicationById(Number(userId)))
  // }, []);

  function handleFindColor(a: IApplication) {
    if (a.requestStatus === 'ON_MODERATION') {
      return 'warning';
    } else if (a.requestStatus === 'DENIED') {
      return 'error'
    } else if (a.requestStatus === 'APPROVED') {
      return 'success'
    } else if (a.requestStatus === 'IN_RESERVE') {
      return 'secondary'
    }
  }

  if (!currentUser) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Typography variant="h4">Мои заявления</Typography>

        {!application && <Button variant="contained" color="secondary" startIcon={<AddIcon />} onClick={handleOpenCreate}>
          Создать заявление
        </Button>}
      </Stack>

      <Box>
      {application ? <Card sx={{ maxWidth: 400 }}>
      <Chip sx={{margin: '10px'}} label={statuses.find((s)=> s.name === application.requestStatus)?.label} color={handleFindColor(application)}/>
      <CardContent>
      <Typography variant="h5" component="div">
        Название подразделения: {application.subunitName}
        </Typography>
        <Typography sx={{ mb: 1.5 }} color="text.secondary">
        Текущая должность: {application.currentPosition}
        </Typography>
        <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
          Руководитель: {application.leaderName}
        </Typography>

      </CardContent>
      <CardActions>
        <Button size="small" onClick={handleOpenEdit}>Посмотреть</Button>
      </CardActions>
    </Card> :
    <Typography>У вас нет заявлений</Typography>
    }

      </Box>

      {currentUser && <CreateApplicationModal open={openCreate} handleClose={handleCloseCreate} user={currentUser} />}
      {currentUser && application && <EditApplicationModal open={openEdit} handleClose={handleCloseEdit} user={currentUser} application={application}/>}

    </Container>
  )
}