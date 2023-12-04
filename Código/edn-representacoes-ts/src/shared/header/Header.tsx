import React, { useState } from "react";
import {PoweroffOutlined} from '@ant-design/icons';
import { Container, HeaderIcon } from "./header.styles";
import { useNavigate } from "react-router-dom";
import { RoutesEnum } from "../routes/routes";
import { Modal } from "antd";

const Header = () => {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const showModal = () => {
    setOpen(true);
  };
  const handleOk = () => {
    navigate(RoutesEnum.LOGIN);
  };

  const handleCancel = () => {
    setOpen(false);
  };

    return (
    <Container>
     <HeaderIcon>
        <PoweroffOutlined onClick={showModal}/>
        <Modal title="DESEJA SAIR?" open={open} onOk={handleOk} onCancel={handleCancel}>
          <p><b>Para confirmar a saída, pressione OK</b></p>
      </Modal>
      </HeaderIcon>
    </Container>
    )
}
export default Header;