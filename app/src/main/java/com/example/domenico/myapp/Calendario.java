package com.example.domenico.myapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import android.view.View.OnClickListener;



public class Calendario extends FragmentActivity  {
    private static final String tag = "MyCalendarActivity";

    private BottomNavigationView bottomNavigationView;


    private TextView currentMonth;
    private Button selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    private Calendar today;
    private int currentMonthInt;
    private int giornoCiclo = 1;
    boolean isOddMonday = true;
    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db = null;

    private int month, year;
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cal_utente);

        db = MainActivity.dbHelper.getWritableDatabase();

        bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        i.setClass(getApplicationContext(), HomepageCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_news:
                        i.setClass(getApplicationContext(), AvvisiCittadino.class);
                        startActivity(i);
                        break;
                    case R.id.navigation_info:
                        i.setClass(getApplicationContext(),Contatti.class);
                        startActivity(i);
                        break;
                }
                return false;
            }
        });




        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: " + year);

//        selectedDayMonthYearButton = (Button) this.findViewById(R.id.selectedDayMonthYear);
//        selectedDayMonthYearButton.setText("Selected: ");

        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        Log.d(tag,"PrevMonth is = "+prevMonth);
        prevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(tag,"PrevMonth clicked. Is = "+prevMonth);
                giornoCiclo=1;
                isOddMonday=true;
                if (month <= 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: " + month + " Year: " + year);
                setGridCellAdapterToDate(month, year);
            }
        });

        currentMonth = (TextView) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,_calendar.getTime()));

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        Log.d(tag,"nextMonth is = "+nextMonth);
        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                giornoCiclo=1;
                isOddMonday=true;
                Log.d(tag,"nextMonth clicked. Is = "+nextMonth);
                if (month > 11) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
                Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
                        + month + " Year: " + year);
                setGridCellAdapterToDate(month, year);
            }
        });

        calendarView = (GridView) this.findViewById(R.id.calendar);

// Initialised
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);



    }


    /**
     *
     * @param month
     * @param year
     */
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }



    // Inner Class
    public class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
        private final String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };
        private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private TextView gridcell;
        private ImageView imgGiorno;
//        private TextView num_events_per_day;
//        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId, int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Log.d(tag, "==> Passed in Date FOR Month: " + month + " " + "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
            Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
            Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
            Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

// Print Month
            printMonth(month, year);

// Find Number of Events
//            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy) {
            Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            currentMonthInt = mm - 1;
            String currentMonthName = getMonthAsString(currentMonthInt);
            daysInMonth = getNumberOfDaysOfMonth(currentMonthInt);

            Log.d(tag, "Current Month: " + " " + currentMonthName + " having " + daysInMonth + " days.");

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonthInt, 1);
            Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

            if (currentMonthInt == 11) {
                prevMonth = currentMonthInt - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
                Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
            } else if (currentMonthInt == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
                Log.d(tag, "**-> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
            } else {
                prevMonth = currentMonthInt - 1;
                nextMonth = currentMonthInt + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                Log.d(tag, "***—> PrevYear: " + prevYear + " PrevMonth:" + prevMonth + " NextMonth: " + nextMonth + " NextYear: " + nextYear);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            Log.d(tag, "Week Day:" + currentWeekDay + " is " + getWeekDayAsString(currentWeekDay));
            Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
            Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

// Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                Log.d(tag, "PREV MONTH:= "+ prevMonth + " => " + getMonthAsString(prevMonth) + " " + String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i));
                list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
            }

            Calendar today = Calendar.getInstance();

// Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {
                Log.d(currentMonthName, String.valueOf(i) + " " + getMonthAsString(currentMonthInt) + " " + yy);
//                Log.d(currentMonthName,"currentMonthInt = "+currentMonthInt + "calendarMonth = "+today.get(Calendar.MONTH));
                if (i == getCurrentDayOfMonth() && currentMonthInt==today.get(Calendar.MONTH)) {
                    list.add(String.valueOf(i) + "-RED" + "-" + getMonthAsString(currentMonthInt) + "-" + yy);
                } else {
                    list.add(String.valueOf(i) + "-BLACK" + "-" + getMonthAsString(currentMonthInt) + "-" + yy);
                }




            }

// Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
                list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }



        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

// Get a reference to the Day gridcell


            gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
//            gridcell.setOnClickListener(this);
            imgGiorno =(ImageView) row.findViewById(R.id.imgGiorno);


// ACCOUNT FOR SPACING

            Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];


