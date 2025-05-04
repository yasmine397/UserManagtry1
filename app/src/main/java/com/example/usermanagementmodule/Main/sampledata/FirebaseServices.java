package com.example.usermanagementmodule.Main.sampledata;

import android.net.Uri;

import com.example.usermanagementmodule.DataUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseServices {

    private static FirebaseServices instance;
    private FirebaseAuth auth;
    private FirebaseFirestore fire;
    private FirebaseStorage storage;
    private Uri selectedImageURL;
    private DataUser currentUser;
    private boolean userChangeFlag;

    public Uri getSelectedImageURL() {
        return selectedImageURL;
    }

    public FirebaseServices() {
        auth = FirebaseAuth.getInstance();
        fire = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        userChangeFlag = false;
    }
    
    public static FirebaseServices getInstance() {
        if (instance == null) {
            instance = new FirebaseServices();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseFirestore getFire() {
        return fire;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }
    
    public DataUser getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(DataUser user) {
        this.currentUser = user;
        this.userChangeFlag = true;
    }
    
    public boolean isUserChanged() {
        return userChangeFlag;
    }
    
    public void resetUserChangeFlag() {
        this.userChangeFlag = false;
    }

    public void setSelectedImageURL(Uri uri) {
        this.selectedImageURL = uri;
    }
    
    /**
     * Logs out the current user
     */
    public void logout() {
        auth.signOut();
        currentUser = null;
    }
    
    /**
     * Checks if a user is currently logged in
     * @return boolean indicating if user is logged in
     */
    public boolean isLoggedIn() {
        return auth.getCurrentUser() != null;
    }
    
    /**
     * Updates the current user data in Firestore
     */
    public void updateUserData() {
        if (currentUser != null && auth.getCurrentUser() != null) {
            fire.collection("users")
                .document(auth.getCurrentUser().getUid())
                .set(currentUser);
        }
    }
}
