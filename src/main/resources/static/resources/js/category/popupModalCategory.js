const btnAdd = document.querySelector('.btn-add');
const btnClose = document.querySelectorAll('.btn-back');
const btnUpdate = document.querySelectorAll('.btn-update');
const btnDelete = document.querySelectorAll('.btn-delete');
let token = document.querySelector('#_csrfToken').content;

btnClose.forEach(function(element){
        element.addEventListener('click',function(event){
            closeModal();
        });
    });

btnUpdate.forEach(function(element){
        element.addEventListener('click',function(event){
        let action = "update";
            document.querySelector('.action').value = action;
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
                            document.querySelector(".categoryName").value = dto.categoryName;
                            document.querySelector(".floor").value = dto.floor;
                            document.querySelector(".isle").value = dto.isle;
                            document.querySelector(".bay").value = dto.bay;
                        },
                        error:function(){},
                        fail:function(){}
                    });
        });
});


function showModalUpsert(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelector('.upsert-dialog').style.display = "block";
}

function showModalConfirmation(){
document.querySelector('.modal-layer').style.display = "flex";
    document.querySelector('.confirmation').style.display ="block";
}

function showModalDelete(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelector('.delete-dialog').style.display = "block";
    }

function closeModal(){
document.querySelector('.modal-layer').style.display='none';
 let error = document.querySelectorAll('.field-validation-error');
 error.forEach(function(element){
      element.textContent = "";
     })
     document.querySelector('.categoryName').value = "";
     document.querySelector('.floor').value="";
     document.querySelector('.isle').value="";
     document.querySelector('.bay').value="";
     document.querySelectorAll('.popup-dialog').forEach(function(element){
     element.style.display='none';
});
}

btnAdd.addEventListener('click',function(event){
        event.preventDefault();
        let action = "insert";
        document.querySelector('.action').value = action;
        showModalUpsert();
});

document.addEventListener("DOMContentLoaded",function(){
        $('.upsert-form').submit(SaveForm);
        document.querySelector('.confirmation-form').addEventListener('submit',function(){
            var data = document.querySelector('.confirmation-form .id').value;
            DeleteForm(data);
        })
    });

function SaveForm(){
    event.preventDefault();
            var href = ""
            if(document.querySelector('.action').value == "insert"){
                href = "http://localhost:7077/Winterhold/category/save"
            }
            if(document.querySelector('.action').value == "update"){
                href = "http://localhost:7077/Winterhold/category/update"
            }
            var dto = {
                "categoryName" : $('.popup-dialog .categoryName').val(),
                "floor" : $('.upsert-dialog  .floor').val(),
                "isle" : $('.upsert-dialog .isle').val(),
                "bay" : $('.upsert-dialog .bay').val(),
            };
            $.ajax({
                type: "POST",
                url: href,
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


btnDelete.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data = this.dataset.categoryname;
        document.querySelector('.confirmation-form input.id').value=data;
        showModalConfirmation();
    })
})
    function DeleteForm(data){
            var dto = {"categoryName":data};
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
                                });
        };