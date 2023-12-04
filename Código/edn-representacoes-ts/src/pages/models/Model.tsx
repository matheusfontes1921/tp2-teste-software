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
} from "./model.styles.ts";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import { editModels, createModels, deleteModels, getModels } from "../../service/models/models.ts";
import {toast, Toaster} from "react-hot-toast";
import Search from "antd/es/input/Search";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ModalType} from "../../shared/types/ModalType.ts";
import { ModelsType } from "../../shared/types/ModelsType.ts";

const modelDefault: ModelsType = {
  detalhes: "",
}
  
const Model = () => {
    const [model, setModel] = useState<ModelsType>(modelDefault);
    const [models, setModels] = useState<ModelsType[] | null>(null)
    const [isModalCreateOpen, setIsModalCreateOpen] = useState(false);
    const [isModalEditOpen, setIsModalEditOpen] = useState(false);
    const [editModelId, setEditModelId] = useState("")

    const columns: ColumnsType<ModelsType> = [
        {
            title: 'Detalhe',
            dataIndex: 'detalhes',
            key: 'detalhes',
            render: (text) => <a>{text}</a>,
            sorter: (a,b) => a.detalhes.localeCompare(b.detalhes),
        },
        {
            title: 'Editar',
            render: (c) => <EditOutlined onClick={() => {
                setEditModelId(c.id)
                showModal(ModalType.edit)
            }}/>
        },
        {
            title: 'Deletar',
            render: (costumerDelete) => <DeleteOutlined onClick={() => handleDelete(costumerDelete)}/>
        },
    ];

    useEffect(() => {
      const getAllModels = async () => {
        if(models === null) {
          await toast.promise(
            getModels(),
            {
              loading: 'Carregando...',
              success: (r) => {
                setModels(r.data)
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

      getAllModels() //mudar aqui
    }, [models]);

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
               createModels(model),
               {
                   loading: 'Criando...',
                   success: (r) => {
                       setModels([...models!, r.data])

                       console.log(models)

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
        if (model.detalhes === "" && modalType === ModalType.create) {
            toast.error("Detalhe vazio")

            return false;
        }
         else {
            return true
        }
    }

    const handleCancel = () => {
        setIsModalCreateOpen(false);
        setIsModalEditOpen(false);
    };

    const handleOkEdit = async () => {
        if(validateData(ModalType.edit) && editModelId !== "") {
            const editModel: ModelsType = {
                id: editModelId,
                detalhes: model.detalhes
            }

            await toast.promise(
                editModels(editModel),
                {
                    loading: 'Editando...',
                    success: (r) => {
                        const newModels = models!.filter(c => c.id !== editModelId)
                        setModels([...newModels, r.data])

                        return <b>Editado</b>
                    },
                    error: (e) => {
                        console.log(e)
                        return <b>{e.message}</b>
                    },
                }
            )
            setIsModalEditOpen(false);
        } else if(editModelId === "") {
            toast.error("Nenhum modelo")

            setIsModalEditOpen(false)
        }
    }

    const handleDelete = async (modelDelete: ModelsType) => {
        console.log(modelDelete.id)

        await toast.promise(
            deleteModels(modelDelete.id!),
            {
                loading: 'Deletando...',
                success: (r) => {
                    console.log(r)
                    const newModels = models!.filter(c =>
                        c.id !== modelDelete.id)

                    setModels(newModels)

                    return <b>Deletado</b>
                },
                error: (e) => {
                    console.log(e)

                    return <b>{e.message}</b>
                },
            }
        )
        console.log(model)
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
              <Modal title="Inserir modelo" onOk={handleOkCreate} open={isModalCreateOpen} onCancel={handleCancel}>
                <LimitInputs>
                    <Input
                        placeholder="Detalhe"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => {setModel({...model, detalhes: e.target.value})}}
                    />                                    
                </LimitInputs>
              </Modal>

                <Modal title="Editar modelo" onOk={handleOkEdit} open={isModalEditOpen} onCancel={handleCancel}>
                    <LimitInputs>
                        <Input
                            placeholder="Detalhe"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setModel({...model, detalhes: e.target.value})}
                        />
                    </LimitInputs>
                </Modal>
            </ContainerButtons>
            <SizeTable>
                <Table
                    columns={columns}
                    dataSource={models === null ? undefined :
                        models!.map((model, index) => ({
                        ...model,
                        key: index, // Add a unique 'key' prop for each row
                    }))} />
            </SizeTable>

          </ContainerWholeTable>
        </Container>

    )
}

export default Model;