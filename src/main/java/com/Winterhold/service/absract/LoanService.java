package com.Winterhold.service.absract;

import com.Winterhold.dto.AllDataDTO;
import com.Winterhold.dto.DropdownListDTO;
import com.Winterhold.dto.JsonResultDTO;
import com.Winterhold.dto.author.CreateUpdateAuthorDTO;
import com.Winterhold.dto.customer.CustomerDTO;
import com.Winterhold.dto.loan.CreateUpdateLoanDTO;
import com.Winterhold.dto.loan.LoanDTO;

import java.util.List;

public interface LoanService {
    public AllDataDTO getAllData(Integer page, String bookName, String customerName, String dueDate);
    public List<LoanDTO> getList(Integer page, String bookName, String customerName, String dueDate);
    public Long getCount(String bookName, String customerName, String dueDate);
    public Long getPageSize(String bookName, String customerName, String dueDate);

    public JsonResultDTO save(CreateUpdateLoanDTO dto);
    public List<DropdownListDTO> getCustomerDDL();
    public List<DropdownListDTO> getBookDDL();

    public JsonResultDTO setReturnDate(Long id);
    public CreateUpdateLoanDTO getLoanDTO(Long id);

    public JsonResultDTO saveRest(CreateUpdateLoanDTO dto);
    public JsonResultDTO updateRest(CreateUpdateLoanDTO dto);
    public JsonResultDTO updateNote(Long id,String note);
}
