package com.escahe.bank.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.escahe.bank.dto.response.MovementResponse;
import com.escahe.bank.exception.report.ReportDBException;
import com.escahe.bank.exception.report.ReportDBException.ReportDBExceptionCause;
import com.escahe.bank.model.Movement;
import com.escahe.bank.service.IMovementsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportController {
    private final IMovementsService movementsService;

    @GetMapping("/{clientId}")
    public ResponseEntity<List<MovementResponse>> getMovementReportByDateRange(
        @PathVariable Long clientId,
        @RequestParam(name = "desde", defaultValue = "") String startDateStr,
        @RequestParam(name = "hasta", defaultValue = "") String endDateStr
    ) {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = startDateStr.isEmpty()? LocalDate.now(): LocalDate.parse(startDateStr);
            endDate = endDateStr.isEmpty()? LocalDate.now(): LocalDate.parse(endDateStr);
        } catch (Exception e) {
            throw new ReportDBException(ReportDBExceptionCause.INVALID_DATE_FORMAT);
        }
        if(endDate.compareTo(startDate) < 0) 
            throw new ReportDBException(ReportDBExceptionCause.INVALID_DATE_RANGE);

        List<Movement> movements = movementsService.getAllMovementsByClientIdBetweenDates(clientId, startDate, endDate);
        System.out.println(movements);
        List<MovementResponse> movementResponses = movements.stream().map(MovementResponse::new).toList();
        return ResponseEntity.ok(movementResponses);
    }
}
