package com.Winterhold.service.implementation;

import com.Winterhold.dao.BookRepository;
import com.Winterhold.dao.LoanRepository;
import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.loan.CreateUpdateLoanDTO;
import com.Winterhold.dto.loan.LoanDTO;
import com.Winterhold.entity.Author;
import com.Winterhold.entity.Book;
import com.Winterhold.entity.Loan;
import com.Winterhold.service.absract.LoanService;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.Temporal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanImplementation implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public AllDataDTO getAllData(Integer page, String bookName, String customerName, String dueDate) {
        var data = getList(page,bookName,customerName,dueDate);
        List<Object> data2 = new ArrayList<>();
        data2.addAll(data);
        var currentPage = (long)page;
        var totalPage = getPageSize(bookName, customerName, dueDate);
        var customerData = new AllDataDTO(data2,currentPage,totalPage,bookName,customerName,dueDate);
        return customerData;
    }

    @Override
    public List<LoanDTO> getList(Integer page, String bookName, String customerName, String dueDate) {
        Pageable pagging = PageRequest.of(page-1,10, Sort.by("id"));
        if (dueDate.equalsIgnoreCase("off")){
            return loanRepository.getList(pagging,bookName,customerName);
        }else {
            return loanRepository.getListByDueDate(pagging,bookName,customerName);
        }
    }

    @Override
    public Long getCount(String bookName, String customerName, String dueDate) {
        if (dueDate.equalsIgnoreCase("off")){
        return loanRepository.getCount(bookName, customerName);
        }else {
            return loanRepository.getCountByDueDate(bookName,customerName);
        }
    }

    @Override
    public Long getPageSize(String bookName, String customerName, String dueDate) {
        var totalData = (double)getCount(bookName, customerName, dueDate);
        var totalPage = (long)Math.ceil(totalData/10);
        return totalPage ;
    }

    @Override
    @Transactional
    public JsonResultDTO save(CreateUpdateLoanDTO dto) {
        try {
            if (dto.getId() == null || dto.getId() == 0) {
                Loan loan = new Loan(dto.getId(), dto.getCustomerId(), dto.getBookCode(), dto.getLoanDate(), dto.getLoanDate().plusDays(5), null, dto.getNote());
                Book book = bookRepository.getReferenceById(dto.getBookCode());
                var jumlah = loanRepository.countLoanByBookCode(dto.getBookCode());
                if (jumlah>0){
                    List<LocalDate> listLoanLocalDate = loanRepository.getListLoanByBookCode(dto.getBookCode());
                    for (LocalDate loanDate:listLoanLocalDate) {
                        var temp = ChronoUnit.DAYS.between(dto.getLoanDate(),loanDate);
                        if ((ChronoUnit.DAYS.between(dto.getLoanDate(),loanDate)<=5 && dto.getLoanDate().isBefore(loanDate))||
                                (ChronoUnit.DAYS.between(loanDate,dto.getLoanDate())<=5 && dto.getLoanDate().isAfter(loanDate))){
                            throw new Exception("Gak bisa pinjam");
                        }
                    }
                    loanRepository.save(loan);
                    return new JsonResultDTO(true,"berhasil melakukan pembokingan");
                }else {
                    if (dto.getLoanDate().isEqual(LocalDate.now())) {
                        book.setIsBorrowed(true);
                        bookRepository.save(book);
                        loanRepository.save(loan);
                        return new JsonResultDTO(true,"berhasil melakukan peminjaman");
                    }else {
                        return new JsonResultDTO(true,"berhasil melakukan pembokingan");
                    }
                }
            } else {
                Loan loan = loanRepository.getReferenceById(dto.getId());
                if (loan != null) {
                    loan.setBookCode(dto.getBookCode());
                    loan.setCustomerNumber(dto.getCustomerId());
                    loan.setLoanDate(dto.getLoanDate());
                    loan.setNote(dto.getNote());
                    loanRepository.save(loan);
                    return new JsonResultDTO(true,"Update Data berhasil");
                }else {
                    return new JsonResultDTO(false,"Data loan tidak ditemukan");
                }
            }
            }catch(Exception ex){
               return new JsonResultDTO(false,ex.getMessage());
            }
        }

    @Override
    public List<DropdownListDTO> getCustomerDDL() {
        return loanRepository.getCustomerDDL();
    }

    @Override
    public List<DropdownListDTO> getBookDDL() {
        return loanRepository.getBookDDL();
    }

    @Override
    @Transactional
    public JsonResultDTO setReturnDate(Long id) {
        var loan = loanRepository.getReferenceById(id);
        try{
            loan.setReturnDate(LocalDate.now());
            loanRepository.save(loan);
            Book book = bookRepository.getReferenceById(loan.getBookCode());
            book.setIsBorrowed(false);
            bookRepository.save(book);
            return new JsonResultDTO(true,String.format("Request Id %d telah di return",id));
        }catch (Exception ex){
            return new JsonResultDTO(false,ex.getMessage());
        }
    }

    @Override
    public CreateUpdateLoanDTO getLoanDTO(Long id) {
        var data = loanRepository.getReferenceById(id);
        CreateUpdateLoanDTO dto = new CreateUpdateLoanDTO(data.getId(), data.getCustomerNumber(), data.getBookCode(), data.getLoanDate(), data.getNote());
        return dto;
    }

    @Override
    @Transactional
    public JsonResultDTO saveRest(CreateUpdateLoanDTO dto) {
        try {
            Loan loan = new Loan(dto.getId(), dto.getCustomerId(),dto.getBookCode(),dto.getLoanDate(),dto.getLoanDate().plusDays(5),null, dto.getNote());
            loan = loanRepository.save(loan);
            Book book= bookRepository.getReferenceById(dto.getBookCode());
            book.setIsBorrowed(true);
            bookRepository.save(book);
            return new JsonResultDTO("Berhasil Insert",true,loan);
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Insert");
        }
    }

    @Override
    public JsonResultDTO updateRest(CreateUpdateLoanDTO dto) {
        try {
            Loan loan = loanRepository.getReferenceById(dto.getId());
            if (loan !=null){
                loan.setBookCode(dto.getBookCode());
                loan.setCustomerNumber(dto.getCustomerId());
                loan.setLoanDate(dto.getLoanDate());
                loan.setNote(dto.getNote());
                loan = loanRepository.save(loan);
                return new JsonResultDTO("Berhasil Update data",true,loan);
            }else {
                return new JsonResultDTO(false,"Author tidak di temukan");
            }
        }catch (Exception ex){
            return new JsonResultDTO(false,"Gagal melakukan Update");
        }
    }

    @Override
    public JsonResultDTO updateNote(Long id, String note) {
        var data = loanRepository.getReferenceById(id);
        if (data == null){
            return new JsonResultDTO(false,"Loan tidak ditemukan");
        }else {
            data.setNote(note);
            data = loanRepository.save(data);
            return new JsonResultDTO("Berhasil update summary",true,data);
        }
    }
}
