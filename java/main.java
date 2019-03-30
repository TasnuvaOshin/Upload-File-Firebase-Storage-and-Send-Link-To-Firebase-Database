
  public class MainActivity extends AppCompatActivity {


      private static final int PICK_FILE = 1 ;
      private DatabaseReference databaseReference;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          databaseReference = FirebaseDatabase.getInstance().getReference().child("UserOne");

      }


      public void FileUpload(View view) {

          Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
          intent.setType("*/*");
          startActivityForResult(intent,PICK_FILE);


      }


      @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          super.onActivityResult(requestCode, resultCode, data);


          if(requestCode == PICK_FILE){

              if(resultCode == RESULT_OK){


              Uri FileUri = data.getData();

              StorageReference Folder = FirebaseStorage.getInstance().getReference().child("Files");


              final StorageReference file_name = Folder.child("file"+FileUri.getLastPathSegment());



             file_name.putFile(FileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                     file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {

                         HashMap<String,String> hashMap = new HashMap<>();
                         hashMap.put("filelink", String.valueOf(uri));


                           databaseReference.setValue(hashMap);
                             Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();


                         }
                     });



                 }
             });



              }



          }



      }
  }







  a
