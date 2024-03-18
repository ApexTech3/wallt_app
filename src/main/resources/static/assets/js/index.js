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
    let walletTable = $('#wallets-table').DataTable({
        paging: false,
        order: [1, 'desc']
    });
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




