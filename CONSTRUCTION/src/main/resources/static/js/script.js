
function toggleFaq(element) {
	const answer = element.nextElementSibling;
	const toggleSign = element.querySelector('.faq-toggle');
	if (answer.style.display === 'block') {
		answer.style.display = 'none';
		toggleSign.textContent = '+';
	} else {
		answer.style.display = 'block';
		toggleSign.textContent = '–';
	}
}

