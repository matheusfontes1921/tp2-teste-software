export interface CostumerType {
    id?: string;
    nome: string;
    cpfCnpj: string;
    endereco: string;
    telefone: string;
    email: string;
}

export interface EditCostumerType {
    id: string;
    endereco: string;
    telefone: string;
    email: string;
}