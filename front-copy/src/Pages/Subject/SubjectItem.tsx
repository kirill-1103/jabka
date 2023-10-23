import { Badge, Box, Breadcrumbs, Button, Container, LinearProgress, Link, List, ListItem, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography, styled } from "@mui/material";
import { useSelector } from "react-redux";
import { useLocation, useNavigate } from "react-router-dom";
import { fetchAllSubjects, getSubjectById } from "../../store/subjectReducer";
import { getFullSchedule, getSchedulesByIds } from "../../store/scheduleReducer";
import { useEffect } from "react";
import { useAppDispatch } from "../../store/store";
import { formatDate } from "../../utils/formatDate";
import { fetchAllGroups, getStudyGroupsByIds } from "../../store/groupReducer";
import { fetchAllMaterials, getMaterialsByIds } from "../../store/materialReducer";
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';

const VisuallyHiddenInput = styled('input')({
  clip: 'rect(0 0 0 0)',
  clipPath: 'inset(50%)',
  height: 1,
  overflow: 'hidden',
  position: 'absolute',
  bottom: 0,
  left: 0,
  whiteSpace: 'nowrap',
  width: 1,
});

export function SubjectItem() {
  const location = useLocation()
  const navigate = useNavigate()
  const dispatch = useAppDispatch()

  const subjectId = Number(location.pathname.split("/")[2])
  const subjectById = useSelector(getSubjectById(subjectId))
  const schedulesById = useSelector(getSchedulesByIds(subjectById?.scheduleIds))
  const studyGroupsByIds = useSelector(getStudyGroupsByIds(subjectById?.studyGroupsIds))
  const materualsByIds = useSelector(getMaterialsByIds(subjectById?.studyMaterialsIds))

  useEffect(() => {
    dispatch(getFullSchedule())
    dispatch(fetchAllGroups())
    dispatch(fetchAllSubjects())
    dispatch(fetchAllMaterials())
  }, []);

  if (!subjectById || !schedulesById ) {
    return <LinearProgress />
  }


  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
      <Breadcrumbs aria-label="breadcrumb">
          <Link underline="hover" color="inherit" href="/subject">
            Предметы
          </Link>
          <Typography color="text.primary">{subjectById.name}</Typography>
      </Breadcrumbs>
      </Stack>

      {schedulesById.length !== 0 &&
        <Box>
          <Typography variant="h5">Расписание</Typography>
          <List>
            {schedulesById.map((s) => <ListItem ><Button key={s.date} onClick={() => navigate(`/schedule/${s.id}`, {replace: true})} sx={{textTransform: 'none'}}>{formatDate(s.date)}</Button></ListItem>)}
          </List>
        </Box>
      }

      {studyGroupsByIds && studyGroupsByIds?.length !== 0 &&
        <Box>
          <Typography variant="h5">Группы</Typography>
          <List>
            {studyGroupsByIds.map((s) => <ListItem ><Button key={s.name} onClick={() => navigate(`/schedule/${s.id}`, {replace: true})} sx={{textTransform: 'none'}}>{s.name}</Button></ListItem>)}
          </List>
        </Box>
      }

      {materualsByIds && materualsByIds?.length !== 0 &&
        <Box>
          <Typography variant="h5">Материалы</Typography>

          <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Название</TableCell>
                <TableCell>Материалы</TableCell>
                <TableCell>Ответ студента</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {materualsByIds.map((m) =>
                <TableRow>
                  <TableCell>{m.materialsText}</TableCell>
                  <TableCell>
                    <Badge badgeContent={m.id} color="secondary">
                      <Button component="label" variant="contained" startIcon={<CloudDownloadIcon />}>Скачать файлы<VisuallyHiddenInput type="file" /></Button>
                    </Badge>
                  </TableCell>
                  <TableCell><Button component="label" variant="contained" startIcon={<CloudUploadIcon />}>Загрузить файл<VisuallyHiddenInput type="file" /></Button></TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        </Box>
      }
    </Container>
  )
}