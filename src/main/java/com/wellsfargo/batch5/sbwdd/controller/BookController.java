package com.wellsfargo.batch5.sbwdd.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.wellsfargo.batch5.sbwdd.exception.LibraryException;
import com.wellsfargo.batch5.sbwdd.model.BookModel;
import com.wellsfargo.batch5.sbwdd.model.GenreModel;
import com.wellsfargo.batch5.sbwdd.service.BookService;
import com.wellsfargo.batch5.sbwdd.service.GenreService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bService;
	
	@Autowired
	private GenreService gService;
	
	@GetMapping
	public ModelAndView booksAction()  throws LibraryException {
		return new ModelAndView("books/books-page","books",bService.getAll());
		
	}
	
	
	@GetMapping("/delete")
	public String deleteAction(@RequestParam("bid") int bookCode) throws LibraryException{
		
		bService.delete(bookCode);
		return "redirect:/books";
	}
	
	
	@PostMapping("/add")
	public ModelAndView addBookAction(
			@ModelAttribute("book") @Valid BookModel book,BindingResult result) throws LibraryException {
		ModelAndView mv=null;
		
		if(result.hasErrors()) {
			mv = new ModelAndView("books/book-form-page","book",book);	
			mv.addObject("isNew",true);
		}else {
			bService.add(book);
			mv = new ModelAndView("redirect:/books");
		}
		return mv;
	}

	
	@PostMapping("/update")
	public ModelAndView updateBookAction(
			@ModelAttribute("book") @Valid BookModel book,BindingResult result) throws LibraryException {
		ModelAndView mv=null;
		
		if(result.hasErrors()) {
			mv = new ModelAndView("books/book-form-page","book",book);	
			mv.addObject("isNew",false);
		}else {
			bService.update(book);
			mv = new ModelAndView("redirect:/books");
		}
		return mv;
	}
	
	
	@GetMapping("/new")
	public ModelAndView newBookAction(){
		ModelAndView mv= new ModelAndView("books/book-form-page","book",new BookModel());
		mv.addObject("isNew",true);
		return mv;
	}
	
	
	@GetMapping("/edit")
	public ModelAndView editBookAction(@RequestParam("bid") int bookCode) throws LibraryException{
		BookModel book=bService.get(bookCode);
		ModelAndView mv= new ModelAndView("books/book-form-page","book",book);
		mv.addObject("isNew",false);
		return mv;
	}
	
	@ModelAttribute("genres")
	public List<GenreModel> allGenres() throws LibraryException{
		return gService.getAll();
		
	}
	
	
	
	
	
	
}
