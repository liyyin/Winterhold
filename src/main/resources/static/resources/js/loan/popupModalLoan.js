const btnAdd = document.querySelector('.btn-add');
const btnClose = document.querySelectorAll('.btn-back');
const btnUpdate = document.querySelectorAll('.btn-update');
const btnReturn = document.querySelectorAll('.btn-return');
const btnDetail = document.querySelectorAll('.btn-detail');
let token = document.querySelector('#_csrfToken').content;


function apiController(){
console.log(document.querySelector('.full-name').textContent);
$.ajax({
             type: "GET",
             url: "http://localhost:7077/Winterhold/api/customer",
             cache:false,
             beforeSend:function(xhr){
                                  xhr.setRequestHeader("Username",
                                  document.querySelector('.full-name').textContent)
                             },
             success:function(response){
             console.log(response);
             },
             error:function(){},
             fail:function(){}
        })
}



btnReturn.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var dto = {"id" : this.dataset.id}
        Swal.fire({
                    title: "Are you sure?",
                    text: "You will not be able to recover this imaginary file!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Yes, I am sure!',
                    cancelButtonText: "No, cancel it!",
                    closeOnConfirm: false,
                    closeOnCancel: true
                 }).then((result)=>{
                    if (result.isConfirmed){
                        setReturn(dto)
                    }else{

                    }
                 });
    });
});

function setReturn(dto){
    $.ajax({
                    type: "POST",
                    url: "return",
                    data: dto,
                    dataType:"json",
                    cache: false,
                    beforeSend:function(xhr){
                        xhr.setRequestHeader('X-CSRF-TOKEN',token)
                    },
                    success:function(response){
                        if(response.success){
                           let error = document.querySelectorAll('.field-validation-error');
                               error.forEach(function(element){
                               element.textContent = "";
                           })
                           Swal.fire({
                             position: 'center',
                             icon: 'success',
                             title: response.message,
                             showConfirmButton: true
                           }).then(function(){
                              location.reload();
                           })
                               closeModal();
                        }else{
                            Swal.fire({
                              position: 'center',
                              icon: 'fail',
                              title: response.message,
                              showConfirmButton: true
                            })
                        }
                    },
                    error:function(){},
                    fail: function(){},
                })
        };

function getDropDownBook(){
$.ajax({
             type: "GET",
             url: "/Winterhold/loan/getBookDDL",
             cache:false,
             success:function(response){
                 var dto = response.obj;
                 console.log(response.obj);
                 let selectElement = document.querySelector('.book');
                 dto.forEach(function(element){
                    const option = document.createElement("option");
                    option.value = element.stringValue;
                    option.textContent = element.text;
                    selectElement.appendChild(option);
                 })
             },
             error:function(){},
             fail:function(){}
        })
}

function getDropDownCustomer(){
$.ajax({
             type: "GET",
             url: "/Winterhold/loan/getCustomerDDL",
             cache:false,
             success:function(response){
                 var dto = response.obj;
                 console.log(response.obj);
                 let selectElement = document.querySelector('.customer');
                 dto.forEach(function(element){
                    const option = document.createElement("option");
                    option.value = element.stringValue;
                    option.textContent = element.text;
                    selectElement.appendChild(option);
                 })
             },
             error:function(){},
             fail:function(){}
        })
}


btnClose.forEach(function(element){
        element.addEventListener('click',function(event){
            closeModal();
        });
    });

btnUpdate.forEach(function(element){
        element.addEventListener('click',function(event){
            event.preventDefault();
            showModalUpsert();
            var href = $(this).attr("href");
            console.log(href);
            $.ajax({
                        type: "GET",
                        url:href,
                        cache:false,
                        success:function(response){
                            var dto = response.obj;
                            console.log(dto);
                            document.querySelector(".idLoan").value = dto.id;
                            document.querySelector(".customer").value = dto.customerId;
                            getBook(dto.bookCode);
                            document.querySelector(".book").value = dto.bookCode;
                            document.querySelector(".loanDate").value = dto.loanDate;
                            let currentDate = new Date().toJSON().slice(0, 10);
                            if(dto.loanDate==currentDate){
                                document.querySelector('.loanDate').setAttribute('readonly', true);
                            }
                            document.querySelector(".note").value = dto.note;
                        },
                        error:function(){},
                        fail:function(){}
                    });
        });
});
function getBook(bookCode){
    $.ajax({
                 type: "GET",
                 url: `getDataBook?bookCode=${bookCode}`,
                 cache:false,
                 success:function(response){
                     var dto = response.obj;
                     let selectElement = document.querySelector('.book');
                        const option = document.createElement("option");
                        option.value = dto.code;
                        option.textContent = dto.title;
                        selectElement.appendChild(option);
                 },
                 error:function(){},
                 fail:function(){}
            })
}

