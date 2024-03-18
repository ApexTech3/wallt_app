let transactionWindow = document.getElementById('transaction-window');
let transactionDialog = document.getElementById('transaction-dialog');
let cancelButton = document.getElementById('transaction-cancel-btn');
let buttons = document.getElementsByClassName('new-transaction-btn');
let transactionTitle = document.getElementById('transaction-title');
let firstName;

let confirmationDetails = document.getElementById('confirmation-details');

for (var i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener('click', function (event) {
        let target = event.target;
        if (target.tagName.toLowerCase() === 'i') {
            target = target.parentElement;
        }
        firstName = target.getAttribute('data-user-first-name');
        transactionTitle.innerHTML = 'Sending money to ' + firstName;
        let userId = parseInt(target.getAttribute('data-user-id'));
        let receiverId = document.getElementById('receiverId');
        receiverId.value = userId;
        transactionWindow.style.display = 'none';
        transactionDialog.style.display = 'block';
        cancelButton.style.display = 'block';
    });
}
let amount;
let senderId;

cancelButton.addEventListener('click', function () {
    transactionTitle.innerHTML = 'Send money to a friend';
    transactionWindow.style.display = 'block';
    transactionDialog.style.display = 'none';
    cancelButton.style.display = 'none';
    transactionReview.style.display = 'none';
    amount.disabled = false;
    senderId.disabled = false;
    reviewButton.disabled = false;
    receiverId.value = '';

});

let transactionForm = document.getElementById('transaction-form');
let transactionReview = document.getElementById('transaction-review');
let confirmTransactionButton = document.getElementById('confirm-transaction-btn');
let reviewButton = document.getElementById('review-and-submit-button');

function confirmSubmit(event) {
    amount = document.getElementById('amount');
    senderId = document.getElementById('senderWalletId');
    let amountValue = parseFloat(amount.value);
    let symbol = senderId.options[senderId.selectedIndex].getAttribute('data-symbol');
    // reviewButton.disabled = true;
    // amount.disabled = true;
    // senderId.disabled = true;
    confirmationDetails.innerText = 'You are about to send ' + amountValue + symbol + ' to ' + firstName + ', are you sure?';
    event.preventDefault();
    transactionReview.style.display = 'block';

}

confirmTransactionButton.addEventListener('click', function () {
    transactionForm.submit();
});