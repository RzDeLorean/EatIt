package fr.delodev.eatit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import fr.delodev.eatit.R;
import fr.delodev.eatit.models.UserModel;

public class SignInActivity extends AppCompatActivity {

    //region Private fields
    private MaterialEditText editPhone, editPassword, editName;
    private Button btnSignIn;
    //endregion

    //region Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editPhone    = (MaterialEditText)findViewById(R.id.editPhone);
        editName     = (MaterialEditText)findViewById(R.id.editName);

        btnSignIn    = (Button) findViewById(R.id.btnSignIn);

        //Initializes Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog((SignInActivity.this));
                progressDialog.setMessage("Please, wait a sec...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {

                        //Check if the user exist in the database
                        if(snapshot.child(editPhone.getText().toString()).exists())
                        {
                            progressDialog.dismiss();
                            //Then, gets the user information
                            UserModel user = snapshot.child(editPhone.getText().toString()).getValue(UserModel.class);

                            if(user.getPassword() == editPassword.getText().toString())
                                Toast.makeText(SignInActivity.this, "Sign In successfull !", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(SignInActivity.this, "Sign In failed !", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(SignInActivity.this, "You're not registered !", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });
            }
        });
    }
    //endregion
}