package com.utils;
/*
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class MainActivity extends Activity  implements OnClickListener{
 
	Session session=null;
	ProgressDialog pdialog=null;
	Context context=null;
	EditText reciept=null;
	EditText sub=null;
	EditText msg=null;
	String recpient=null;
	String subject=null;
	String textmessage=null;
 
	  @Override
	  protected void onCreate(Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_main);
      context=this;
	  Button login = (Button) findViewById(R.id.send);
	  reciept=(EditText)findViewById(R.id.editText_to);
	  sub = (EditText) findViewById(R.id.editText_sub);
	  msg = (EditText) findViewById(R.id.editText_text);
	  reciept.setText("guedirabr@gmail.com");
 
	  login.setOnClickListener(this); 
 
 
	  }
 
		@Override
		public void onClick(View v) {
 
			recpient= reciept.getText().toString();
		    subject= sub.getText().toString();
		    textmessage= msg.getText().toString();
 
			 Properties props = new Properties();
			  props.put("mail.smtp.host", "smtp.gmail.com");
			  props.put("mail.smtp.socketFactory.port", "465");
			  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			  props.put("mail.smtp.auth", "true");
			  props.put("mail.smtp.port", "465");
 
			  session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			                    protected PasswordAuthentication getPasswordAuthentication() {
			  return new PasswordAuthentication("guedirabr@gmail.com", "judo1986");
			  }
			  });
			  pdialog = ProgressDialog.show(context, "", "Sending Mail...",true);
			  RetreiveFeedTask task= new RetreiveFeedTask();
			  task.execute();
		}
 
 
	  class RetreiveFeedTask extends AsyncTask<String, Void, String> {
 
 
		  protected String doInBackground(String... urls) {
		        try {
 
		        	  Message message = new MimeMessage(session);
		        	  message.setFrom(new InternetAddress("guedirabr@gmail.com"));
		        	  message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recpient));
		        	  message.setSubject(subject);
		        	  message.setContent(textmessage, "text/html; charset=utf-8");
 
 
		        	  Transport.send(message);
 
 
		        } 
		        catch (MessagingException e) {
	        		 e.printStackTrace();
	        		  }
		        catch (Exception e) {
		           e.printStackTrace();
 
		        }
		        return null;
		    }
 
		    protected void onPostExecute(String feed) {
		    	pdialog.dismiss();
		    	reciept.setText("");
		    	msg.setText("");
		    	sub.setText("");
		    	Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
 
		    }
	  }
 
 
	}

*/