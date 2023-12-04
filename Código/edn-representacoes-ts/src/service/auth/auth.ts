import axios from "axios";

type LoginType = {
    email: string;
    senha: string;
}

export async function handleAuth(login: LoginType) {
    if(login.email !== "" && login.senha !== "") {
        const headers = {"Content-Type": "application/json"}

       return await axios.post("http://localhost:8081/api/employee/login", JSON.stringify(login), {headers: headers})
    }
}