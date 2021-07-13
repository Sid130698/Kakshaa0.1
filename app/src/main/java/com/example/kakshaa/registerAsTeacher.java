package com.example.kakshaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerAsTeacher extends AppCompatActivity {
    EditText regAsTeacherName,regAsTeacherEmail,regAsTeacherPassword,regAsTeacherConfPassword;
    Button submitButton;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userNameS,userEmailS,passwordS,confPasswordS;
    CustomLoadingScreen registerDialogBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_teacher);
        intializeFields();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameS = regAsTeacherName.getText().toString();
                userEmailS = regAsTeacherEmail.getText().toString();
                passwordS = regAsTeacherPassword.getText().toString();
                confPasswordS = regAsTeacherConfPassword.getText().toString();
                if (userNameS.isEmpty()) {
                    regAsTeacherName.setError("name Cannot be Empty");
                    return;
                }
                if (userEmailS.isEmpty()) {
                    regAsTeacherEmail.setError("Email Cannot be Empty");
                    return;
                }
                if (passwordS.isEmpty()) {
                    regAsTeacherPassword.setError("passowrd Cannot be Empty");
                    return;
                }
                if (confPasswordS.isEmpty()) {
                    regAsTeacherConfPassword.setError("This Field is empty");
                    return;
                }
                if (!passwordS.equals(confPasswordS)) {
                    regAsTeacherConfPassword.setError("Password doNot match");
                    return;
                } else {
                    registerDialogBox.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    registerDialogBox.setTitle("Registering User");

                    registerDialogBox.setCanceledOnTouchOutside(true);
                    registerDialogBox.show();
                    firebaseAuth.createUserWithEmailAndPassword(userEmailS, passwordS).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            createAccount();
                            registerDialogBox.dismiss();
                            startActivity(new Intent(registerAsTeacher.this,DashBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registerDialogBox.dismiss();
                            Toast.makeText(registerAsTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            regAsTeacherPassword.setText(" ");
                            regAsTeacherConfPassword.setText(" ");
                        }
                    });

                }
            }
        });
    }

    private void createAccount() {
        databaseReference.child("Teacher").child(firebaseAuth.getCurrentUser().getUid()).setValue("");
        HashMap<String,String> TeacherDetails=new HashMap<>();
        TeacherDetails.put("teacherName",userNameS);
        TeacherDetails.put("userId",firebaseAuth.getCurrentUser().getUid());
        databaseReference.child("Teacher").child(firebaseAuth.getCurrentUser().getUid()).setValue(TeacherDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(registerAsTeacher.this, "dataStored", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(registerAsTeacher.this,DashBoardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(registerAsTeacher.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void intializeFields() {
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        regAsTeacherName=(EditText)findViewById(R.id.registerAsTeacherEnterNameEditText);
        regAsTeacherEmail=(EditText)findViewById(R.id.registerAsTeacherEmailIDEditText);
        regAsTeacherPassword=(EditText)findViewById(R.id.registerAsTeacherPasswordEditText);
        regAsTeacherConfPassword=(EditText)findViewById(R.id.registerAsTeacherConfPasswordEditText);
        submitButton=(Button)findViewById(R.id.submitAsTeacherRegisterBtn);
        registerDialogBox=new CustomLoadingScreen(this);
    }
}