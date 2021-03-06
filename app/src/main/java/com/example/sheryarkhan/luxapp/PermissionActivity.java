package com.example.sheryarkhan.luxapp;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.aware.DiscoverySession;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class PermissionActivity extends AppCompatActivity {

    RadioButton consentRadioButton;
    RadioButton nonConsentRadioButton;
    Button btnConfrim;
    public static boolean radio = true;
    public static ProgressBar progressBar = null;
    static View parentLayout;
    private static Multipart _multipart;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);


        consentRadioButton = (RadioButton) findViewById(R.id.consentRadioButton);
        nonConsentRadioButton = (RadioButton) findViewById(R.id.nonConsentRadioButton);
        btnConfrim = (Button) findViewById(R.id.btnConfirm);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        parentLayout = findViewById(android.R.id.content);


        consentRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio = true;

            }
        });

        nonConsentRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio = false;
            }
        });


        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                File storagePath = null;
                if (radio) {
                    storagePath = new File(Environment.
                            getExternalStorageDirectory() + "/LuxSelfies/Consent");
                } else {
                    storagePath = new File(Environment.
                            getExternalStorageDirectory() + "/LuxSelfies/NonConsent");
                }
                storagePath.mkdirs();

                File myImage = new File(storagePath, "/" + LoginActivity.user.getName() + "_" + LoginActivity.user.getEmail() + ".jpg");

//
//                try {
//                    FileOutputStream out = new FileOutputStream(myImage);
//                    SampleActivity.newImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
//
//
//                    out.flush();
//                    out.close();
//                } catch (FileNotFoundException e) {
//                    Log.d("In Saving File", e + "");
//                } catch (IOException e) {
//                    Log.d("In Saving File", e + "");
//                }


                try {
                    String[] myTaskParams = {"Lux Selfie", "Lux", "consent750@gmail.com", LoginActivity.user.getEmail()};

                    //GMailSender sender = new GMailSender("szabistdemo@gmail.com", "demoforszabist");



                    SaveImageTask saveImageTask = new SaveImageTask();
                    saveImageTask.execute(SampleActivity.newImage,myImage);

                    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if (mWifi.isConnected()) {
                        // Do whatever
                        progressBar.setVisibility(View.VISIBLE);
                        btnConfrim.setClickable(false);
                        GMailSender sender = new GMailSender("consent750@gmail.com", "ailaan123");

                        addAttachment(myImage.toString());

                        RetrieveReadTask task = new RetrieveReadTask(PermissionActivity.this);
                        task.execute(myTaskParams);
                    }else {
                        Toast.makeText(getBaseContext(),"Wifi is not connected!",Toast.LENGTH_SHORT).show();
                    }



//                    sender.sendMail("This is Subject",
//                            "This is Body anmsdbasdbakjdbkjasbdbas",
//                            "szabistdemo@gmail.com",
//                            LoginActivity.user.getEmail());
//                } catch (NetworkErrorException e) {
//                    Toast.makeText(getBaseContext(),"WIFI disabled, please turn on WIFI!",Toast.LENGTH_SHORT).show();
//
                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(),"Network Error!",Toast.LENGTH_SHORT).show();
                    //Log.d("SendMail", ex.getMessage(), ex);
                }

                //Toast.makeText(getBaseContext(),String.valueOf(radio),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAttachment(String filename) throws Exception {

        _multipart = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(filename);

        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
//        messageBodyPart.setHeader("Content-ID", "<image>");
//        DataSource ds = new ByteArrayDataSource(image, "image/jpeg");
        messageBodyPart.setFileName("Lux Selfie.jpg");

        _multipart.addBodyPart(messageBodyPart);

    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }

    private class SaveImageTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... data) {



//                File sdCard = Environment.getExternalStorageDirectory();
//                File dir = new File(sdCard.getAbsolutePath() + "/Name");
//                dir.mkdirs();
//
//                String fileName = "Any Name";
//                File outFile = new File(dir, fileName);

                try {
                    Bitmap image = (Bitmap) data[0];
                    FileOutputStream out = new FileOutputStream((File) data[1]);
                    image.compress(Bitmap.CompressFormat.JPEG, 80, out);


                    out.flush();
                    out.close();

//                catch(FileNotFoundException e)
//                {
//                    Log.d("In Saving File", e + "");
//                }
//                catch(IOException e)
//                {
//                    Log.d("In Saving File", e + "");
//                }
//
//                outStream = new FileOutputStream(outFile);
//                outStream.write(data[0]);
//                outStream.flush();
//                outStream.close();


                    //refreshGallery(outFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                }
                return null;


        }

        @Override
        protected void onPostExecute(Void result) {



        }
    }


    public static class RetrieveReadTask extends AsyncTask<String,String,Void> {


        Context context;
        private RetrieveReadTask(Context context) {
            this.context = context.getApplicationContext();
        }
        @Override
        protected Void doInBackground(String... params) {
            try{
                MimeMessage message = new MimeMessage(GMailSender.session);
                //DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
                message.setSender(new InternetAddress(params[2]));
                message.setSubject(params[0]);
                message.setText("Lux Selfie");
                message.setContent(_multipart);
                //message.setDataHandler(handler);
                if (params[3].indexOf(',') > 0)
                {

                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(params[3]));
                    //message.addRecipient(Message.RecipientType.TO, InternetAddress.parse("consent750@gmail.com")); //Consent Email

                }
                    //message.addRecipient(Message.RecipientType.CC, InternetAddress);
                else
                    if(radio)
                    {
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(params[3])); // User Email
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("consent750@gmail.com")); //Consent Email
                    }
                    else
                    {
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(params[3])); //User Email
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress("nonconcent@gmail.com")); //NonConsent Email
                    }
                Transport.send(message);
            }catch(MessagingException ex){

                Log.d("dada",ex.toString());
            }
            catch(Exception e){
                Log.d("dad2",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            progressBar.setVisibility(View.GONE);
//            Snackbar snackbar = Snackbar.make(parentLayout,"Selfie emailed!",Snackbar.LENGTH_LONG);
//            View view = snackbar.getView();
//            TextView tv = (TextView)view.findViewById(android.support.design.R.id.snackbar_text);
//            tv.setTextColor(Color.RED);
//            snackbar.show();


            Toast.makeText(context,"Selfie emailed!",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context,splashScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
