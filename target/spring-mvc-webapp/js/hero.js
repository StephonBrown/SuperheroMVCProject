/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var HeroId = $(e.relatedTarget).data('id');
        var HeroName = $(e.relatedTarget).data('name');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#boldedHeroName').append(HeroName);
        $('#deleteButton').attr("href", pageContext + "/hero/delete/"+ HeroId);
    }); 
    $(document).on("hide.bs.modal", function(){
        $('#boldedHeroName').empty();
    });
  
});
    