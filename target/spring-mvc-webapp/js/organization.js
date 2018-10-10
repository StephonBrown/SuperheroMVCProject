/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var OrgId = $(e.relatedTarget).data('id');
        var OrgName = $(e.relatedTarget).data('name');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#boldedOrganizationName').append(OrgName);
        $('#deleteButton').attr("href", pageContext+"/organization/delete/"+OrgId);
    }); 
    $(document).on("hide.bs.modal", function(){
        $('#boldedOrganizationName').empty();
    });
  
});
    