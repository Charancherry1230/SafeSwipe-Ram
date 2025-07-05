@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SafeSwipeScreen() {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current

    var cardNumber by remember { mutableStateOf("") }
    var isValidCard by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("SafeSwipe - Credit Card Detector", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(10.dp))

        if (cameraPermissionState.hasPermission) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = androidx.camera.core.Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val analyzer = ImageAnalysis.Builder()
                            .setTargetResolution(Size(1280, 720))
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .also {
                                it.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                                    processImage(imageProxy) { number ->
                                        cardNumber = number
                                        isValidCard = isValidCardNumber(number)
                                    }
                                }
                            }

                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            context as ComponentActivity,
                            cameraSelector,
                            preview,
                            analyzer
                        )
                    }, ContextCompat.getMainExecutor(ctx))

                    previewView
                },
                modifier = Modifier.height(400.dp)
            )
        } else {
            Text("Camera permission is required.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cardNumber.isNotEmpty()) {
            Text("Detected Card Number: $cardNumber")
            Text("Valid Card: ${if (isValidCard == true) "✅ Valid" else "❌ Invalid"}")
        }
    }
}
