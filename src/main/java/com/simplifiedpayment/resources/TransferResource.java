package com.simplifiedpayment.resources;

import com.simplifiedpayment.data.dtos.CreateTransferDTO;
import com.simplifiedpayment.data.models.Transfer;
import com.simplifiedpayment.services.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferResource {

    private final TransferService transferService;
    private static final Logger logger = LoggerFactory.getLogger(TransferResource.class);

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public List<Transfer> getAll() {
        logger.info("Requisição para buscar todas as transferências.");
        return this.transferService.getAll();
    }

    @GetMapping("/{id}")
    public Transfer getOneById(@PathVariable("id") Long id) {
        logger.info("Requisição para buscar transferência por ID: {}", id);
        return this.transferService.getOneById(id);
    }

    @PostMapping
    public Transfer create(@RequestBody CreateTransferDTO transferDto) {
        logger.info("Requisição para criar uma nova transferência.");
        return this.transferService.create(transferDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        logger.info("Requisição para deletar transferência com ID: {}", id);
        this.transferService.delete(id);
    }
}
