package com.example.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    ArrayList<MessageModel> messageHolder;
    RecyclerView chatRecview;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1,String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("odaIsmi");
            mParam2 = getArguments().getString("email");
            mParam3 = getArguments().getString("chatRoomName");
        }
       // Log.d("mParam3",mParam3);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        DatabaseReference reference2;

        Log.d("mp3",mParam3);

        reference2 = FirebaseDatabase.getInstance().getReference().child(mParam1).child("rooms");
        Log.d("ref2",reference2.toString());

        Log.d("ref2",reference2.child(mParam3).toString());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageHolder.clear();

                for(DataSnapshot rmchild: dataSnapshot.getChildren()) {
                    Log.d("rmdeta", rmchild.getValue().toString());
                    Log.d("rmdeta2", rmchild.getKey());

                    if(rmchild.getKey().equals(mParam3)){
                        for (DataSnapshot subsubrmchild : rmchild.getChildren()) {
                           /*if(subsubrmchild.child("email").getValue().toString().equals(mParam2)){
                            }else{
                            }*/
                            MessageModel message_model = new MessageModel(subsubrmchild.child("email").getValue().toString(), subsubrmchild.child("message").getValue().toString());
                            messageHolder.add(message_model);
                        }
                    }
                }

                //dataHolder.clear();
                MessageAdapter messageAdapter = new MessageAdapter(messageHolder);
                chatRecview.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("message","firebase error");

            }


        });


        chatRecview = view.findViewById(R.id.chatRecview);
        chatRecview.setLayoutManager(new LinearLayoutManager(getContext()));
        messageHolder = new ArrayList<>();

        MessageAdapter adapter = new MessageAdapter(messageHolder);
        chatRecview.setAdapter(adapter);

        Button sendButton = view.findViewById(R.id.chatSendButton);
        EditText chatMessageText = (EditText) view.findViewById(R.id.chatMessageText);



        //DatabaseReference reference1;
        //reference1 = FirebaseDatabase.getInstance().getReference(); //.child(mParam1).child("rooms").child("message");
        //Log.d("mp3",mParam3);

        DatabaseReference reference1;
        reference1 = FirebaseDatabase.getInstance().getReference().child(mParam1).child("rooms").child(mParam3);
        Log.d("sss",reference1.getKey());
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageModel message = new MessageModel(mParam2,chatMessageText.getText().toString());
                messageHolder.add(message);
                chatRecview.setAdapter(adapter);
                //reference1.push().setValue("burhan@burhan.com");
                //reference1.push().setValue("selam");

                Map<String, String> messageData = new HashMap<>();
                messageData.put("email",mParam2);
                messageData.put("message",chatMessageText.getText().toString());


                //reference1.child("email").setValue(mParam2);
                //reference1.child("message").setValue(chatMessageText.getText().toString());
                reference1.push().setValue(messageData);
                chatMessageText.setText("");
            }
        });
        return view;
    }
}