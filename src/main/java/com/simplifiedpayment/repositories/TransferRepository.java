package com.simplifiedpayment.repositories;

import com.simplifiedpayment.data.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
