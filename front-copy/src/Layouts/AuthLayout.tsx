import { useState } from "react";
import { useParams } from "react-router-dom";
import { Box, Button, Typography } from "@mui/material";
import AuthRegistration from "../Pages/Authentication/AuthRegistration";
import AuthLogin from "../Pages/Authentication/AuthLogin";


function AuthLayout() {
    const { type } = useParams();
    const [formType, setFormType] = useState(
        type === "register" ? type : "login"
    );
    const toggleFormType = () => {
        setFormType((prevState) =>
            prevState === "register" ? "login" : "register"
        );
    };

    return (
      <Box display="flex" justifyContent="center" alignItems="center" sx={{ minHeight: 'calc(100vh - 69px)', background: 'lightGray'}}>
        <Box my={3} borderRadius="25px" bgcolor="white" p={4}>
          {formType === 'register' ? (
            <>
              <Typography variant="h4" textAlign='center' mb={3}>Регистрация</Typography>
                <AuthRegistration changeType={toggleFormType}/>
                <Typography variant="subtitle2" color="text.secondary" mb={3}>Звездочкой* отмечены обязательные поля</Typography>

                <Button variant="text" sx={{ textTransform: 'none'}} onClick={toggleFormType}>Уже есть аккаунт? Войти</Button>
            </>
            ) : (
            <>
              <Typography variant="h4" textAlign='center' mb={3}>Авторизация</Typography>
              <AuthLogin/>
              <Box display="flex" justifyContent="space-between">
                  <Button variant="text" sx={{ textTransform: 'none'}} onClick={toggleFormType}>Зарегистрироваться</Button>
                  <Button variant="text" sx={{ textTransform: 'none'}}>Забыли пароль?</Button>
              </Box>
            </>
          )}
        </Box>
      </Box>
    );
}

export default AuthLayout;