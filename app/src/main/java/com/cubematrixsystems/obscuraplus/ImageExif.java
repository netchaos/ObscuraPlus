package com.cubematrixsystems.obscuraplus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by vimal on 14-09-04.
 */
public class ImageExif {

    private String imagePath;

    ImageExif(String imagePath){
        this.imagePath = imagePath;
    }

    public LinkedHashMap getImageExif()
    {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        try{
            ExifInterface exif = new ExifInterface(imagePath);

            map.put("Model", exif.getAttribute(ExifInterface.TAG_MODEL));
            map.put("Make", exif.getAttribute(ExifInterface.TAG_MAKE));
            map.put("Date Time", exif.getAttribute(ExifInterface.TAG_DATETIME));
            if(exif.getAttribute(ExifInterface.TAG_FLASH) != null) {
                map.put("Flash", getFlashType(exif.getAttribute(ExifInterface.TAG_FLASH)));
            }
            map.put("Orientation", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
            map.put("Aperture", exif.getAttribute(ExifInterface.TAG_APERTURE));
            map.put("Exposure Time", exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME));
            map.put("Image Length", exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH));
            map.put("Image Width", exif.getAttribute(ExifInterface.TAG_IMAGE_WIDTH));
            map.put("ISO", exif.getAttribute(ExifInterface.TAG_ISO));
            map.put("Focal Length", exif.getAttribute(ExifInterface.TAG_FOCAL_LENGTH));
            map.put("White Balance", exif.getAttribute(ExifInterface.TAG_WHITE_BALANCE));
            map.put("GPS Altitude", exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE));
            map.put("GPS Altitude Ref", exif.getAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF));
            map.put("GPS Date Stamp", exif.getAttribute(ExifInterface.TAG_GPS_DATESTAMP));
            map.put("GPS Latitude", exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE));
            map.put("GPS Latitude Ref", exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));
            map.put("GPS Longitude", exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE));
            map.put("GPS Longitude Ref", exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));
            map.put("GPS Processing Method", exif.getAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD));
            map.put("GPS Time Stamp", exif.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP));
        }
        catch (IOException e)
        {
            //ToDO
        }

        return map;
    }

    public Bitmap getImageThumb()
    {
        Bitmap bitmap = null;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            if(exif != null) {

                byte[] thumbnail = exif.getThumbnail();

                if (thumbnail != null) {
                    bitmap = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
                } else {
                    bitmap = BitmapFactory.decodeFile(imagePath);
                }
            }
        }
        catch (Exception e){
            //ToDO
        }

        return bitmap;
    }

    private String getFlashType(String flashTypeNumber)
    {
        String flashType;
        int flashInt = Integer.parseInt(flashTypeNumber);

        HashMap<Integer, String> focusSet = new HashMap<Integer, String>();

        focusSet.put(0, "Flash did not fire");
        focusSet.put(1, "Flash fired");
        focusSet.put(5, "Strobe return light not detected");
        focusSet.put(7, "Strobe return light detected");
        focusSet.put(9, "Flash fired, compulsory flash mode");
        focusSet.put(13, "Flash fired, compulsory flash mode, return light not detected");
        focusSet.put(15, "Flash fired, compulsory flash mode, return light detected");
        focusSet.put(16, "Flash did not fire, compulsory flash mode");
        focusSet.put(24, "Flash did not fire, auto mode");
        focusSet.put(25, "Flash fired, auto mode");
        focusSet.put(29, "Flash fired, auto mode, return light not detected");
        focusSet.put(31, "Flash fired, auto mode, return light detected");
        focusSet.put(32, "No flash function");
        focusSet.put(65, "Flash fired, red-eye reduction mode");
        focusSet.put(69, "Flash fired, red-eye reduction mode, return light not detected");
        focusSet.put(71, "Flash fired, red-eye reduction mode, return light detected");
        focusSet.put(73, "Flash fired, compulsory flash mode, red-eye reduction mode");
        focusSet.put(77, "Flash fired, compulsory flash mode, red-eye reduction mode, return light not detected");
        focusSet.put(79, "Flash fired, compulsory flash mode, red-eye reduction mode, return light detected");
        focusSet.put(89, "Flash fired, auto mode, red-eye reduction mode");
        focusSet.put(93, "Flash fired, auto mode, return light not detected, red-eye reduction mode");
        focusSet.put(95, "Flash fired, auto mode, return light detected, red-eye reduction mode");

        String value = focusSet.get(flashInt);

        if(value != null)
        {
            flashType = value;
        }else{
            flashType = "No flash data";
        }

        return flashType;
    }

    public void ClearGPSData()
    {
        try{
            ExifInterface exif = new ExifInterface(imagePath);
            exif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP, " ");
            exif.saveAttributes();
        }
        catch (Exception e){
            //Todo
        }
    }

    public void ClearAllData()
    {
        try{
            ExifInterface exif = new ExifInterface(imagePath);
            exif.setAttribute(ExifInterface.TAG_MODEL, " ");
            exif.setAttribute(ExifInterface.TAG_MAKE, " ");
            exif.setAttribute(ExifInterface.TAG_DATETIME, " ");
            exif.setAttribute(ExifInterface.TAG_FLASH, " ");
            exif.setAttribute(ExifInterface.TAG_APERTURE, " ");
            exif.setAttribute(ExifInterface.TAG_EXPOSURE_TIME, " ");
            exif.setAttribute(ExifInterface.TAG_ISO, " ");
            exif.setAttribute(ExifInterface.TAG_FOCAL_LENGTH, " ");
            exif.setAttribute(ExifInterface.TAG_WHITE_BALANCE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_ALTITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_DATESTAMP, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_PROCESSING_METHOD, " ");
            exif.setAttribute(ExifInterface.TAG_GPS_TIMESTAMP, " ");
            exif.saveAttributes();
        }
        catch (Exception e){
            //Todo
        }
    }
}
