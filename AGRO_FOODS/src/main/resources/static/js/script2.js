let slideIndex = 0; // Initialize the slide index

// Show the first slide initially
showSlides(slideIndex);

// Function to increment or decrement the slide index
function plusSlides(n) {
    showSlides(slideIndex += n);
}

// Function to display the correct slide based on the current index
function showSlides(n) {
    let slides = document.getElementsByClassName("testimonial-slide");

    // If the index is greater than the number of slides, reset to the first slide
    if (n >= slides.length) {
        slideIndex = 0;
    }
    // If the index is less than 0, go to the last slide
    if (n < 0) {
        slideIndex = slides.length - 1;
    }

    // Hide all slides initially
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }

    // Display the current slide
    slides[slideIndex].style.display = "block";
}
