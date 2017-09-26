package com.funworld.example.heogiutien.data.dao;

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
public class Expense implements Parcelable {
    @Column(name = "expense_id", unique = true)
    private int expenseId;
    @Column(name = "type")
    private String type;
    @Column(name = "money_amount")
    private int amount;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "created_at")
    private long createAt;
    @Column(name = "updated_at")
    private long updatedAt;
    @Column(name = "related_type")
    private String relatedType;
    @Column(name = "related_person")
    private String relatedPerson;
    @Column(name = "resource_id")
    private int resourceId;
    private String relatedExpense; // cac chi tieu lien quan, tach chi tieu...

    public Expense(String type, int amount, String purpose, long createAt, String relatedType,
                   String relatedPerson, String relatedExpense, int resourceId) {
        this.type = type;
        this.amount = amount;
        this.purpose = purpose;
        this.createAt = createAt;
        this.updatedAt = createAt;
        this.relatedType = relatedType;
        this.relatedPerson = relatedPerson;
        this.relatedExpense = relatedExpense;
        this.resourceId = resourceId;
    }


    protected Expense(Parcel in) {
        expenseId = in.readInt();
        type = in.readString();
        amount = in.readInt();
        purpose = in.readString();
        createAt = in.readLong();
        updatedAt = in.readLong();
        relatedType = in.readString();
        relatedPerson = in.readString();
        relatedExpense = in.readString();
        resourceId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(expenseId);
        dest.writeString(type);
        dest.writeInt(amount);
        dest.writeString(purpose);
        dest.writeLong(createAt);
        dest.writeLong(updatedAt);
        dest.writeString(relatedType);
        dest.writeString(relatedPerson);
        dest.writeString(relatedExpense);
        dest.writeInt(resourceId);
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    public String getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(String relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getRelatedExpense() {
        return relatedExpense;
    }

    public void setRelatedExpense(String relatedExpense) {
        this.relatedExpense = relatedExpense;
    }
}
