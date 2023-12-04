import axios from "axios";
import { ClothType } from "../../shared/types/ClothsType";

export async function getCloths() {
    return await axios.get(`http://localhost:8081/api/tecido/get`)
}
export async function getClothsName() {
    return await axios.get(`http://localhost:8081/api/tecido/options`)
}

export async function  createCloths(cloth: ClothType) {
    cloth = {...cloth}
    const data = JSON.stringify(cloth);
    const header = {"Content-Type": "application/json"}

    return await axios.post('http://localhost:8081/api/tecido/create', data, {headers: header})
}

export async function  editCloths(cloth: ClothType) {
    const data = JSON.stringify(cloth);
    const header = {"Content-Type": "application/json"}

    return await axios.put('http://localhost:8081/api/tecido/update', data, {headers: header})
}

export async function deleteCloths(id: string) {
    return await axios.delete(`http://localhost:8081/api/tecido/delete/${id}`)
}