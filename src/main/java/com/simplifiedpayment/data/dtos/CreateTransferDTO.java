package com.simplifiedpayment.data.dtos;

public record CreateTransferDTO(Long fromId, Long toId, Double value) {
}
