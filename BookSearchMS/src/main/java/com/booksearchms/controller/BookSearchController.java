package com.booksearchms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.booksearchms.info.BookInfo;
import com.booksearchms.info.BookPriceInfo;


@RestController
public class BookSearchController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/mybook/{bookId}") 
	public BookInfo getBookById(@PathVariable Integer bookId) {
		System.out.println("---BookSearchController -- getBookById()-----");
		BookInfo bookInfo = new BookInfo(bookId, "Master Spring Boot 2", "Srinivas", "JLC", "Java");
		
		//3.Invoking BookPrice with RestTempalate
		String endpoint = "http://MyBookPriceMS/bookPrice/"+bookId; 
		
		ResponseEntity<BookPriceInfo> respEntity=restTemplate.getForEntity(endpoint,BookPriceInfo.class); 

		BookPriceInfo bookPriceInfo = respEntity.getBody();
		bookInfo.setPrice(bookPriceInfo.getPrice());
		bookInfo.setOffer(bookPriceInfo.getOffer());
		bookInfo.setServerPort(bookPriceInfo.getServerPort());
		
		System.out.println("---BookController --- getBookById()-- "+bookId+" --Port--"+bookInfo.getServerPort());
		
		return bookInfo; 
	}
}
