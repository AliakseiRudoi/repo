$().ready(function() {

		// validate news form on keyup and submit
		$('#newsForm').validate({
			rules : {
				'news.newsTitle' : {
					required : true,
					minlength : 3,
					maxlength : 20
				},
				'news.newsShortText' : {
					required : true,
					minlength : 3,
					maxlength : 100
				},
				'news.newsFullText': {
					required : true,
					minlength : 4,
					maxlength : 2000
				}
			},
			messages : {
				'news.newsTitle' : {
					required : "Please enter news title",
					minlength : "title must consist of at least 3-20 chars",
					maxlength : "title must consist of at least 3-20 chars"
				},
				'news.newsShortText' : {
					required : "Please enter news short text",
					minlength : "comment must consist of at least 3-100 chars",
					maxlength : "title must consist of at least 3-100 chars"
				},
				'news.newsFullText' : {
					required : "Please enter news text",
					minlength : "comment must consist of at least 3-2000 chars",
					maxlength : "title must consist of at least 3-2000 chars"
				}
			}
		});
		
		
		// validate comment form on keyup and submit
		$('#formComment').validate({
			rules : {
				commentText : {
					required : true,
					minlength : 3,
					maxlength : 100
				}
			},
			messages : {
				commentText : {
					required : "Please enter your comment",
					minlength : "comment must consist of at least 3-100 chars"
				}
			}
		});
	});