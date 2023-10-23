import React from 'react';
import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Card from '@mui/material/Card';
import Typography from '@mui/material/Typography';
import { INews } from '../../store/newsReducer';
import { useNavigate } from 'react-router-dom';

interface Props {
  post: INews,
}

function NewsCard({ post }: Props) {
  const navigate = useNavigate()
  const { header, date, text, hashtags } = post;

  return (
      <Card sx={{marginY: '20px'}}>
        <Box m={3} height="80%" display="flex" flexDirection="column" justifyContent="space-between">
          <Link
            color='primary'
            variant="h6"
            underline="hover"
            sx={{cursor: 'pointer'}}
            onClick={() => navigate(`/news/${post.id}`, {replace: true})}
          >
            {header}
          </Link>

          <Box my={2}>
            <Typography variant='subtitle2' color= 'text.secondary'>{text}</Typography>
          </Box>

          <Box display='flex' justifyContent='space-between'>
            <Box display='flex'>
            {hashtags.map((t) =><Typography key={t} mr={1} variant='caption' color='text.secondary'>{t}</Typography>)}
          </Box>

          <Typography variant='caption' color= 'text.secondary'>{date}</Typography>

          </Box>
        </Box>
      </Card>
  );
}

export default NewsCard