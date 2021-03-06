package com.nhom10.moneymanagement_android;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.models.income;
import com.nhom10.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.nhom10.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.IncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.budgetViewModel;
import com.nhom10.moneymanagement_android.viewmodels.expenseViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class category_edit extends AppCompatActivity {

    MenuItem btnLuu;
    Button btnXoa;
    LinearLayout linearLayoutOption;

    ImageView imageViewIcon;
    EditText editTextTenNhom;
    String tennhom = "";
    int ImageResource;
    int imgViewChangeIcon;
    int flash = 0; // 1: expense, 2: income
    String stringEditText = "";

    private catincome catincome;
    private catexpense catexpense;
    private CatExpenseViewModel catExpenseViewModel;
    private CatIncomeViewModel catIncomeViewModel;
    private IncomeViewModel incomeVM;
    private expenseViewModel expenseVM;
    private List<expense> expenseList;
    private List<income> incomeList;
    private budgetViewModel budgetVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        //mapping
        btnXoa = (Button) findViewById(R.id.btnXoaCategory);
        linearLayoutOption = (LinearLayout) findViewById(R.id.lnOptionsIconGroup);
        imageViewIcon = (ImageView) findViewById(R.id.imgViewIcon);
        editTextTenNhom = (EditText) findViewById(R.id.editTextTenNhom);
        catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
        catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);

        incomeVM = new IncomeViewModel(getApplication());

        incomeVM = ViewModelProviders.of(this).get(IncomeViewModel.class);

        try {
            expenseVM = new expenseViewModel(getApplication());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        expenseVM = ViewModelProviders.of(this).get(expenseViewModel.class);

        try {
            budgetVM = new budgetViewModel(getApplication());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        budgetVM = ViewModelProviders.of(this).get(budgetVM.getClass());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chỉnh sửa nhóm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receive data intent
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        if (category.equals("expense")) {
            //b = (budget) i.getSerializableExtra("budget");
            catexpense = (catexpense) intent.getSerializableExtra("catexpense");
            flash = 1;
            //Toast.makeText(getApplicationContext(),catexpense.getName(),Toast.LENGTH_SHORT).show();
            ImageResource = catexpense.getImage();
            tennhom = catexpense.getName();


        } else {
            catincome = (catincome) intent.getSerializableExtra("catincome");
            flash = 2;
            ImageResource = catincome.getImage();
            tennhom = catincome.getName();

        }
        imageViewIcon.setImageResource(ImageResource);
        editTextTenNhom.setText(tennhom);
        editTextTenNhom.requestFocus();
        //set enable button Lưu
        imgViewChangeIcon = ImageResource;
        stringEditText = tennhom;


        linearLayoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), option_icon.class);
                startActivityForResult(intent, 1);
            }
        });

//        editTextTenNhom.setOnKeyListener(new View.OnKeyListener() {
////            @Override
////            public boolean onKey(View v, int keyCode, KeyEvent event) {
////                stringEditText = editTextTenNhom.getText().toString();
////                if ((stringEditText.equals(tennhom) && imgViewChangeIcon == ImageResource) || stringEditText.equals("")) {
////                    btnLuu.setEnabled(false);
////                } else {
////                    btnLuu.setEnabled(true);
////                }
////                return false;
////            }
////        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveCategory();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_category_add_menu, menu);
        //menu.getItem(0).setEnabled(false);
        btnLuu = (MenuItem) menu.findItem(R.id.menuSaveCategory);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSaveCategory:
                stringEditText = editTextTenNhom.getText().toString().trim();
                if (flash == 1) {

                    if (!checkCatExpenseByName(stringEditText)) {
                        break;
                    }
                    catexpense.setImage(imgViewChangeIcon);
                    catexpense.setName(stringEditText);
                    catExpenseViewModel.updateCatExpense(catexpense);
                    finish();
                } else if (flash == 2) {
                    if (!checkCatIncomeByName(stringEditText)) {
                        break;
                    }
                    catincome.setImage(imgViewChangeIcon);
                    catincome.setName(stringEditText);
                    catIncomeViewModel.updateCatIncome(catincome);
                    finish();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imgViewChangeIcon = data.getIntExtra("resrc", 1);
                imageViewIcon.setImageResource((imgViewChangeIcon));
                if ((stringEditText.equals(tennhom) && imgViewChangeIcon == ImageResource) || stringEditText.equals("")) {
                    btnLuu.setEnabled(false);
                } else {
                    btnLuu.setEnabled(true);
                }
            }
        }
    }

    private void RemoveCategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove);
        dialog.setCanceledOnTouchOutside(false);
        Button btnDongY = (Button) dialog.findViewById(R.id.btnDongy);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        TextView txtRemoveCategory = (TextView) dialog.findViewById(R.id.txtRemoveCategory);

        if (flash == 1) {
            txtRemoveCategory.setText("Bạn sẽ mất toàn bộ giao dịch trong nhóm " + catexpense.getName());
        } else if (flash == 2) {
            txtRemoveCategory.setText("Bạn sẽ mất toàn bộ giao dịch trong nhóm " + catincome.getName());
        }

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (flash == 1) {
                        budget bud = infobudget.b;
                        List<expense> lex = expenseVM.getListexpenseByCatId(catexpense.getId());
                        long tt=bud.getBmoney();
                        for (expense ex:lex){
                            tt+=ex.getNmoney();
                        }
                        bud.setBmoney(tt);

                        budgetVM.updateBudget(bud);
                        catExpenseViewModel.deleteCatExpense(catexpense);

                    } else if (flash == 2) {
                        budget bud = infobudget.b;
                        List<income> lin = incomeVM.getListIncomeByCatId(catincome.getId());
                        long tt=bud.getBmoney();
                        for (income in: lin){
                            tt-=in.getNmoney();
                        }
                        bud.setBmoney(tt);
                        budgetVM.updateBudget(bud);
                        catIncomeViewModel.deleteCatIncome(catincome);

                    }
                    dialog.cancel();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        dialog.show();
    }

    private boolean checkCatExpenseByName(String TenNhom) {
        try {
            if(TenNhom.equals("")){
                Toast.makeText(getApplicationContext(), "Nhập tên nhóm !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!catExpenseViewModel.checkCatExpenseByName(TenNhom)) {
                Toast.makeText(getApplicationContext(), "Tên nhóm đã tồn tại !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkCatIncomeByName(String TenNhom) {
        try {
            if(TenNhom.equals("")){
                Toast.makeText(getApplicationContext(), "Nhập tên nhóm !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!catIncomeViewModel.checkCatIncomeByName(TenNhom)) {
                Toast.makeText(getApplicationContext(), "Tên nhóm đã tồn tại !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}