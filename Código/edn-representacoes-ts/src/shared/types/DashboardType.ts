import {StatusEnum} from "./StatusType";
export interface DashboardType {
    id: string;
    customerName: string;
    employeeName: string;
    status: StatusEnum;
    date: string;
}

export type CreatePurchaseType = {
    produtoIDList: string[];
    customerId: string;
    employeeId: string;
}

export type CreateProductType = {
    idTipo: string;
    idModelo: string;
    idTecido: string;
    descricaoBolso: string;
    quantidadeBolso: number;
    descricaoLogo: string;
    silkando: boolean;
    urlImagem: string;
}

export interface ProductType {
    id: string;
    product: string;
}

export type OptionType = {
    value: string;
    label: string;
}

export interface Infos {
   purchases: DashboardType[]
   costumersName: OptionType[]
   clothsName: OptionType[]
   modelsName: OptionType[]
   typesName: OptionType[]
}

export enum DashboardModalType {
    PurchaseCreate,
    PurchaseEdit,
    PurchaseDelete,
    ProductCreate,
    ProductEdit,
    ProductDelete

}