package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.model.ErrorResponse;
import com.example.demo.model.Expense;
import com.example.demo.respository.ExpenseRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@SpringBootApplication
public class DemoApplication {
	@Autowired
	ExpenseRespository expenseRespository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// method get
	@RequestMapping("/")
	public String hello() {
		return "hello world";
	}

	@GetMapping(value = "/expense/id/{id}")
	public ResponseEntity getExpenseById(@PathVariable String id) {
		Long mid = Long.parseLong(id);
		Optional<Expense> eOptional = expenseRespository.findById(mid);
		Expense expense;
		try {
			expense = eOptional.get();
			return ResponseEntity.status(HttpStatus.OK).body(expense);
		} catch (Exception e) {
			HttpStatus status = HttpStatus.NOT_FOUND;
			ErrorResponse errorResponse = new ErrorResponse(status, mid);
			return ResponseEntity.status(status).body(errorResponse);
		}
	}

	@GetMapping(value = "/expense/item/{item}")
	public ResponseEntity<List> getExpenseByItem(@PathVariable String item) {
		List<Expense> list = expenseRespository.findByItem(item);
		
		if (list.size() > 0) {
			return new ResponseEntity<>(list, HttpStatus.OK);	
		}
		System.out.println(list.toString());
		
		return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
		// return ResponseEntity.status(HttpStatus.OK).body(expense);
	}

	@PutMapping(value = "/expense/id/{id}")
	public ResponseEntity<Expense> updateExpense(@PathVariable String id, @Valid @RequestBody Expense expense) {
		Long mid = Long.parseLong(id);
		Optional<Expense> eOptional = expenseRespository.findById(mid);
		if (!eOptional.isPresent()) {
			return new ResponseEntity<Expense>(expense, HttpStatus.NOT_MODIFIED);
		}
		expense.setId(mid);
		expenseRespository.save(expense);
		return new ResponseEntity<Expense>(expense, HttpStatus.ACCEPTED);
	}


// 	public Optional<Customer> updateCustomer(Long id, Customer customer) {
// 		Optional<Customer> customerOptional = customerRepository.findById(id);
// 		if(!customerOptional.isPresent()) {
// 				return customerOptional;
// 		}
// 		customer.setId(id);
// 		return Optional.of(customerRepository.save(customer));
// }


	// method get
	// @RequestMapping("/expense/id")
	// public ResponseEntity getExpenseById(@RequestParam(required = false) String id) {
	// 	Long mid = Long.parseLong(id);
	// 	Optional<Expense> eOptional = expenseRespository.findById(mid);
	// 	Expense expense;
	// 	try {
	// 		expense = eOptional.get();
	// 		return ResponseEntity.status(HttpStatus.OK).body(expense);
	// 	} catch (Exception e) {
	// 		HttpStatus status = HttpStatus.NOT_FOUND;
	// 		ErrorResponse errorResponse = new ErrorResponse(status, mid);
	// 		// System.out.println(HttpStatus.NOT_FOUND.compareTo(1));
	// 		return ResponseEntity.status(status).body(errorResponse);
	// 	}
	// }

	// method post
	@PostMapping(
		value = "/expense", 
		consumes = { 
			MediaType.APPLICATION_JSON_VALUE, 
			MediaType.APPLICATION_XML_VALUE, 
			MediaType.MULTIPART_FORM_DATA_VALUE 
		}
	)
	public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
		expenseRespository.save(expense);
		return new ResponseEntity<>(expense, HttpStatus.OK);
	}

	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	public Iterable<Expense> getExpenses() {

		return expenseRespository.getAll();
	}

	// @Bean
	// public CommandLineRunner demo(ExpenseRespository respository) {
	// return (args) -> {
	// respository.save(new Expense("test0", 10020));
	// respository.save(new Expense("test1", 10400));
	// respository.save(new Expense("test2", 10001));
	// respository.save(new Expense("test3", 10400));
	// respository.save(new Expense("test4", 10050));
	// respository.save(new Expense("test5", 11000));

	// log.info("Expense found with findAll()");
	// log.info("----------------------------");
	// for (Expense expense: respository.findAll()) {
	// log.info(expense.toString());
	// }
	// log.info("");
	// };
	// }
}
