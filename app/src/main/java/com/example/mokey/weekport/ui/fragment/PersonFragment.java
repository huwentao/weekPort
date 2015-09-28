package com.example.mokey.weekport.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mokey.weekport.R;
import com.example.mokey.weekport.data.annotation.XmlUtil;
import com.example.mokey.weekport.data.user.User;
import com.example.mokey.weekport.db.exception.DbException;
import com.example.mokey.weekport.ui.core.BaseFragment;
import com.example.mokey.weekport.util.TextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_TITLE = "arg_param_title";
    private static final String ARG_PARAM_USER = "arg_param_user";

    // TODO: Rename and change types of parameters
    private User mUser;

    @Bind(R.id.userName) EditText userName;
    @Bind(R.id.IDNum) EditText IDNum;
    @Bind(R.id.filePath) EditText filePath;
    private File mWeekPortDirecoty;
    private Logger logger = LoggerFactory.getLogger(PersonFragment.class);
    private boolean isNewPerson = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param user  Parameter 2.
     * @return A new instance of fragment PersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonFragment newInstance(String title, User user) {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, title);
        args.putParcelable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setTitle(getArguments().getString(ARG_PARAM_TITLE));
            mUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(storageState)) {
            mWeekPortDirecoty = XmlUtil.getXmlProjectDirectory();
            if (mWeekPortDirecoty.exists() || mWeekPortDirecoty.mkdirs()) {
            }
        }
        if (mUser != null) {
            TextUtil.setText(userName, mUser.getReportPerson());
            TextUtil.setText(IDNum, mUser.getIdNo());
            TextUtil.setText(filePath, mWeekPortDirecoty.getAbsolutePath());
        }
    }

    public void savePerson() {
        if (isNewPerson) mUser = new User();
        mUser.setReportPerson(userName.getText().toString());
        mUser.setIdNo(IDNum.getText().toString());
        filePath.setInputType(InputType.TYPE_NULL);
        try {
            getBaseActivity().getDbUtils().saveOrUpdate(mUser);
        } catch (DbException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void newPerson() {
        isNewPerson = true;
        TextUtil.setText(userName, null);
        TextUtil.setText(IDNum, null);
    }
}
