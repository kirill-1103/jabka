import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import PersonIcon from '@mui/icons-material/Person';import LoginIcon from '@mui/icons-material/Login';
import SchoolIcon from '@mui/icons-material/School';
import MenuIcon from '@mui/icons-material/Menu';
import React from 'react';
import { useSelector } from 'react-redux';
import { getCurrentUserData, logOut } from '../../store/authReducer';
import localStorageService from '../../services/localStorage.service';
import { useAppDispatch } from '../../store/store';

const settings = [{title: 'Профиль', action: 'profile'}, {title: 'Выйти', action: 'logout'}]

function Navigation() {
  const navigate = useNavigate()
  const currentUser = useSelector(getCurrentUserData());
  const userId = localStorageService.getUserId()
  const dispatch = useAppDispatch()

  const [anchorElNav, setAnchorElNav] = useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = useState<null | HTMLElement>(null);
  const isEnrollee = currentUser?.roles?.find((r) => r.name === 'ROLE_ENROLLEE') !== undefined
  const isCommittee = currentUser?.roles?.find((r) => r.name === 'ROLE_COMMITTEE') !== undefined

  const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = (path: string) => {
    setAnchorElNav(null);
    navigate(path)
  };

  const handleCloseUserMenu = (action: string) => {
    if (action === 'logout') {
      dispatch(logOut())
    } else if (action === 'profile') {
      navigate('/profile')
    }
    setAnchorElUser(null);
  };

  return (
    <AppBar position="sticky">
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <SchoolIcon sx={{ display: { xs: 'none', md: 'flex' }, mr: 1 }} />
          <Typography
            onClick={() => navigate('/')}
            variant="h6"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'none', md: 'flex' },
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            JABKA
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
              color="inherit"
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: 'block', md: 'none' },
              }}
            >

              {/* pages */}
              <MenuItem onClick={() => handleCloseNavMenu('news')}>
                  <Typography textAlign="center">Новости</Typography>
              </MenuItem>
              <MenuItem onClick={() => handleCloseNavMenu('schedule')}>
                  <Typography textAlign="center">Расписание</Typography>
              </MenuItem>
              {isEnrollee &&
              <MenuItem onClick={() => handleCloseNavMenu('application')}>
                  <Typography textAlign="center">Мои заявления</Typography>
              </MenuItem>
              }

            </Menu>
          </Box>
          <SchoolIcon sx={{ display: { xs: 'flex', md: 'none' }, mr: 1 }} />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href="#app-bar-with-responsive-menu"
            sx={{
              mr: 2,
              display: { xs: 'flex', md: 'none' },
              flexGrow: 1,
              fontFamily: 'monospace',
              fontWeight: 700,
              letterSpacing: '.3rem',
              color: 'inherit',
              textDecoration: 'none',
            }}
          >
            JABKA
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
            {/* pages */}
            <Button
                onClick={() => handleCloseNavMenu('news')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Новости
            </Button>
            {userId && <Button
                onClick={() => handleCloseNavMenu('schedule')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Расписание
            </Button>}
            {isEnrollee && <Button
                onClick={() => handleCloseNavMenu('application')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Мои заявления
            </Button>}
            {isCommittee && <Button
                onClick={() => handleCloseNavMenu('applications')}
                sx={{ my: 2, color: 'white', display: 'block' }}
              >
                Просмотр заявлений
            </Button>}

          </Box>

          {userId ? <Box sx={{ flexGrow: 0 }}>

            <Box display="flex" alignItems="center">
            <Typography mx={1}>{currentUser?.name} {currentUser?.surname}</Typography>

            <Tooltip title="Open settings">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              {currentUser?.photo ?
                <Avatar alt="Remy Sharp" src={currentUser?.photo} />
                :
                <Avatar sx={{ bgcolor: '#ba68c8' }}>
                  <PersonIcon />
                </Avatar>
              }

              </IconButton>
            </Tooltip>
            </Box>

            <Menu
              sx={{ mt: '45px' }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {settings.map((setting) => (
                <MenuItem key={setting.title} onClick={() => handleCloseUserMenu(setting.action)}>
                  <Typography textAlign="center">{setting.title}</Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          :
          <Box>
              <MenuItem onClick={() => navigate('/auth')}>
                <Typography textAlign="center">Войти</Typography>
                <LoginIcon/>
              </MenuItem>
          </Box>
          }

        </Toolbar>
      </Container>
    </AppBar>
  );
}
export default Navigation;