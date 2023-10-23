import { Box, Button, Card, CardActions, CardContent, Chip, Container, FormControl, InputLabel, LinearProgress, MenuItem, Modal, Select, SelectChangeEvent, Stack, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../store/store";
import { useSelector } from "react-redux";
import { IAuth } from "../../store/authReducer";
import { toast } from "react-toastify";
import { getAllUserApplications, getApplicationsError, getUserApplications } from "../../store/applicationsReducer";
import { IApplication, updateApplication } from "../../store/applicationReducer";

interface IApplicationForm {
  requestStatus?: string;
  id?: number;
  leaderName?: string;
  subunitName?: string;
  currentPosition?: string;
  workExperience?: string;
  personalAchievements?: string;
  motivationMessage?: string;
  user?: IAuth;
}

const statuses = [
  {name: 'ON_MODERATION', label: 'На модерации'},
  {name: 'DENIED',  label: 'Отклонено'},
  {name: 'APPROVED', label: 'Принято'},
  {name: 'IN_RESERVE', label: 'В резерве'},
]

const style = {
  position: 'absolute' as 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  p: 4,
};

export default function Applications() {
  //modal
  const [currentApplication, setCurrentApplication] = useState<IApplicationForm>({})
  const [submit, setSubmit] = useState(false);

  function handleOpen(a: IApplication) {
    setCurrentApplication(a)
  }

  function handleChange(event: SelectChangeEvent) {
    if (currentApplication !== null) {
      setCurrentApplication((prev) => {
        return { ...prev, requestStatus: event.target.value as string };
      });
    }
  }
  const handleClose = () => setCurrentApplication({});
  const dispatch = useAppDispatch()
  const applications = useSelector(getUserApplications())
  const reqError = useSelector(getApplicationsError())


  useEffect(() => {
    if (reqError) {
      toast.error(reqError, {
        position: 'top-right',
        autoClose: 3000,
      });
    } else {

    }  }, [reqError]);

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

    function handleSubmit() {
      if (currentApplication) {
        dispatch(updateApplication(currentApplication as IApplication))
        setSubmit(true)
        handleClose()
        dispatch(getAllUserApplications())

        toast.success("Форма отправлена!", {
          position: 'bottom-left',
          autoClose: 3000,
        })
      }
    }

  useEffect(() => {
    dispatch(getAllUserApplications())
  }, [dispatch, submit]);

  if (!applications) {
    return <LinearProgress />
  }

  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Typography variant="h4">Заявления</Typography>
      </Stack>

      <Box>
      {applications && applications.length !== 0 ? applications.map((application) => (
        <Card key={application.id} sx={{ maxWidth: 400 }}>
      <Chip sx={{margin: '10px'}} label={statuses.find((s)=> s.name === application.requestStatus)?.label} color={ handleFindColor(application) }/>
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
        <Button size="small" onClick={() => handleOpen(application)}>Изменить статус</Button>
      </CardActions>
    </Card>)) :
    <Typography>Нет заявлений</Typography>
    }

      </Box>

      {currentApplication.id && <Modal
        open={!!currentApplication}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Изменить статус заявления
          </Typography>

          <Typography id="modal-modal-description" sx={{ my: 4 }}>
            Заявление от: {currentApplication?.user?.name} {currentApplication?.user?.surname}
          </Typography>

          <FormControl fullWidth>
            <InputLabel id="demo-simple-select-label">Статус</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={currentApplication.requestStatus}
              label="Status"
              onChange={handleChange}
            >
              <MenuItem value='ON_MODERATION'>На модерации</MenuItem>
              <MenuItem value='DENIED'>Отклонено</MenuItem>
              <MenuItem value='APPROVED'>Принято</MenuItem>
              <MenuItem value='IN_RESERVE'>В резерве</MenuItem>

            </Select>
          </FormControl>

          <Button sx={{marginTop: 2}} onClick={handleSubmit}>Изменить статус</Button>
        </Box>
      </Modal>}

    </Container>
  )
}