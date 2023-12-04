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
} from "./tipo.styles.ts";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import { getTipos, createTipos, editTipos, deleteTipos } from "../../service/type/type.ts";
import {toast, Toaster} from "react-hot-toast";
import Search from "antd/es/input/Search";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
import {ModalType} from "../../shared/types/ModalType.ts";
import { TipoType, EditTipoType } from "../../shared/types/TipoType.ts";

const tipoDefault: TipoType = {
  tipo: "",
}
  
const Tipo = () => {
    const [tipo, setTipo] = useState<TipoType>(tipoDefault);
    const [tipos, setTipos] = useState<TipoType[] | null>(null)
    const [isModalCreateOpen, setIsModalCreateOpen] = useState(false);
    const [isModalEditOpen, setIsModalEditOpen] = useState(false);
    const [editTipoId, setEditTipoId] = useState("")

    const columns: ColumnsType<TipoType> = [
        {
            title: 'Descrição',
            dataIndex: 'tipo',
            key: 'tipo',
            render: (text) => <a>{text}</a>,
        },
        {
            title: 'Editar',
            render: (c) => <EditOutlined onClick={() => {
                setEditTipoId(c.id)
                showModal(ModalType.edit)
            }}/>
        },
        {
            title: 'Deletar',
            render: (costumerDelete) => <DeleteOutlined onClick={() => handleDelete(costumerDelete)}/>
        },
    ];

    useEffect(() => {
      const getAllTipos = async () => {
        if(tipos === null) {
          await toast.promise(
            getTipos(),
            {
              loading: 'Carregando...',
              success: (r) => {
                setTipos(r.data)
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

      getAllTipos() //mudar aqui
    }, [tipos]);

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
               createTipos(tipo),
               {
                   loading: 'Criando...',
                   success: (r) => {
                       setTipos([...tipos!, r.data])

                       console.log(tipos)

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
        if (tipo.tipo === "" && modalType === ModalType.create) {
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
        if(validateData(ModalType.edit) && editTipoId !== "") {
            const editTipo: EditTipoType = {
                id: editTipoId,
                tipo: tipo.tipo
            }

            await toast.promise(
                editTipos(editTipo),
                {
                    loading: 'Editando...',
                    success: (r) => {
                        const newTipos = tipos!.filter(c => c.id !== editTipoId)
                        setTipos([...newTipos, r.data])

                        return <b>Editado</b>
                    },
                    error: (e) => {
                        console.log(e)
                        return <b>{e.message}</b>
                    },
                }
            )
            setIsModalEditOpen(false);
        } else if(editTipoId === "") {
            toast.error("Nenhum tipo")

            setIsModalEditOpen(false)
        }
    }

    const handleDelete = async (tipoDelete: TipoType) => {
        console.log(tipoDelete.id)

        await toast.promise(
            deleteTipos(tipoDelete.id!),
            {
                loading: 'Deletando...',
                success: (r) => {
                    console.log(r)
                    const newTipos = tipos!.filter(c =>
                        c.id !== tipoDelete.id)

                    setTipos(newTipos)

                    return <b>Deletado</b>
                },
                error: (e) => {
                    console.log(e)

                    return <b>{e.message}</b>
                },
            }
        )
        console.log(tipo)
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
                <Search placeholder="Buscar tipo"  onSearch={onSearch} enterButton />
              </ContainerButtonsSeacrh>
              <ContainerButtonInsert>
                <Button type="primary" onClick={() => showModal()}>Inserir Tipo</Button>
              </ContainerButtonInsert>
              <Modal title="Inserir tipo" onOk={handleOkCreate} open={isModalCreateOpen} onCancel={handleCancel}>
                <LimitInputs>
                    <Input
                        placeholder="Descrição"
                        style={{"margin":"0 0 1vh 0"}}
                        onChange={(e) => {setTipo({...tipo, tipo: e.target.value})}}
                    />                                    
                </LimitInputs>
              </Modal>

                <Modal title="Editar modelo" onOk={handleOkEdit} open={isModalEditOpen} onCancel={handleCancel}>
                    <LimitInputs>                     
                        <Input
                            placeholder="Descrição"
                            style={{"margin":"0 0 1vh 0"}}
                            onChange={(e) => setTipo({...tipo, tipo: e.target.value})}
                        />
                    </LimitInputs>
                </Modal>
            </ContainerButtons>
            <SizeTable>
                <Table
                    columns={columns}
                    dataSource={tipos === null ? undefined :
                        tipos!.map((tipo, index) => ({
                        ...tipo,
                        key: index, // Add a unique 'key' prop for each row
                    }))} />
            </SizeTable>

          </ContainerWholeTable>
        </Container>

    )
}

export default Tipo;