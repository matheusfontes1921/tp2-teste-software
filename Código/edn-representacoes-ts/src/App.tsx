import Costumers from './pages/costumers/Costumers.tsx'
import Users from './pages/users/Users'
import Login from './pages/login/Login'
import Dashboard from './pages/dashboard/Dashboard'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Tecido from "./pages/cloths/Cloths"
import Modelo from "./pages/models/Model"
import Tipo from "./pages/tipos/Tipos"


function App() {
  return (
    <BrowserRouter>
        <Routes>
            <Route element={<Login/>} path="/" />
            <Route element={<Dashboard />} path="/dashboard"/>
            <Route element={<Costumers />} path="/dashboard/costumers" />
            <Route element={<Users/>} path="/dashboard/users"/>
            <Route element={<Tecido />} path="/dashboard/cloths"/>
            <Route element={<Modelo />} path ="/dashboard/models" />
            <Route element={<Tipo/>} path="/dashboard/types" />
        </Routes>
    </BrowserRouter>
  )
}

export default App
