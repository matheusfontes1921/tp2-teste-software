import axios from "axios";
import {CreatePurchaseType} from "../../shared/types/DashboardType.ts";

export async function getPurchase(id: string) {
    return await axios.get(`http://localhost:8081/api/venda/getEmployee/${id}`)
}

export async function  createPurchase(purchase: CreatePurchaseType) {
    const data = JSON.stringify(purchase);
    const headers = { "Content-Type": "application/json" };

    return await axios.post('http://localhost:8081/api/venda/create', data, {headers: headers})
}

export async function deletePurchase(id: String) {
    return await  axios.delete(`http://localhost:8081/api/venda/delete/${id}`)
}

export async function relatorioPurchase(id: String) {
    return await  axios.post(`http://localhost:8081/api/venda/generateReport/${id}`)
}