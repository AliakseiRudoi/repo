 function validateComments(input) {
		   if (input.value.length < 3 || input.value.length > 100 || input.value.length === 0) {
			  input.setCustomValidity("Please enter a value between 3 and 100 characters long.");   
		   }
		   else {
			  input.setCustomValidity("");
		   }
	}
	  