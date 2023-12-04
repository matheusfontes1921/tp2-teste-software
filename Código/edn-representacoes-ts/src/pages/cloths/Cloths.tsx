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
} from "./cloths.styles.ts";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import { createCloths, getCloths, editCloths, deleteCloths } from "../../service/cloths/cloths.ts";
import {toast, Toaster} from "react-hot-toast";
import Search from "antd/es/input/Search";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ModalType} from "../../shared/types/ModalType.ts";
import { ClothType } from "../../shared/types/ClothsType.ts";

const clothDefault: ClothType = {
  nome: "",
  fornecedor: "",
  codigo: "",
  classificacao: "",
}
  
const Cloths = () => {
    const [cloth, setCloth] = useState<ClothType>(clothDefault);
    const [cloths, setCloths] = useState<ClothType[] | null>(null)
    const [isModalCreateOpen, setIsModalCreateOpen] = useState(false);
    const [isModalEditOpen, setIsModalEditOpen] = useState(false);
    const [editClothId, setEditClothId] = useState("")

    const columns: ColumnsType<ClothType> = [
        {
            title: 'Nome',
            dataIndex: 'nome',
            key: 'nome',
            render: (text) => <a>{text}</a>,
            sorter: (a,b) => a.nome.localeCompare(b.nome),
        },
        {
            title: 'Fornecedor',
            dataIndex: 'fornecedor',
            key: 'fornecedor',
        },
        {
            title: 'Código',
            dataIndex: 'codigo',
            key: 'codigo',
        },
        {
            title: 'Classificacao',
            dataIndex: 'classificacao',
            key: 'classificacao',
        },
        {
            title: 'Editar',
            render: (c) => <EditOutlined onClick={() => {
                setEditClothId(c.id)
                showModal(ModalType.edit)
            }}/>
        },
        {
            title: 'Deletar',
            render: (costumerDelete) => <DeleteOutlined onClick={() => handleDelete(costumerDelete)}/>
        },
    ];

    useEffect(() => {
      const getAllCloths = async () => {
        if(cloths === null) {
          await toast.promise(
            getCloths(),
            {
              loading: 'Carregando...',
              success: (r) => {
                setCloths(r.data)
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

      getAllCloths() //mudar aqui
    }, [cloths]);

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
               createCloths(cloth),
               {
                   loading: 'Criando...',
                   success: (r) => {
                       setCloths([...cloths!, r.data])

                       console.log(cloths)

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
        if (cloth.nome === "" && modalType === ModalType.create) {
            toast.error("Nome vazio")

            return false;
        }
        else if (cloth.fornecedor === "" && modalType === ModalType.create) {
            toast.error("Fornecedor vazio")
            return false
        } else if (cloth.codigo === "" && modalType === ModalType.create) {
            toast.error("Código vazio")
            return false
        } else if (cloth.classificacao === "" && modalType === ModalType.create) {
            toast.error("Classificação vazia")
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
        if(validateData(ModalType.edit) && editClothId !== "") {
            const editCostumer: ClothType = {
                id: editClothId,
                nome: cloth.nome,
                fornecedor: cloth.fornecedor,
                codigo: cloth.codigo,
                classificacao: cloth.classificacao,
            }

            await toast.promise(
                editCloths(editCostumer),
                {
                    loading: 'Editando...',
                    success: (r) => {
                        const newCostumers = cloths!.filter(c => c.id !== editClothId)
                        setCloths([...newCostumers, r.data])

                        return <b>Editado</b>
                    },
                    error: (e) => {
                        console.log(e)
                        return <b>{e.message}</b>
                    },
                }
            )
            setIsModalEditOpen(false);
        } else if(editClothId === "") {
            toast.error("Nenhum tecido")

            setIsModalEditOpen(false)
        }
    }

    const handleDelete = async (clothDelete: ClothType) => {
        console.log(clothDelete.id)

        await toast.promise(
            deleteCloths(clothDelete.id!),
            {
                loading: 'Deletando...',
                success: (r) => {
                    console.log(r)
                    const newCostumers = cloths!.filter(c =>
                        c.id !== clothDelete.id)

                    setCloths(newCostumers)

                    return <b>Deletado</b>
                },
                error: (e) => {
                    console.log(e)

                    return <b>{e.message}</b>
                },
            }
        )
        console.log(cloth)
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
                <Search placeholder="Buscar tecido"  onSearch={onSearch} enterButton />
              </ContainerButtonsSeacrh>
              <ContainerButtonInsert>
                <Button type="primary" onClick={() => showModal()}>Inserir Tecido</Button>
              </ContainerButtonInsert>
              <Modal title="Inserir tecido" onOk={handleOkCreate} open={isModalCreateOpen} onCancel={handleCancel}>
                <LimitInputs>
                    <Input
                        placeholder="Nome"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => {setCloth({...cloth, nome: e.target.value})}}
                    />
                    <Input
                        placeholder="Fornecedor"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCloth({...cloth, fornecedor: e.target.value})}
                    />
                    <Input
                        placeholder="Código"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCloth({...cloth, codigo: e.target.value})}
                    />  
                    <Input
                        placeholder="Classificação"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => setCloth({...cloth, classificacao: e.target.value})}
                    />                                      
                </LimitInputs>
              </Modal>

                <Modal title="Editar tecido" onOk={handleOkEdit} open={isModalEditOpen} onCancel={handleCancel}>
                    <LimitInputs>
                        <Input
                            placeholder="Nome"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCloth({...cloth, nome: e.target.value})}
                        />
                        <Input
                            placeholder="Fornecedor"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCloth({...cloth, fornecedor: e.target.value})}
                        />
                        <Input
                            placeholder="Código"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCloth({...cloth, codigo: e.target.value})}
                        />  
                        <Input
                            placeholder="Classificação"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setCloth({...cloth, classificacao: e.target.value})}
                        /> 
                    </LimitInputs>
                </Modal>
            </ContainerButtons>
            <SizeTable>
                <Table
                    columns={columns}
                    dataSource={cloths === null ? undefined :
                        cloths!.map((cloth, index) => ({
                        ...cloth,
                        key: index, // Add a unique 'key' prop for each row
                    }))} />
            </SizeTable>

          </ContainerWholeTable>
        </Container>

    )
}

export default Cloths;