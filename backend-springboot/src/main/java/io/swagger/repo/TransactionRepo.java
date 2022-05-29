package io.swagger.repo;

import io.swagger.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {

    Transaction findTransactionByFrom(String from);

    Transaction findTransactionByTo(String to);

    Transaction findTransactionById(UUID id);
}
