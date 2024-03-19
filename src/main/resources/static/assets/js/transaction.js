let transactionWindow = document.getElementById('transaction-window');
let transactionDialog = document.getElementById('transaction-dialog');
let cancelButton = document.getElementById('transaction-cancel-btn');
let buttons = document.getElementsByClassName('new-transaction-btn');
let transactionTitle = document.getElementById('transaction-title');
let confirmationDetails = document.getElementById('confirmation-details');
let firstName;
let amount;
let senderId;

Array.from(buttons).forEach(button => {
    button.addEventListener('click', function (event) {
        let target = event.target;
        if (target.tagName.toLowerCase() === 'i') {
            target = target.parentElement;
        }
        firstName = target.getAttribute('data-user-first-name');
        transactionTitle.innerHTML = `Sending money to ${firstName}`;
        let userId = parseInt(target.getAttribute('data-user-id'));
        let receiverId = document.getElementById('receiverId');
        receiverId.value = userId;
        transactionWindow.style.display = 'none';
        transactionDialog.style.display = 'block';
        cancelButton.style.display = 'block';
    });
});

function cancelTransaction() {
    transactionTitle.innerHTML = 'Send money to a friend';
    transactionWindow.style.display = 'block';
    transactionDialog.style.display = 'none';
    cancelButton.style.display = 'none';
    transactionReview.style.display = 'none';
    amount.disabled = false;
    senderId.disabled = false;
    reviewButton.disabled = false;
    receiverId.value = '';
}

let transactionForm = document.getElementById('transaction-form');
let transactionReview = document.getElementById('transaction-review');
let reviewButton = document.getElementById('review-and-submit-button');

function confirmSubmit(event) {
    amount = document.getElementById('amount');
    senderId = document.getElementById('senderWalletId');
    let amountValue = parseFloat(amount.value);
    let symbol = senderId.options[senderId.selectedIndex].getAttribute('data-symbol');
    let walletFunds = parseFloat(senderId.options[senderId.selectedIndex].getAttribute('data-funds-available'));
    if (amountValue <= 0 || isNaN(amountValue)) {
        alert('Amount must be a positive number');
        event.preventDefault();
        return
    }
    if (amountValue > walletFunds) {
        alert('Insufficient funds in this wallet. Try another wallet or top up your wallet.');
        event.preventDefault();
        return
    }
    amount.disabled = true;
    senderId.disabled = true;
    confirmationDetails.innerText = 'You are about to send ' + amountValue + symbol + ' to ' + firstName + ', are you sure?';
    event.preventDefault();
    transactionReview.style.display = 'block';
}


function confirmTransaction() {
    amount.disabled = false;
    senderId.disabled = false;
    transactionForm.submit();
}
