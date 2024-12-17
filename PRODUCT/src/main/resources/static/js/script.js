// SLIDES
const slides = document.querySelectorAll('.slide');
const prevSlideButton = document.querySelector('.prev-slide');
const nextSlideButton = document.querySelector('.next-slide');
let currentSlide = 0;
let slideInterval;

// Function to show the current slide
function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.style.display = (i === index) ? 'block' : 'none';
    });
}

// Function to go to the next slide
function nextSlide() {
    currentSlide = (currentSlide + 1) % slides.length;
    showSlide(currentSlide);
}

// Function to go to the previous slide
function prevSlide() {
    currentSlide = (currentSlide - 1 + slides.length) % slides.length;
    showSlide(currentSlide);
}

// Start automatic slide transition every 5 seconds
function startSlideShow() {
    slideInterval = setInterval(nextSlide, 5000); // Change slide every 5 seconds
}

// Stop the slideshow when hovering over the navigation buttons
function stopSlideShow() {
    clearInterval(slideInterval);
}

// Event listeners for manual slide navigation
nextSlideButton.addEventListener('click', () => {
    stopSlideShow();
    nextSlide();
    startSlideShow();  // Restart slideshow after manual slide change
});

prevSlideButton.addEventListener('click', () => {
    stopSlideShow();
    prevSlide();
    startSlideShow();  // Restart slideshow after manual slide change
});

// Initial call to display the first slide and start the automatic slideshow
showSlide(currentSlide);
startSlideShow();


// REVIEWS
const reviews = document.querySelectorAll('.review');
const prevReviewButton = document.querySelector('.prev-review');
const nextReviewButton = document.querySelector('.next-review');
let currentReview = 0;
let reviewInterval;

// Function to show the current review
function showReview(index) {
    reviews.forEach((review, i) => {
        review.style.display = (i === index) ? 'block' : 'none';
    });
}

// Function to go to the next review
function nextReview() {
    currentReview = (currentReview + 1) % reviews.length;
    showReview(currentReview);
}

// Function to go to the previous review
function prevReview() {
    currentReview = (currentReview - 1 + reviews.length) % reviews.length;
    showReview(currentReview);
}

// Start automatic review transition every 5 seconds
function startReviewSlideshow() {
    reviewInterval = setInterval(nextReview, 5000); // Change review every 5 seconds
}

// Stop the review slideshow when hovering over the navigation buttons
function stopReviewSlideshow() {
    clearInterval(reviewInterval);
}

// Event listeners for manual review navigation
nextReviewButton.addEventListener('click', () => {
    stopReviewSlideshow();
    nextReview();
    startReviewSlideshow();  // Restart slideshow after manual review change
});

prevReviewButton.addEventListener('click', () => {
    stopReviewSlideshow();
    prevReview();
    startReviewSlideshow();  // Restart slideshow after manual review change
});

// Initial call to display the first review and start the automatic slideshow
showReview(currentReview);
startReviewSlideshow();
