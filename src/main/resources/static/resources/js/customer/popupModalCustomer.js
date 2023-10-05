const btnExtend = document.querySelectorAll('.btn-extend');
const btnDelete = document.querySelectorAll('.btn-delete');
const btnClose = document.querySelectorAll('.btn-back');
const btnDetail = document.querySelectorAll('.membershipBox');
let token = document.querySelector('#_csrfToken').content;


document.addEventListener("DOMContentLoaded",function(){
        document.querySelector('.confirmation-form').addEventListener('submit',function(){
            var data = document.querySelector('.confirmation-form .id').value;
            DeleteForm(data);
        })
    });

btnDetail.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data = this.dataset.membershipnumber
        getDataCustomer(data);
        showModalDetail();
    })
});

function getDataCustomer(customerNumber){
    $.ajax({
                 type: "GET",
                 url: `/Winterhold/customer/getCustomer?customerNumber=${customerNumber}`,
                 cache:false,
                 success:function(response){
                     var dto = response.obj;
                     document.querySelector('.membershipSpan').textContent=dto.membership;
                     document.querySelector('.fullName').textContent=dto.fullName;
                     document.querySelector('.birthDate').textContent=dto.birthDate;
                     document.querySelector('.gender').textContent=dto.gender;
                     document.querySelector('.phone').textContent=dto.phone;
                     document.querySelector('.address').textContent=dto.address;
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

function showModalConfirmation(){
document.querySelector('.modal-layer').style.display = "flex";
    document.querySelector('.confirmation').style.display ="block";
}

function showModalDetail(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelector('.detail-dialog').style.display= "block";
    }

function closeModal(){
    document.querySelector('.modal-layer').style.display='none';
     document.querySelectorAll('.popup-dialog').forEach(function(element){
          element.style.display='none';
     });
};

btnDelete.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data = this.dataset.membershipnumber;
        document.querySelector('.confirmation-form input.id').value=data;
        showModalConfirmation();
    })
})
function DeleteForm(data){
            var dto = {"membershipNumber":data};
            console.log(dto);
            event.preventDefault()
               $.ajax({
                      type: "POST",
                      url: "delete",
                      data: dto,
                      cache:false,
                      beforeSend:function(xhr){
                          xhr.setRequestHeader('X-CSRF-TOKEN',token)
                      },
                      success:function(response){
                          console.log(response)
                          if(response.success){
                                           Swal.fire({
                                             position: 'center',
                                             icon: 'success',
                                             title: response.message,
                                             showConfirmButton: true
                                           }).then(function(){
                                              location.reload();
                                           })
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
                      fail:function(){}
              })
        }

btnExtend.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault()
        var dto = {"membershipNumber" : this.dataset.membershipnumber}
                Swal.fire({
                            title: "Are you sure?",
                            text: "You are going to extend this member",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: '#DD6B55',
                            confirmButtonText: 'Yes, I am sure!',
                            cancelButtonText: "No, cancel it!",
                            closeOnConfirm: false,
                            closeOnCancel: true
                         }).then((result)=>{
                            if (result.isConfirmed){
                                setExtend(dto);
                            }else{

                            }
                         });
    })
})

function setExtend(dto){
    $.ajax({
                        type: "POST",
                        url: "extend",
                        data: dto,
                        dataType:"json",
                        cache: false,
                        beforeSend:function(xhr){
                            xhr.setRequestHeader('X-CSRF-TOKEN',token)
                        },
                        success:function(response){
                            if(response.success){
                               Swal.fire({
                                 position: 'center',
                                 icon: 'success',
                                 title: response.message,
                                 showConfirmButton: true
                               }).then(function(){
                                  location.reload();
                               })
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
}