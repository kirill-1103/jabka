import { Box, Button, Chip, FormControl, FormHelperText, InputLabel, Modal, OutlinedInput, TextField, Typography, useMediaQuery } from "@mui/material";
import { useAppDispatch } from "../../store/store";
import { ChangeEvent, FormEvent, KeyboardEvent, useEffect, useState } from "react";
import Textarea from '@mui/joy/Textarea';
import { INews, createNewsPost, deleteNewsPostById, editNewsPost, fetchNews } from "../../store/newsReducer";

interface FormParams {
  header?: string;
  date?: string;
  text?: string;
  hashtags?: string[];
  imgs?: string[];
}

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  bgcolor: 'background.paper',
  boxShadow: 24,
  p: 4,
};

interface Props {
  open: boolean;
  post?: IPartialNews;
  handleClose: () => void;
  isEdit: boolean;
}

interface IPartialNews {
  date?: string;
  id?: number;
  imgs?: string[];
  header: string,
  text: string,
  hashtags: string[]
}

const initialState: IPartialNews = {
  header: "",
  text: "",
  hashtags: []
}

export default function EditNewsModal({open, post, handleClose, isEdit}: Props) {
  const xs = useMediaQuery('(max-width:550px)');
	const sm = useMediaQuery('(max-width:750px)');
  const dispatch = useAppDispatch()
  const [formValues, setFormValues] = useState<IPartialNews>(post ? post : initialState)
  const [formErrors, setFormErrors] = useState<FormParams>({})
  const [isSubmitting, setIsSubmitting] = useState(false)


  const submit = () => {
    if (isEdit) {
      dispatch(editNewsPost(formValues as INews))
      dispatch(fetchNews())
    } else {
      dispatch(createNewsPost(formValues as INews))
      dispatch(fetchNews())
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormValues((prev) => {return { ...prev, [name]: value }});
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmitting(true);
  };

  const validate = (values: IPartialNews) => {
    const errors: FormParams = {};

    if (!values.header) {
      errors.header = "Это поле не может быть пустым!";
    }

    if (!values.hashtags) {
      errors.header = "Добавьте теги!";
    }


    if (!values.text) {
      errors.text = "Это поле не может быть пустым!";
    }


    return errors
  };

  useEffect(() => {
    if (post) {
      setFormValues(post)
    }
  }, [open]);

  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmitting) {
      submit();
    }
  }, [formErrors, isSubmitting]);

  function handleKeyDown(e: KeyboardEvent<HTMLInputElement>){
      if(e.key !== 'Enter') return
      let tags = formValues.hashtags
      const value = (e.target as HTMLInputElement).value;

      if(!value.trim()) return
    (e.target as HTMLInputElement).value = '';
      setFormValues((prev) => {
        return { ...prev, hashtags: [...tags, value] };
      });
  }

  function removeTag(index: number){
    const filteredTags = formValues.hashtags.filter((el, i) => i !== index)
    setFormValues((prev) => {
      return { ...prev, hashtags: filteredTags };
    });
  }

  const handleChangeTextarea = (e: ChangeEvent<HTMLTextAreaElement>, name: string) => {
    const { value } = e.target;
    setFormValues((prev) => {return { ...prev, [name]: value }});
  };

  function handleDeletePost() {
    if (formValues) {
      dispatch(deleteNewsPostById(Number(formValues.id)))
      dispatch(fetchNews())
    }
  }


  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
        <Typography variant="h4" textAlign='center' mb={3}>{isEdit ? 'Редактирование поста' : 'Создание поста'}</Typography>

        <Box
        component="form"
        onSubmit={handleSubmit}
        noValidate
        width={sm ? (xs ? '11rem' : '25rem') : '32.5rem'}
      >

        <FormControl
          sx={{ marginTop: '20px', width: '100%' }}
          variant="outlined"
          error={!!formErrors.header}
        >
          <InputLabel htmlFor="header">Заголовок</InputLabel>
          <OutlinedInput
            sx={{ borderRadius: '8px' }}
            value={formValues.header}
            onChange={handleChange}
            id="header"
            name='header'
            label="Заголовок" />
          <FormHelperText id="header">{formErrors.header ? formErrors.header : ''}</FormHelperText>
        </FormControl>

        <Box my={3}>
          <Typography variant="h5">Теги</Typography>
            {formValues.hashtags.map((tag, index) => (
              <Chip
              sx={{margin: '0 10px 10px 0'}}
              key={index}
              label={tag}
              onDelete={() => removeTag(index)}
              />
            )) }

            <Box>
            <TextField onKeyDown={handleKeyDown} id="standard-basic" label="Добавить тег" variant="standard" />
            <FormHelperText id="header">{formErrors.header ? formErrors.header : ''}</FormHelperText>

            </Box>
        </Box>

        <Box width={'100%'}>
          <Typography variant="h5">Текст новости</Typography>

          <Textarea
            onChange={(e) => handleChangeTextarea(e, "text")}

            color={formErrors.text ? 'danger' : 'primary'}
            minRows={2}
            size="sm"
            defaultValue={formValues.text}
            variant="soft"
          />
        </Box>

        {/* {isSubmitting && Object.keys(formErrors).length === 0 && <Typography sx={{ display: 'block', marginTop: '20px', textAlign: 'center' }} color='#FF3C02'>{authError ? authError : ""}</Typography>} */}

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
          Сохранить
        </Button>

        {isEdit && <Button
          fullWidth
          color="error"
          onClick={handleDeletePost}
          variant="text"
          disableRipple
          sx={{
            mt: 3,
            mb: 1.5,
            height: '3.5rem',
            borderRadius: '8px',
            textTransform: 'none',
          }}
        >
          Удалить
        </Button>}
      </Box>



        </Box>
      </Modal>
    </div>
  );
}