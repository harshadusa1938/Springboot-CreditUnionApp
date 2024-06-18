package com.creditunion.controller;

import java.util.List;

import com.creditunion.model.Account;
import com.creditunion.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@GetMapping
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}

	@GetMapping("/search")
	public ResponseEntity<List<Account>> searchAccounts(@RequestParam String query) {
		List<Account> accounts = accountService.searchAccounts(query);
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAccountById(@PathVariable Long id) {
		Account account = accountService.getAccountById(id);
		if (account == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		}
		return ResponseEntity.ok(account);
	}

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody Account account) {
		Account newAccount = accountService.createAccount(account);
		return ResponseEntity.ok(newAccount);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
		Account updatedAccount = accountService.updateAccount(id, accountDetails);
		return ResponseEntity.ok(updatedAccount);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}
}