function showModalUpsert(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelector('.upsert-dialog').style.display = "block";
}
function showModalDetail(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelectorAll('.detail-dialog').forEach(function(element){
            element.style.display = 'block';
        });
    }

function closeModal(){
document.querySelector('.modal-layer').style.display='none';
 let error = document.querySelectorAll('.field-validation-error');
 error.forEach(function(element){
      element.textContent = "";
     })
     document.querySelector('.customer').value = "null";
     document.querySelector('.idLoan').value = "null";
     document.querySelector('.book').innerHTML= "";
     document.querySelector('.loanDate').value="";
     document.querySelector('.note').value="";
     document.querySelector('.loanDate').removeAttribute('readonly');
     document.querySelectorAll('.popup-dialog').forEach(function(element){
     element.style.display='none';
});
}

btnAdd.addEventListener('click',function(event){
        event.preventDefault();
        getDropDownBook();
        showModalUpsert();
});

document.addEventListener("DOMContentLoaded",function(){
        getDropDownCustomer();

        $('.upsert-form').submit(SaveForm);
    });

function SaveForm(){
    event.preventDefault();
            var dto = {
                "id" : $('.popup-dialog .idLoan').val(),
                "customerId" : $('.upsert-dialog  .customer').val(),
                "bookCode" : $('.upsert-dialog .book').val(),
                "loanDate" : $('.upsert-dialog .loanDate').val(),
                "note" : $('.upsert-dialog .note').val(),
            };
            $.ajax({
                type: "POST",
                url: "/Winterhold/loan/save",
                contentType:"application/json; charset=utf-8",
                data:JSON.stringify(dto),
                dataType:"json",
                cache: false,
                beforeSend:function(xhr){
                     xhr.setRequestHeader('X-CSRF-TOKEN',token)
                },
                success:function(response){
                    if(response.success){
                       let error = document.querySelectorAll('.field-validation-error');
                           error.forEach(function(element){
                           element.textContent = "";
                       })
                           closeModal();
                            Swal.fire({
                                position: 'center',
                                icon: 'success',
                                title: response.message,
                                showConfirmButton: true
                            }).then(function(){
                                location.reload();
                            })
                    }else{
                        console.log(response.obj);
                        if(response.obj == null){
                            alert(response.message);
                        }else{
                            response.obj.forEach(function(element){
                                document.querySelector(`.error-${element.propertyName}`).textContent = element.msg;
                            })
                        }
                    }
                },
                error:function(errorThrown){
                    console.log(errorThrown);
                },
                fail: function(errorThrown){
                    console.log(errorThrown);
                },
            })
    }


//DETAIL
btnDetail.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data =
        {
            "bookCode" : this.dataset.bookcode,
            "customerNumber": this.dataset.customernumber
        }
        getDataCustomer(this.dataset.customernumber);
        getDataBook(this.dataset.bookcode);
        showModalDetail();
    })
});

function getDataCustomer(customerNumber){
    $.ajax({
                 type: "GET",
                 url: `/Winterhold/loan/getCustomer?customerNumber=${customerNumber}`,
                 cache:false,
                 success:function(response){
                     var dto = response.obj;
                     document.querySelector('.customerNumber').textContent=dto.memberShip;
                     document.querySelector('.fullName').textContent=dto.fullName;
                     document.querySelector('.phone').textContent=dto.phone;
                     document.querySelector('.membershipExpireDate').textContent=dto.expireDate;
                 },
                 error:function(){},
                 fail:function(){}
            })
}
function getDataBook(bookCode){
    $.ajax({
                 type: "GET",
                 url: `/Winterhold/loan/getBook?bookCode=${bookCode}`,
                 cache:false,
                 success:function(response){
                     var dto = response.obj;
                     document.querySelector('.title').textContent=dto.title;
                     document.querySelector('.categoryName').textContent=dto.category;
                     document.querySelector('.author').textContent=dto.author;
                     document.querySelector('.floor').textContent=dto.floor;
                     document.querySelector('.isle').textContent=dto.isle;
                     document.querySelector('.bay').textContent=dto.bay;
                 },
                 error:function(){},
                 fail:function(){}
            })
}

