package com.nhom10.moneymanagement_android.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.nhom10.moneymanagement_android.converters.dateConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(indices = {@Index("idbudget"), @Index("idcatex")},
        foreignKeys = {
                @ForeignKey(
                        entity = budget.class,
                        parentColumns = "id",
                        childColumns = "idbudget",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = catexpense.class,
                        parentColumns = "id",
                        childColumns = "idcatex",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE)
        })
public class expense implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private long nmoney;

    @TypeConverters(dateConverters.class)
    private Date dcreated;

    private String note;

    private int idcatex;

    private int idbudget;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNmoney() {
        return nmoney;
    }

    public void setNmoney(long nmoney) {
        this.nmoney = nmoney;
    }

    public Date getDcreated() {
        return dcreated;
    }

    public void setDcreated(Date dcreated) {
        this.dcreated = dcreated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdcatex() {
        return idcatex;
    }

    public void setIdcatex(int idcatex) {
        this.idcatex = idcatex;
    }

    public int getIdbudget() {
        return idbudget;
    }

    public void setIdbudget(int idbudget) {
        this.idbudget = idbudget;
    }

    public expense(String name, long nmoney, Date dcreated, String note, int idcatex, int idbudget) {
        this.name = name;
        this.nmoney = nmoney;
        this.dcreated = dcreated;
        this.note = note;
        this.idcatex = idcatex;
        this.idbudget = idbudget;
    }

    @Ignore
    public expense() {

    }
}
