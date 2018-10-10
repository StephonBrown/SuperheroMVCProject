/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function(){
   $(document).on("shown.bs.modal",function(e){
        var dvdId = $(e.relatedTarget).data('id');
        var pageContext = $(e.relatedTarget).data('page-context');
        $('#deleteButton').attr("href", pageContext+"/delete?dvdId="+dvdId);
    }); 
    
    $('#searchButton').click(function(){
        var term = $('#searchTerm').val();
        var category = $('#selectCategory').val();
        var pageContext = $('#searchRow').data('page-context');
        
        if(term ==="" ||category===null){
            console.log(term +" "+category);
            clearError();
            displayError();
        }else{
            var searchMap = {
                    category: category,
                    term: term
            };   
            $.ajax({
                type: 'POST',
                url: pageContext + '/search/dvds?category='+ category+'&term='+term,
                dataType: 'json',
                success: function (data) {
                    // clear errorMessages
                    clearError();
                    fillDvdTable(data);
                },
                error: function () {
                    clearError();
                    $('#errorDiv')
                            .append($('<li>')
                                    .attr({class: 'list-group-item list-group-item-danger'})
                                    .text('Error calling web service.  Please try again later.'));
                }
        });
        
    };
  });
  
});

function clearError(){
    $('#errorDiv').empty();
}

function displayError () {
    $('#errorDiv').append($('<li>')
                    .attr({class: 'list-group-item list-group-item-danger'})
                    .text('Both a category and a term must be selected'));
}

function clearDvdTable() {
    $('#dvdContentRows').empty();
}

function fillDvdTable(data) {
    // we need to clear the previous content so we don't append to it
    clearDvdTable();

    // grab the the tbody element that will hold the rows of contact information
    var contentRows = $('#dvdContentRows');
    var pageContext = $('#searchRow').data('page-context');
    $.each(data, function (index, dvd) {
        var dvdId = dvd.dvdId
        var title = dvd.title;
        var releaseDateYear = dvd.releaseDateYear.year;
        var directorName = dvd.directorName;
        var filmRating = dvd.filmRating;
        var userRating = dvd.userRating;

        var row = '<tr>';
        row += '<td>' +'<a href="'+ pageContext+'/displayDvdDetails?dvdId='+dvdId+'">'+ title + '</a></td>';
        row += '<td>' +releaseDateYear+'</td>';
        row += '<td>' + directorName + '</td>';
        row += '<td>' + filmRating + '</td>';
        row += '<td>'+'<a href="'+pageContext +'/displayEditDvdPage?dvdId=' + dvdId + '">Edit</a>'+ ' | '+ '<a data-toggle="modal" data-page-context='+pageContext+' data-id="'+dvdId +'" data-target="#confirmationModal">Delete</a></td>';
        row += '</tr>';
        contentRows.append(row);
    });
    
}