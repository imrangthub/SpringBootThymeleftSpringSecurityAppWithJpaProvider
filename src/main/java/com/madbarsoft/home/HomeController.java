package com.madbarsoft.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.madbarsoft.book.BookEntity;
import com.madbarsoft.book.BookService;
import com.madbarsoft.role.RoleEntity;
import com.madbarsoft.user.UserEntity;
import com.madbarsoft.user.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	


	
	@RequestMapping(value="/home")
	public String home(Model model) {
		
		List<RoleEntity> rolesList = new ArrayList<RoleEntity>(); 
		
//		UserEntity user = userService.findByUserName("hossain");
//		
//		rolesList = user.getRoles();
//		
//		
//		System.out.println("User: "+user+" Roles:"+rolesList);
		
		List<BookEntity> bookList = bookService.list();
		model.addAttribute("bookList", bookList);
		return "home";
	}
	

}
