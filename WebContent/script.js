var insertAt = -1;
var destext = "";
var quantext = "";

function addRow(tableID) {
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
    for(var r = 0, n = table.rows.length; r < n; r++) {
    	for(var c = 0, m = table.rows[r].cells.length; c < m; c++) {
    		var text = table.rows[r].cells[c].innerHTML;
            if(text.indexOf("id=\"description1\"") > 0 || text.indexOf("id=description1") > 0) {
            	destext = text;
            }
            else if(text.indexOf("id=\"quantity1\"") > 0 || text.indexOf("id=quantity1") > 0) {
            	quantext = text;
            }
            else if(text.indexOf("New order item") > 0) {
            	insertAt = r-1;
            }
        }
    } 
    if(insertAt==-1) {
    	return;
	}		 
    var row = table.insertRow(insertAt);
	var cell1 = row.insertCell(0);	 
	cell1.innerHTML = "Select product:";
	var cell2 = row.insertCell(1);
	cell2.innerHTML = destext;
		
    insertAt++;
	row = table.insertRow(insertAt);
	cell1 = row.insertCell(0);	 
	cell1.innerHTML = "Quantity:";
	cell2 = row.insertCell(1);
	cell2.innerHTML = quantext;
	insertAt++;
}