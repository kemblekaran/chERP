$(function() {

	// Variable declaration

	var amount = 0;
	var payNow = $("#payNow");
	var closingBal = $("#closingBal");
	var balAmount = 0;
	var insertData = document.getElementById('insertBtn');
	var actionURL = $('#actionURL').val();

	$.getJSON('/server/jsonfiles/company.json', function(data) {
		var jsonDataProduct = data['data'];
		$.each(jsonDataProduct, function(key, val) {
			$('#companyList').append(
					'<option value="' + val.name + '">' + val.name
							+ '</option>');
		});
	});

	// //Get the table
	var oTable = $('#dataTable').DataTable({
		responsive: true
	});

	// Hide first id column
	oTable.column(0).visible(false);

	// colored selected row
	$('#dataTable tbody').on('click', 'tr', function() {

		if ($(this).hasClass('selected')) {
			$(this).removeClass('selected');
		} else {
			oTable.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});

	// To display payment details of purchase for each company.
	$("#companyList").on(
			"change",
			function() {
				var companyName = $(this).val();

				$.ajax({
					type : "POST",
					url : "/server/jsonfiles/payload.json",
					dataType : "json",
					select : true,
					success : function(data) {
						var jsonDataProduct = data['data'];
						var jsonPayment = data['data'];

						// To display total amount to be paid
						$.each(jsonPayment, function(key, val) {
							if (companyName == val.company) {
								amount = amount + val.balanceAmount;
							}
						});
						var balAmt = amount;
						amount = 0;

						$.each(jsonDataProduct, function(key, val) {

							if (companyName == val.company) {
								oTable.row.add(
										[ val.id, val.date, val.purchaseId,
												val.finalAmount,
												val.balanceAmount ]).draw();

								toBePaid = $("#toBePaid").val(balAmt.toFixed(2));
								$("#companyList").on("change", function() {
									oTable.clear().draw();
									toBePaid.val(null);
									payNow.val(null);
									closingBal.val(null);
									selectedBal = null;

								});

							}
						});// forEach method close

					}// success method close
				});// ajax method close
			});// onchange event close

	// AmountDetails
	$("#payNow").on('input', function() {
		var total = parseFloat(toBePaid.val()) - parseFloat(payNow.val());

		// var balTotal = selectedBal - parseFloat(payNow.val());
		if (total >= 0) {

			var bal = parseFloat(closingBal.val(total.toFixed(2)));
		} else {
			closingBal.val(null);
			payNow.val(null);
			// alert("Please enter right amount");
		}
	});

	// insertButtonListener and insert data function
	insertData.addEventListener('click', function() {

		$('#InsertForm').submit(function(e) {
			$.ajax({
				url : actionURL,
				type : "POST",
				data : $('#InsertForm').serializeArray(),
				async : false,
				success : function(data) {
					if (data != null) {
						alert(data);
					}
				},
				error : function(xhr, ajaxOptions, thrownError) {
					alert("Insert Form Error");
				}
			});

		});

	});

	// cash bank enabled disabled
	$(document).ready(function() {

		// set current date to payment using datepicker
		$(function() {
			$("#paymentDate, #chDate").datepicker({
//				dateFormat: 'dd/mm/yy',
				showAnim : 'drop',
				showButtonPanel : true,
			}).datepicker('setDate', 'today');
		});

		function checkradiobox() {
			var radio = $("input[name='paymentMode']:checked").val();
			$('#name, #chDate, #chNo ').attr('disabled', true);
			$('#toBePaid, #closingBal').attr('readonly', true);
			if (radio == "cash") {
				$('#name').attr('disabled', true);
				$('#chDate').attr('disabled', true);
				$('#chNo').attr('disabled', true);

			} else if (radio == "bank") {
				$('#name').attr('disabled', false);
				$('#chDate').attr('disabled', false);
				$('#chNo').attr('disabled', false);

			}
		}

		$("#optionsRadios1, #optionsRadios2").change(function() {
			checkradiobox();
		});

		checkradiobox();

	});

	// select data from json file
	function selectAllAjax() {
		if (actionURL !== 'PurchaseServlet') {
			$.ajax({
				url : actionURL,
				type : "post",
				success : function(data) {

				},
				error : function() {
					alert('selectAllAjax error');
				}
			});
		}
	}
	selectAllAjax();

});