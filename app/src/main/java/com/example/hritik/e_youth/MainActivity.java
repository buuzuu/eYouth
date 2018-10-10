package com.example.hritik.e_youth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hritik.e_youth.Adapter.ViewPagerAdapter;
import com.example.hritik.e_youth.Common.Common;
import com.example.hritik.e_youth.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.main_layout)
    RelativeLayout rootLayout;
    ViewPagerAdapter adapter;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.btnSignin)
    Button btnSignIn;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    private String verificationId;
    private MaterialEditText edtOtp;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    public AlertDialog.Builder dialog;
    private AlertDialog alertDialog;
    private FirebaseDatabase database;
    private DatabaseReference users;
    MaterialEditText edtEmail;
    MaterialEditText edtName;
    MaterialEditText edtPassword;
    private AlertDialog waitingDialog;
    MaterialEditText edtPhone;
    private GoogleSignInClient mGoogleSignInClient;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        waitingDialog = new SpotsDialog(MainActivity.this);
        users = database.getReference("Users");
        adapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(adapter);
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                if (currentPage == 5) {
                    currentPage = 0;
                }

                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignInDialog();
            }
        });

        findViewById(R.id.google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase

                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
//                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
// [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]

        waitingDialog.show();

        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(MainActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();

                            Log.d(TAG, "onComplete: " + user.getEmail());
                            Log.d(TAG, "onComplete: " + user.getPhoneNumber());
                            Log.d(TAG, "onComplete: " + user.getDisplayName());
                            Log.d(TAG, "onComplete: " + user.getPhotoUrl());

                            //    updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed Sorry.", Snackbar.LENGTH_SHORT).show();
                            //       updateUI(null);
                        }

                        // [START_EXCLUDE]
                        waitingDialog.dismiss();
                        // [END_EXCLUDE]
                    }
                });
    }
// [END auth_with_google]


    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //       updateUI(null);
                    }
                });
    }


    private void showRegisterDialog() {

        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setTitle("Register");
        checkforRegisteredNumber();
        dialog.setMessage("PLease use email to register");
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_register, null);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtName = view.findViewById(R.id.edtName);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtPhone = view.findViewById(R.id.edtPhone);
        Button getOTP = view.findViewById(R.id.getOTP);
        edtOtp = view.findViewById(R.id.edtOtp);
        dialog.setView(view);
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = "+91" + edtPhone.getText().toString().trim();

                if (TextUtils.isEmpty(edtEmail.getText().toString())) {

                    Snackbar.make(rootLayout, "Please Enter Email Address..", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtPhone.getText().toString())) {

                    Snackbar.make(rootLayout, "Please Enter Phone Number..", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (edtPassword.getText().toString().length() < 6) {

                    Snackbar.make(rootLayout, "Please Too Short...", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtName.getText().toString())) {

                    Snackbar.make(rootLayout, "Please Enter Name..", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.d(TAG, "onSuccess: " + number);
                        for (int i = 0; i < Common.registederPhone.size(); i++) {


                            if (edtPhone.getText().toString().equals(Common.registederPhone.get(i)) ) {
                                check = true;
                            }

                        }
                        if (check==true){
                            check=false;
                            Snackbar.make(view, "Phone Number Already Registered !", Snackbar.LENGTH_LONG).show();

                            Common.registederPhone.clear();
                            alertDialog.dismiss();
                        }else if(check==false) {
                            Common.registederPhone.clear();
                            sendVerificationCode(number);
                        }
                        waitingDialog.show();


                        Toast.makeText(MainActivity.this, "Waiting for OTP...", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //      Snackbar.make(rootLayout,"Failed !"+e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog=dialog.create();
        alertDialog.show();

    }

    private void showSignInDialog() {

        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setTitle("SIGN IN");
        dialog.setMessage("PLease use email to sign in");
        View login_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_login, null);
        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = login_layout.findViewById(R.id.edtPassword);

        dialog.setView(login_layout);
        ButterKnife.bind(login_layout, MainActivity.this);

        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                btnSignIn.setEnabled(false);

                if (TextUtils.isEmpty(edtEmail.getText().toString())) {

                    Snackbar.make(rootLayout, "Please Enter Email Address..", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtPassword.getText().toString())) {

                    Snackbar.make(rootLayout, "Please Enter Password..", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (edtPassword.getText().toString().length() < 6) {

                    Snackbar.make(rootLayout, "Please Too Short...", Snackbar.LENGTH_SHORT).show();
                    return;
                }


                final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                waitingDialog.show();
                //Sign in new user

                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        waitingDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitingDialog.dismiss();
                        Snackbar.make(rootLayout, "Failed " + e.getMessage(), Snackbar.LENGTH_SHORT).show();

                        btnSignIn.setEnabled(true);
                    }
                });


            }
        });


        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    //OTP CODE DOWN HERE


    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            users.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                    //                        //Save user to db
                                    User user = new User(edtEmail.getText().toString(), edtName.getText().toString(), edtPassword.getText().toString(), edtPhone.getText().toString());
                                    StringBuilder builder=new StringBuilder(edtEmail.getText().toString());
                                    users.child(String.valueOf(builder.substring(0,builder.indexOf("@"))))
                                            .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            waitingDialog.dismiss();
                                            Snackbar.make(rootLayout, "Registration Successful--"+edtName.getText().toString(), Snackbar.LENGTH_SHORT).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    alertDialog.dismiss();
                                                }
                                            },1800);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(rootLayout, "Failed !" + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    alertDialog.dismiss();
                                                }
                                            },1800);

                                        }
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                }
                            });


                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                edtOtp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void checkforRegisteredNumber() {

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                    Iterator<DataSnapshot> iterable = dataSnapshot.getChildren().iterator();

                    while (iterable.hasNext()) {


                        DataSnapshot tempItem = iterable.next();
                        Common.registederPhone.add(tempItem.child("phone").getValue().toString());

                    }

                }else {
                    check=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
