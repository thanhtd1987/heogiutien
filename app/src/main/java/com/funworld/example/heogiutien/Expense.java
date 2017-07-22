package com.funworld.example.heogiutien;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThanhTD on 6/25/2017.
 */

@Table(name = "Expense")
public class Expense implements Parcelable{
    @Column(name = "expense_id", unique = true)
    private int expenseId;
    @Column(name = "type")
    private String type;
    @Column (name = "related_person")
    private String relatedPerson;
    @Column(name = "money_amount")
    private int amount;
    @Column(name = "reason")
    private String reason;
    @Column(name = "time")
    private Date time;
    private String relatedExpense; // cac chi tieu lien quan, tach chi tieu...

    public Expense(int expenseId, String type, String relatedPerson, int amount, String reason, Date time, String relatedExpense) {
        this.expenseId = expenseId;
        this.type = type;
        this.relatedPerson = relatedPerson;
        this.amount = amount;
        this.reason = reason;
        this.time = time;
        this.relatedExpense = relatedExpense;
    }

    protected Expense(Parcel in) {
        expenseId = in.readInt();
        type = in.readString();
        relatedPerson = in.readString();
        amount = in.readInt();
        reason = in.readString();
        relatedExpense = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenseId);
        dest.writeString(type);
        dest.writeString(relatedPerson);
        dest.writeInt(amount);
        dest.writeString(reason);
        dest.writeString(relatedExpense);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(String relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRelatedExpense() {
        return relatedExpense;
    }

    public void setRelatedExpense(String relatedExpense) {
        this.relatedExpense = relatedExpense;
    }


    public Date setDateFromString(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setLenient(true);
        return sf.parse(date);
    }
}
