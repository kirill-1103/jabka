import { Box, Breadcrumbs, Button, Container, Divider, Link, Stack, Typography } from "@mui/material"
import EditIcon from '@mui/icons-material/Edit';
import {  useLocation } from "react-router-dom";
import { useSelector } from "react-redux";
import { getPostById } from "../../store/newsReducer";
import { useState } from "react";
import EditNewsModal from "./EditNewsModal";

const postBody = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Vel ea qui similique ullam fugiat beatae quisquam quia eum libero porro deleniti, mollitia facilis, quibusdam, culpa incidunt nobis! Fuga, ab ut!"

export default function NewsPost() {
  const location = useLocation()
  const postId = Number(location.pathname.split("/")[2])
  const post = useSelector(getPostById(postId))

  //modal
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  if (!post) {
    return <p>Loading</p>
  }
  return (
    <Container>
      <Stack direction="row" alignItems="center" justifyContent="space-between" my={5}>
        <Breadcrumbs aria-label="breadcrumb">
          <Link underline="hover" color="inherit" href="/news">
            Новости
          </Link>
          <Typography color="text.primary">{post.header}</Typography>
        </Breadcrumbs>
        <Button onClick={handleOpen} variant="contained" color='secondary' startIcon={<EditIcon />}>Редактировать</Button>
      </Stack>

      <Box m={3}>
        <Box display='flex' justifyContent='space-between'>
          <Typography variant="h4" color="primary">{post.header}</Typography>
          <Typography variant='h6' color= 'text.secondary'>{post.date}</Typography>
        </Box>

        <Box display='flex'>
            {post.hashtags.map((t) =><Typography key={t} mr={1} variant='subtitle1' color='text.secondary'>#{t}</Typography>)}
        </Box>

        <Divider />

        <Box my={2}>
          <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{post.text}</Typography>
          <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody.repeat(2)}</Typography>
          <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody.repeat(3)}</Typography>
          <Typography mb={1} textAlign="justify" variant='subtitle1' color= 'text.secondary'>{postBody.repeat(5)}</Typography>
        </Box>
      </Box>

      <EditNewsModal post={post} open={open}  handleClose={handleClose} isEdit={true}/>
    </ Container>
  )
}
