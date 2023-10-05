const btnSummary = document.querySelectorAll('.btn-summary');
const btnClose = document.querySelector('.btn-back');
const btnDelete = document.querySelectorAll('.btn-delete');
let token = document.querySelector('#_csrfToken').content;

btnDelete.forEach(function(element){
    element.addEventListener('click',function(event){
        event.preventDefault();
        var data = {"bookCode" : this.dataset.bookcode};
        $.ajax({
                         type: "POST",
                         url: "delete",
                         data: data,
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
    })
})

btnClose.addEventListener('click',function(event){
    event.preventDefault();
    closeModal();
})

btnSummary.forEach(function(element){
    element.addEventListener('click',function(event){
    event.preventDefault();
        var dto = this.dataset.bookcode
        showSummary(dto);
    })
})

function showSummary(dto){
    $.ajax({
                 type: "GET",
                 url: `/Winterhold/book/showSummary?bookCode=${dto}`,
                 cache:false,
                 success:function(response){
                     var dto = response.message;
                     document.querySelector(".summary").textContent=dto;
                     showModalSummary();
                 },
                 error:function(){},
                 fail:function(){}
            })

}


function showModalSummary(){
        document.querySelector('.modal-layer').style.display = "flex";
        document.querySelector('.summary-dialog').style.display = "block";
}

function closeModal(){
        document.querySelector('.modal-layer').style.display='none';
        document.querySelector('.summary-dialog').style.display = "none";
}