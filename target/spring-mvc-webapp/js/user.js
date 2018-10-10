/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var userId = $(e.relatedTarget).data('id');
        var userName = $(e.relatedTarget).data('name');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#boldedHeroName').append(userName);
        $('#deleteButton').attr("href", pageContext + "/user/delete/"+ userId);
    }); 
    $(document).on("hide.bs.modal", function(){
        $('#boldedUserName').empty();
    });
  
});
    