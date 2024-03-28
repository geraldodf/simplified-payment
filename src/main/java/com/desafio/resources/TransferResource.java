package com.desafio.resources;

import com.desafio.data.dtos.CreateTransferDTO;
import com.desafio.data.models.Transfer;
import com.desafio.services.TransferService;
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

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody Transfer transfer) {
        this.transferService.update(id, transfer);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        this.transferService.delete(id);
    }
}
