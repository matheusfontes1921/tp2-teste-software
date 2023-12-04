import axios from "axios";
import { ModelsType } from "../../shared/types/ModelsType";

export async function getModels() {
    return await axios.get(`http://localhost:8081/api/modelo/get`)
}
export async function getModelsName() {
    return await axios.get(`http://localhost:8081/api/modelo/options`)
}

export async function  createModels(model: ModelsType) {
    model = {...model}
    const data = JSON.stringify(model);
    const header = {"Content-Type": "application/json"}

    return await axios.post('http://localhost:8081/api/modelo/create', data, {headers: header})
}

export async function  editModels(model: ModelsType) {
    const data = JSON.stringify(model);
    const header = {"Content-Type": "application/json"}

    return await axios.put('http://localhost:8081/api/modelo/update', data, {headers: header})
}

export async function deleteModels(id: string) {
    return await axios.delete(`http://localhost:8081/api/modelo/delete/${id}`)
}