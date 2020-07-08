package com.example.shop.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.helpers.ErrorManage;
import com.example.shop.models.CreditCard;
import com.example.shop.models.User;
import com.example.shop.repositories.implementation.UserRepository;
import com.example.shop.repositories.interfaces.IUserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cards extends Fragment {
    Spinner spExpM,spExpY;
    User user;
    ListView lvCards;
    CardsArrayAdapter cardsArrayAdapter;
    Button btnAddCreditCard;
    EditText txtCardNum,txtCVV;
    IUserRepository repository;

    public Cards(User user) {
        this.user = user;
        repository = new UserRepository();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cards, container, false);
        txtCardNum = view.findViewById(R.id.txtCardNumber);
        txtCVV = view.findViewById(R.id.txtCVV);
        spExpM = view.findViewById(R.id.spExpM);
        spExpY = view.findViewById(R.id.spExpY);
        lvCards = view.findViewById(R.id.lvCards);
        cardsArrayAdapter = new CardsArrayAdapter(getContext(),0,new ArrayList<CreditCard>());
        lvCards.setAdapter(cardsArrayAdapter);
        LoadCardList();

        btnAddCreditCard = view.findViewById(R.id.btnAddCreditCard);
        btnAddCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCreditCard(txtCardNum.getText().toString(),spExpM.getSelectedItem().toString(),spExpY.getSelectedItem().toString(),txtCVV.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //LoadCardList();
    }

    private void AddCreditCard(String cardNum, String ExpM, String ExpY, String CVV){
        ErrorManage errorManage = new ErrorManage();
        if(cardNum.length() == 16){
            if(CVV.length() == 3){
                String Num = "";
                for(int i = 0;i<16;i+=4) {
                    if(i+4 <= 16)
                        Num+=cardNum.substring(i,i+4) + "-";
                }
                CreditCard creditCard = new CreditCard(Num.substring(0,Num.length()-1),ExpM,ExpY,CVV);
                repository.addCreditCard(user,creditCard,getContext().getApplicationContext());
                AddToCardList(creditCard);
                txtCardNum.setText("");
                txtCVV.setText("");
            }else {
                errorManage.AddError("CVV-ის სიგრძე არ არის სწორი");
            }
        }else{
            errorManage.AddError("ბარათის სიგრძე არ არის სწორი");
        }
        Toast.makeText(getContext(),errorManage.getErrorData(),Toast.LENGTH_LONG).show();
    }

    private void LoadCardList(){
        List<CreditCard> cardList = user.getCreditCards();
        if(!cardsArrayAdapter.isEmpty()){
            cardsArrayAdapter.clear();
        }
        cardsArrayAdapter.addAll(cardList);
    }

    private void AddToCardList(CreditCard creditCard){
        cardsArrayAdapter.add(creditCard);
    }

    private void DeleteCard(CreditCard card){
        user.DeleteCreditCard(card);
        repository.Save(user,getContext().getApplicationContext());
        LoadCardList();
    }


    class CardsArrayAdapter extends ArrayAdapter<CreditCard>{
        Context context;
        public CardsArrayAdapter(@NonNull Context context, int resource, @NonNull List<CreditCard> objects) {
            super(context, resource, objects);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final CreditCard card = getItem(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cards_list_item,parent,false);
            TextView txtCardNumber = view.findViewById(R.id.lblCardNumber);
            txtCardNumber.setText(card.getCardNumber());

            Button btnDetele = view.findViewById(R.id.btn_deleteCard);
            btnDetele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteCard(card);
                }
            });
            return view;
        }

    }
}
