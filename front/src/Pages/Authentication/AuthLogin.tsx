import { Box, Button, FormControl, FormHelperText, IconButton, InputAdornment, InputLabel, OutlinedInput, Typography, useMediaQuery } from '@mui/material';
import { ChangeEvent, FormEvent, useEffect, useState } from 'react';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useAppDispatch } from '../../store/store';
import { getAuthErrors, isAuthenticated, login } from '../../store/authReducer';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

interface FormErrors {
  login?: string;
  password?: string;
}

function AuthLogin() {
	const xs = useMediaQuery('(max-width:550px)');
	const sm = useMediaQuery('(max-width:750px)');
  const dispatch = useAppDispatch()
  const navigate = useNavigate()

  const intialValues = { login: "kminchelle", password: "0lelplR" }
  const [formValues, setFormValues] = useState(intialValues)
  const [formErrors, setFormErrors] = useState<FormErrors>({})
  const [isSubmitting, setIsSubmitting] = useState(false)

	const [showPassword, setShowPassword] = useState(false);
  const handleClickShowPassword = () => setShowPassword((show) => !show);
  const handleMouseDownPassword = (event: { preventDefault: () => void; }) => {
    event.preventDefault();
  };
  const isUserAuthenticated = useSelector(isAuthenticated())
  const authError = useSelector(getAuthErrors())

  const submit = () => {
		dispatch(login(formValues.login, formValues.password));
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmitting(true);
  };

  const validate = (values: FormErrors) => {
    const errors: FormErrors = {};

    if (!values.login) {
      errors.login = "Это поле не может быть пустым!";
    }

    if (!values.password) {
      errors.password = "Это поле не может быть пустым!";
    }

    return errors
  };

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmitting) {
      submit();
    }
  }, [formErrors, isSubmitting]);

	useEffect(() => {
		if (isUserAuthenticated) {
			navigate(`/`, { replace: true });
		}
	}, [isUserAuthenticated, navigate]);

	return (
      <Box
        component="form"
        onSubmit={handleSubmit}
        noValidate
        width={sm ? (xs ? '11rem' : '25rem') : '32.5rem'}
      >

        <FormControl
          sx={{ marginTop: '20px', width: '100%' }}
          variant="outlined"
          error={!!formErrors.login }
        >
          <InputLabel htmlFor="login">Логин</InputLabel>
          <OutlinedInput
            sx={{ borderRadius: '8px' }}
            onChange={handleChange}
            id="login"
            name='login'
            label="Логин" />
          <FormHelperText id="login">{formErrors.login ? formErrors.login : ''}</FormHelperText>
        </FormControl>

        <FormControl
          sx={{ marginTop: '20px', width: '100%' }}
          variant="outlined"
          error={!!formErrors.password}
        >
          <InputLabel htmlFor="password">Пароль</InputLabel>
          <OutlinedInput
            sx={{ borderRadius: '8px' }}
            onChange={handleChange}
            id="password"
            name='password'
            type={showPassword ? 'text' : 'password'}

            endAdornment={<InputAdornment position="end">
              <IconButton
                aria-label="toggle password visibility"
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}
                edge="end"
              >
                {showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>}
            label="Пароль" />
          <FormHelperText id="password">{formErrors.login ? formErrors.login : ''}</FormHelperText>
        </FormControl>

        {isSubmitting && Object.keys(formErrors).length === 0 && <Typography sx={{ display: 'block', marginTop: '20px', textAlign: 'center' }} color='#FF3C02'>{authError ? authError : ""}</Typography>}

        <Button
          type="submit"
          fullWidth
          variant="contained"
          disableRipple
          sx={{
            mt: 3,
            mb: 1.5,
            height: '3.5rem',
            borderRadius: '8px',
            textTransform: 'none',
          }}
        >
          Войти
        </Button>
      </Box>
	);
}
export default AuthLogin