package com.example.eventify;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.eventify.Prevalent.Prevalent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private FloatingActionButton floatingActionButton;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    View myFragment;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String societyName,societyDesc,societyDomain,societyPhone;
    MembersAdapter adapter;
    Dialog dialogPay;
    EditText Sname,Sdesc,Sphone,Sdomain;
    EditText empName,empPost,empTemp;
    ImageView img;
    RelativeLayout addStaffDialogBtn;
    private ProgressDialog mProProgress;
    EditText searchEdt,phoneNo;
    private CountryCodePicker ccp;
    Button save;
    String nameS,postS,tempS,phoneS;
    DocumentReference documentReference;
    public String productRandomKey,saveCurrentDate,saveCurrentTime;
    private static final int CAMERA_REQUEST_CODE=100;
    private static final int STORAGE_REQUEST_CODE=200;

    private static final int IMAGE_PICK_CAMERA_CODE=300;
    private static final int IMAGE_PICK_GALLERY_CODE=400;
    private final int PICK_IMAGE = 71;
    private static final int gallerypic = 1;
    private Uri ImageUri,ImageUri2;
    public static final String Storage_Path = "ImageFolder";
    private StorageReference ProductImageRefs;
    private String  downloadImageUrl;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment= inflater.inflate(R.layout.fragment_home, container, false);
        Paper.init(getActivity());
        mProProgress = new ProgressDialog(getActivity());
        ProductImageRefs = FirebaseStorage.getInstance().getReference().child("Society Images");
        dialogPay=new Dialog(getActivity());
        dialogPay.setContentView(R.layout.diabled_staff_dialog);
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Societies");
        Bundle bundle=getArguments();
        societyName=bundle.getString("Sname");
        Sname=myFragment.findViewById(R.id.society_name);
        Sphone=myFragment.findViewById(R.id.phone_edt);
        Sdesc=myFragment.findViewById(R.id.society_desc);
        Sdomain=myFragment.findViewById(R.id.society_domain);
        img=myFragment.findViewById(R.id.cameraview);
        if (societyName!=null){
            Sname.setText(societyName);
        }
        empName=dialogPay.findViewById(R.id.edt_name_add_staff_dis);
        empPost=dialogPay.findViewById(R.id.edt_desig_add_staff_dis);
        phoneNo=dialogPay.findViewById(R.id.phone_txt);
        save=myFragment.findViewById(R.id.save_changes);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                societyDesc=Sdesc.getText().toString();
                societyDomain=Sdomain.getText().toString();
                societyPhone=Sphone.getText().toString();
                if (societyName==null){
                    Toast.makeText(getActivity(), "Please enter society name", Toast.LENGTH_SHORT).show();
                }
                else if (societyDesc==null){
                    Toast.makeText(getActivity(), "Please enter society description", Toast.LENGTH_SHORT).show();
                }
                else if (societyDomain==null){
                    Toast.makeText(getActivity(), "Please enter society domain", Toast.LENGTH_SHORT).show();
                }else if (societyPhone==null){
                    Toast.makeText(getActivity(), "Please enter society phone", Toast.LENGTH_SHORT).show();
                }
                else{
                    StoreProductInfo();
                }
            }
        });
        ccp=dialogPay.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phoneNo);
        layoutManager = new LinearLayoutManager(getActivity());
        addStaffDialogBtn=dialogPay.findViewById(R.id.add_staff_dialog_btn_dis);
        addStaffDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new HomeFragment();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("Sname", societyName);//put string, int, etc in bundle with a key value
                data.putString("name",empName.getText().toString());
                data.putString("post",empPost.getText().toString());
                final String getNo = "+"+ccp.getFullNumber();
                data.putString("phone",getNo);
                newFragment.setArguments(data);
                // consider using Java coding conventions (upper first char class names!!!)
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frameLayout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                dialogPay.dismiss();
            }
        });
        Query query=collectionReference.document(societyName).collection("staff").orderBy("Mname",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Members> options=new FirestoreRecyclerOptions.Builder<Members>()
                .setQuery(query,Members.class).build();
        floatingActionButton=myFragment.findViewById(R.id.add_member);
        recyclerView=myFragment.findViewById(R.id.offers_rv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new MembersAdapter(options);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.startListening();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPay.show();
            }
        });
        return myFragment;
    }

    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, gallerypic);
    }
    public void ChooseImageToUpload(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,PICK_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallerypic && resultCode == RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            img.setImageURI(ImageUri);

        } else if (resultCode == RESULT_CANCELED){

        }
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getClipData() != null ) {

            int countClipData=data.getClipData().getItemCount();
            int currentImageSelect=0;
//            while(currentImageSelect < countClipData)
//            {
//                ImageUri2=data.getClipData().getItemAt(currentImageSelect).getUri();
//                imageList.add(ImageUri2);
//                currentImageSelect++;
//            }
//            alerttext.setText("You have selected "+imageList.size()+" Images!");
        }

