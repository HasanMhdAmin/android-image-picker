# android-image-picker
Allow the user to select one or multiple images from a pre-defined image list provided by the developer

- Download images from external URLs
- Save the images locally
- Allow the user to select one or more from this list
- Return the selected images to the previews view controller

## Change Mode
To change between the single and the multiple mode modify this variable

```java
Intent gallery = new Intent(MainActivity.this, GalleryActivity.class);
gallery.putParcelableArrayListExtra(IMAGES, images);
gallery.putExtra(ALLOW_MULTI_SELECTION, switchMood.isChecked());
startActivityForResult(gallery, GALLERY_REQUEST_CODE);
```

## Add image form deferat resources

```java
// Drawable
new ImageItem("android.resource://com.murmuras.MockPhotoGallery/drawable/murmuras")
// Asset: 
new ImageItem("file:///android_asset/images/murmuras.png")
// Local file: 
new ImageItem("file://" + file.getAbsolutePath())
// External url: 
new ImageItem("https://upload.wikimedia.org/wikipedia/commons/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg")
```
