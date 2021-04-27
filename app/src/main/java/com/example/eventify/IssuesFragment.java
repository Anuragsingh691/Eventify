package com.example.eventify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eventify.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class IssuesFragment extends Fragment {
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    View myFragment;
    private ProgressDialog mProProgress;
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
    public EditText name,desc,date,time,venue;
    String Name,Desc,Date,Time,Venue;
    public Button save;
    public ImageView img;
    String societyName;

    public IssuesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment= inflater.inflate(R.layout.fragment_issues, container, false);
        name=myFragment.findViewById(R.id.name_edt);
        desc=myFragment.findViewById(R.id.desc_edt);
        venue=myFragment.findViewById(R.id.venue_edt);
        date=myFragment.findViewById(R.id.date_edt);
        time=myFragment.findViewById(R.id.time_edt);
        save=myFragment.findViewById(R.id.add);
        Paper.init(getActivity());
        img=myFragment.findViewById(R.id.add_img);
        mProProgress = new ProgressDialog(getActivity());
        ProductImageRefs = FirebaseStorage.getInstance().getReference().child("Events Images");
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Societies");
        Bundle bundle1=getArguments();
        societyName=bundle1.getString("Sname");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=name.getText().toString();
                Desc=desc.getText().toString();
                Date=date.getText().toString();
                Time=time.getText().toString();
                Venue=venue.getText().toString();
                if (Name!=null && Desc!=null && Date!=null && Time!=null && Venue!=null){
                    StoreProductInfo();
                }
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
        mProProgress.setTitle("Adding New Event");
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
        productMap.put("name",Name);
        productMap.put("desc",Desc);
        productMap.put("date",Date);
        productMap.put("time",Time);
        productMap.put("venue",Venue);
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate+saveCurrentTime;
        productMap.put("id",productRandomKey);
        productMap.put("society",societyName);
        collectionReference.document(societyName).collection("Events").document(productRandomKey).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}