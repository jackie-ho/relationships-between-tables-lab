package com.adi.ho.jackie.relationships_db_lab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addDataButton;
    Button employeesSameCompany;
    Button companiesinBoston;
    Button companyHighestSalary;
    TextView highestPayingText;
    ListView listView;
    ArrayList<String> infoList;
    ArrayAdapter<String> mAdapter;
    SimpleCursorAdapter bostonCompaniesAdapter;
    ListView listView2;
    SimpleCursorAdapter employeesSameCompanyAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDataButton = (Button) findViewById(R.id.add_data);
        employeesSameCompany = (Button) findViewById(R.id.employees_samecompany);
        companiesinBoston = (Button) findViewById(R.id.boston_companies);
        companyHighestSalary = (Button) findViewById(R.id.companies_with_highestsalaries);
        highestPayingText = (TextView)findViewById(R.id.highestpaying_salary);
        fab  = (FloatingActionButton)findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listview);
        listView2 = (ListView)findViewById(R.id.listview2);
        infoList = new ArrayList<>();

        bostonCompaniesAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,null, new String[]{"company"},new int[]{android.R.id.text1});
        listView.setAdapter(bostonCompaniesAdapter);
        employeesSameCompanyAdapter = new SimpleCursorAdapter(MainActivity.this,android.R.layout.simple_list_item_2,null, new String[]{"firstname","lastname"},new int[]{android.R.id.text1,android.R.id.text2});
        listView2.setAdapter(employeesSameCompanyAdapter);
        addDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Employee, Void, Void>() {
                    @Override
                    protected Void doInBackground(Employee... params) {
                        Employee employee = new Employee("John", "Smith", 1973, "NY", "123-04-5678");
                        Employee employee1 = new Employee("David", "Sarker", 1982, "Seattle", "123-04-5679");
                        Employee employee2 = new Employee("Katerina", "Wise", 1973, "Boston", "123-04-5680");
                        Employee employee3 = new Employee("Donald", "Lee", 1992, "London", "123-04-5681");
                        Employee employee4 = new Employee("Gary", "Hemwood", 1987, "Las Vegas", "123-04-5682");
                        Employee employee5 = new Employee("Anthony", "Bright", 1963, "Seattle", "123-04-5683");
                        Employee employee6 = new Employee("William", "Newey", 1995, "Boston", "123-04-5684");
                        Employee employee7 = new Employee("Melony", "Smith", 1970, "Chicago", "123-04-5685");

                        Job job1 = new Job("123-04-5678", "Fuzz", 60, 1);
                        Job job2 = new Job("123-04-5679", "GA", 70, 2);
                        Job job3 = new Job("123-04-5680", "Little Place", 120, 5);
                        Job job4 = new Job("123-04-5681", "Macy's", 78, 3);
                        Job job5 = new Job("123-04-5682", "New Life", 65, 1);
                        Job job6 = new Job("123-04-5683", "Believe", 158, 6);
                        Job job7 = new Job("123-04-5684", "Macy's", 200, 8);
                        Job job8 = new Job("123-04-5685", "Stop", 299, 12);

                        Helper helper = Helper.getInstance(MainActivity.this);
                        helper.insertRow(employee);
                        helper.insertRow(employee1);
                        helper.insertRow(employee2);
                        helper.insertRow(employee3);
                        helper.insertRow(employee4);
                        helper.insertRow(employee5);
                        helper.insertRow(employee6);
                        helper.insertRow(employee7);

                        helper.insertRowDepartment(job1);
                        helper.insertRowDepartment(job2);
                        helper.insertRowDepartment(job3);
                        helper.insertRowDepartment(job4);
                        helper.insertRowDepartment(job5);
                        helper.insertRowDepartment(job6);
                        helper.insertRowDepartment(job7);
                        helper.insertRowDepartment(job8);

                        // String name = helper.getNameJoins();
                        return null;
                    }

                }.execute();
            }
        });

        companiesinBoston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   SimpleCursorAdapter bostonCompaniesAdapter = new SimpleCursorAdapter(MainActivity.this,android.R.layout.simple_list_item_1,cursor, new String[]{"city"}, new int[]{android.R.id.text1});
                Helper helper = Helper.getInstance(MainActivity.this);
                Cursor cursor = helper.companiesInBoston();
                bostonCompaniesAdapter.swapCursor(cursor);
            }
        });

        employeesSameCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper helper = Helper.getInstance(MainActivity.this);
                Cursor cursor = helper.employeesInSameCompany();
                employeesSameCompanyAdapter.swapCursor(cursor);
            }
        });

        companyHighestSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper helper = Helper.getInstance(MainActivity.this);
                highestPayingText.setText("The highest paying company is " + helper.getHighestPaying());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Insert Data");

                // Set up the input
                final EditText ssn = new EditText(MainActivity.this);
                final EditText firstname = new EditText(MainActivity.this);
                final EditText secondname = new EditText(MainActivity.this);
                final EditText city = new EditText(MainActivity.this);
                final EditText year = new EditText(MainActivity.this);

                //builder.setView(ssn);
                builder.setView(R.layout.dialog_layout);


                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Helper helper = Helper.getInstance(MainActivity.this);
                        helper.insertRow(new Employee(firstname.getText().toString(),secondname.getText().toString(),Integer.parseInt(year.getText().toString()),city.getText().toString(),ssn.getText().toString()));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }
}
