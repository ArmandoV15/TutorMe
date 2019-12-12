package com.example.tutorme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import static android.text.TextUtils.isEmpty;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

        private static final String TAG = "RegisterActivity";

        //widgets
        private EditText mEmail, mPassword, mConfirmPassword, mName;
        Spinner majorSpinner, yearSpinner;
        DatabaseReference dbRef;


        private FirebaseFirestore mDb;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        mEmail = (EditText) findViewById(R.id.emailEditText);
        mPassword = (EditText) findViewById(R.id.passwordEditText);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);
        mName = (EditText) findViewById(R.id.nameEditText);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        majorSpinner = (Spinner)findViewById(R.id.majorSpinner);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);


        ArrayAdapter<String> majorAdapter = new ArrayAdapter<String>(RegisterPage.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Majors));

        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(RegisterPage.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Year));

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        ;

        findViewById(R.id.submitButton).setOnClickListener(this);


        mDb = FirebaseFirestore.getInstance();

        hideSoftKeyboard();
    }

        /**
         * Register a new email and password to Firebase Authentication
         * @param email
         * @param password
         */
        public void registerNewEmail(final String email, String password){


        getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + getInstance().getCurrentUser().getUid());

                            //insert some default data
                            final String uid = getInstance().getUid();
                            User user = new User();
                            user.setEmail(email);
                            user.setName(email.substring(0, email.indexOf("@")));
                            user.setUser_id(uid);

                            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                                    .setTimestampsInSnapshotsEnabled(true)
                                    .build();
                            mDb.setFirestoreSettings(settings);


                            DocumentReference newUserRef = mDb
                                    .collection("Users")
                                    .document(uid);

                            newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        // Add the user to the realtime database
                                        User newUser = new User(mName.getText().toString(),
                                                mEmail.getText().toString(),
                                                uid,
                                                majorSpinner.getSelectedItem().toString(),
                                                yearSpinner.getSelectedItem().toString(),
                                                true);

                                        dbRef.push().setValue(newUser);


                                        redirectLoginScreen();
                                    }else{
                                        View parentLayout = findViewById(android.R.id.content);
                                    }
                                }
                            });

                        }
                        else {
                            View parentLayout = findViewById(android.R.id.content);

                        }

                        // ...
                    }
                });
    }

        /**
         * Redirects the user to the login screen
         */
        private void redirectLoginScreen(){
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterPage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }



        private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

        public static boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    /**
     onClick listener for the register button which takes all the entered fields and enters them into our Firebase real time database.
     Also does checks for password match and makes sure all fields are filled.
     * @param view
     */
        @Override
        public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitButton:{
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if(!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())){

                    //check if passwords match
                    if(doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())){

                        //Initiate registration task
                        registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
                        finishActivity(RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(RegisterPage.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterPage.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

}
