package carteckh.carteckh.fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import carteckh.carteckh.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickImage extends Fragment {

    Button save,del;
    ImageView image1,image2,image3,image4;
    Bitmap bm;
    int check=0;
    TextView textView;
    String encodedImage1 = "";
    String encodedImage2 = "";
    String encodedImage3 = "";
    String encodedImage4 = "";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.pick_image, container, false);
        save=(Button)view.findViewById(R.id.save);
        image1=(ImageView)view.findViewById(R.id.image1);
        image2=(ImageView)view.findViewById(R.id.image2);
        image3=(ImageView)view.findViewById(R.id.image3);
        image4=(ImageView)view.findViewById(R.id.image4);
        textView =(TextView)view.findViewById(R.id.text);

        //del=(Button)view.findViewById(R.id.del);
//    del.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            image1.setImageDrawable(null);
//            image1.setImageResource(0);
//        }
//    });

            final Bundle bundle=getArguments();
            final String val_brad1=bundle.getString("b1_brandlist");
            final String val_model1=bundle.getString("b1_modellist");
            final String val_version=bundle.getString("b1_verlist");
            final String val_months=bundle.getString("b1_monthlist");
            final String val_year=bundle.getString("b1_yearlist");
            final String val_kms=bundle.getString("b1_kmslist");
            final String val_location=bundle.getString("b1_locationlist");
            final String val_pin=bundle.getString("b1_pinlist");
            final String val_price=bundle.getString("b1_pricelist");
            final String val_owner=bundle.getString("b1_ownerlist");
            final String val_seller=bundle.getString("b1_sellerlist");
            final String val_insurance=bundle.getString("b1_insuranceist");
            final String val_fuel=bundle.getString("b1_fuellist");
            final int val_transmission=bundle.getInt("b1_transmissionlist");
            final String val_mobile=bundle.getString("b1_mobile");

        //textView.setText(val_brad);
        //save.setText(val_brad1);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(check==1) {

                    Fragment fragment = new Show_Details();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("b2_brandlist", val_brad1);
                    bundle1.putString("b2_modellist", val_model1);
                    bundle1.putString("b1_verlist", val_version);
                    bundle1.putString("b1_monthlist", val_months);
                    bundle1.putString("b1_yearlist", val_year);
                    bundle1.putString("b1_kmslist", val_kms);
                    bundle1.putString("b1_locationlist", val_location);
                    bundle1.putString("b1_pinlist", val_pin);
                    bundle1.putString("b1_pricelist", val_price);
                    bundle1.putString("b1_ownerlist", val_owner);
                    bundle1.putString("b1_sellerlist", val_seller);
                    bundle1.putString("b1_insuranceist", val_insurance);
                    bundle1.putString("b1_fuellist", val_fuel);
                    bundle1.putInt("b1_transmissionlist", val_transmission);
                    bundle1.putString("b1_mobile", val_mobile);
                    bundle1.putString("image1", encodedImage1);
                    bundle1.putString("image2", encodedImage2);
                    bundle1.putString("image3", encodedImage3);
                    bundle1.putString("image4", encodedImage4);
                    fragment.setArguments(bundle1);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.container, fragment).addToBackStack("test").commit();
                }
                else
                {
                    Toast.makeText(getActivity(), "Please Select Default Image", Toast.LENGTH_SHORT).show();
                }
//

            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        1);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        2);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        3);
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        4);
            }
        });



