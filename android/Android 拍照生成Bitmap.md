# Android 拍照生成Bitmap

```java
private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    try {
        startActivityForResult(takePictureIntent, 1);
    } catch (ActivityNotFoundException e) {
        Log.d("markTest", "ex=" + e.getMessage());
    }
}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1) {
        Bundle extras = Objects.requireNonNull(data).getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        imgShow.setImageBitmap(imageBitmap);
    }
}
```