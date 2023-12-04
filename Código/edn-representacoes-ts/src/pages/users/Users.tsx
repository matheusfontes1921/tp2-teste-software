import React, {useEffect, useState} from "react";
import { Table, Button, Input, Modal } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import {ModalType} from "../../shared/types/ModalType.ts";
import type { UserType, EditUserType } from "../../shared/types/UsersType";
import { createEmployee, editEmployee, deleteEmployee, getEmployee } from "../../service/user/employee.ts";

import {
  Container,
  ContainerButtonInsert,
  ContainerButtons,
  ContainerButtonsSeacrh,
  ContainerHeaderTable,
  ContainerWholeTable,
  LimitInputs,
  SizeTable
} from "./users.styles.ts";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import {toast, Toaster} from "react-hot-toast";
import {DeleteOutlined, EditOutlined} from "@ant-design/icons";
const { Search } = Input;


const userDefault: UserType = {
  nome: "",
  email: "",
  senha: ""
}
  
const Users = () => {
  const [users, setUsers] = useState<UserType[]>([]);
  const [user, setUser] = useState<UserType>(userDefault);
  const [isModalCreateOpen, setIsModalCreateOpen] = useState<boolean>(false);
  const [isModalEditOpen, setIsModalEditOpen] = useState(false);
  const [editUserId, setEditUserId] = useState("");
    
  const columns: ColumnsType<UserType> = [
      {
        title: 'Nome',
        dataIndex: 'nome',
        key: 'nome',
        render: (text) => <a>{text}</a>,
        sorter: (a,b) => a.nome.localeCompare(b.nome),
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
                setEditUserId(c.id)
                showModal(ModalType.edit)
            }}/>
        },
        {
            title: 'Deletar',
            render: (costumerDelete) => <DeleteOutlined onClick={() => handleDelete(costumerDelete)}/>
        },
  ];

  useEffect(() => {
    const getUsers = async () => {
      await toast.promise(
          getEmployee(),
          {
            loading: 'Carregando...',
            success: (r) => {
              console.log(r)
 
              setUsers(r.data)

              return <b>Pegou</b>
            },
            error: (e) => {
              console.log(e)

              return <b>error</b>
            },
          }
      )
    }

    getUsers();
  }, [])

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
              createEmployee(user),
              {
                  loading: 'Criando...',
                  success: (r) => {
                      setUsers([...users!, r.data])

                      console.log(users)

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
  if (user.nome === "" && modalType === ModalType.create) {
      toast.error("Nome vazio")

      return false;
  }
  else if (user.email === "" && modalType === ModalType.create) {
      toast.error("Email vazio")
      return false
  } else if(user.senha === "") {
      toast.error("Sennha vazia")
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
      if(validateData(ModalType.edit) && editUserId !== "") {
          const editUser: EditUserType = {
              id: editUserId,
              senha: user.senha,
          }

          await toast.promise(
              editEmployee(editUser),
              {
                  loading: 'Editando...',
                  success: (r) => {
                      const newUsers = users!.filter(c => c.id !== editUserId)
                      setUsers([...newUsers, r.data])

                      return <b>Editado</b>
                  },
                  error: (e) => {
                      console.log(e)
                      return <b>{e.message}</b>
                  },
              }
          )
          setIsModalEditOpen(false);
      } else if(editUserId === "") {
          toast.error("Nenhum usuário")

          setIsModalEditOpen(false)
      }
  }


  const handleDelete = async (userDelete: UserType) => {
      console.log(userDelete.id)

      await toast.promise(
          deleteEmployee(userDelete.id!),
          {
              loading: 'Deletando...',
              success: (r) => {
                  console.log(r)
                  const newUsers = users!.filter(c =>
                      c.id !== userDelete.id)

                  setUsers(newUsers)

                  return <b>Deletado</b>
              },
              error: (e) => {
                  console.log(e)

                  return <b>{e.message}</b>
              },
          }
      )
      console.log(user)
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
            <Search placeholder="Buscar usuário"  onSearch={onSearch} enterButton />
          </ContainerButtonsSeacrh>
          <ContainerButtonInsert>
            <Button type="primary" onClick={() => showModal()}>Inserir Usuário</Button>
          </ContainerButtonInsert>
          <Modal title="Inserir usuário" open={isModalCreateOpen} onOk={handleOkCreate} onCancel={handleCancel}>
            <LimitInputs>
              <Input
                  placeholder="Nome"
                  style={{"margin":"0 0 1vh 0"}}
                  onChange={(e) => {setUser({...user, nome: e.target.value})}}
              />
              <Input
                  placeholder="Email"
                  style={{"margin":"0 0 1vh 0"}}
                  onChange={(e) => {setUser({...user, email: e.target.value})}}
              />
              <Input
                  type={"password"}
                  placeholder="Senha"
                  style={{"margin":"0 0 1vh 0"}}
                  onChange={(e) => {setUser({...user, senha: e.target.value})}}
              />
            </LimitInputs>
          </Modal>
          <Modal title="Editar usuário" open={isModalEditOpen} onOk={handleOkEdit} onCancel={handleCancel}>
            <LimitInputs>
              <Input
                  type={"password"}
                  placeholder="Senha"
                  style={{"margin":"0 0 1vh 0"}}
                  onChange={(e) => {setUser({...user, senha: e.target.value})}}
              />
            </LimitInputs>
          </Modal>
        </ContainerButtons>
        <SizeTable>
          <Table columns={columns} 
            dataSource={users === null ? undefined :
            users!.map((user, index) => ({
            ...user,
            key: index, // Add a unique 'key' prop for each row
          }))} />
        </SizeTable>
      </ContainerWholeTable>
    </Container>
  )
}
export default Users;