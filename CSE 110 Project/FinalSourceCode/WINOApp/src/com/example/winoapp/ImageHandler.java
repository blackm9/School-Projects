package com.example.winoapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageHandler {
	
	/**
	 * Return value can be null ! make sure to check this
	 * 
	 * camera default stores an original copy of image in a path
	 * so we need to delete that copy after resizing and storing a new image
	 */
	public static Bitmap processCameraImage (String cameraImagePath, double imageRatio) {
	    File f = new File(cameraImagePath);
	    Bitmap b = null;
	    if (f.exists()) {
	    	// resize image depends on file size
	    	int sampleSize = 4;
	    	if (f.length() >= 1024*1024*4) // if greater than 4mb
	    		sampleSize = 8;
	
	    	b = loadImage (cameraImagePath, imageRatio, sampleSize);
	    	f.delete(); // delete the first copy of photo
		}
	    else {
	    	Log.d("Unable to load file",":(");
	    }
	    return b;
	}
	
	/**
	 * This method has two results:
	 * 1. return the correct bitmap
	 * 2. throw exception
	 * Therefore return value should not be null
	 */
	public static Bitmap loadImage (String imagePath, double imageRatio, int sampleSize) {
		Bitmap b = null;
		BitmapFactory.Options o = new BitmapFactory.Options();
		
    	// image size will = original size / inSampleSize
    	// sampleSize has to be 2^x where x > 0
        o.inSampleSize = sampleSize;
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, o);
        
        // rescale image
        int h = (int)(o.outHeight*imageRatio);
        int w = (int)(o.outWidth*imageRatio);

        Log.d("ANDRO_ASYNC",String.format("going in h=%d w=%d resample = %d",h,w,o.inSampleSize));

        o.inJustDecodeBounds = false;
        try{
        	b = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(imagePath, o), w, h, true);
        }catch(OutOfMemoryError e){
            Log.d("ANDRO_ASYNC",String.format("catch Out Of Memory error"));
            e.printStackTrace();
            System.gc();
        }
		return b;
	}
	
	public static void storeBitmap(Bitmap b, String filepath) {
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		b.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		File f = new File(filepath);
		try {
			// create new file use the filepath
			f.createNewFile();
			// write the bytes in file
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());

			fo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
