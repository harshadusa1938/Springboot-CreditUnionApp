package com.creditunion.service;

import com.creditunion.model.Account;
import com.creditunion.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGetAccountById_Success() {
        Account account = new Account(1L, "John", "Doe", "123 Main St", "123456");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account foundAccount = accountService.getAccountById(1L);

        assertEquals(account.getId(), foundAccount.getId());
        assertEquals(account.getFirstName(), foundAccount.getFirstName());
        assertEquals(account.getLastName(), foundAccount.getLastName());
        assertEquals(account.getAddress(), foundAccount.getAddress());
        assertEquals(account.getAccountNumber(), foundAccount.getAccountNumber());
    }

    @Test
    void testGetAccountById_NotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.getAccountById(1L);
        });

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void testCreateAccount() {
        Account account = new Account(null, "John", "Doe", "123 Main St", "123456");
        Account savedAccount = new Account(1L, "John", "Doe", "123 Main St", "123456");
        when(accountRepository.save(account)).thenReturn(savedAccount);

        Account createdAccount = accountService.createAccount(account);

        assertEquals(savedAccount.getId(), createdAccount.getId());
        assertEquals(savedAccount.getFirstName(), createdAccount.getFirstName());
        assertEquals(savedAccount.getLastName(), createdAccount.getLastName());
        assertEquals(savedAccount.getAddress(), createdAccount.getAddress());
        assertEquals(savedAccount.getAccountNumber(), createdAccount.getAccountNumber());
    }

    @Test
    void testUpdateAccount_Success() {
        Account existingAccount = new Account(1L, "John", "Doe", "123 Main St", "123456");
        Account updatedDetails = new Account(null, "Jane", "Doe", "456 Elm St", "123456");
        Account updatedAccount = new Account(1L, "Jane", "Doe", "456 Elm St", "123456");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(existingAccount)).thenReturn(updatedAccount);

        Account result = accountService.updateAccount(1L, updatedDetails);

        assertEquals(updatedAccount.getId(), result.getId());
        assertEquals(updatedAccount.getFirstName(), result.getFirstName());
        assertEquals(updatedAccount.getLastName(), result.getLastName());
        assertEquals(updatedAccount.getAddress(), result.getAddress());
        assertEquals(updatedAccount.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    void testUpdateAccount_NotFound() {
        Account updatedDetails = new Account(null, "Jane", "Doe", "456 Elm St", "123456");
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            accountService.updateAccount(1L, updatedDetails);
        });

        assertEquals("Account not found", exception.getMessage());
    }
}
