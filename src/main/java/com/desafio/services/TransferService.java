package com.desafio.services;

import com.desafio.data.dtos.CreateTransferDTO;
import com.desafio.data.models.Transfer;
import com.desafio.data.models.User;
import com.desafio.repositories.TransferRepository;
import com.desafio.notification.SendEmailNotification;
import com.desafio.utils.ObterMock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<Transfer> getAll() {
        return this.transferRepository.findAll();
    }


    public Transfer getOneById(Long id) {
        Optional<Transfer> optionalTransfer = this.transferRepository.findById(id);
        boolean isPresent = optionalTransfer.isPresent();
        Transfer transfer = isPresent ? optionalTransfer.get() : null;
        if (isPresent) {
            return transfer;
        } else {
            return null;
        }
    }

    @Transactional
    public Transfer create(CreateTransferDTO transferDto) {
        try {
            User userTo = this.userService.getOneById(transferDto.toId());
            User userFrom = this.userService.getOneById(transferDto.fromId());

            if ("lojista".equalsIgnoreCase(userFrom.getType())) {
                throw new IllegalArgumentException("Um lojista não é capaz de fazer transferencias.");
            }

            if (transferDto.value() > userFrom.getWallet()) {
                throw new IllegalArgumentException("Você não tem esse dinheiro todo não, amigo.");
            }

            userFrom.setWallet(userFrom.getWallet() - transferDto.value());
            userService.updateWallet(userFrom);

            userTo.setWallet(userTo.getWallet() + transferDto.value());
            userService.updateWallet(userTo);

            Transfer transfer = new Transfer();
            transfer.setTo(userTo);
            transfer.setFrom(userFrom);
            transfer.setValue(transferDto.value());

            final String URL_MOCK_AUTHORIZATION = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";
            final String URL_MOCK_NOTIFICATION = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

            boolean autorizado = "autorizado".equalsIgnoreCase(ObterMock.obterMensagemDoMock(URL_MOCK_AUTHORIZATION).getMessage());
            if (!autorizado) throw new IllegalArgumentException("Não está autorizado!");
            var emailNotificationSender = new SendEmailNotification();
            emailNotificationSender.sendNotification(URL_MOCK_NOTIFICATION, userFrom, "Dinheiro enviado");
            emailNotificationSender.sendNotification(URL_MOCK_NOTIFICATION, userTo, "Dinheiro recebido");

            return this.transferRepository.save(transfer);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void delete(Long id) {
        Transfer transferToDelete = Objects.requireNonNull(this.getOneById(id));
        this.transferRepository.delete(transferToDelete);
    }
}
