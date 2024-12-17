
    let currentIndex = 0;
    let galleryImages = [];

    function showImageInModal(image) {
        // Collect all image elements only the first time
        if (galleryImages.length === 0) {
            galleryImages = Array.from(document.querySelectorAll('.image-card.large-img'));
        }

        // Set the current index based on the clicked image's data-index
        currentIndex = parseInt(image.getAttribute('data-index'));

        // Show the clicked image in the modal
        updateModalImage();
        $('#imageModal').modal('show');
    }

    function updateModalImage() {
        const imageSrc = galleryImages[currentIndex].src;
        document.getElementById('modalImage').src = imageSrc;
    }

    function nextImage() {
        currentIndex = (currentIndex + 1) % galleryImages.length; // Loop to the start
        updateModalImage();
    }

    function previousImage() {
        currentIndex = (currentIndex - 1 + galleryImages.length) % galleryImages.length; // Loop to the end
        updateModalImage();
    }

