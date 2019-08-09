package com.madbarsoft.book;

import java.util.List;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

    public List<BookEntity> list(){
        return repository.list();
    }
    

    public List<BookEntity> removeBooklist(){
        return repository.removeBooklist();
    }

    public void saveOrUpdate(BookEntity obj){
        try{
            if(obj.getId()==null){
                repository.save(obj);
                return;
            }
            repository.update(obj);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new PersistentObjectException(ex.getCause().toString());
        }
    }

    public BookEntity findById(Long id) {
        return repository.findById(id);
    }
    
    public BookEntity findByIdAllItem(Long id) {
        return repository.findByIdAllItem(id);
    }
    
    public Boolean removeById(Long id){
        if(findById(id)!=null){
            return repository.remove(findById(id));
        }
        return false;
    }
    
    public Boolean deleteById(Long id){
        if(findByIdAllItem(id)!=null){
            return repository.delete(findByIdAllItem(id));
        }
        return false;
    }
	

}
