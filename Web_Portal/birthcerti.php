<link href="css/style_birth.css" rel="stylesheet">

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
  <form id="contact" action="http://172.31.130.123:5000/transactions/origin" method="post" enctype="application/json">
    <h3>Birth Details</h3>

    <fieldset>
      <input name="sender" placeholder="Hospital's Name" type="text" tabindex="1" required autofocus>
      <input name="name" placeholder="Person's Name" type="text" tabindex="1" required autofocus>  
      <input name="amount" placeholder="Fees Payable" type="text" tabindex="1" required autofocus>
      <input name="gender" placeholder="Gender" type="text" tabindex="1" required autofocus>
      <input name="blood_group" placeholder="Blood Group" type="text" tabindex="1" required autofocus>  
      <input name="mother_hash" placeholder="Mother's hash" type="text" tabindex="1" required autofocus>
      <input name="father_hash" placeholder="Father's hash" type="text" tabindex="1" required autofocus>


    </fieldset>


    <fieldset>
      <button type="submit" id="contact-submit">Add Birth Certificate</button>
    </fieldset>

  </form>
</div>
