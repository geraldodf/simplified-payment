package com.desafio.services;

import com.desafio.dtos.CreateTransferDTO;
import com.desafio.models.Transfer;
import com.desafio.models.User;
import com.desafio.repositories.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final UserService userService;

    public TransferService(TransferRepository transferRepository, UserService userService) {
        this.transferRepository = transferRepository;
        this.userService = userService;
    }

    public ArrayList<Transfer> getAll() {
        try {
            return (ArrayList<Transfer>) this.transferRepository.findAll();
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar transferencias");
        }

    }

    public Transfer getOneById(Long id) {
        try {
            return this.transferRepository.findById(id).get();
        } catch (Exception e) {
            throw new IllegalArgumentException("Transferencia inexistente");
        }
    }

    public Transfer create(CreateTransferDTO transferDto) {
        try {
            User userTo = this.userService.getOneById(transferDto.getToId());
            User userFrom = this.userService.getOneById(transferDto.getToId());
            Transfer transfer = new Transfer();
            transfer.setTo(userTo);
            transfer.setFrom(userFrom);
            transfer.setValue(transferDto.getValue());
            return this.transferRepository.save(transfer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problema ao criar uma transferencia");
        }
    }

    public Transfer update(Long id, Transfer newTransfer) {
        Transfer transfer = this.getOneById(id);
        if (newTransfer != null && newTransfer.getValue() != null && newTransfer.getValue() >= 0) {
            transfer.setValue(newTransfer.getValue());
        } else {
            throw new IllegalArgumentException("Nome nulo ou vazio!");
        }
        return this.transferRepository.save(transfer);
    }

    public void delete(Long id) {
        try {
            this.transferRepository.delete(this.getOneById(id));
        } catch (Exception e) {
            throw new IllegalArgumentException("Transferencia inexistente");
        }
    }
}
