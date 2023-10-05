const btnDelete = document.querySelectorAll('.btn-delete');
const btnClose = document.querySelectorAll('.btn-back');
let token = document.querySelector('#_csrfToken').content;

document.addEventListener("DOMContentLoaded",function(){
        document.querySelector('.confirmation-form').addEventListener('submit',function(){
            var data = document.querySelector('.confirmation-form .id').value;
            DeleteForm(data);
        })
});

btnClose.forEach(function(element){
        element.addEventListener('click',function(event){
            closeModal();
        });
    });
function showModalConfirmation(){
document.querySelector('.modal-layer').style.display = "flex";
    document.querySelector('.confirmation').style.display ="block";
}

function closeModal(){
    document.querySelector('.modal-layer').style.display='none';
     document.querySelector('.popup-dialog').style.display='none';
};

btnDelete.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data = this.dataset.id;
        document.querySelector('.confirmation-form input.id').value=data;
        showModalConfirmation();
    })
})

function DeleteForm(data){
            var dto = {"id":data};
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
