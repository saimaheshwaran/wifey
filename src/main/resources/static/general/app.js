
let now = new Date();

// Toggle password visibility
function togglePassword() {
    var passwordInput = document.getElementById('password');
    var toggleIcon = document.getElementById('toggleIcon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleIcon.classList.remove('fa-eye');
        toggleIcon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        toggleIcon.classList.remove('fa-eye-slash');
        toggleIcon.classList.add('fa-eye');
    }
}

// Auto-dismiss alerts after 10 seconds
document.addEventListener('DOMContentLoaded', function () {
    setTimeout(function () {
        var alerts = document.querySelectorAll('.alert');
        alerts.forEach(function (alert) {
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 10000);

    // Reaction buttons functionality
    document.querySelectorAll('.reaction-btn').forEach(button => {
        button.addEventListener('click', function() {
            const reaction = this.getAttribute('data-reaction');
            const postId = this.getAttribute('post-id');

            fetch('/blog/' + postId + '/react/' + reaction, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: JSON.stringify({ reaction: reaction })
            })
                .then(response => response.json())
                .then(data => {
                    if(data.success) {
                        // Update the count on the button
                        const badge = this.querySelector('.badge');
                        badge.textContent = data.newCount;
                        // Visual feedback
                        this.classList.add('disabled');
                        //this.classList.remove('reaction-btn');
                    }
                });
        });
    });
});

function updateTimeLive() {
    const timeOptions = {
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    for (let i = 0,
             dateControl = document.getElementsByClassName('liveTime');
         i < dateControl.length; i++) {
        dateControl[i].textContent = now.toLocaleDateString('en-US', timeOptions);
    }
}

function updateDateLive() {
    const dateOptions = {
        weekday: 'long',
        month: 'long',
        day: 'numeric',
        year: 'numeric'
    };
    for (let i = 0,
             dateControl = document.getElementsByClassName('liveDate');
         i < dateControl.length; i++) {
        dateControl[i].textContent = now.toLocaleDateString('en-US', dateOptions);
    }
}

updateTimeLive();
const  intervalTimeId = setInterval(updateTimeLive, 1000);

updateDateLive();
const  intervalDateId = setInterval(updateDateLive, 1000);

// Clean up interval when page unloads (optional but good practice)
window.addEventListener('beforeunload', function() {
    clearInterval(intervalId);
});