package com.example.sqlitedemo_cz;

public class People_cz {
    public int ID = -1;
    public String Name_cz;
    public String Age_cz;
    public String Height_cz;

    public String toString_cz(){
        String result_cz = "";
        result_cz += "ID"+this.ID+",";
        result_cz += "姓名"+this.Name_cz+",";
        result_cz += "年龄"+this.Age_cz+",";
        result_cz += "身高"+this.Height_cz+",";
        return result_cz;
    }
}
