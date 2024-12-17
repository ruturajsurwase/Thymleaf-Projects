// Set the date we're counting down to
const countDownDate = new Date("Dec 31, 2024 23:59:59").getTime();

// Update the countdown every second
const countdownInterval = setInterval(() => {
	const now = new Date().getTime();
	const distance = countDownDate - now;

	// Calculate days, hours, minutes, and seconds
	const days = Math.floor(distance / (1000 * 60 * 60 * 24));
	const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
	const seconds = Math.floor((distance % (1000 * 60)) / 1000);

	// Display the result
	document.getElementById("days").innerText = days;
	document.getElementById("hours").innerText = hours;
	document.getElementById("minutes").innerText = minutes;
	document.getElementById("seconds").innerText = seconds;

	// If the countdown is finished, stop the countdown
	if (distance < 0) {
		clearInterval(countdownInterval);
		document.querySelector(".timer").innerHTML = "EXPIRED";
	}
}, 1000);
