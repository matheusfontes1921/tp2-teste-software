import axios from "axios";
import {CostumerType, EditCostumerType} from "../../shared/types/CostumerType.ts";

export async function getCostumers() {
    return await axios.get(`http://localhost:8081/api/customer/get`)
}
export async function getCostumersName() {
    return await axios.get(`http://localhost:8081/api/customer/options`)
}

export async function  createCostumers(costumer: CostumerType) {
    costumer = {...costumer, cpfCnpj: geraCPF()}
    const data = JSON.stringify(costumer);
    const header = {"Content-Type": "application/json"}

    return await axios.post('http://localhost:8081/api/customer/create', data, {headers: header})
}

export async function  editCostumers(costumer: EditCostumerType) {
    const data = JSON.stringify(costumer);
    const header = {"Content-Type": "application/json"}

    return await axios.put('http://localhost:8081/api/customer/update', data, {headers: header})
}

function geraCPF() {
    const n = 9;
    const n1 = Math.floor(Math.random() * n);
    const n2 = Math.floor(Math.random() * n);
    const n3 = Math.floor(Math.random() * n);
    const n4 = Math.floor(Math.random() * n);
    const n5 = Math.floor(Math.random() * n);
    const n6 = Math.floor(Math.random() * n);
    const n7 = Math.floor(Math.random() * n);
    const n8 = Math.floor(Math.random() * n);
    const n9 = Math.floor(Math.random() * n);
    const d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;
    const d1Resto = d1 % 11;
    const d1Verificador = d1Resto < 2 ? 0 : 11 - d1Resto;

    const d2 = d1Verificador * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;
    const d2Resto = d2 % 11;
    const d2Verificador = d2Resto < 2 ? 0 : 11 - d2Resto;

    return `${n1}${n2}${n3}.${n4}${n5}${n6}.${n7}${n8}${n9}-${d1Verificador}${d2Verificador}`;
}

export async function deleteCostumer(id: string) {
    return await axios.delete(`http://localhost:8081/api/customer/delete/${id}`)
}