package com.Winterhold.service.implementation;

import com.Winterhold.dao.AuthorRepository;
import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.author.AuthorDTO;
import com.Winterhold.dto.author.CreateUpdateAuthorDTO;
import com.Winterhold.dto.book.BookDTO;
import com.Winterhold.entity.Author;
import com.Winterhold.service.absract.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorImplementation implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public AllDataDTO getAllData(Integer page, String searchName) {
        var data = getList(page,searchName);
        List<Object> data2 = new ArrayList<>();
        data2.addAll(data);
        var currentPage = (long)page;
        var totalPage = getPageSize(searchName);
        var authorData = new AllDataDTO(data2,currentPage,totalPage,searchName,null,null);
        return authorData;
    }

    @Override
    public List<AuthorDTO> getList(Integer page, String searchName) {
        Pageable pagging = PageRequest.of(page-1,10, Sort.by("id"));
        return authorRepository.getList(pagging,searchName);
    }

    @Override
    public Long getCount(String searchName) {
        return authorRepository.getCount(searchName);
    }

    @Override
    public Long getPageSize(String searchName) {
        var totalData = (double)getCount(searchName);
        var totalPage = (long)Math.ceil(totalData/10);
        return totalPage ;
    }

    @Override
    public AuthorDTO getDataAuthor(Long id) {
        return authorRepository.getAuthor(id);
    }

    @Override
    public List<BookDTO> getListBookByIdAuthor(Long id) {
        return authorRepository.getListBookByIdAuthor(id);
    }

    @Override
    public void save(CreateUpdateAuthorDTO dto) {
        Author author = new Author(dto.getId(),dto.getTitle(), dto.getFirstName(), dto.getLastName(),dto.getBirthDate(),dto.getDeceasedDate(),dto.getEducation(), dto.getSummary());
        author.setIsRemove(false);
        authorRepository.save(author);
    }

    @Override
    public CreateUpdateAuthorDTO getAuthorDTO(Long id) {
        var entity = authorRepository.getReferenceById(id);
        CreateUpdateAuthorDTO dto = new CreateUpdateAuthorDTO(entity.getId(), entity.getTitle(), entity.getFirstName(), entity.getLastName(), entity.getBirthDate(),entity.getDeceasedDate(),entity.getEducation(),entity.getSummary());
        return dto;
    }

    @Override
    public JsonResultDTO delete(Long id) {
        var author = authorRepository.getReferenceById(id);
        try{
            if (author != null){
                author.setIsRemove(true);
                authorRepository.save(author);
                return new JsonResultDTO(true,String.format("Author %d telah di delete",id));
            }else {
                return new JsonResultDTO(false,String.format("Auhtor %d tidak ditemukan",author));
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Internal Server Error");
        }
    }

    @Override
    public JsonResultDTO saveRest(CreateUpdateAuthorDTO dto) {
        try {
        Author author = new Author(dto.getId(),dto.getTitle(), dto.getFirstName(), dto.getLastName(),dto.getBirthDate(),dto.getDeceasedDate(),dto.getEducation(), dto.getSummary());
        author.setIsRemove(false);
        author = authorRepository.save(author);
        return new JsonResultDTO("Berhasil Insert",true,author);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Insert");
        }
    }

    @Override
    public JsonResultDTO updateRest(CreateUpdateAuthorDTO dto) {
        try {
            Author author = authorRepository.getReferenceById(dto.getId());
            if (author !=null){
                author.setFirstName(dto.getFirstName());
                author.setTitle(dto.getTitle());
                author.setLastName(dto.getLastName());
                author.setBirthDate(dto.getBirthDate());
                author.setDeceasedDate(dto.getDeceasedDate());
                author.setEducation(dto.getEducation());
                author.setSummary(dto.getSummary());
            author = authorRepository.save(author);
            return new JsonResultDTO("Berhasil Update data",true,author);
            }else {
                return new JsonResultDTO(false,"Author tidak di temukan");
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Update");
        }
    }

    @Override
    public JsonResultDTO updateSummary(Long id,String summary) {
        var data = authorRepository.getReferenceById(id);
        if (data == null){
            return new JsonResultDTO(false,"author tidak ditemukan");
        }else {
            data.setSummary(summary);
            data = authorRepository.save(data);
            return new JsonResultDTO("Berhasil update summary",true,data);
        }
    }

    @Override
    public JsonResultDTO deleteRest(Long id) {
        var data = authorRepository.getReferenceById(id);
        if (data == null){
            return new JsonResultDTO(false,"author tidak ditemukan");
        }else {
            try {
                var jumlah = authorRepository.getCountOnBook(id);
                if (jumlah >0){
                    return new JsonResultDTO(false,String.format("Author tidak bisa dipake karena masih ada %d yang masih di jual",jumlah));
                }else {
                    authorRepository.delete(data);
                    return new JsonResultDTO(true,"berhasil delete");
                }
            }catch (Exception ex){
                    return new JsonResultDTO(false,"gagal melakukan delete");
            }
        }
    }
}
