var clickBtn = document.getElementById("cacheButton");
var button = document.getElementsByClassName('btn')[0];

// Disable the button on initial page load
button.disabled = true;

//add event listener
clickBtn.addEventListener('click', function(event) {
alert("Blub");
    button.disabled = !button.disabled;
});

