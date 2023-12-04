package com.seta.tis4.model.services;

import com.seta.tis4.model.dtos.purchase.CreatePurchaseDTO;
import com.seta.tis4.model.dtos.purchase.GetPurchaseDTO;
import com.seta.tis4.model.dtos.purchase.UpdatePurchaseDTO;
import com.seta.tis4.model.entities.produto.Produto;
import com.seta.tis4.model.entities.purchase.Purchase;
import com.seta.tis4.model.entities.user.Employee;
import com.seta.tis4.model.entities.user.customer.Customer;
import com.seta.tis4.model.repositories.CustomerRepository;
import com.seta.tis4.model.repositories.EmployeeRepository;
import com.seta.tis4.model.repositories.ProdutoRepository;
import com.seta.tis4.model.repositories.PurchaseRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PurchaseService {
    PurchaseRepository purchaseRepository;
    ProdutoRepository produtoRepository;
    CustomerRepository customerRepository;
    EmployeeRepository employeeRepository;

    public PurchaseService(
            CustomerRepository customerRepository,
            EmployeeRepository employeeRepository,
            ProdutoRepository produtoRepository,
            PurchaseRepository purchaseRepository
    ) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.produtoRepository = produtoRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public ResponseEntity<?> get() {
        return ResponseEntity.ok().body(purchaseRepository.findAll());
    }

    public ResponseEntity<?> getByCustomerID(UUID costumerID) {
        Optional<Customer> customerOptional = customerRepository.findById(costumerID);
        if (customerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa nao existe");
        }

        List<Purchase> purchaseList = purchaseRepository.findPurchaseByCustomer_Id(costumerID);

        return ResponseEntity.ok().body(purchaseList);
    }

    public ResponseEntity<?> getByEmployeeID(UUID employeeIDID) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeIDID);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Fuincionario nao existe");
        }

        List<GetPurchaseDTO> purchaseDTOList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        purchaseRepository.findPurchaseByEmployee_Id(employeeIDID).forEach(purchase -> {
            GetPurchaseDTO getPurchaseDTO = GetPurchaseDTO.builder()
                    .id(purchase.getId())
                    .status(purchase.getStatus().name())
                    .customerName(purchase.getCustomer().getNome())
                    .employeeName(purchase.getEmployee().getNome())
                    .date(purchase.getDate().format(dateTimeFormatter))
                    .build();

            purchaseDTOList.add(getPurchaseDTO);
        });


        return ResponseEntity.ok().body(purchaseDTOList);
    }

    public ResponseEntity<?> create(CreatePurchaseDTO dto) {
        Optional<Customer> customerOptional = customerRepository.findById(dto.customerId());
        if (customerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Company nao existe");
        }

        Optional<Employee> employeeOptional = employeeRepository.findById(dto.employeeId());
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Employee nao existe");
        }

        List<Produto> produtoList = handleProdutoList(dto.produtoIDList());
        if (produtoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum produto selecionado");
        }

        Purchase purchase = dto.toPurchase(produtoList, customerOptional.get(), employeeOptional.get());

        purchase = purchaseRepository.save(purchase);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        GetPurchaseDTO getPurchaseDTO = GetPurchaseDTO.builder()
                .id(purchase.getId())
                .status(purchase.getStatus().name())
                .customerName(purchase.getCustomer().getNome())
                .employeeName(purchase.getEmployee().getNome())
                .date(purchase.getDate().format(dateTimeFormatter))
                .build();

        return ResponseEntity.ok().body(getPurchaseDTO);
    }

    public ResponseEntity<?> update(UpdatePurchaseDTO dto) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(dto.id());
        if (purchaseOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Venda nao existe");
        }

        List<Produto> produtoList = handleProdutoList(dto.produtoIDList());

        if (produtoList.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhum produto selecionado");
        }

        purchaseOptional.get().getProducts().addAll(produtoList);

        return ResponseEntity.ok().body(purchaseRepository.saveAndFlush(purchaseOptional.get()));
    }

    public ResponseEntity<?> delete(UUID id) {
        try {
            purchaseRepository.deleteById(id);

            if (!purchaseRepository.existsById(id)) {
                return ResponseEntity.ok().body("Deletado");
            }

            return ResponseEntity.badRequest().body("Nao existe");
        } catch (IllegalArgumentException error) {
            return ResponseEntity.badRequest().body(error);

        }
    }

    private List<Produto> handleProdutoList(List<UUID> produtoIDList) {
        List<Produto> produtoList = new ArrayList<>();
        produtoIDList.forEach(produtoDto -> {
            Optional<Produto> produtoOptional = produtoRepository.findById(produtoDto);
            produtoOptional.ifPresent(produtoList::add);
        });

        return produtoList;
    }


    public ResponseEntity<?> deleteProduto(UUID id, UUID produtoId) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if (purchaseOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Venda nao existe");
        }
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto nao existe");
        }

        purchaseOptional.get().getProducts().remove(produtoOptional.get());
        produtoRepository.deleteById(produtoId);
        if (produtoRepository.existsById(produtoId)) {
            return ResponseEntity.badRequest().body("Error");
        }

        return ResponseEntity.ok().body("Deletado");
    }

    public ResponseEntity<?> generateReport(UUID purchaseId) {
        try {
            Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
            if (purchaseOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Venda nao existe");
            }
            var purchase = purchaseOptional.get();

            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Relatório do pedido" + purchase.getId());
            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Modelo");
            headerRow.createCell(2).setCellValue("Tecido");
            headerRow.createCell(3).setCellValue("Tipo");
            headerRow.createCell(4).setCellValue("Quantidade de bolso");
            headerRow.createCell(5).setCellValue("Descrição bolsos");
            headerRow.createCell(6).setCellValue("Logo");
            headerRow.createCell(7).setCellValue("É silkado?");
            headerRow.createCell(9).setCellValue(purchase.getCustomer().getNome() + " " + purchase.getCustomer().getCpfCnpj());

            var products = purchase.getProducts();
            for (int i = 0; i < products.size(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                var product = products.get(i);
                dataRow.createCell(0).setCellValue(product.getId().toString());
                dataRow.createCell(1).setCellValue(product.getModelo().toString());
                dataRow.createCell(2).setCellValue(product.getTecido().toString());
                dataRow.createCell(3).setCellValue(product.getTipo().toString());
                dataRow.createCell(4).setCellValue(product.getQuantidadeBolso());
                dataRow.createCell(5).setCellValue(product.getDescricaoBolso());
                dataRow.createCell(6).setCellValue(product.getDescricaoLogo());
                dataRow.createCell(7).setCellValue(product.getSilkando() ? "Sim" : "Não");
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Relatório.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gerar relatório", e);
        }
    }

}