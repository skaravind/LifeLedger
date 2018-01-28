<link href="css/style_school.css" rel="stylesheet">

<script type="text/javascript" src="jquery-1.2.1.pack.js"></script>
<script type="text/javascript">
	function lookup(inputString) {
		if(inputString.length == 0) {
			// Hide the suggestion box.
			$('#suggestions').hide();
		} else {
			$.post("rpc.php", {queryString: ""+inputString+""}, function(data){
				if(data.length >0) {
					$('#suggestions').show();
					$('#autoSuggestionsList').html(data);
				}
			});
		}
	} // lookup

	function fill(thisValue) {
		$('#Location').val(thisValue);
		setTimeout("$('#suggestions').hide();", 200);
	}
</script>


<div class="container">
  <form id="contact" action="http://172.31.130.123:5000/transactions/school" method="post" enctype="application/json">
    <h3>Student Details</h3>

    <fieldset>
      <input name="sender" placeholder="School's Name" type="text" tabindex="1" required autofocus>
      <input name="name" placeholder="Person's Name" type="text" tabindex="1" required autofocus>  
      <input name="amount" placeholder="Fees Payable" type="text" tabindex="1" required autofocus>
      <input name="admission_no" placeholder="Admisson no." type="text" tabindex="1" required autofocus>
      <input name="time_of_admission" placeholder="Time of Admisson" type="text" tabindex="1" required autofocus>
    </fieldset>

   
    <fieldset>
      <button type="submit" id="contact-submit">Add Student Details</button>
    </fieldset>

  </form>
</div>
