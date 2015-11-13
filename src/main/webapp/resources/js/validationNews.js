function validateTitle(input) {
	   if (input.value.length < 4 || input.value.length > 20 || input.value.length === 0) {
		  input.setCustomValidity("Please enter a value between 4 and 20 characters long.");   
	   }
	   else {
		  input.setCustomValidity("");
	   }
}
 
 function validateShortText(input) {
	   if (input.value.length < 4 || input.value.length > 100 || input.value.length === 0) {
			  input.setCustomValidity("Please enter a value between 4 and 100 characters long.");   
		   }
		   else {
			  input.setCustomValidity("");
		   }
	}
	  
	  function validateFullText(input) {
		   if (input.value.length < 3 || input.value.length > 2000 || input.value.length === 0) {
			  input.setCustomValidity("Please enter a value between 3 and 2000 characters long.");   
		   }
		   else {
			  input.setCustomValidity("");
		   }
	}
	  
	  