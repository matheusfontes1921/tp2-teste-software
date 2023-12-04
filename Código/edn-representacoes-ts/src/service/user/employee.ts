import axios from "axios";
import { UserType, EditUserType } from "../../shared/types/UsersType";

export async function getEmployee() {
    return await axios.get(`http://localhost:8081/api/employee/get`)
}

export async function  createEmployee(user: UserType) {
    user = {...user}
    const data = JSON.stringify(user);
    const header = {"Content-Type": "application/json"}

    return await axios.post('http://localhost:8081/api/employee/create', data, {headers: header})
}

export async function  editEmployee(user: EditUserType) {
    const data = JSON.stringify(user);
    const header = {"Content-Type": "application/json"}

    return await axios.put('http://localhost:8081/api/employee/update', data, {headers: header})
}

export async function deleteEmployee(id: string) {
    return await axios.delete(`http://localhost:8081/api/employee/delete/${id}`)
}