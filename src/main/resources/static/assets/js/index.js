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
    $('#activityTable').DataTable();
});
$(document).ready(function () {
    $('#adminTable').DataTable();
});


$(document).ready(function () {
    var table = $('#transactionTable').DataTable({
        autoWidth: false,
        paging: false,
        scrollY: 250,
        ordering: false,
        language: {
            searchPlaceholder: 'Search a friend'
        },
    });

});