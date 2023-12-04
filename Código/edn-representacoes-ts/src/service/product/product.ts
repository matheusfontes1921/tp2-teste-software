import {CreateProductType} from "../../shared/types/DashboardType.ts";
import axios from "axios";

export async function  createProduct(product: CreateProductType) {
    console.log(product)

    const data = JSON.stringify(product);
    const headers = { "Content-Type": "application/json" };

    return await axios.post('http://localhost:8081/api/produto/create', data, {headers: headers})
}

export async function deleteProduct(id: String) {
    return await  axios.delete(`http://localhost:8081/api/produto/${id}`)
}