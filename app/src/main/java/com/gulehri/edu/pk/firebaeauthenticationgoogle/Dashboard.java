package com.gulehri.edu.pk.firebaeauthenticationgoogle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.gulehri.edu.pk.firebaeauthenticationgoogle.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        updateUI();

        binding.signOut.setOnClickListener(view -> {
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
            googleSignInClient.signOut();

            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        account = GoogleSignIn.getLastSignedInAccount(Dashboard.this);

        String name = account.getDisplayName();
        Uri imgUrl = account.getPhotoUrl();
        String email = account.getEmail();

        Glide.with(this).load(imgUrl).placeholder(R.drawable.user).into(binding.profileImage);
        binding.email.setText(email);
        binding.name.setText(name);
        binding.email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail, 0, 0, 0);
        binding.name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.name, 0, 0, 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}