function paginationNews(link) {
		var numberPage = $(link).text();	
	
		if (numberPage == "") {
			var numberPage = "1";
		} 
		
	  $.ajax({
          type: 'GET',
          url: "/news-admin/news/adminHome/pagin/" + numberPage,
          success: function (data) {
            	 var respContent = "";
            	 $.each(data, function(i, newsVOList){
            		 respContent += 
            			 "<br>"
            			 + "<div id='newsTitleDiv'>"
            			 + "<a id='titleButton' href='/news-admin/news/editSingleNews?&newsId="+newsVOList.news.newsId+"'>"
            			 + newsVOList.news.newsTitle + "</a>" 
            			 + "</div>"
            			 + "<div id='newsAuthorDiv'> ("
            			 + newsVOList.author.authorName
            			 + ") </div>"
            			 + "<div id='newsCreationDateDiv'>"
            			 + newsVOList.news.newsModificationDate 
            			 + "</div>"
            			 + "<br><br>"
            			 + "<div id='newsShortTextDiv'>" 
            			 + newsVOList.news.newsShortText
            			 + "</div>"
            			 + "<br>"
            			 + "<div id='linkReadMoreDiv'>"
            			 + 	"<a href='/news-admin/news/editSingleNews?&newsId="+newsVOList.news.newsId+"'>edit</a>"
            			 + "<label for='"+newsVOList.news.newsId+"'>" 
         				 + "<input type='checkbox' name='newsIdList' value='"+newsVOList.news.newsId+"'>"
         				 + "</label>"
         				 + "</div>"            			 
            			 
            			 + "<div id='newsCommentsDiv'>"
            			 + "</div>"
            			 
            			 $.each(newsVOList.tagsList, function(j, tagsList) {
 							respContent += 
 								"<div id='newsTagsDiv'>"
 								+ tagsList.tagName
 								+ "</div>"
 								+"<br>"
 						});
            			 
            	 });
            	 respContent += 
        			 "<br>"
        			 + "<input type='submit' value='Delete News'>"
        			 
        			 + "<br>"
        			 ;
            	 $("#ajaxAsynchronousArea").html(respContent);
          }, 
           error: function (data, status, er) {
              alert("error: " + data + " status: " + status + " er:" + er + "!!!!!!!!!!");
          }
          
      });
	  event.preventDefault();
} 