// Set the Day GridCell
            gridcell.setText(theday);

            imgGiorno.setTag("img-"+theday + "-" + themonth + "-" + theyear);


            int giorno = Integer.parseInt(gridcell.getText().toString());
            int giorniInMese =  getNumberOfDaysOfMonth(currentMonthInt);
            if(giorno==giornoCiclo && giorno<= giorniInMese) {

                ++giornoCiclo;

                Calendar calendar = new GregorianCalendar(year, month-1, giorno); // Note that Month value is 0-based. e.g., 0 for January.
                int result = calendar.get(Calendar.DAY_OF_WEEK);
                Log.d(tag,"Day: "+theday + "-" + themonth + "-" + theyear);
                Log.d(tag,"Day: "+giorno + "-" + month + "-" + year);

                String dayOfWeek = "";


                int id = 0;
                switch (result) {
                    case Calendar.MONDAY:
                        Log.d(tag," it's MONDAY");
                        if(isOddMonday)
                            dayOfWeek="Mon_Odd";
                        else
                            dayOfWeek="Mon_Even";

                        isOddMonday = !isOddMonday;

                        break;
                    case Calendar.TUESDAY:
                        Log.d(tag," it's TUESDAY");
                        dayOfWeek="Tue";

                        break;
                    case Calendar.WEDNESDAY:
                        Log.d(tag," it's WEDNESDAY");
                        dayOfWeek="Wed";

                        break;
                    case Calendar.THURSDAY:
                        Log.d(tag," it's THURSDAY");
                        dayOfWeek="Thu";

                        break;
                    case Calendar.FRIDAY:
                        Log.d(tag," it's FRIDAY");
                        dayOfWeek="Fri";

                        break;
                    case Calendar.SATURDAY:
                        Log.d(tag," it's SATURDAY");
                        dayOfWeek="Sat";

                        break;
                    case Calendar.SUNDAY:
                        Log.d(tag," it's SUNDAY");
                        dayOfWeek="Sun";
                        break;


                }

                gridcell.setTag(theday + "-" + themonth + "-" + theyear+":"+dayOfWeek);
                Log.d("CALENDARIO",dayOfWeek);


                id=searchDayinDB(dayOfWeek);


                Bitmap tmp = BitmapFactory.decodeResource(getResources(), id);

                imgGiorno.setImageBitmap(tmp);


            }

            Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-" + theyear);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources().getColor(R.color.grey));
            }
            if (day_color[1].equals("BLACK")) {
                gridcell.setTextColor(getResources().getColor(R.color.black));
            }
            if (day_color[1].equals("RED")) {
                gridcell.setTextColor(getResources().getColor(R.color.red));
            }
            return row;
        }
        @Override
        public void onClick(View view) {
            String date_month_year = (String) view.getTag();
            selectedDayMonthYearButton.setText("Selected: " + date_month_year);
            Log.d("Selected date", date_month_year);
            try {
                Date parsedDate = dateFormatter.parse(date_month_year);
                Log.d(tag, "Parsed Date: " + parsedDate.toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }
    }




    public void back(View v) {

     /*Intent i = new Intent();
        i.setClass(getApplicationContext(), HomepageCittadino.class);
        startActivity(i);*/
        finish();
    }


    public void areaPersonale(View v) {
        Intent i = new Intent();
        i.putExtra("ActivityPrecedente","calendario");
        i.setClass(getApplicationContext(), AreaPersonale.class);
        startActivity(i);
    }

    @Override
    public void onDestroy() {
        Log.d(tag, "Destroying View …");
        super.onDestroy();
    }

    public void calendarioLegenda(View view) {
//        Intent i = new Intent();
//        i.putExtra("ActivityPrecedente","calendario");
//        i.setClass(getApplicationContext(), CalendarioLegenda.class);
//        startActivity(i);

        DialogFragment x = new CalendarioLegenda();
        x.show(getSupportFragmentManager(),"legenda");

    }

    public int searchDayinDB(String day){

        int id =0;
        Cursor c = db.rawQuery("SELECT giorno,tipologia FROM calendario WHERE giorno = ?", new String[]{day});
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                Log.d("CALENDARIO","giorno = "+c.getString(0)+" tipologia = "+c.getString(1));
                String tipo = c.getString(1);
                String str_id = String.format("%s_calendar",tipo);
                Log.d("CALENDARIO","str_id = "+str_id);
                id = getResources().getIdentifier(str_id, "drawable", getPackageName());
                Log.d("CALENDARIO","id = "+id);

            }
        }
        else{
            Log.d("CALENDARIO","non va");
        }

        return id;
    }








}










