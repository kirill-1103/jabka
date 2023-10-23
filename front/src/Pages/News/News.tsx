import { useSelector } from "react-redux"
import { INews, getAllNews } from "../../store/newsReducer"
import { Box, Button, Container, Divider, LinearProgress, MenuItem, Pagination, Stack, TextField, Typography } from "@mui/material"
import NewsCard from "./NewsCard"
import AddIcon from '@mui/icons-material/Add';
import { ChangeEvent, useState } from "react";
import SearchIcon from '@mui/icons-material/Search';
import EditNewsModal from './EditNewsModal';
import { getCurrentUserData } from "../../store/authReducer";

const allHashtags = ['Всe новости', 'Поступающим', 'Образование', 'Наука', 'Экспертиза', 'Общество', 'Университетская жизнь']
const options=[
  { value: 'latest', label: 'Сначала новые' },
  { value: 'oldest', label: 'Сначала старые' },
]

export function paginate(items: INews[], pageNumber: number, pageSize: number) {
  const startIndex = (pageNumber - 1) * pageSize;
  return [...items].splice(startIndex, pageSize);
}

function News() {
  const news = useSelector(getAllNews())
  const [page, setPage] = useState(1);
  const [searchQuery, setSearchQuery] = useState("");
  const newsPerPage = 5;
  const currentUser = useSelector(getCurrentUserData());
  const isAdmin = currentUser?.roles?.find((r) => r.name === 'ROLE_ADMIN') !== undefined

  //modal
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  if (!news) {
    return <LinearProgress />
  }

  const handleChange = (e: ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };
  const handleSearchQuery = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };

  function filterNews(data: INews[]) {
    const filtered = searchQuery ? data.filter(
              (n) =>
                  n.header
                      .toLowerCase()
                      .indexOf(searchQuery.toLowerCase()) !== -1
          ) : data

    return filtered
  }
  const filteredNews = filterNews(news);
  const count = filteredNews.length;
  const sortedNews = filteredNews
  const newsCrop = paginate(sortedNews, page, newsPerPage);

  return(
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Typography variant="h4">Новости нашего университета</Typography>

        {isAdmin && <Button variant="contained" color="secondary" startIcon={<AddIcon />} onClick={handleOpen}>
          Добавить новость
        </Button>}
      </Stack>

    <Stack mb={5} direction="row" alignItems="center" justifyContent="space-between">
      <Box sx={{ display: 'flex', alignItems: 'flex-end' }} >
        <SearchIcon sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
        <TextField  onChange={handleSearchQuery} value={searchQuery}id="input-with-sx" label="Поиск по новостям" variant="standard" sx={{width: '600px'}}/>
      </Box>

      <TextField select size="small" value="latest">
      {options.map((option) => (
        <MenuItem key={option.value} value={option.value}>
          {option.label}
        </MenuItem>
      ))}
    </TextField>
    </Stack>

    <Box display='flex' justifyContent='space-between' mb={4}>
      <Box display='flex' flexDirection='column' width='55%'>
        {newsCrop && newsCrop.map((post) => (
          <NewsCard key={post.id} post={post} />
        ))}
        <Pagination count={Math.ceil(count/ newsPerPage)} color="primary"  page={page} onChange={handleChange} />
      </Box>

      <Box width='300px' height='100%' bgcolor='lightgray' p={2}>
        <Typography variant="h6">Категории</Typography>
        <Divider/>

        <Box display='flex' flexDirection='column' mt={2}>
          {allHashtags.map((h) =><Typography key={h} variant="button" my={1} sx={{cursor: 'pointer'}}>{h}</Typography>)}
        </Box>

      </Box>
    </Box>
    <EditNewsModal open={open}  handleClose={handleClose} isEdit={false}/>

 </Container>
  )

}

export default News