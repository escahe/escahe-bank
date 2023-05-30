package com.escahe.bank.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escahe.bank.dto.request.MovementRequest;
import com.escahe.bank.dto.response.MovementResponse;
import com.escahe.bank.model.Movement;
import com.escahe.bank.service.IMovementsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementController {
    
    private final IMovementsService movementsService;

    @PostMapping("/deposito")
    public ResponseEntity<MovementResponse> deposit(@RequestBody MovementRequest movementRequest) {
        Movement movement = movementsService.deposit(movementRequest.getAccountNumber(), movementRequest.getAmount());
        MovementResponse movementResponse = new MovementResponse(movement);
        return ResponseEntity.created(URI.create("/movimientos/"+movement.getId())).body(movementResponse);
    }

    @PostMapping("/retiro")
    public ResponseEntity<MovementResponse> withdrawal(@RequestBody MovementRequest movementRequest) {
        Movement movement = movementsService.withdraw(movementRequest.getAccountNumber(), movementRequest.getAmount());
        MovementResponse movementResponse = new MovementResponse(movement);
        return ResponseEntity.created(URI.create("/movimientos/"+movement.getId())).body(movementResponse);
    }
}
