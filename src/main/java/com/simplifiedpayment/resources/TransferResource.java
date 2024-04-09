package com.simplifiedpayment.resources;

import com.simplifiedpayment.data.dtos.CreateTransferDTO;
import com.simplifiedpayment.data.models.Transfer;
import com.simplifiedpayment.services.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferResource {

    private final TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public List<Transfer> getAll() {
        return this.transferService.getAll();
    }

    @GetMapping("/{id}")
    public Transfer getOneById(@PathVariable("id") Long id) {
        return this.transferService.getOneById(id);
    }

    @PostMapping
    public Transfer create(@RequestBody CreateTransferDTO transferDto) {
        return this.transferService.create(transferDto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.transferService.delete(id);
    }
}
