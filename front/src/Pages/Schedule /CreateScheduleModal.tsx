import { Box, Button, Dialog, DialogContent, DialogTitle, FormControl, FormHelperText, InputLabel, OutlinedInput, Typography, useMediaQuery } from "@mui/material";
import { useAppDispatch } from "../../store/store";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { IAuth } from "../../store/authReducer";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';

interface FormParams {
  date?: string
  studyGroupId?: number
  subjectId?: number
  classFormat?: string
  auditorium?: string
  linkForTheClass?: string
  professorId?: number
}

interface Props {
  open: boolean;
  user?: IAuth;
  handleClose: () => void;
}

export default function CreateScheduleModal({open, user, handleClose}: Props) {
  const dispatch = useAppDispatch()

  const initialState: FormParams = {
    date: "",
    studyGroupId: 0,
    subjectId: 0,
    classFormat: "IN_PERSON",
    auditorium: "",
    linkForTheClass: "",
    professorId: 0
  }

  const [formValues, setFormValues] = useState<FormParams>(initialState)
  const [formErrors, setFormErrors] = useState<FormParams>({})
  const [isSubmitting, setIsSubmitting] = useState(false)

  const submit = () => {
    handleAppClose()
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues((prev) => {return { ...prev, [name]: value }});
  };

  const handleChangeTextarea = (e: ChangeEvent<HTMLTextAreaElement>, name: string) => {
    const { value } = e.target;
    setFormValues((prev) => {return { ...prev, [name]: value }});
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmitting(true);
  };

  const validate = (values: FormParams) => {
    const errors: FormParams = {};

    if (!values.date) {
      errors.date = "Это поле не может быть пустым!";
    }

    // if (!values.subunitName) {
    //   errors.subunitName = "Это поле не может быть пустым!";
    // }
    // if (!values.currentPosition) {
    //   errors.currentPosition = "Это поле не может быть пустым!";
    // }
    // if (!values.workExperience) {
    //   errors.workExperience = "Это поле не может быть пустым!";
    // }
    // if (!values.personalAchievements) {
    //   errors.personalAchievements = "Это поле не может быть пустым!";
    // }

    // if (!values.motivationMessage) {
    //   errors.motivationMessage = "Это поле не может быть пустым!";
    // }

    return errors
  };

  useEffect(() => {
    if (user) {
      setFormValues((prev) => {return { ...prev, user: user }})
    }
  }, [open]);

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmitting) {
      submit();
    }
  }, [formErrors, isSubmitting]);

  function handleAppClose() {
    handleClose()
  }

  return (
    <Dialog open={open} onClose={handleAppClose} fullWidth maxWidth="md">
      <DialogContent sx={{height: '90vh'}} >
        <DialogTitle>Добавить расписание</DialogTitle>
        <Box
        component="form"
        onSubmit={handleSubmit}
        noValidate
      >
        {/* DatePicker */}

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
          Отправить
        </Button>

        </Box>

      </DialogContent>
    </Dialog>
  );
}