document.getElementById('blurToggleButton').addEventListener('click', function () {
    var sensitiveContentElements = document.querySelectorAll('[data-blur-toggle]');
    for (var i = 0; i < sensitiveContentElements.length; i++) {
        if (sensitiveContentElements[i].style.filter === 'blur(5px)') {
            sensitiveContentElements[i].style.filter = 'none';
        } else {
            sensitiveContentElements[i].style.filter = 'blur(5px)';
        }
    }
});

$(document).ready(function () {
    $('#activity-table').DataTable(
        {
            scrollY: 350,
            columns: [{width: '15%'}, {width: '25%'}, {width: '30%'}, {width: '20%'}, {width: '10%'}]
        }
    );
});
$(document).ready(function () {
    $('#admin-table').DataTable();
});


$(document).ready(function () {
    var table = $('#transaction-table').DataTable({
        autoWidth: false,
        paging: false,
        scrollY: 250,
        ordering: false,
        language: {
            searchPlaceholder: 'Search a friend'
        },
    });

});

var transactionWindow = document.getElementById('transaction-window');
var transactionDialog = document.getElementById('transaction-dialog');
var cancelButton = document.getElementById('transaction-cancel-btn');
var buttons = document.getElementsByClassName('new-transaction-btn');
for (var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('click', function () {
        transactionWindow.style.display = 'none';
        transactionDialog.style.display = 'block';
        cancelButton.style.display = 'block';
    });
}

cancelButton.addEventListener('click', function () {
    transactionWindow.style.display = 'block';
    transactionDialog.style.display = 'none';
    cancelButton.style.display = 'none';

});