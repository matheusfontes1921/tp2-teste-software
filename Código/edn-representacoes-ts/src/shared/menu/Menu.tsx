import { ContainerMenu, Container, CompanyName, ContainerLogoName } from "./menu.style";
import React, { useState } from 'react';
import { HomeOutlined, PoundOutlined, UserOutlined, GiftOutlined, ScissorOutlined, BgColorsOutlined, ShopOutlined, SkinOutlined } from '@ant-design/icons';
import type { MenuProps, MenuTheme } from 'antd';
import { Menu as MenuAntD, Switch } from 'antd';
import { useNavigate } from "react-router-dom";
import { RoutesEnum } from "../routes/routes";


type MenuItem = Required<MenuProps>['items'][number];

function getItem(
  label: React.ReactNode,
  key?: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
  type?: 'group',
): MenuItem {
  return {
    key,
    icon,
    children,
    label,
    type,
  } as MenuItem;
}


const Menu = () => {
  const navigate = useNavigate();
  const [theme, setTheme] = useState<MenuTheme>('dark');
  const items: MenuItem[] = [
    {
        key:"home",
        label:"Página Inicial",
        onClick: () => {navigate(RoutesEnum.HOME)},
        icon: <HomeOutlined />,
    },
    {
        key: "empresas",
        onClick: () => {navigate(RoutesEnum.EMPRESAS)},
        label: "Empresas",
        icon: <PoundOutlined />,
    },
    {
        key: "roupas",
        label: "Roupas",
        icon: <ShopOutlined />,
        children: [
            {
                key: "modelo",
                label: "Modelo",
                onClick: () => {navigate(RoutesEnum.MODELOS)},
                icon: <BgColorsOutlined />,

            },
            {
                key: "tecidos",
                label: "Tecidos",
                onClick: () => {navigate(RoutesEnum.TECIDOS)},
                icon: <ScissorOutlined />,
            },
            {
              key: "tipo",
              label: "Tipo",
              onClick: () => {navigate(RoutesEnum.TIPOS)},
              icon: <SkinOutlined />,

          }
        ],
    },
    {
        key: "usuarios",
        label: "Usuários",
        onClick: () => {navigate(RoutesEnum.USUARIOS)},
        icon: <UserOutlined />,
    }

  ];
  const [current, setCurrent] = useState('1');

  const changeTheme = (value: boolean) => {
    setTheme(value ? 'dark' : 'light');
  };

  const onClick: MenuProps['onClick'] = (e) => {
    console.log('click ', e);
    setCurrent(e.key);
  };
    return (
      
        <ContainerMenu>
            <ContainerLogoName>
            <CompanyName>EDN Representações</CompanyName>
            </ContainerLogoName>
            <MenuAntD
        theme={theme}
        onClick={onClick}
        style={{ width: "100%" }}
        defaultOpenKeys={['sub1']}
        selectedKeys={[current]}
        mode="inline"
        items={items}
        
      />
        </ContainerMenu>
        
    )
}
export default Menu;