package com.mkandeel.currencyconversion.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mkandeel.currencyconversion.Helper.HelperClass;
import com.mkandeel.currencyconversion.R;
import com.mkandeel.currencyconversion.ViewModel.CurrencyExchangeViewModel;
import com.mkandeel.currencyconversion.data.CurrencyModel;
import com.mkandeel.currencyconversion.databinding.FragmentCurrencyConversionBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyConversionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyConversionFragment extends Fragment {

    public CurrencyConversionFragment() {
        // Required empty public constructor
    }

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentCurrencyConversionBinding binding;
    private CurrencyExchangeViewModel currencyViewModel;
    private Observer<CurrencyModel> currencyModelObserver;
    private HelperClass helper;
    private List<String> currency;
    String from = "";
    String to = "";
    private CurrencyModel mcurrency;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyConversionBinding.inflate(inflater,container,false);
        currencyViewModel = new ViewModelProvider(this).get(CurrencyExchangeViewModel.class);
        helper = HelperClass.getInstance(this);
        currency = new ArrayList<>();
        currencyModelObserver = new Observer<CurrencyModel>() {
            @Override
            public void onChanged(CurrencyModel currencyModel) {
                currency.addAll(helper.getMapKeys(currencyModel.getRates()));
                currency.add(currencyModel.getBase());
                adapter.notifyDataSetChanged();
                mcurrency = currencyModel;
            }
        };

        getAllCurrency();

        binding.EditTxtFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                from = binding.spnFrom.getSelectedItem().toString();
                to = binding.spnTo.getSelectedItem().toString();
                if (!s.toString().isEmpty()) {
                    // if user want to convert from EUR
                    if (from.equals(mcurrency.getBase())) {
                        double txt_from = Double.parseDouble(s.toString());
                        double txt_to = txt_from * mcurrency.getRates().get(to);
                        binding.EditTxtTo.setText(String.valueOf(txt_to));
                    } // if user want to convert to EUR
                    else if (to.equals(mcurrency.getBase())){
                        double txt_from = Double.parseDouble(s.toString());
                        double txt_to = txt_from / mcurrency.getRates().get(from);
                        binding.EditTxtTo.setText(String.valueOf(txt_to));
                    } else {
                        // convert from to EUR first
                        double fromToEur =  Double.parseDouble(binding.EditTxtFrom.getText().toString()) / mcurrency.getRates().get(from);
                        // get value of Eur per selected value
                        double fromEurToAny = fromToEur * mcurrency.getRates().get(to);
                        binding.EditTxtTo.setText(String.valueOf(fromEurToAny));
                    }
                } else {
                    binding.EditTxtTo.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btnSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from = binding.spnFrom.getSelectedItem().toString();
                to = binding.spnTo.getSelectedItem().toString();

                // swap selected items from spinners
                String temp = from;
                from = to;
                to = temp;

                // set selected item of each spinner
                binding.spnFrom.setSelection(currency.indexOf(from));
                binding.spnTo.setSelection(currency.indexOf(to));
                String to_txt = binding.EditTxtTo.getText().toString();
                binding.EditTxtFrom.setText(to_txt);
            }
        });

        return binding.getRoot();
    }

    private ArrayAdapter adapter;
    private void getAllCurrency() {
        // get all currency
        currencyViewModel.getAllCurrency();
        currencyViewModel.getAllCurrencyPerEuro()
                .observe(requireActivity(),currencyModelObserver);

        // setting array adapter to spinners
        adapter = new ArrayAdapter(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,currency);
        binding.spnFrom.setAdapter(adapter);
        binding.spnTo.setAdapter(adapter);
    }
}