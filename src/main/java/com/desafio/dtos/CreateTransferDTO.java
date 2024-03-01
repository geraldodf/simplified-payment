package com.desafio.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransferDTO {
    Long fromId;
    Long ToId;
    Double value;
}