//        image1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CharSequence[] items = { "Take from Camera", "Choose from Gallery",
//                        "Cancel" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Add Photo!");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        try {
//                            if (items[item].equals("Take from Camera")) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 0);
//                            } else if (items[item].equals("Choose from Gallery")) {
//                                Intent intent = new Intent(
//                                        Intent.ACTION_PICK,
//                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.setType("image/*");
//                                startActivityForResult(
//                                        Intent.createChooser(intent, "Select File"),
//                                        1);
//                            } else if (items[item].equals("Cancel")) {
//                                dialog.dismiss();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(),"errorrr...",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                builder.setCancelable(false);
//                builder.show();
//            }
//        });
//        image2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CharSequence[] items = { "Take from Camera", "Choose from Gallery",
//                        "Cancel" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Add Photo!");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        try {
//                            if (items[item].equals("Take from Camera")) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 5);
//                            } else if (items[item].equals("Choose from Gallery")) {
//                                Intent intent = new Intent(
//                                        Intent.ACTION_PICK,
//                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.setType("image/*");
//                                startActivityForResult(
//                                        Intent.createChooser(intent, "Select File"),
//                                        2);
//                            } else if (items[item].equals("Cancel")) {
//                                dialog.dismiss();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(),"errorrr...",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                builder.setCancelable(false);
//                builder.show();
//            }
//        });
//
//        image3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CharSequence[] items = { "Take from Camera", "Choose from Gallery",
//                        "Cancel" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Add Photo!");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        try {
//                            if (items[item].equals("Take from Camera")) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 6);
//                            } else if (items[item].equals("Choose from Gallery")) {
//                                Intent intent = new Intent(
//                                        Intent.ACTION_PICK,
//                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.setType("image/*");
//                                startActivityForResult(
//                                        Intent.createChooser(intent, "Select File"),
//                                        3);
//                            } else if (items[item].equals("Cancel")) {
//                                dialog.dismiss();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(),"errorrr...",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                builder.setCancelable(false);
//                builder.show();
//            }
//        });
//        image4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final CharSequence[] items = { "Take from Camera", "Choose from Gallery",
//                        "Cancel" };
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Add Photo!");
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        try {
//                            if (items[item].equals("Take from Camera")) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 7);
//                            } else if (items[item].equals("Choose from Gallery")) {
//                                Intent intent = new Intent(
//                                        Intent.ACTION_PICK,
//                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                intent.setType("image/*");
//                                startActivityForResult(
//                                        Intent.createChooser(intent, "Select File"),
//                                        4);
//                            } else if (items[item].equals("Cancel")) {
//                                dialog.dismiss();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(),"errorrr...",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                builder.setCancelable(false);
//                builder.show();
//            }
//        });


        return  view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1)
                onSelectFromGalleryResult(data);
            else if (requestCode == 2)
                onSelectFromGalleryResult2(data);
            else if (requestCode == 3)
                onSelectFromGalleryResult3(data);
            else if (requestCode == 4)
                onSelectFromGalleryResult4(data);

            else if (requestCode == 0)
                onCaptureImageResult(data);
            else if (requestCode == 5)
                onCaptureImageResult02(data);
            else if (requestCode == 6)
                onCaptureImageResult03(data);
            else if (requestCode == 7)
                onCaptureImageResult04(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image1.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image1.setLayoutParams(layoutParams);
        image1.setImageBitmap(bm);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        check=1;
    }
    private void onCaptureImageResult02(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image2.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image2.setLayoutParams(layoutParams);
        image2.setImageBitmap(bm);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void onCaptureImageResult03(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image3.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image3.setLayoutParams(layoutParams);
        image3.setImageBitmap(bm);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage3 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void onCaptureImageResult04(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image4.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image4.setLayoutParams(layoutParams);
        image4.setImageBitmap(bm);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage4 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor =getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 100;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        image1.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image1.setLayoutParams(layoutParams);
        image1.setImageBitmap(bm);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();

            encodedImage1 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //image1.setMaxHeight(40);
        check=1;
    }

    private void onSelectFromGalleryResult2(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor =getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 100;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        image2.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image2.setLayoutParams(layoutParams);
        image2.setImageBitmap(bm);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void onSelectFromGalleryResult3(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor =getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 100;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        image3.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image3.setLayoutParams(layoutParams);
        image3.setImageBitmap(bm);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage3 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void onSelectFromGalleryResult4(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor =getActivity().getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 100;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        image4.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 230);
        image4.setLayoutParams(layoutParams);
        image4.setImageBitmap(bm);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage4 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //Toast.makeText(getActivity(), encodedImage, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
