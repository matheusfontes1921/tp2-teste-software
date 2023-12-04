import axios from "axios";
import { TipoType } from "../../shared/types/TipoType";

export async function getTipos() {
    return await axios.get(`http://localhost:8081/api/tipo/get`)
}
export async function getTiposName() {
    return await axios.get(`http://localhost:8081/api/tipo/options`)
}

export async function  createTipos(tipo: TipoType) {
    tipo = {...tipo}
    const data = JSON.stringify(tipo);
    const header = {"Content-Type": "application/json"}

    return await axios.post('http://localhost:8081/api/tipo/create', data, {headers: header})
}

export async function  editTipos(tipo: TipoType) {
    const data = JSON.stringify(tipo);
    const header = {"Content-Type": "application/json"}

    return await axios.put('http://localhost:8081/api/tipo/update', data, {headers: header})
}

export async function deleteTipos(id: string) {
    return await axios.delete(`http://localhost:8081/api/tipo/delete/${id}`)
}