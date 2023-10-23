import { Box, Button, Container, LinearProgress, Stack, Typography } from "@mui/material"
import NewsCard from "../News/NewsCard"
import { useSelector } from "react-redux"
import { getAllNews } from "../../store/newsReducer"
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import { useNavigate } from "react-router-dom";

function Main() {
  const news = useSelector(getAllNews())
  const navigate = useNavigate()
  const selectedNews = news?.slice(0, 3)

  if (!news) {
    return <LinearProgress />
  }

  return (
    <>
    <Container>
        <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
          <Typography variant="h4">Главное</Typography>
          <Button onClick={() => navigate('/news')} variant="text" endIcon={<KeyboardArrowRightIcon />}>Все новости</Button>
        </Stack>

        <Box>
          <Box display='flex' columnGap={4}>
            {selectedNews && selectedNews.map((post) => (
              <NewsCard key={post.id} post={post} />
            ))}
          </Box>
        </Box>
      </Container>
      </>
  )
}

export default Main
