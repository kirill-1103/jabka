import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.js'
import { CssBaseline } from '@mui/material'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import { store } from './store/store.js'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
      <BrowserRouter>
        <CssBaseline />
          <Provider store={store}>
            <App />
            <ToastContainer/>
          </Provider>
      </BrowserRouter>
  </React.StrictMode>,
)
