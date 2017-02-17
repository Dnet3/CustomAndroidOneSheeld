package com.integreight.onesheeld.services;

import android.content.Intent;
import android.app.Service;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.integreight.onesheeld.model.ArduinoHistoryObject;

import java.io.File;

/**
 * Created by Max Deasy on 14/2/17.
 *
 * This service is started using an Intent that contains phone data to be sent
 * to Firebase Storage and Firebase Database
 *
 * This code is modified from an example found @ https://github.com/firebase/quickstart-android/tree/master/storage/app/src/main/java/com/google/firebase/quickstart/firebasestorage
 */

public class FirebaseUploadService extends Service {

    /** Intent Actions **/
    public static final String ACTION_UPLOAD = "action_upload";

    /** Intent Extras **/
    public static final String EXTRA_ARDUINO_ID = "extra_arduino_id",
            EXTRA_ARDUINO_HISTORY_ENTRY = "extra_arduino_hsitory_entry";

    // A variable to hold a reference to the arduino folder in Firebase Storage
    private StorageReference mArduinoStorageRef;
    // A variable to hold a reference to the arduino section of the Firebase Database
    private DatabaseReference mArduinoDatabaseRef;
    // A variable to track the number of upload tasks
    private int mNumTasks = 0;

    // Android lifecycle method that is called when this service is created
    @Override
    public void onCreate() {
        super.onCreate();

        // Get a reference to Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://lammsite-fc813.appspot.com");
        // Get a reference to the Arduinos folder
        mArduinoStorageRef = storageRef.child("arduinos");

        // Get a reference to Firebase Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lammsite-fc813.firebaseio.com");
        // Get a reference to the Arduinos table
        mArduinoDatabaseRef = databaseRef.child("arduinos");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Android Service method that is called when this service is initiated using the startService(Intent intent) method
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(FirebaseUploadService.class.getSimpleName(), "onStartCommand:" + intent + ":" + startId);
        // If the Intent action is set to ACTION_UPLOAD
        if (intent.getAction().equals(ACTION_UPLOAD)) {
            // Retrieve the Arduino ID and the location_history object from the Intent
            String arduinoID = intent.getStringExtra(EXTRA_ARDUINO_ID);
            ArduinoHistoryObject arduinoHistoryEntry = intent.getParcelableExtra(EXTRA_ARDUINO_HISTORY_ENTRY);
            // Pass retrieved objects to the upload method
            upload(arduinoID, arduinoHistoryEntry);
        }

        return START_REDELIVER_INTENT;
    }

    // Custom method for uploading a photo to Firebase Storage then creating a Firebase Database entry
    private void upload(final String arduinoID, final ArduinoHistoryObject arduinoHistoryEntry) {

        // Track that the upload task has started
        taskStarted();

        Log.d(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:src:" + arduinoHistoryEntry.getImageStorageAddress());
        Log.d(FirebaseUploadService.class.getSimpleName(), "uploadFromUri: Photo upload begin:" + 0 + "%");

        // Get the full Uri to the image file
        final Uri imageUri = Uri.fromFile(new File(arduinoHistoryEntry.getImageStorageAddress()));
        // Get a reference to store the file at gs://lammsite-fc813.appspot.com/arduino/<ARDUINO_ID>/<FILENAME>.jpg
        final StorageReference imageRef = mArduinoStorageRef.child(arduinoID).child(imageUri.getLastPathSegment());

        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();

        Log.d(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:dst:" + imageRef.getPath());
        // Upload file to Firebase Storage
        imageRef.putFile(imageUri, metadata).
                addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        // Log the progress of the image upload at 25% intervals
                        long totalBytes = taskSnapshot.getTotalByteCount();
                        int percentComplete = 0;
                        if (totalBytes > 0) {
                            percentComplete = (int) (100 * taskSnapshot.getBytesTransferred() / totalBytes);
                        }

                        if (percentComplete % 25 == 0) {
                            Log.d(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:onProgress: Photo upload progress:" + percentComplete + "%");
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get the public download URL
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getDownloadUrl() != null) {
                                // Upload phone info to Firebase Database
                                Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                                Log.d(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:onSuccess: Photo upload complete. Download uri = " + downloadUri);

                                // Change the image storage address from the device folder to the web host address
                                arduinoHistoryEntry.setImageStorageAddress(downloadUri.toString());
                                // Get the current timestamp
                                Long timestampLong = System.currentTimeMillis() / 1000;
                                // Get a reference to this arduino device in the database and then the location_history branch
                                DatabaseReference locationHistoryDatabaseRef = mArduinoDatabaseRef.child(arduinoID).child("location_history");
                                // Create a new location_history entry using the timestamp as an identifier
                                locationHistoryDatabaseRef.child(String.valueOf(timestampLong)).setValue(arduinoHistoryEntry);

                                // Show a toast to the user to inform them that this entry has been saved to the website
                                Toast.makeText(FirebaseUploadService.this, "LAMM Secure: Upload complete", Toast.LENGTH_LONG).show();

                                // Query the Arduino database for the entries with an ID equal to the arduinoID
                                /*int id = Integer.parseInt(arduinoID);
                                Query query = mArduinoDatabaseRef.orderByChild("id").equalTo(id);
                                DatabaseReference arduinoIDDatabaseRef = query.getRef();
                                DatabaseReference historyDatabaseRef = arduinoIDDatabaseRef.child("history");
                                // Add an entry to that specific Arduino history
                                historyDatabaseRef.push().setValue(arduinoHistoryEntry);*/
                            }
                            else {
                                Log.e(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:onSuccess: Photo upload complete. Download uri was NULL ");
                            }
                        }

                        // Track that the task has completed
                        taskCompleted();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(FirebaseUploadService.class.getSimpleName(), "uploadFromUri:onFailure: Photo upload failed. Exception = " + exception);

                        // Track that the task has completed
                        taskCompleted();
                    }
                });
    }

    // Method for increasing the number of tasks
    public void taskStarted() {
        changeNumberOfTasks(1);
    }

    // Method for decreasing the number of tasks
    public void taskCompleted() {
        changeNumberOfTasks(-1);
    }

    // Method for stopping this service if all tasks are complete
    private synchronized void changeNumberOfTasks(int delta) {
        Log.d(FirebaseUploadService.class.getSimpleName(), "changeNumberOfTasks:" + mNumTasks + ":" + delta);
        mNumTasks += delta;

        // If there are no tasks left, stop this service
        if (mNumTasks <= 0) {
            Log.d(FirebaseUploadService.class.getSimpleName(), "stopping");
            stopSelf();
        }
    }
}
