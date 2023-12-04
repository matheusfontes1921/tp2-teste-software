import React, {useEffect, useState} from "react";
import {Button, Checkbox, Input, Modal, Select, Spin, Table, Upload} from 'antd';
import type {ColumnsType} from 'antd/es/table';
import {
    Container,
    ContainerButtonInsert,
    ContainerButtons,
    ContainerButtonsSeacrh,
    ContainerHeaderTable,
    ContainerWholeTable,
    Hello,
    LimitInputs,
    SizeTable
} from "./dashboard.styled.ts";
import Menu from "../../shared/menu/Menu";
import Header from "../../shared/header/Header";
import {
    CreateProductType,
    CreatePurchaseType,
    DashboardModalType,
    DashboardType,
    Infos,
    ProductType
} from "../../shared/types/DashboardType";
import {toast, Toaster} from "react-hot-toast";
import {createPurchase, deletePurchase, getPurchase} from "../../service/purchase/purchase.ts";
import StatusColumn from "../../components/StatusColumn.tsx";
import {StatusEnum, StatusType} from "../../shared/types/StatusType.ts";
import {getCostumersName} from "../../service/costumers/costumers.ts";
import {getClothsName} from "../../service/cloths/cloths.ts";
import {getModelsName} from "../../service/models/models.ts";
import {getTiposName} from "../../service/type/type.ts";
import {createProduct, deleteProduct} from "../../service/product/product.ts";
import {CheckboxChangeEvent} from "antd/es/checkbox";
import {DeleteOutlined, EditOutlined, PlusOutlined} from "@ant-design/icons";
import {toBase64} from "../../service/image/image.ts";

const { Search } = Input;

const purchaseDefault: CreatePurchaseType = {
    produtoIDList: [],
    customerId: "",
    employeeId: ""
}

const productDefault: CreateProductType = {
    idTipo: "",
    idModelo: "",
    idTecido: "",
    descricaoBolso: "",
    quantidadeBolso: 0,
    descricaoLogo: "",
    silkando: false,
    urlImagem: "",
}

const infoDefault: Infos = {
    purchases: [],
    costumersName: [],
    clothsName: [],
    modelsName: [],
    typesName: [],
}

