/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var SightingId = $(e.relatedTarget).data('id');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#deleteButton').attr("href", pageContext+ "/sighting/delete/" + SightingId);
    }); 
  
});
    