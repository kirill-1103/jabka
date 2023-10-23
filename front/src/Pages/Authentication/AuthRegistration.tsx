import { ChangeEvent, FormEvent, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Visibility, VisibilityOff } from '@mui/icons-material';
import { useSelector } from 'react-redux';
import { getAuthErrors, isAuthenticated, register } from '../../store/authReducer';
import { Box, Button, Divider, FormControl, FormHelperText, IconButton, InputAdornment, InputLabel, OutlinedInput, Typography, useMediaQuery } from '@mui/material';
import { useAppDispatch } from '../../store/store';

interface FormParams {
	login?: string;
	name?: string;
	surname?: string;
	patronymic?: string;
  email?: string;
  password?: string;
  passwordRepeated?: string;
}

interface Props {
	changeType: () => void;
}


function AuthRegistration({changeType}: Props) {
	const dispatch = useAppDispatch()
	const [showPassword, setShowPassword] = useState(false);
	const handleClickShowPassword = () => setShowPassword((show) => !show);

	const xs = useMediaQuery('(max-width:550px)');
	const sm = useMediaQuery('(max-width:750px)');
	const navigate = useNavigate();

  const authError = useSelector(getAuthErrors())
  const isUserAuthenticated = useSelector(isAuthenticated())

  const intialValues = { login: "", email: "", username: "", password: "", passwordRepeated: "", name: "", surname: "", patronymic: "" };
  const [formValues, setFormValues] = useState(intialValues);
  const [formErrors, setFormErrors] = useState<FormParams>({})
  const [isSubmitting, setIsSubmitting] = useState(false);

  const submit = () => {

		if (formValues.patronymic !== "") {
			dispatch(register(formValues.login, formValues.password,formValues.email,formValues.name,formValues.surname,
				 formValues.patronymic
			))
		} else {
			dispatch(register(formValues.login, formValues.password,formValues.email,formValues.name,formValues.surname
		 ))
		}

		changeType();

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

  const validate = (values: FormParams) => {
    const errors: FormParams = {};
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;

    if (!values.email) {
      errors.email = "Это поле не может быть пустым!";
    } else if (!emailRegex.test(values.email)) {
      errors.email = "Введите корректный адрес электронной почты";
    }

		if (!values.login) {
      errors.login = "Это поле не может быть пустым!";
    } else if (values.login.length < 4) {
      errors.login = "Логин должен состоять не менее чем из 4 символов ";
    }

		if (!values.name) {
      errors.name = "Это поле не может быть пустым!";
    } else if (values.name.length < 2) {
      errors.name = "Имя должно состоять не менее чем из 2 символов ";
    }

		if (!values.surname) {
      errors.surname = "Это поле не может быть пустым!";
    } else if (values.surname.length < 2) {
      errors.surname = "Фамилия должна состоять не менее чем из 2 символов ";
    }

    if (!values.password) {
      errors.password = "Это поле не может быть пустым!";
    } else if (values.password.length < 3) {
      errors.password = "Пароль должен содержать минимум 3 символа";
    }

		if (!errors.password && !values.passwordRepeated && values.password) {
      errors.passwordRepeated = "Повторите пароль";
    }

		if (!errors.password && values.password !== values.passwordRepeated ) {
      errors.passwordRepeated = "Пароли не совпадают!";
			errors.password = "Пароли не совпадают!";
    }
    return errors;
  };

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmitting) {
      submit();
    }
  }, [formErrors]);

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
					error={!!formErrors.email}
				>
          <InputLabel htmlFor="email">Email*</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="email"
						name='email'
						label="email"
          />
					<FormHelperText id="email">{formErrors.email ? formErrors.email : ''}</FormHelperText>
        </FormControl>

				<FormControl
					sx={{ marginTop: '20px', width: '100%' }}
					variant="outlined"
					error={!!formErrors.login}
				>
          <InputLabel htmlFor="login">Логин*</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="login"
						name='login'
						label="login"
          />
					<FormHelperText id="login">{formErrors.login ? formErrors.login : ''}</FormHelperText>
        </FormControl>

				<FormControl
					sx={{ marginTop: '20px', width: '100%' }}
					variant="outlined"
					error={!!formErrors.name}
				>
          <InputLabel htmlFor="name">Имя*</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="name"
						name='name'
						label="name"
          />
					<FormHelperText id="name">{formErrors.name ? formErrors.name : ''}</FormHelperText>
        </FormControl>

				<FormControl
					sx={{ marginTop: '20px', width: '100%' }}
					variant="outlined"
					error={!!formErrors.surname}
				>
          <InputLabel htmlFor="surname">Фамилия*</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="surname"
						name='surname'
						label="surname"
          />
					<FormHelperText id="surname">{formErrors.surname ? formErrors.surname : ''}</FormHelperText>
        </FormControl>

				<FormControl
					sx={{ marginTop: '20px', width: '100%' }}
					variant="outlined"
					error={!!formErrors.patronymic}
				>
          <InputLabel htmlFor="patronymic">Отчество</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="patronymic"
						name='patronymic'
						label="patronymic"
          />
        </FormControl>

				<Divider />

				<FormControl
					sx={{ marginY: '20px', width: '100%' }}
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

            endAdornment={
              <InputAdornment position="end">
                <IconButton
                  aria-label="toggle password visibility"
                  onClick={handleClickShowPassword}
                  edge="end"
                >
                  {showPassword ? <VisibilityOff /> : <Visibility />}
                </IconButton>
              </InputAdornment>

            }
						label="Пароль"
          />
					<FormHelperText id="password">{formErrors.password ? formErrors.password : ''}</FormHelperText>
        </FormControl>

				<FormControl
					sx={{ marginTop: '20px', width: '100%' }}
					variant="outlined"
					error={!!formErrors.passwordRepeated}
				>
          <InputLabel htmlFor="passwordRepeated">Повторите пароль</InputLabel>
          <OutlinedInput
						sx={{ borderRadius: '8px' }}
						onChange={handleChange}
            id="passwordRepeated"
						name='passwordRepeated'
            type={showPassword ? 'text' : 'password'}
						label="Повторите пароль"
          />
					<FormHelperText id="passwordRepeated">{formErrors.passwordRepeated ? formErrors.passwordRepeated : ''}</FormHelperText>
        </FormControl>


        {isSubmitting && Object.keys(formErrors).length === 0 && <Typography sx={{ display: 'block', marginTop: '20px', textAlign: 'center' }} color='#FF3C02'>{authError ? authError : ""}</Typography>}

				<Button
					type="submit"
					fullWidth
					variant="contained"
					disableRipple
					sx={{
						mt: 2,
						mb: 1.5,
						height: '3.5rem',
						borderRadius: '8px',
						textTransform: 'none',
					}}
				>
					Зарегестрироваться
				</Button>

			</Box>
	);
}

export default AuthRegistration