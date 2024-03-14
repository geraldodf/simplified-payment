package com.desafio.resources;

import com.desafio.dtos.CreateTransferDTO;
import com.desafio.models.Transfer;
import com.desafio.services.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/transfers")
public class TransferResource {

    private final TransferService transferService;

    public TransferResource(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping()
    public ResponseEntity<ArrayList<Transfer>> getAll() {
        return this.transferService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transfer> getOneById(@PathVariable("id") Long id) {
        return this.transferService.getOneById(id);
    }

    @PostMapping
    public ResponseEntity<Transfer> create(@RequestBody CreateTransferDTO transferDto) {
        return this.transferService.create(transferDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody Transfer transfer) {
        return this.transferService.update(id, transfer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        return this.transferService.delete(id);
    }
}
