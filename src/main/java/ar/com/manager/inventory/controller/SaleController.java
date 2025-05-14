package ar.com.manager.inventory.controller;

import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.exception.NotFoundException;
import ar.com.manager.inventory.exception.ValidationException;
import ar.com.manager.inventory.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.com.manager.inventory.controller.util.MessageConstants.*;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {

    SaleService saleService;
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addSale(@RequestBody SaleDto saleDto) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SALE, saleService.addSale(saleDto));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ValidationException | NotFoundException | IllegalArgumentException e){
            response.put(MESSAGE, SALE_SAVE_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteSale(@PathVariable Integer id) {
        try {
            saleService.deleteSale(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(MESSAGE, SALE_DELETED_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSale(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put(SALE, saleService.getSaleById(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e){
            response.put(MESSAGE, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSales() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<SaleDto> sales = saleService.getAllSales();
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /*
    @GetMapping("/between")
    public ResponseEntity<Map<String, Object>> getSalesBetweenDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateBetween(startDate,endDate);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }*/

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Map<String, Object>> getSalesByClientId(@PathVariable Integer clientId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByClientId(clientId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getSalesByUserId(@PathVariable Integer userId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByUserId(userId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e){
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/month")
    public ResponseEntity<Map<String, Object>> getSalesByMonth(@RequestParam int month){
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateMonth(month);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/year")
    public ResponseEntity<Map<String, Object>> getSalesByYear(@RequestParam int year) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findBySaleDateYear(year);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @GetMapping("/product/{productId}")
    public ResponseEntity<Map<String, Object>> getSalesByProduct(@PathVariable Integer productId) {
        Map<String, Object> response = new HashMap<>();
        try{
            List<SaleDto> sales = saleService.findByProductId(productId);
            response.put(SALES, sales);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            response.put(MESSAGE, INTERNAL_ERROR);
            response.put(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
