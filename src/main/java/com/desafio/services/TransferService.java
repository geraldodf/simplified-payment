package com.desafio.services;

import com.desafio.dtos.CreateTransferDTO;
import com.desafio.models.Transfer;
import com.desafio.models.User;
import com.desafio.repositories.TransferRepository;
import com.desafio.utils.ObterMock;
import com.desafio.utils.SendEmailNotification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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

    @Transactional
    public Transfer create(CreateTransferDTO transferDto) {
        try {

            User userTo = this.userService.getOneById(transferDto.getToId());
            User userFrom = this.userService.getOneById(transferDto.getFromId());

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

            return this.transferRepository.save(transfer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problema ao criar uma transferencia: " + e);
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
