package tnpify.tnp_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewResume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        TextView textView1 =(TextView)findViewById(R.id.cv_analytics);
        textView1.setClickable(true);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());
        String link1 = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Analytics </a>";
        textView1.setText(Html.fromHtml(link1));

        TextView textView2 =(TextView)findViewById(R.id.cv_core);
        textView2.setClickable(true);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        String link2 = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Core (Technical) </a>";
        textView2.setText(Html.fromHtml(link2));

        TextView textView3 =(TextView)findViewById(R.id.cv_consulting);
        textView3.setClickable(true);
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        String link3 = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Consulting</a>";
        textView3.setText(Html.fromHtml(link3));

        TextView textView4 =(TextView)findViewById(R.id.cv_finance);
        textView4.setClickable(true);
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        String link4 = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Finance</a>";
        textView4.setText(Html.fromHtml(link4));

        TextView textView5 =(TextView)findViewById(R.id.cv_management);
        textView5.setClickable(true);
        textView5.setMovementMethod(LinkMovementMethod.getInstance());
        String link5 = "<a href='http://www.cse.iitd.ernet.in/~cs1120244/RAHUL_PATIDAR_CV.pdf'>Management</a>";
        textView5.setText(Html.fromHtml(link5));

    }
    Intent intent = getIntent();
}
