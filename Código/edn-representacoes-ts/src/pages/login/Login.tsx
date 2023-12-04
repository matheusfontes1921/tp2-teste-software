import React, {useState} from "react";
import {Container, Card, CardElements, CardLogin, CardButtons, CardButtonSignUp, ForgotButton } from "./login.styles";
import { Input, Button } from "antd";
import axios from "axios";
import {toast, Toaster} from "react-hot-toast";
import {handleAuth} from "../../service/auth/auth.ts";
import {useNavigate} from "react-router-dom";

type LoginType = {
    email: string;
    senha: string;
}

const LoginDefault: LoginType = {
    email: "",
    senha: ""
}
const Login = () => {
    const navigate = useNavigate();

    const [login, setLogin] = useState<LoginType>(LoginDefault)
    const [passwordVisible, setPasswordVisible] = useState(false);

    function handleLogin() {
        toast.promise(
            handleAuth(login),
            {
                loading: 'Entrando...',
                success: (r) => {
                    localStorage.setItem("user", JSON.stringify(r?.data));
                    navigate("/dashboard")
                    return <b>Entrou</b>
                },
                error: <b>Email ou senha incorretos</b>,
            }
        );
    }

    return (
        <Container>
            <div><Toaster position="top-center" reverseOrder={false}/></div>
            <Card>
                <CardLogin>Faça seu login</CardLogin>               
                <CardElements>
                    <Input
                        style={{"margin": "0 0 2vh 0"}}
                        placeholder="Username"
                        onChange={(e) =>
                            setLogin({...login, email: e.target.value})}
                    />
                    <Input.Password
                        style={{"margin": "0 0 2vh 0"}}
                        placeholder="Password"
                        visibilityToggle={{ visible: passwordVisible, onVisibleChange: setPasswordVisible }}
                        onChange={(e) =>
                            setLogin({...login, senha: e.target.value})}
                    />
                </CardElements>
                <CardButtons>
                    <Button type="primary" style={{"margin": "0 0 2vh 0"}} onClick={handleLogin}>Login</Button>
                    <CardButtonSignUp>
                        <ForgotButton>
                            <Button type="primary" danger ghost>Esqueceu a senha?</Button>
                        </ForgotButton>
                    
                        <Button type="primary"  ghost> Cadastrar</Button>
                    </CardButtonSignUp>                
                </CardButtons>
            </Card>
        </Container>
    )
}
export default Login;
