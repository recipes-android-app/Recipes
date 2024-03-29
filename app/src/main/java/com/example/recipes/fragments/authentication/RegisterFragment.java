package com.example.recipes.fragments.authentication;

import com.example.recipes.MainActivity;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentRegisterBinding;
import com.example.recipes.model.ModelClient;
import com.example.recipes.model.UserModel;
import com.example.recipes.dto.User;
import com.example.recipes.model.interfaces.AuthenticationListener;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    ProgressDialog pd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pd = new ProgressDialog(getActivity());
        ((MainActivity) getActivity()).setBottomNavigationVisibility(view.GONE);
        ((MainActivity) getActivity()).hideSupportActionBar();

        binding.loginUser.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_registerFragment_to_loginFragment);
        });

        binding.registerBtn.setOnClickListener(v -> {
            String txtName = binding.name.getText().toString();
            String txtEmail = binding.email.getText().toString();
            String txtPassword = binding.password.getText().toString();

            if (TextUtils.isEmpty(txtName)
                    || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                Toast.makeText(getContext(),"Empty credentials!",Toast.LENGTH_LONG).show();
            } else if (txtPassword.length() < 6) {
                Toast.makeText(getContext(),"Password too short!",Toast.LENGTH_LONG).show();
            } else {
                registerUser(txtName, txtEmail, txtPassword, view);
            }
        });

        this.listenToBackButtonClick(view);

        return view;
    }

    private void registerUser(final String name, final String email, String password, View view) {
        pd.setMessage("Please wait...");
        pd.show();

        User user = new User("", name, email, "");
        ModelClient.instance().users.registerUser(user, password, new AuthenticationListener() {
            @Override
            public void onComplete() {
                pd.dismiss();
                ModelClient.instance().users.refreshCurrentUser();
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_recipesListPageFragment);

            }

            @Override
            public void onFailed(String err) {
                Log.e("E", err);
                pd.dismiss();
                Toast.makeText(getContext(),err,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listenToBackButtonClick(View view) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack();
                Navigation.findNavController(view).navigate(R.id.loginFragment);            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}