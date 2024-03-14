package com.desafio.services;

import com.desafio.dtos.CreateTransferDTO;
import com.desafio.models.Transfer;
import com.desafio.models.User;
import com.desafio.repositories.TransferRepository;
import com.desafio.utils.ObterMock;
import com.desafio.utils.SendEmailNotification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final UserService userService;

    public TransferService(TransferRepository transferRepository, UserService userService) {
        this.transferRepository = transferRepository;
        this.userService = userService;
    }

    public ResponseEntity<ArrayList<Transfer>> getAll() {
        try {
            ArrayList<Transfer> transfers = (ArrayList<Transfer>) this.transferRepository.findAll();
            if (!transfers.isEmpty()) {
                return ResponseEntity.ok(transfers);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Transfer> getOneById(Long id) {
        try {
            Optional<Transfer> optionalTransfer = this.transferRepository.findById(id);
            boolean isPresent = optionalTransfer.isPresent();
            Transfer transfer = isPresent ? optionalTransfer.get() : null;
            if (isPresent) {
                return ResponseEntity.ok(transfer);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Transactional
    public ResponseEntity<Transfer> create(CreateTransferDTO transferDto) {
        try {

            User userTo = this.userService.getOneById(transferDto.getToId()).getBody();
            User userFrom = this.userService.getOneById(transferDto.getFromId()).getBody();

            if ("lojista".equalsIgnoreCase(userFrom.getType())) {
                throw new IllegalArgumentException("Um lojista não é capaz de fazer transferencias.");
            }

            if (transferDto.getValue() > userFrom.getWallet()) {
                throw new IllegalArgumentException("Você não tem esse dinheiro todo não, amigo.");
            }

            userFrom.setWallet(userFrom.getWallet() - transferDto.getValue());
            userService.updateWallet(userFrom);

            userTo.setWallet(userTo.getWallet() + transferDto.getValue());
            userService.updateWallet(userTo);

            Transfer transfer = new Transfer();
            transfer.setTo(userTo);
            transfer.setFrom(userFrom);
            transfer.setValue(transferDto.getValue());

            final String URL_MOCK_AUTHORIZATION = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";
            final String URL_MOCK_NOTIFICATION = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

            boolean autorizado = "autorizado".equalsIgnoreCase(ObterMock.obterMensagemDoMock(URL_MOCK_AUTHORIZATION).getMessage());
            if (!autorizado) throw new IllegalArgumentException("Não está autorizado!!");

            SendEmailNotification.sendEmailNotification(URL_MOCK_NOTIFICATION, userFrom, "Dinheiro enviado com sucesso!");
            SendEmailNotification.sendEmailNotification(URL_MOCK_NOTIFICATION, userTo, "Dinheiro recebido");

            Transfer savedTransfer = this.transferRepository.save(transfer);
            return ResponseEntity.status(201).body(savedTransfer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Void> update(Long id, Transfer newTransfer) {
        Transfer transfer = this.getOneById(id).getBody();
        if (newTransfer != null && newTransfer.getValue() != null && newTransfer.getValue() >= 0 && transfer != null) {
            transfer.setValue(newTransfer.getValue());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<Void> delete(Long id) {
        try {
            Transfer transferToDelete = Objects.requireNonNull(this.getOneById(id).getBody());
            this.transferRepository.delete(transferToDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
