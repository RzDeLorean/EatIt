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

import org.jetbrains.annotations.NotNull;

import fr.delodev.eatit.R;
import fr.delodev.eatit.models.UserModel;

public class SignUpActivity extends AppCompatActivity {

    //region Private fields
    private MaterialEditText editPhone, editPassword, editName;
    private Button btnSignUp;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editPassword = (MaterialEditText)findViewById(R.id.editPassword);
        editPhone    = (MaterialEditText)findViewById(R.id.editPhone);
        editName     = (MaterialEditText)findViewById(R.id.editName);

        btnSignUp    = (Button) findViewById(R.id.btnSignUp);

        //Initializes Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog((SignUpActivity.this));
                progressDialog.setMessage("Please, wait a sec...");
                progressDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        progressDialog.dismiss();

                        //Check if the user is already existing
                        if(snapshot.child(editPhone.getText().toString()).exists())
                            Toast.makeText(SignUpActivity.this, "You're already registered !", Toast.LENGTH_SHORT).show();
                        else
                        {
                            //Creates a new user
                            UserModel user = new UserModel(editName.getText().toString(), editPassword.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUpActivity.this, "You're now registered !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });
    }
}