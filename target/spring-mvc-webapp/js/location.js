/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var locationId = $(e.relatedTarget).data('id');
        var locationName = $(e.relatedTarget).data('name');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#boldedLocationName').append(locationName);
        $('#deleteButton').attr("href", pageContext+"/location/delete/"+locationId);
    }); 
    $(document).on("hide.bs.modal", function(){
        $('#boldedLocationName').empty();
    });
  
});
    