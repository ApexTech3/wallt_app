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
            order: [],
        }
    );
});
$(document).ready(function () {
    $('#admin-table').DataTable();
});

$(document).ready(function () {
    $('#activity-table-admin').DataTable();
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

let transactionWindow = document.getElementById('transaction-window');
let transactionDialog = document.getElementById('transaction-dialog');
let cancelButton = document.getElementById('transaction-cancel-btn');
let buttons = document.getElementsByClassName('new-transaction-btn');

for (var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('click', function (event) {
        let target = event.target;
        if (target.tagName.toLowerCase() === 'i') {
            target = target.parentElement;
        }
        let userId = parseInt(target.getAttribute('data-user-id'));
        let receiverId = document.getElementById('receiverId');
        receiverId.value = userId;
        transactionWindow.style.display = 'none';
        transactionDialog.style.display = 'block';
        cancelButton.style.display = 'block';
    });
}

cancelButton.addEventListener('click', function () {
    transactionWindow.style.display = 'block';
    transactionDialog.style.display = 'none';
    cancelButton.style.display = 'none';
    receiverId.value = '';

});
