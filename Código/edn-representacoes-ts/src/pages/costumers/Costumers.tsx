import React, {useEffect, useState} from "react";
import {Button, Input, Modal, Table} from 'antd';
import type {ColumnsType} from 'antd/es/table';
import {
    Container,
    ContainerButtonInsert,
    ContainerButtons,
    ContainerButtonsSeacrh,
    ContainerHeaderTable,
    ContainerWholeTable,
    LimitInputs,
    SizeTable
} from "./companies.styles";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import {createCostumers, deleteCostumer, editCostumers, getCostumers} from "../../service/costumers/costumers.ts";
import {toast, Toaster} from "react-hot-toast";
import Search from "antd/es/input/Search";
import {CostumerType, EditCostumerType} from "../../shared/types/CostumerType.ts";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ModalType} from "../../shared/types/ModalType.ts";

const costumerDefault: CostumerType = {
  nome: "",
  cpfCnpj: "",
  endereco: "",
  telefone: "",
  email: ""
}
  
const Costumers = () => {
    const [costumer, setCostumer] = useState<CostumerType>(costumerDefault);
    const [costumers, setCostumers] = useState<CostumerType[] | null>(null)
    const [isModalCreateOpen, setIsModalCreateOpen] = useState(false);
    const [isModalEditOpen, setIsModalEditOpen] = useState(false);
    const [editCostumerId, setEditCustumerId] = useState("")

    const columns: ColumnsType<CostumerType> = [
        {
            title: 'Nome',
            dataIndex: 'nome',
            key: 'nome',
            render: (text) => <a>{text}</a>,
            sorter: (a,b) => a.nome.localeCompare(b.nome),
        },
        {
            title: 'CPF_CNPJ',
            dataIndex: 'cpfCnpj',
            key: 'cpfCnpj',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
            render: (text) => <a>{text}</a>,
        },
        {
            title: 'Editar',
            render: (c) => <EditOutlined onClick={() => {
                setEditCustumerId(c.id)
                showModal(ModalType.edit)
            }}/>
        },
        {
            title: 'Deletar',
            render: (costumerDelete) => <DeleteOutlined onClick={() => handleDelete(costumerDelete)}/>
        },
    ];

    useEffect(() => {
      const getAllCostumers = async () => {
        if(costumers === null) {
          await toast.promise(
            getCostumers(),
            {
              loading: 'Carregando...',
              success: (r) => {
                setCostumers(r.data)
                return <b>Pegou</b>
              },
              error: (e) => {
                console.log(e)

                return <b>error</b>
              },
            }
          )
        }
      }

      getAllCostumers()
    }, [costumers]);

    const showModal = (modalType = ModalType.create) => {
        if(modalType === ModalType.create)
            setIsModalCreateOpen(true);
        else {
            setIsModalEditOpen(true)
        }
    };

    const handleOkCreate = async () => {
       if(validateData()) {
           await toast.promise(
               createCostumers(costumer),
               {
                   loading: 'Criando...',
                   success: (r) => {
                       setCostumers([...costumers!, r.data])

                       console.log(costumers)

                       return <b>Criado</b>
                   },
                   error: (e) => {
                       console.log(e)
                       return <b>{e.message}</b>
                   },
               }
           )
           setIsModalCreateOpen(false);
       }
    };

    const validateData = (modalType = ModalType.create) => {
        if (costumer.nome === "" && modalType === ModalType.create) {
            toast.error("Nome vazio")

            return false;
        }
        // else if (costumer.cpfCnpj === "" && modalType === ModalType.create) {
        //     toast.error("CPF/CNPJ vazio")
        //     return false
        // }
        else if (costumer.endereco === "") {
            toast.error("Endereço vazio")
            return false
        }
        else if (costumer.telefone === "") {
            toast.error("Telefone vazio")
            return false
        }
        else if (costumer.email === "") {
            toast.error("E-mail vazio")
            return false
        } else {
            return true
        }
    }

    const handleCancel = () => {
        setIsModalCreateOpen(false);
        setIsModalEditOpen(false);
    };

    const handleOkEdit = async () => {
        if(validateData(ModalType.edit) && editCostumerId !== "") {
            const editCostumer: EditCostumerType = {
                id: editCostumerId,
                email: costumer.email,
                endereco: costumer.endereco,
                telefone: costumer.telefone
            }

            await toast.promise(
                editCostumers(editCostumer),
                {
                    loading: 'Editando...',
                    success: (r) => {
                        const newCostumers = costumers!.filter(c => c.id !== editCostumerId)
                        setCostumers([...newCostumers, r.data])

                        return <b>Editado</b>
                    },
                    error: (e) => {
                        console.log(e)
                        return <b>{e.message}</b>
                    },
                }
            )
            setIsModalEditOpen(false);
        } else if(editCostumerId === "") {
            toast.error("Nenhum clinte")

            setIsModalEditOpen(false)
        }
    }

    const handleDelete = async (costumerDelete: CostumerType) => {
        console.log(costumerDelete.id)

        await toast.promise(
            deleteCostumer(costumerDelete.id!),
            {
                loading: 'Deletando...',
                success: (r) => {
                    console.log(r)
                    const newCostumers = costumers!.filter(c =>
                        c.id !== costumerDelete.id)

                    setCostumers(newCostumers)

                    return <b>Deletado</b>
                },
                error: (e) => {
                    console.log(e)

                    return <b>{e.message}</b>
                },
            }
        )
        console.log(costumer)
    }

    const onSearch = (value: string) => {
        console.log(value);
    }

    return (
        <Container>
            <div><Toaster position="top-center" reverseOrder={false}/></div>
          <Menu/> 
          <ContainerWholeTable>
            <ContainerHeaderTable>
              <Header/>
            </ContainerHeaderTable>
            <ContainerButtons>
              <ContainerButtonsSeacrh>
                <Search placeholder="Buscar empresa"  onSearch={onSearch} enterButton />
              </ContainerButtonsSeacrh>
              <ContainerButtonInsert>
                <Button type="primary" onClick={() => showModal()}>Inserir Empresa</Button>
              </ContainerButtonInsert>
              <Modal title="Inserir client" onOk={handleOkCreate} open={isModalCreateOpen} onCancel={handleCancel}>
                <LimitInputs>
                    <Input
                        placeholder="Nome"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => {setCostumer({...costumer, nome: e.target.value})}}
                    />
                    <Input
                        placeholder="CPF_CNPJ"
                        style={{"margin":"0 0 1vh 0"}}
                        // onChange={(e) => setCostumer({...costumer, cpfCnpj: e.target.value})}
                    />
                    <Input
                        placeholder="Endereço"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCostumer({...costumer, endereco: e.target.value})}
                    />
                    <Input
                        placeholder="Telefone"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCostumer({...costumer, telefone: e.target.value})}
                    />
                    <Input
                        placeholder="Email"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCostumer({...costumer, email: e.target.value})}
                    />
                </LimitInputs>
              </Modal>

                <Modal title="Editar cliente" onOk={handleOkEdit} open={isModalEditOpen} onCancel={handleCancel}>
                    <LimitInputs>
                        <Input
                            placeholder="Endereço"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCostumer({...costumer, endereco: e.target.value})}
                        />
                        <Input
                            placeholder="Telefone"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCostumer({...costumer, telefone: e.target.value})}
                        />
                        <Input
                            placeholder="Email"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCostumer({...costumer, email: e.target.value})}
                        />
                    </LimitInputs>
                </Modal>
            </ContainerButtons>
            <SizeTable>
                <Table
                    columns={columns}
                    dataSource={costumers === null ? undefined :
                        costumers!.map((costumer, index) => ({
                        ...costumer,
                        key: index, // Add a unique 'key' prop for each row
                    }))} />
            </SizeTable>

          </ContainerWholeTable>
        </Container>

    )
}
export default Costumers;