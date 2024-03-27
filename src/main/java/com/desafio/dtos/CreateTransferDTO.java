package com.desafio.dtos;

public record CreateTransferDTO(Long fromId, Long toId, Double value) {
}
