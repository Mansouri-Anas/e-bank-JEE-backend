package org.sid.digitalbankingbackend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.digitalbankingbackend.dtos.CustomerDTO;
import org.sid.digitalbankingbackend.entities.Customer;
import org.sid.digitalbankingbackend.exceptions.CustomerNotFoundException;
import org.sid.digitalbankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor //Injection des dependencs
@Slf4j
@CrossOrigin("*")
//@RequestMapping("/customers")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    private List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")
    private CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    private CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)  {
        bankAccountService.saveCustomer(customerDTO);
        return customerDTO;
    }

    @PutMapping("/customers/{customerid}")
    private CustomerDTO updateCustomer(@PathVariable Long customerid ,@RequestBody CustomerDTO customerDTO) {
            customerDTO.setId(customerid);
           return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{customerid}")
    private void deleteCustomer(@PathVariable Long customerid) {
         bankAccountService.deleteCustomer(customerid);
    }

}