const Dashboard = () => {
  const [purchase, setPurchase] = useState<CreatePurchaseType>(purchaseDefault)
  const [product, setProduct] = useState<CreateProductType>(productDefault)
  const [editProductId, setEditProductId] = useState("")
  const [products, setProducts] = useState<ProductType[]>([])
  const [infos, setInfos] = useState<Infos>(infoDefault)
  const [isModalCreatePurchaseOpen, setIsModalCreatePurchaseOpen] = useState(false);
  const [isModalCreateProductOpen, setIsModalCreateProductOpen] = useState(false);

  const columns: ColumnsType<DashboardType> = [
    {
      title: 'Nome do cliente',
      dataIndex: 'customerName',
      key: 'customerName',
      render: (text) => <a>{text}</a>,
      sorter: (a,b) => a.customerName.localeCompare(b.customerName),
    },
    {
      title: 'Nome do vendedor',
      dataIndex: 'employeeName',
      key: 'employeeName',
      render: (text) => <a>{text}</a>,
      sorter: (a,b) => a.employeeName.localeCompare(b.employeeName),
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (_,dashboard) => {
        const index = +StatusEnum[dashboard.status]
        const text = StatusEnum[index]

        const status: StatusType = {
          id: index,
          status: text
        }

        return <StatusColumn status={status}/>
      },
    },
    {
      title: 'Data do pedido',
      dataIndex: 'date',
      key: 'date',
      render: (text) => <a>{text}</a>,
    },
    {
      title: 'Exportar Relatório',
      render: (purchase) => <Button style={{"margin": "0 1vh 0 0"}} onClick={() => exportRelatorio(purchase.id)}>Exportar Relatório</Button>
    },
    {
      title: 'Deletar',
      render: (purchase) => <DeleteOutlined onClick={() => handleDelete(purchase.id)}/>
    },
  ];

  const productColumns: ColumnsType<ProductType> = [
    {
        title: 'Produto',
        dataIndex: 'product',
        key: 'product',
        render: (text) => <a>{text}</a>,
    },
    {
      title: 'Editar',
      render: (c) => <EditOutlined onClick={() => {
        setEditProductId(c.id)
        showModal(DashboardModalType.ProductEdit)
      }}/>
    },
    {
      title: 'Deletar',
      render: (productDelete) => <DeleteOutlined onClick={() => handleDelete(productDelete.id, DashboardModalType.ProductDelete)}/>
    },
  ]


  useEffect(() => {
      const purchases = async () => {
          await toast.promise(
            getAll(),
            {
              loading: 'Carregando...',
              success: () => {
                return <b>Pegou</b>
              },
              error: (e) => {
                console.log(e)

                return <b>error</b>
              },
            }
          )
      }

      purchases()
  }, []);

  const showModal = (dashboardModalType: DashboardModalType = DashboardModalType.PurchaseCreate) => {
    switch (dashboardModalType) {
        case DashboardModalType.PurchaseCreate:
            setIsModalCreatePurchaseOpen(true)
            break;
        case DashboardModalType.PurchaseEdit:
            break
        case DashboardModalType.ProductCreate:
            setIsModalCreateProductOpen(true)
            break;
        case DashboardModalType.ProductEdit:
            break;
    }
  };

  const getAll = async () => {
      const userStr = localStorage.getItem("user")
      if(userStr !== null) {
          const user = JSON.parse(userStr)
          const purchases = await getPurchase(user.id)
          const costumers = await getCostumersName();
          const cloths = await getClothsName();
          const models = await getModelsName();
          const types = await getTiposName();

          setPurchase({...purchase, employeeId: user.id})

          setInfos({
              purchases: purchases.data,
              costumersName: costumers.data,
              clothsName: cloths.data,
              modelsName: models.data,
              typesName: types.data
          })

          return true
      }

      return false
  }


  async function exportRelatorio(id: string) {
      if(id !== "") {
          window.open(`http://localhost:8081/api/venda/generateReport/${id}`, "_blank")
      }
  }


  const handleCreatePurchaseOk = async () => {
    const productsId: string[] = [];

    products.map((p) => {
        productsId.push(p.id)
    })

    setPurchase({...purchase, produtoIDList: productsId})

    await toast.promise(
        createPurchase(purchase),
        {
            loading: 'Criando...',
            success: (r) => {
                setInfos({...infos, purchases: [...infos.purchases, r.data]})

                return <b>Criado</b>
            },
            error: (e) => {
                console.log(e)

                return <b>error</b>
            },
        }
    )

    setIsModalCreatePurchaseOpen(false);
  };

  const handleCreateProductOk = async () => {
    await toast.promise(
      createProduct(product),
      {
        loading: 'Criando...',
        success: (r) => {
          setProducts([...products, r.data])
          setPurchase({...purchase, produtoIDList: [...purchase.produtoIDList, r.data.id]})

          return <b>Criado</b>
        },
        error: (e) => {
          console.log(e)

          return <b>error</b>
        },
      }
    )

    setIsModalCreateProductOpen(false);
    };

  const handleDelete = async (id: String, dashboardModalType: DashboardModalType = DashboardModalType.PurchaseDelete) => {
      if(dashboardModalType === DashboardModalType.PurchaseDelete) {
          await toast.promise(
              deletePurchase(id),
              {
                  loading: 'Deletando...',
                  success: () => {
                      const newPurchases = infos.purchases.filter(c =>
                          c.id !== id)

                      setInfos({...infos, purchases: newPurchases})

                      return <b>Deletado</b>
                  },
                  error: (e) => {
                      console.log(e)

                      return <b>{e.message}</b>
                  },
              }
          )
      } else {
          await toast.promise(
              deleteProduct(id),
              {
                  loading: 'Deletando...',
                  success: () => {
                      const newProducts = products.filter(c =>
                          c.id !== id)
                      const newProductsId = purchase.produtoIDList.filter(pid => pid !== id)

                      setProducts(newProducts)
                      setPurchase({...purchase, produtoIDList: newProductsId})

                      return <b>Deletado</b>
                  },
                  error: (e) => {
                      console.log(e)

                      return <b>{e.message}</b>
                  },
              }
          )
      }
    }

  const handleCancel = (dashboardModalType: DashboardModalType = DashboardModalType.PurchaseCreate) => {
      switch (dashboardModalType) {
          case DashboardModalType.PurchaseCreate:
              setIsModalCreatePurchaseOpen(false)
              break;
          case DashboardModalType.PurchaseEdit:
              break
          case DashboardModalType.ProductCreate:
              setIsModalCreateProductOpen(false)
              break;
          case DashboardModalType.ProductEdit:
              break;
      }
  };

  const onSearch = () => {}

  const onChange = (value: string) => {
      setPurchase({...purchase, customerId: value})
  };

  const filterOption = (input: string, option?: { label: string; value: string }) =>
      (option?.label ?? '').toLowerCase().includes(input.toLowerCase());


  return (
    <Container>
      <div><Toaster position="top-center" reverseOrder={false}/></div>
      <Menu/>
      <ContainerWholeTable>
        <ContainerHeaderTable>
          <Header/>
        </ContainerHeaderTable>
        <Hello>Olá, ADMIN</Hello>

        <ContainerButtons>
          <ContainerButtonsSeacrh>
            <Search placeholder="Buscar pedido"  onSearch={onSearch} enterButton />
          </ContainerButtonsSeacrh>
          <ContainerButtonInsert>
            <Button type="primary"  onClick={() => showModal()}>Inserir Pedido</Button>
          </ContainerButtonInsert>
          <Modal title="Inserir Pedido" open={isModalCreatePurchaseOpen} onOk={handleCreatePurchaseOk} onCancel={() => handleCancel()}>
            <LimitInputs>
              <Select
                showSearch
                placeholder="Cliente"
                optionFilterProp="children"
                onChange={onChange}
                onSearch={onSearch}
                filterOption={filterOption}
                options={infos.costumersName}
                style={{"margin":"0 0 1vh 0"}}
              />
              <ContainerButtonInsert style={{padding: 0}}>
                <Button type="primary" onClick={() => showModal(DashboardModalType.ProductCreate)}>Inserir Produto</Button>
              </ContainerButtonInsert>
              <SizeTable>
                <Table
                    columns={productColumns}
                    dataSource={products.map((p, index) => ({
                        ...p,
                        key: index, // Add a unique 'key' prop for each row
                    }))}
                />
              </SizeTable>
            </LimitInputs>
          </Modal>
          <Modal title="Criar Produto" open={isModalCreateProductOpen} onOk={handleCreateProductOk} onCancel={() => handleCancel(DashboardModalType.ProductCreate)}>
            <LimitInputs>
              <b>Modelo</b>
              <Select
                showSearch
                placeholder="Modelo"
                optionFilterProp="children"
                onChange={(value: string) => setProduct({...product, idModelo: value})}
                onSearch={onSearch}
                filterOption={filterOption}
                options={infos.modelsName}
                style={{"margin":"0 0 1vh 0"}}
              />
              <b>Tecido</b>
              <Select
                showSearch
                placeholder="Tecido"
                optionFilterProp="children"
                onChange={(value: string) => setProduct({...product, idTecido: value})}
                onSearch={onSearch}
                filterOption={filterOption}
                options={infos.clothsName}
                style={{"margin":"0 0 1vh 0"}}
              />
              <b>Tipo</b>
              <Select
                showSearch
                placeholder="Tipo"
                optionFilterProp="children"
                onChange={(value: string) => setProduct({...product, idTipo: value})}
                onSearch={onSearch}
                filterOption={filterOption}
                options={infos.typesName}
                style={{"margin":"0 0 1vh 0"}}
              />
              <b>Bolso</b>
                <Input
                    type={"number"}
                    placeholder="Quantidade de bolsos"
                    style={{"margin":"0 0 1vh 0"}}
                    onChange={(e) => setProduct({...product, quantidadeBolso: +e.target.value})}
                />
                <Input
                    placeholder="Descrição de bolsos"
                    style={{"margin":"0 0 1vh 0"}}
                    onChange={(e) => setProduct({...product, descricaoBolso: e.target.value})}
                />
              <b>Logo</b>
              <Input
                placeholder="Descrição da logo"
                style={{"margin":"0 0 1vh 0"}}
                onChange={(e) => setProduct({...product, descricaoLogo: e.target.value})}
              />
              <Checkbox
                  onChange={(e: CheckboxChangeEvent) => setProduct({...product, silkando: e.target.checked})}
                  style={{"margin":"0 0 1vh 0"}}
              >
                  Checkbox
              </Checkbox>

              {product.urlImagem ? <img src={product.urlImagem} alt="avatar" style={{ width: '100%' }} /> : (
                  <Upload.Dragger
                      multiple={false}
                      listType="picture"
                      action={"http://localhost:5173/"}
                      showUploadList={{showRemoveIcon: true}}
                      accept={".png,.jpeg,.doc"}
                      beforeUpload={file => {
                          toBase64(file).then(r => setProduct({...product, urlImagem: `${r}`}))
                          // Prevent upload
                          return false;
                      }}
                      iconRender={() => <Spin></Spin>}
                      progress={{
                          strokeWidth: 3,
                          style: { top: 12 }
                      }}
                      style={{"margin":"0 0 1vh 0"}}
                  >
                    <div>
                      <PlusOutlined />
                       <div style={{ marginTop: 8 }}>Upload</div>
                    </div>
                  </Upload.Dragger>
              )}
            </LimitInputs>
          </Modal>
        </ContainerButtons>
        <SizeTable>
          <Table
              columns={columns}
              dataSource={infos.purchases.map((costumer, index) => ({
                  ...costumer,
                  key: index, // Add a unique 'key' prop for each row
              }))}
          />
        </SizeTable>
      </ContainerWholeTable>
    </Container>
  )
}
export default Dashboard;