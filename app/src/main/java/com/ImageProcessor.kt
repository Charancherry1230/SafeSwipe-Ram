fun processImage(imageProxy: ImageProxy, onResult: (String) -> Unit) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val rawText = visionText.text
                val regex = Regex("\\b(?:\\d[ -]*?){13,16}\\b")
                val match = regex.find(rawText)?.value ?: ""
                if (match.isNotEmpty()) onResult(match.replace(" ", "").replace("-", ""))
            }
            .addOnFailureListener {
                Log.e("SafeSwipe", "OCR failed", it)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}
