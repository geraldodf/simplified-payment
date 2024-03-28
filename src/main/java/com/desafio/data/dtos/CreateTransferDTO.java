package com.desafio.data.dtos;

public record CreateTransferDTO(Long fromId, Long toId, Double value) {
}