//        else {
//            Toast.makeText(this,"Please Select Multiple Image",Toast.LENGTH_SHORT).show();
//        }

    }
    private void StoreProductInfo() {
        mProProgress.setTitle("Adding New Society");
        mProProgress.setMessage("Please wait while we are adding new product !");
        mProProgress.setCanceledOnTouchOutside(false);
        mProProgress.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
//        customId = uidS;
        final StorageReference filepath = ProductImageRefs.child(ImageUri.getLastPathSegment() + societyName + ".jpg");
        final UploadTask uploadTask = filepath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getActivity(), "Error:" + message, Toast.LENGTH_SHORT).show();
                mProProgress.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Product Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();

                        }
                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(getActivity(), "got Product Image Saved to Database Successfully", Toast.LENGTH_SHORT).show();
                            storeData();
                        }
                    }
                });
            }
        });
    }

    private void storeData(){
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("image", downloadImageUrl);
        productMap.put("Title",societyName);
        productMap.put("Sdesc",societyDesc);
        productMap.put("Sdomain",societyDomain);
        productMap.put("Sphone",societyPhone);
        Paper.book().write(Prevalent.society,societyName);
        collectionReference.document(societyName).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mProProgress.dismiss();
                    Toast.makeText(getActivity(), "Details Saved Successfully", Toast.LENGTH_SHORT).show();
//                    Fragment newFragment = new HomeFragment();
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    // Replace whatever is in the fragment_container view with this fragment,
//                    // and add the transaction to the back stack
//                    transaction.replace(R.id.frameLayout, newFragment);
//                    transaction.addToBackStack(null);
//
//                    // Commit the transaction
//                    transaction.commit();
                }else{
                    String error = task.getException().getMessage();
                    Toast.makeText(getActivity(), "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        nameS=bundle.getString("name");
        postS=bundle.getString("post");
        phoneS=bundle.getString("phone");
        if (nameS!=null){
            addData();
        }
        String society=Paper.book().read(Prevalent.society);
        collectionReference.document(societyName).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot!=null && documentSnapshot.exists()){
                    Society society1=documentSnapshot.toObject(Society.class);
                    if (society1.getImage()!=null){
                        Picasso.get().load(society1.getImage()).into(img);
                    }
                    if (society1.getTitle()!=null){
                        Sname.setText(society1.getTitle());
                    }
                    if (society1.getSdesc()!=null){
                        Sdesc.setText(society1.getSdesc());
                    }
                    if (society1.getSdomain()!=null){
                        Sdomain.setText(society1.getSdomain());
                    }
                    if (society1.getSphone()!=null){
                        Sphone.setText(society1.getSphone());
                    }
                }
            }
        });
    }
    private void addData() {
        String name,post,temp,phone,email;
        name=nameS;
        post=postS;
        temp=tempS;
        phone=phoneS;
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate+saveCurrentTime;
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("Mname",name);
        productMap.put("Mpost",post);
        productMap.put("Mphone",phone);
        documentReference=collectionReference.document(societyName);
        documentReference.collection("staff").document(phone).set(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Details stored successfully", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getActivity(), "error while uploading", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}